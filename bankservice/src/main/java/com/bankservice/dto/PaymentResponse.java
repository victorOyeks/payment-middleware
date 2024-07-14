package com.bankservice.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@XmlRootElement(name = "PaymentResponse")
public class PaymentResponse {

    @XmlElement(name = "SessionID")
    private String sessionID;
    @XmlElement(name = "OriginatorInstitutionCode")
    private String originatorInstitutionCode;
    @XmlElement(name = "ChannelCode")
    private int channelCode;
    @XmlElement(name = "Narration")
    private String narration;
    @XmlElement(name = "Amount")
    private BigDecimal amount;
    @XmlElement(name = "status")
    private String status;
}
