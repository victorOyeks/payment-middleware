package com.bankservice.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String pan;
    private String cvv;
    private LocalDateTime issued;
    private LocalDateTime expiry;
    private String embossedName;

    @OneToOne
    @JoinColumn(name = "account_id")
    @JsonBackReference
    private Account account;
}
