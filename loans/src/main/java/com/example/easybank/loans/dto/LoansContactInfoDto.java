package com.example.easybank.loans.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * Belyaev.
 * 05.07.2025
 */
@ConfigurationProperties(prefix = "loans")
@Getter @Setter
public class LoansContactInfoDto {

  private String message;
  private Map<String, String> contactDetails;
  private List<String> onCallSupport;
}
