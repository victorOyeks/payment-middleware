package com.bankservice.services;

import com.bankservice.dto.ErrorResponse;
import com.bankservice.dto.PaymentResponse;
import com.bankservice.dto.TransactionStatusDto;
import com.bankservice.entities.Account;
import com.bankservice.entities.Card;
import com.bankservice.entities.Transaction;
import com.bankservice.enums.TransactionStatus;
import com.bankservice.exceptions.CustomException;
import com.bankservice.repositories.AccountRepository;
import com.bankservice.repositories.CardRepository;
import com.bankservice.repositories.TransactionRepository;
import com.bankservice.security.EncryptionDecryptService;
import com.bankservice.utils.PaymentUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final EncryptionDecryptService encryptionDecryptService;
    private final PaymentUtils paymentUtils;
    private final CardRepository cardRepository;

    public String initiateTransaction(String encryptedMessage) throws JAXBException {
        System.out.println("ENCRYPTED MESSAGE: " + encryptedMessage);

        try {
            String decryptedMessage = encryptionDecryptService.decryptMessage(encryptedMessage);
            System.out.println("DECRYPTED MESSAGE: " + decryptedMessage);

            String jsonMessage = paymentUtils.convertXmlToJson(decryptedMessage);
            System.out.println("JSON Message: " + jsonMessage);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonMessage);

            String pan = jsonNode.path("Pan").asText();
            if (pan.isEmpty()) {
                throw new CustomException("Card ID not found in JSON message");
            }

            Card card = cardRepository.findByPan(pan);
            if (card == null) {
                throw new CustomException("Customer not found");
            }

            Account account = accountRepository.findByCardId(card.getId())
                    .orElseThrow(() -> new CustomException("Account not found"));

            BigDecimal amount = new BigDecimal(jsonNode.path("Amount").asText());
            String details = jsonNode.path("Narration").asText();

            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction.setStatus(TransactionStatus.INITIATED);
            transaction.setNarration(details);
            transaction.setAccount(account);

            transactionRepository.save(transaction);

            PaymentResponse response = PaymentResponse.builder()
                    .sessionID(jsonNode.path("SessionID").asText())
                    .originatorInstitutionCode(jsonNode.path("OriginatorInstitutionCode").asText())
                    .channelCode(jsonNode.path("ChannelCode").asInt())
                    .narration(details)
                    .amount(amount)
                    .status("00")
                    .build();

            String xmlString = paymentUtils.marshalRequest(response, PaymentResponse.class);

            return encryptionDecryptService.encryptMessage(xmlString);
        } catch (CustomException | IOException | NumberFormatException e) {
            System.err.println("Error occurred during transaction initiation: " + e.getMessage());

            ErrorResponse errorResponse = ErrorResponse.builder()
                    .errorMessage(e.getMessage())
                    .status("99")
                    .build();

            String errorXmlString = paymentUtils.marshalRequest(errorResponse, ErrorResponse.class);
            return encryptionDecryptService.encryptMessage(errorXmlString);
        }
    }

    public TransactionStatusDto getTransactionStatus(String transactionId) {
        Transaction transaction =  transactionRepository.findById(transactionId).orElseThrow(() -> new CustomException("Transaction not found"));

        return TransactionStatusDto.builder()
                .transactionId(transactionId)
                .transactionStatus(transaction.getStatus())
                .build();
    }

    public Transaction updateTransactionStatus(String transactionId, TransactionStatus status) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new CustomException("Transaction not found"));
        if (transaction != null) {
            transaction.setStatus(status);
            transactionRepository.save(transaction);
        }
        return transaction;
    }

    public List<Transaction> getTransactionsByAccountId(String accountId) {
        return transactionRepository.findByAccount_Id(accountId);
    }

    public List<Transaction> getTransactionsByClientId(String clientId) {
        return transactionRepository.findByAccount_Client_Id(clientId);
    }

//    public List<Transaction> getTransactionsByCardId(String cardId) {
//        return transactionRepository.findByAccount_Cards_Id(cardId);
//    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}