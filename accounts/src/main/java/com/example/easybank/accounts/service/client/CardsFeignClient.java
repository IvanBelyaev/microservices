package com.example.easybank.accounts.service.client;

import com.example.easybank.accounts.dto.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Belyaev.
 * 28.08.2025
 */
@FeignClient(name = "cards", fallback = CardsFallback.class)
public interface CardsFeignClient {

  @GetMapping(value = "/api/v1/fetch", consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<CardDto> fetchCardDetails(@RequestHeader("easyBank-correlation-id") String correlationId,
                                           @RequestParam String mobileNumber);
}
