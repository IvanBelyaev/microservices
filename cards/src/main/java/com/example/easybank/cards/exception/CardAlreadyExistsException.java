package com.example.easybank.cards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Belyaev.
 * 27.06.2025
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CardAlreadyExistsException extends RuntimeException {

  public CardAlreadyExistsException(String message) {
    super(message);
  }
}
