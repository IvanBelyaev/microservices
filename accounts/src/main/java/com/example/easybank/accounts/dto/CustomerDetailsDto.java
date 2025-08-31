package com.example.easybank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Belyaev.
 * 28.08.2025
 */
@Data
@Schema(
    name = "CustomerDetails",
    description = "Schema to hold Customer, Account, Cards and Loans information"
)
public class CustomerDetailsDto {
  @Schema(
      description = "Name of the customer", example = "Sergey Petrov"
  )
  @NotEmpty(message = "Name can not be a null or empty")
  @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
  private String name;

  @Schema(
      description = "Email address of the customer", example = "sergey@example.com"
  )
  @NotEmpty(message = "Email address can not be a null or empty")
  @Email(message = "Email address should be a valid value")
  private String email;

  @Schema(
      description = "Mobile Number of the customer", example = "9234567890"
  )
  @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
  private String mobileNumber;

  @Schema(description = "Account details of the Customer")
  private AccountDto account;

  @Schema(description = "Loan details of the Customer")
  private LoanDto loan;

  @Schema(description = "Card details of the Customer")
  private CardDto card;
}
