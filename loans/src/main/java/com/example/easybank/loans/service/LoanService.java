package com.example.easybank.loans.service;

import com.example.easybank.loans.constatns.LoanConstants;
import com.example.easybank.loans.dto.LoanDto;
import com.example.easybank.loans.entity.Loan;
import com.example.easybank.loans.exception.LoanAlreadyExistsException;
import com.example.easybank.loans.exception.ResourceNotFoundException;
import com.example.easybank.loans.mapper.LoanMapper;
import com.example.easybank.loans.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

/**
 * Belyaev.
 * 27.06.2025
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoanService {

  private final LoanRepository loanRepository;

  @Transactional
  public void createLoan(String mobileNumber) {
    loanRepository.findByMobileNumber(mobileNumber)
        .ifPresent(loan -> {
          throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber " + mobileNumber);
        });
    loanRepository.save(createNewLoan(mobileNumber));
  }

  private Loan createNewLoan(String mobileNumber) {
    Loan newLoan = new Loan();
    long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
    newLoan.setLoanNumber(Long.toString(randomLoanNumber));
    newLoan.setMobileNumber(mobileNumber);
    newLoan.setLoanType(LoanConstants.HOME_LOAN);
    newLoan.setTotalLoan(LoanConstants.NEW_LOAN_LIMIT);
    newLoan.setAmountPaid(0);
    newLoan.setOutstandingAmount(LoanConstants.NEW_LOAN_LIMIT);
    return newLoan;
  }

  public LoanDto fetchLoan(String mobileNumber) {
    return loanRepository.findByMobileNumber(mobileNumber)
        .map(loan -> LoanMapper.mapToLoansDto(loan, new LoanDto()))
        .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
  }

  @Transactional
  public boolean updateLoan(LoanDto loansDto) {
    Loan loans = loanRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
        () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));
    LoanMapper.mapToLoans(loansDto, loans);
    loanRepository.save(loans);
    return true;
  }

  @Transactional
  public boolean deleteLoan(String mobileNumber) {
    Loan loans = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(
        () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
    );
    loanRepository.deleteById(loans.getLoanId());
    return true;
  }
}
