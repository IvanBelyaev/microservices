package com.example.easybank.gatewayserver.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Belyaev.
 * 08.09.2025
 */
@Component
public class FilterUtility {

  public static final String CORRELATION_ID = "easyBank-correlation-id";

  public Optional<String> getCorrelationId(HttpHeaders requestHeaders) {
    return Optional.ofNullable(requestHeaders.get(CORRELATION_ID))
        .map(List::getFirst);
  }

  public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
    return exchange.mutate().request(exchange.getRequest().mutate().header(name, value).build()).build();
  }

  public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
    return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
  }

  public boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
    return getCorrelationId(requestHeaders).isPresent();
  }

  public String generateCorrelationId() {
    return UUID.randomUUID().toString();
  }
}
