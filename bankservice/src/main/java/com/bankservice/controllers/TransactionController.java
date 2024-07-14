package com.bankservice.controllers;

import com.bankservice.dto.TransactionStatusDto;
import com.bankservice.dto.WebhookNotification;
import com.bankservice.entities.Transaction;
import com.bankservice.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/{transactionId}/status")
    public ResponseEntity<TransactionStatusDto> getTransactionStatus(@PathVariable String transactionId) {
        TransactionStatusDto transactionStatus = transactionService.getTransactionStatus(transactionId);
        return ResponseEntity.ok(transactionStatus);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody WebhookNotification notification) {
        transactionService.updateTransactionStatus(notification.getTransactionId(), notification.getStatus());
        return ResponseEntity.ok("Webhook received");
    }

    @GetMapping("/by-account/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccount(@PathVariable String accountId) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/by-client/{clientId}")
    public ResponseEntity<List<Transaction>> getTransactionsByClient(@PathVariable String clientId) {
        List<Transaction> transactions = transactionService.getTransactionsByClientId(clientId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
}
