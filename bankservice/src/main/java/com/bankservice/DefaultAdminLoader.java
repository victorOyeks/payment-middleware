package com.bankservice;
import com.bankservice.entities.Account;
import com.bankservice.entities.Admin;
import com.bankservice.entities.Card;
import com.bankservice.entities.Client;
import com.bankservice.enums.AccountType;
import com.bankservice.enums.Role;
import com.bankservice.repositories.AccountRepository;
import com.bankservice.repositories.AdminRepository;
import com.bankservice.repositories.CardRepository;
import com.bankservice.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DefaultAdminLoader implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final ClientRepository clientRepository;

    @Value("${admin.username}")
    private String adminUserName;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args)  {
        createDefaultAdmin();
        createDefaultAccountAndCard();
    }

    private void createDefaultAdmin() {
        String adminEmail = "admin@email.com";
        if (!adminRepository.existsByUserName(adminEmail)) {
            Admin admin = Admin.builder()
                    .userName(adminUserName)
                    .password(passwordEncoder.encode(adminPassword))
                    .role(Role.ADMIN)
                    .build();
            adminRepository.save(admin);
        }
    }

    private void createDefaultAccountAndCard() {
        String defaultEmail = "user@domain.com";
        String defaultFirstName = "John";
        String defaultLastName = "Doe";
        String defaultAddress = "123 Main St";
        String defaultCurrency = "USD";
        AccountType defaultAccountType = AccountType.SAVINGS;

        Client client = clientRepository.findByEmail(defaultEmail).orElseGet(() -> {
            Client newClient = new Client();
            newClient.setEmail(defaultEmail);
            newClient.setFirstName(defaultFirstName);
            newClient.setLastName(defaultLastName);
            newClient.setAddress(defaultAddress);
            return clientRepository.save(newClient);
        });

        if (accountRepository.findByClient(client).isEmpty()) {
            Account account = new Account();
            account.setCurrency(defaultCurrency);
            account.setBalance(BigDecimal.ZERO);
            account.setAccountType(defaultAccountType);
            account.setClient(client);
            account = accountRepository.save(account);

            Card card = new Card();
            card.setTitle("Mr.");
            card.setPan("1234567890123456");
            card.setCvv("123");
            card.setIssued(LocalDateTime.now());
            card.setExpiry(LocalDateTime.now().plusYears(3));
            card.setEmbossedName("John Doe");
            card.setAccount(account);
            cardRepository.save(card);
        }
    }
}
