package com.example.easybank.loans.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Belyaev.
 * 27.06.2025
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoanAlreadyExistsException extends RuntimeException{

  public LoanAlreadyExistsException(String message) {
    super(message);
  }
}
