package com.bankservice.enums;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ADMIN"),
    CLIENT("CLIENT");


    private final String name;

    Role(String name) {
        this.name = name;
    }
}
