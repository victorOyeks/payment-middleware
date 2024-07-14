package com.bankservice.dto;

import com.bankservice.enums.AccountType;
import lombok.Data;

@Data
public class AccountRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String currency;
    private AccountType accountType;

}
