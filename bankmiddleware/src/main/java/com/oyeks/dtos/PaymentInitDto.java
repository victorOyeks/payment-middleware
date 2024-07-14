package com.oyeks.dtos;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "PaymentInitDto")
public class PaymentInitDto {

    @XmlElement(name = "SessionID")
    private String sessionID;
    @XmlElement(name = "OriginatorInstitutionCode")
    private String originatorInstitutionCode;
    @XmlElement(name = "ChannelCode")
    private int channelCode;
    @XmlElement(name = "Pan")
    private String pan;
    @XmlElement(name = "cvv")
    private String cvv;
    @XmlElement(name = "ExpiryDate")
    private LocalDate expiryDate;
    @XmlElement(name = "Narration")
    private String narration;
    @XmlElement(name = "Amount")
    private BigDecimal amount;
}
