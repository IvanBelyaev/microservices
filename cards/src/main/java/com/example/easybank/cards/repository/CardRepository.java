package com.example.easybank.cards.repository;

import com.example.easybank.cards.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Belyaev.
 * 27.06.2025
 */
public interface CardRepository extends JpaRepository<Card, Integer> {

  Optional<Card> findByMobileNumber(String mobileNumber);

  Optional<Card> findByCardNumber(String cardNumber);
}
