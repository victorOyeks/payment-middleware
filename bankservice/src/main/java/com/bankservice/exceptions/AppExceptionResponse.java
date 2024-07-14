package com.bankservice.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AppExceptionResponse <T>{
    private String message;
    private T data;
}