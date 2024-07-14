package com.bankservice.controllers;

import com.bankservice.dto.AccountRequest;
import com.bankservice.dto.CardRequest;
import com.bankservice.entities.Account;
import com.bankservice.entities.Card;
import com.bankservice.enums.AccountType;
import com.bankservice.services.ClientService;
import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@RequestBody AccountRequest request) {
        Account account = clientService.createAccount(request);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/accounts/{accountId}/cards")
    public ResponseEntity<Card> createCard(@PathVariable String accountId, @RequestBody CardRequest cardRequest) {
        Card card = clientService.createCard(accountId, cardRequest);
        return ResponseEntity.ok(card);
    }

}
