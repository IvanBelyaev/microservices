package com.example.easybank.loans.repository;

import com.example.easybank.loans.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Belyaev.
 * 27.06.2025
 */
public interface LoanRepository extends JpaRepository<Loan, Long> {

  Optional<Loan> findByMobileNumber(String mobileNumber);

  Optional<Loan> findByLoanNumber(String loanNumber);

}
