package com.bankservice.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "ErrorResponse")
public class ErrorResponse {

    @XmlElement(name = "ErrorMessage")
    private String errorMessage;

    @XmlElement(name = "Status")
    private String status;

}
