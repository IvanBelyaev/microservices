package com.example.easybank.gatewayserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Belyaev.
 * 11.09.2025
 */
@RestController
public class FallbackController  {

  @RequestMapping("/contactSupport")
  public Mono<String> contactSupport() {
    return Mono.just("An error occurred. Please try after some time or contact support team.");
  }
}
