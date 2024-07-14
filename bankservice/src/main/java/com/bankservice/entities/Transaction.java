package com.bankservice.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bankservice.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String description;
    private BigDecimal amount;
    @CreationTimestamp
    private LocalDateTime initiatedDateTime;

    @CreationTimestamp
    private LocalDateTime updatedDateTime;

    private TransactionStatus status;
    private String narration;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonBackReference
    private Account account;
}
