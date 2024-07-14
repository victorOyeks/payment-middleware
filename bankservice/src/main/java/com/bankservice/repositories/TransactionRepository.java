package com.bankservice.repositories;

import com.bankservice.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findByAccount_Id(String accountId);
    List<Transaction> findByAccount_Client_Id(String clientId);
//    List<Transaction> findBy(String cardId);
}
