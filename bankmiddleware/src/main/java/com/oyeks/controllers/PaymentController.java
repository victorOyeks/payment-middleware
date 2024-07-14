package com.oyeks.controllers;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.oyeks.dtos.PaymentRequest;
import com.oyeks.services.PaymentService;
import jakarta.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping(value = "initiate-payment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> initiatePayment(@RequestBody PaymentRequest request) throws JAXBException, UnirestException {
        String response = paymentService.initializePayment(request);
        return ResponseEntity.ok(response);
    }
}