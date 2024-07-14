package com.bankservice.repositories;

import com.bankservice.entities.Account;
import com.bankservice.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByCardId(String cardId);

    Collection<Object> findByClient(Client client);
}
