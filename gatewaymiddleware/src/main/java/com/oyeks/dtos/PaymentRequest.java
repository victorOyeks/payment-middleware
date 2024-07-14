package com.oyeks.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentRequest {
    private String pan;
    private String cvv;
    private LocalDate expiryDate;
    private String narration;
    private BigDecimal amount;
}
