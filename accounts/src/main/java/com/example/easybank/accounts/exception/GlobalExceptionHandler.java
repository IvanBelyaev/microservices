package com.example.easybank.accounts.exception;

import com.example.easybank.accounts.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Belyaev.
 * 23.06.2025
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers,
                                                                HttpStatusCode status,
                                                                WebRequest request) {
    Map<String, String> validationErrors = ex.getAllErrors().stream()
        .collect(Collectors.toMap(
            error -> ((FieldError) error).getField(),
            error -> Optional.ofNullable(error.getDefaultMessage()).orElse("")));
    return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CustomerAlreadyExistsException.class)
  public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException e,
                                                                               HttpServletRequest request) {
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(
        getApiPath(request),
        HttpStatus.BAD_REQUEST,
        e.getMessage(),
        LocalDateTime.now()
    );
    return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException e,
                                                                          HttpServletRequest request) {
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(
        getApiPath(request),
        HttpStatus.NOT_FOUND,
        e.getMessage(),
        LocalDateTime.now()
    );
    return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception e,
                                                                HttpServletRequest request) {
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(
        getApiPath(request),
        HttpStatus.INTERNAL_SERVER_ERROR,
        e.getMessage(),
        LocalDateTime.now()
    );
    return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private String getApiPath(HttpServletRequest request) {
    StringBuilder sb = new StringBuilder();
    sb.append(request.getRequestURI());
    String queryString = request.getQueryString();
    if (StringUtils.hasText(queryString)) {
      sb.append('?').append(queryString);
    }
    return sb.toString();
  }
}
