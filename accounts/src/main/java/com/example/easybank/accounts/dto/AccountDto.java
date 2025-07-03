package com.example.easybank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Belyaev.
 * 21.06.2025
 */
@Data
@Schema(
    name = "Account",
    description = "Schema to hold Account information"
)

public class AccountDto {

  @Schema(
      description = "Account Number of Easy Bank account", example = "3211549467"
  )
  @NotEmpty(message = "accountNumber can not be a null or empty")
  @Pattern(regexp = "^\\d{10}$", message = "accountNumber must be 10 digits")
  private Long accountNumber;

  @Schema(
      description = "Account type of Easy Bank account", example = "Savings"
  )
  @NotEmpty(message = "accountType can not be a null or empty")
  private String accountType;

  @Schema(
      description = "Easy Bank branch address", example = "123,Moscow"
  )
  @NotEmpty(message = "branchAddress can not be a null or empty")
  private String branchAddress;
}
