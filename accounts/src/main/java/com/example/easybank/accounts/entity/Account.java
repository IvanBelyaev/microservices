package com.example.easybank.accounts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

/**
 * Belyaev.
 * 21.06.2025
 */
@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Account extends BaseEntity {

  @Id
  private Long accountNumber;

  private String accountType;

  private String branchAddress;

  private Integer customerId;
}
