package com.bankservice.services;

import com.bankservice.dto.AccountRequest;
import com.bankservice.dto.CardRequest;
import com.bankservice.entities.Account;
import com.bankservice.entities.Card;
import com.bankservice.entities.Client;
import com.bankservice.enums.AccountType;
import com.bankservice.exceptions.CustomException;
import com.bankservice.repositories.AccountRepository;
import com.bankservice.repositories.CardRepository;
import com.bankservice.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;

    public Account createAccount(AccountRequest accountRequest) {
        String email = accountRequest.getEmail();
        String firstName = accountRequest.getFirstName();
        String lastName = accountRequest.getLastName();
        String address = accountRequest.getAddress();
        String currency = accountRequest.getCurrency();
        AccountType accountType = accountRequest.getAccountType();

        Client client = clientRepository.findByEmail(email).orElse(null);
        if (client == null) {
            client = new Client();
            client.setEmail(email);
            client.setFirstName(firstName);
            client.setLastName(lastName);
            client.setAddress(address);
            client = clientRepository.save(client);
        }

        Account account = new Account();
        account.setCurrency(currency);
        account.setBalance(BigDecimal.ZERO);
        account.setAccountType(accountType);
        account.setClient(client);

        client.getAccounts().add(account);

        return accountRepository.save(account);
    }


    public Card createCard(String accountId, CardRequest cardRequest) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new CustomException("Account not found"));
        Card card = new Card();
        card.setTitle(cardRequest.getTitle());
        card.setPan(cardRequest.getPan());
        card.setCvv(cardRequest.getCvv());
        card.setIssued(LocalDateTime.now());
        card.setExpiry(LocalDateTime.now().plusYears(3));
        card.setEmbossedName(cardRequest.getEmbossedName());
        card.setAccount(account);
        return cardRepository.save(card);
    }

}
