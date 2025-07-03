package com.example.easybank.accounts.repository;

import com.example.easybank.accounts.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Belyaev.
 * 21.06.2025
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

  Optional<Account> findByCustomerId(Integer customerId);

  void deleteByCustomerId(Integer customerId);
}
