package com.example.easybank.loans.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Belyaev.
 * 27.06.2025
 */
@Component
public class AuditAwareImpl implements AuditorAware<String> {

  /**
   * Returns the current auditor of the application.
   *
   * @return the current auditor.
   */
  @Override
  public Optional<String> getCurrentAuditor() {
    return Optional.of("loans_ms");
  }

}
