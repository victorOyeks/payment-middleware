package com.bankservice.dto;


import com.bankservice.enums.TransactionStatus;
import lombok.Data;

@Data
public class WebhookNotification {
    private String transactionId;
    private TransactionStatus status;

}
