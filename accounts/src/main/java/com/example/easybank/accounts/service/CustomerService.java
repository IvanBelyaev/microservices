package com.example.easybank.accounts.service;

import com.example.easybank.accounts.dto.*;
import com.example.easybank.accounts.entity.Account;
import com.example.easybank.accounts.entity.Customer;
import com.example.easybank.accounts.exception.ResourceNotFoundException;
import com.example.easybank.accounts.mapper.AccountMapper;
import com.example.easybank.accounts.mapper.CustomerMapper;
import com.example.easybank.accounts.repository.AccountRepository;
import com.example.easybank.accounts.repository.CustomerRepository;
import com.example.easybank.accounts.service.client.CardsFeignClient;
import com.example.easybank.accounts.service.client.LoansFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Belyaev.
 * 28.08.2025
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

  private final AccountRepository accountRepository;
  private final CustomerRepository customerRepository;
  private final CardsFeignClient cardsFeignClient;
  private final LoansFeignClient loansFeignClient;

  public CustomerDetailsDto fetchCustomerDetails(String correlationId, String mobileNumber) {
    Customer customer = customerRepository.findByMobileNumber(mobileNumber)
        .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
    Account account = accountRepository.findByCustomerId(customer.getCustomerId())
        .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", mobileNumber));

    CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
    customerDetailsDto.setAccount(AccountMapper.mapToAccountDto(account, new AccountDto()));

    ResponseEntity<LoanDto> loanDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
    if (loanDtoResponseEntity != null) {
      customerDetailsDto.setLoan(loanDtoResponseEntity.getBody());
    }

    ResponseEntity<CardDto> cardDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
    if (cardDtoResponseEntity != null) {
      customerDetailsDto.setCard(cardDtoResponseEntity.getBody());
    }

    return customerDetailsDto;
  }
}
