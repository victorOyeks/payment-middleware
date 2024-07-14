package com.bankservice.repositories;

import com.bankservice.entities.Card;
import com.bankservice.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CardRepository extends JpaRepository<Card, String> {
    Card findByPan(String pan);
}

