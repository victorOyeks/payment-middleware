package com.bankservice.dto;

import lombok.Data;

@Data
public class CardRequest {
    private String accountId;
    private String title;
    private String pan;
    private String cvv;
    private String embossedName;

}
