package com.example.easybank.cards.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

/**
 * Belyaev.
 * 27.06.2025
 */
@Entity
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Card extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer cardId;

  private String mobileNumber;

  private String cardNumber;

  private String cardType;

  private int totalLimit;

  private int amountUsed;

  private int availableAmount;
}
