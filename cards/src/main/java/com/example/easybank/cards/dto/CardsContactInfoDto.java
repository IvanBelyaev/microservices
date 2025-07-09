package com.example.easybank.cards.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * Belyaev.
 * 05.07.2025
 */
@ConfigurationProperties(prefix = "cards")
public record CardsContactInfoDto(String message,
                                  Map<String, String> contactDetails,
                                  List<String> onCallSupport) {
}
