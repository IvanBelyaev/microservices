package com.example.easybank.accounts.service.client;

import com.example.easybank.accounts.dto.CardDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Belyaev.
 * 11.09.2025
 */
@Component
public class CardsFallback implements CardsFeignClient {

  @Override
  public ResponseEntity<CardDto> fetchCardDetails(String correlationId, String mobileNumber) {
    return null;
  }
}
