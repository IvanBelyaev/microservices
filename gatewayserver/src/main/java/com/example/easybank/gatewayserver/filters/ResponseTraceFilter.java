package com.example.easybank.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import static com.example.easybank.gatewayserver.filters.FilterUtility.CORRELATION_ID;

/**
 * Belyaev.
 * 08.09.2025
 */
@Configuration
public class ResponseTraceFilter {

  private final Logger logger = LoggerFactory.getLogger(ResponseTraceFilter.class);

  private final FilterUtility filterUtility;

  @Autowired
  public ResponseTraceFilter(FilterUtility filterUtility) {
    this.filterUtility = filterUtility;
  }

  @Bean
  public GlobalFilter postGlobalFilter() {
    return (exchange, chain) ->
      chain.filter(exchange).then(Mono.fromRunnable(() -> {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
        String correlationId = filterUtility.getCorrelationId(requestHeaders)
                .orElseThrow(() -> new RuntimeException(String.format("%s not found in request headers", CORRELATION_ID)));
        logger.debug("Updated the correlation id to the outbound headers: {}", correlationId);
        exchange.getResponse().getHeaders().add(CORRELATION_ID, correlationId);
      }));
  }
}
