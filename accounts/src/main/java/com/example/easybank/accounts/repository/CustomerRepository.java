package com.example.easybank.accounts.repository;

import com.example.easybank.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Belyaev.
 * 21.06.2025
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

   Optional<Customer> findByMobileNumber(String mobileNumber);
}
