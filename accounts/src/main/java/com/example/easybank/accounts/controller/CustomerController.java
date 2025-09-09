package com.example.easybank.accounts.controller;

import com.example.easybank.accounts.dto.CustomerDetailsDto;
import com.example.easybank.accounts.dto.ErrorResponseDto;
import com.example.easybank.accounts.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Belyaev.
 * 28.08.2025
 */
@Tag(
    name = "REST APIs for Customers in EasyBank",
    description = "REST APIs in EasyBank to FETCH customer details"
)
@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class CustomerController {

  private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

  private final CustomerService customerService;

  @Operation(
      summary = "Fetch Customer Details REST API",
      description = "REST API to fetch Customer details based on a mobile number"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "HTTP Status OK"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "HTTP Status NOT FOUND",
          content = @Content(
              schema = @Schema(implementation = ErrorResponseDto.class)
          )
      ),
      @ApiResponse(
          responseCode = "500",
          description = "HTTP Status Internal Server Error",
          content = @Content(
              schema = @Schema(implementation = ErrorResponseDto.class)
          )
      )
  })
  @GetMapping("/fetchCustomerDetails")
  public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(
      @RequestHeader("easyBank-correlation-id") String correlationId,
      @Validated @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
      @RequestParam String mobileNumber) {
    logger.debug("easyBank-correlation-id found: {}", correlationId);
    CustomerDetailsDto customerDetailsDto = customerService.fetchCustomerDetails(correlationId, mobileNumber);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(customerDetailsDto);
  }
}
