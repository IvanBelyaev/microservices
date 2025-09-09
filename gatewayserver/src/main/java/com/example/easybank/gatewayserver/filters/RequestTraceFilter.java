package com.example.easybank.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Belyaev.
 * 08.09.2025
 */
@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {

  private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

  private final FilterUtility filterUtility;

  @Autowired
  public RequestTraceFilter(FilterUtility filterUtility) {
    this.filterUtility = filterUtility;
  }


  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
    if (filterUtility.isCorrelationIdPresent(requestHeaders)) {
      logger.debug("easyBank-correlation-id found in RequestTraceFilter : {}",
          filterUtility.getCorrelationId(requestHeaders));
    } else {
      String correlationID = filterUtility.generateCorrelationId();
      exchange = filterUtility.setCorrelationId(exchange, correlationID);
      logger.debug("eazyBank-correlation-id generated in RequestTraceFilter : {}", correlationID);
    }
    return chain.filter(exchange);
  }

}
