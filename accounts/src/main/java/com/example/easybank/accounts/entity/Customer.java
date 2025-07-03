package com.example.easybank.accounts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Belyaev.
 * 21.06.2025
 */
@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Customer extends BaseEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Integer customerId;

  private String name;

  private String email;

  private String mobileNumber;
}
