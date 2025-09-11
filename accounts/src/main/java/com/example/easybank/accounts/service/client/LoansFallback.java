package com.example.easybank.accounts.service.client;

import com.example.easybank.accounts.dto.LoanDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Belyaev.
 * 11.09.2025
 */
@Component
public class LoansFallback implements LoansFeignClient {

  @Override
  public ResponseEntity<LoanDto> fetchLoanDetails(String correlationId, String mobileNumber) {
    return null;
  }
}
