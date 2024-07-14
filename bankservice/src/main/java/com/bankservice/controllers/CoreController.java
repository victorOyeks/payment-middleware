package com.bankservice.controllers;

import com.bankservice.entities.Transaction;
import com.bankservice.services.TransactionService;
import jakarta.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/core")
@RequiredArgsConstructor
public class CoreController {

    private final TransactionService transactionService;

    @PostMapping(value = "/initiate", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> initiateTransaction(@RequestBody String encryptedMessage) throws JAXBException {
        String response = transactionService.initiateTransaction(encryptedMessage);
        return ResponseEntity.ok(response);
    }
}
