package com.oyeks.services;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.oyeks.dtos.PaymentInitDto;
import com.oyeks.dtos.PaymentRequest;
import com.oyeks.utils.EncryptionDecryptService;
import com.oyeks.utils.PaymentUtils;
import jakarta.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentUtils paymentUtils;
    private final EncryptionDecryptService encryptionDecryptService;

    @Value("${app.institution.code}")
    private String institutionCode;

    @Value("${app.channel.code}")
    private int channelCode;

    public String initializePayment(PaymentRequest request) throws JAXBException, UnirestException {

        System.out.println("REQUEST: " + request);
        PaymentInitDto initDto = PaymentInitDto.builder()
                .sessionID(generateSessionId())
                .originatorInstitutionCode(institutionCode)
                .channelCode(channelCode)
                .pan(request.getPan())
                .cvv(request.getCvv())
                .expiryDate(request.getExpiryDate())
                .narration(request.getNarration())
                .amount(request.getAmount())
                .build();

        System.out.println("DTO: " + initDto);

        String xmlRequest = paymentUtils.marshalRequest(initDto, PaymentInitDto.class);

        System.out.println("XML REQUEST: " + xmlRequest);
        String encryptedRequest = encryptionDecryptService.encryptMessage(xmlRequest);

        System.out.println("ENCRYPTED REQUEST: " + encryptedRequest);
        String encryptedResponse = paymentUtils.contactBankService(encryptedRequest);
        System.out.println("ENCRYPTED RESPONSE: " + encryptedResponse);
        String xmlResponse = encryptionDecryptService.decryptMessage(encryptedResponse);

        System.out.println("XML RESPONSE: " + xmlResponse);

        return paymentUtils.convertXmlToJson(xmlResponse);
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }
}
