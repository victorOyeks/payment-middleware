package com.bankservice.dto;

import com.bankservice.enums.TransactionStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionStatusDto {
    private String transactionId;
    private TransactionStatus transactionStatus;
}
