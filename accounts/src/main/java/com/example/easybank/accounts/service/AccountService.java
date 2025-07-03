package com.example.easybank.accounts.service;

import com.example.easybank.accounts.constants.AccountConstants;
import com.example.easybank.accounts.dto.AccountDto;
import com.example.easybank.accounts.dto.CustomerDto;
import com.example.easybank.accounts.entity.Account;
import com.example.easybank.accounts.entity.Customer;
import com.example.easybank.accounts.exception.CustomerAlreadyExistsException;
import com.example.easybank.accounts.exception.ResourceNotFoundException;
import com.example.easybank.accounts.mapper.AccountMapper;
import com.example.easybank.accounts.mapper.CustomerMapper;
import com.example.easybank.accounts.repository.AccountRepository;
import com.example.easybank.accounts.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

/**
 * Belyaev.
 * 23.06.2025
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

  private final AccountRepository accountRepository;
  private final CustomerRepository customerRepository;

  @Transactional
  public void createAccount(CustomerDto customerDto) {
    customerRepository.findByMobileNumber(customerDto.getMobileNumber())
        .ifPresent(findedCustomer -> {
          throw new CustomerAlreadyExistsException("Customer already registered with given phoneNumber " + findedCustomer.getMobileNumber());
        });
    var customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
    var savedCustomer = customerRepository.save(customer);
    accountRepository.save(createNewAccount(savedCustomer));
  }

  private Account createNewAccount(Customer customer) {
    var newAccount = new Account();
    newAccount.setCustomerId(customer.getCustomerId());
    long randomAccNumber = 1_000_000_000L + new Random().nextInt(900_000_000);
    newAccount.setAccountNumber(randomAccNumber);
    newAccount.setAccountType(AccountConstants.SAVINGS);
    newAccount.setBranchAddress(AccountConstants.ADDRESS);
    return newAccount;
  }

  public CustomerDto fetchAccount(String mobileNumber) {
    Customer customer = customerRepository.findByMobileNumber(mobileNumber)
        .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
    Account account = accountRepository.findByCustomerId(customer.getCustomerId())
        .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", mobileNumber));
    CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
    customerDto.setAccount(AccountMapper.mapToAccountDto(account, new AccountDto()));
    return customerDto;
  }

  @Transactional
  public boolean updateAccount(CustomerDto customerDto) {
    AccountDto accountDto = customerDto.getAccount();
    if (accountDto == null) {
      return false;
    }

    Integer customerId = accountRepository.findById(accountDto.getAccountNumber())
        .map(findedAccount -> AccountMapper.mapToAccount(accountDto, findedAccount))
        .map(accountRepository::save)
        .orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber", accountDto.getAccountNumber().toString()))
        .getCustomerId();

    customerRepository.findById(customerId)
        .map(findedCustomer -> CustomerMapper.mapToCustomer(customerDto, findedCustomer))
        .map(customerRepository::save)
        .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerid", customerId.toString()));
    return true;
  }

  @Transactional
  public boolean deleteAccount(String mobileNumber) {
    Customer customer = customerRepository.findByMobileNumber(mobileNumber)
        .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
    accountRepository.deleteByCustomerId(customer.getCustomerId());
    customerRepository.delete(customer);
    return true;
  }
}
