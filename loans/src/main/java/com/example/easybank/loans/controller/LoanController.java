package com.example.easybank.loans.controller;

import com.example.easybank.loans.constatns.LoanConstants;
import com.example.easybank.loans.dto.ErrorResponseDto;
import com.example.easybank.loans.dto.LoanDto;
import com.example.easybank.loans.dto.LoansContactInfoDto;
import com.example.easybank.loans.dto.ResponseDto;
import com.example.easybank.loans.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Belyaev.
 * 27.06.2025
 */
@Tag(
    name = "CRUD REST APIs for Loans in EasyBank",
    description = "CRUD REST APIs in EasyBank to CREATE, UPDATE, FETCH AND DELETE loan details"
)
@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class LoanController {

  @Value("${build.version}")
  private String buildVersion;

  private final Environment environment;

  private final LoansContactInfoDto loansContactInfoDto;

  private final LoanService loanService;

  @Operation(
      summary = "Create Loan REST API",
      description = "REST API to create new loan inside EasyBank"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "201",
          description = "HTTP Status CREATED"
      ),
      @ApiResponse(
          responseCode = "400",
          description = "HTTP Status BAD REQUEST",
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
  }
  )
  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createLoan(
      @Validated @RequestParam
      @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
      String mobileNumber
  ) {
    loanService.createLoan(mobileNumber);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(new ResponseDto(LoanConstants.STATUS_201, LoanConstants.MESSAGE_201));
  }

  @Operation(
      summary = "Fetch Loan Details REST API",
      description = "REST API to fetch loan details based on a mobile number"
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
  @GetMapping("/fetch")
  public ResponseEntity<LoanDto> fetchLoanDetails(
      @Validated @RequestParam
      @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
      String mobileNumber
  ) {
    LoanDto loansDto = loanService.fetchLoan(mobileNumber);
    return ResponseEntity.status(HttpStatus.OK).body(loansDto);
  }

  @Operation(
      summary = "Update Loan Details REST API",
      description = "REST API to update loan details based on a loan number"
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
          responseCode = "417",
          description = "Expectation Failed"
      ),
      @ApiResponse(
          responseCode = "500",
          description = "HTTP Status Internal Server Error",
          content = @Content(
              schema = @Schema(implementation = ErrorResponseDto.class)
          )
      )
  })
  @PutMapping("/update")
  public ResponseEntity<ResponseDto> updateLoanDetails(@Validated @RequestBody LoanDto loanDto) {
    boolean isUpdated = loanService.updateLoan(loanDto);
    if (isUpdated) {
      return ResponseEntity
          .status(HttpStatus.OK)
          .body(new ResponseDto(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200));
    } else {
      return ResponseEntity
          .status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseDto(LoanConstants.STATUS_417, LoanConstants.MESSAGE_417_UPDATE));
    }
  }

  @Operation(
      summary = "Delete Loan Details REST API",
      description = "REST API to delete Loan details based on a mobile number"
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
          responseCode = "417",
          description = "Expectation Failed"
      ),
      @ApiResponse(
          responseCode = "500",
          description = "HTTP Status Internal Server Error",
          content = @Content(
              schema = @Schema(implementation = ErrorResponseDto.class)
          )
      )
  })
  @DeleteMapping("/delete")
  public ResponseEntity<ResponseDto> deleteLoanDetails(
      @Validated @RequestParam
      @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
      String mobileNumber
  ) {
    boolean isDeleted = loanService.deleteLoan(mobileNumber);
    if (isDeleted) {
      return ResponseEntity
          .status(HttpStatus.OK)
          .body(new ResponseDto(LoanConstants.STATUS_200, LoanConstants.MESSAGE_200));
    } else {
      return ResponseEntity
          .status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseDto(LoanConstants.STATUS_417, LoanConstants.MESSAGE_417_DELETE));
    }
  }

  @Operation(
      summary = "Get Build information",
      description = "Get Build information that is deployed into loans microservice"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "HTTP Status OK"
      ),
      @ApiResponse(
          responseCode = "500",
          description = "HTTP Status Internal Server Error",
          content = @Content(
              schema = @Schema(implementation = ErrorResponseDto.class)
          )
      )
  }
  )
  @GetMapping("/build-info")
  public ResponseEntity<String> getBuildInfo() {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(buildVersion);
  }

  @Operation(
      summary = "Get Java version",
      description = "Get Java versions details that is installed into loans microservice"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "HTTP Status OK"
      ),
      @ApiResponse(
          responseCode = "500",
          description = "HTTP Status Internal Server Error",
          content = @Content(
              schema = @Schema(implementation = ErrorResponseDto.class)
          )
      )
  }
  )
  @GetMapping("/java-version")
  public ResponseEntity<String> getJavaVersion() {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(environment.getProperty("JAVA_HOME"));
  }

  @Operation(
      summary = "Get Contact Info",
      description = "Contact Info details that can be reached out in case of any issues"
  )
  @ApiResponses({
      @ApiResponse(
          responseCode = "200",
          description = "HTTP Status OK"
      ),
      @ApiResponse(
          responseCode = "500",
          description = "HTTP Status Internal Server Error",
          content = @Content(
              schema = @Schema(implementation = ErrorResponseDto.class)
          )
      )
  }
  )
  @GetMapping("/contact-info")
  public ResponseEntity<LoansContactInfoDto> getContactInfo() {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(loansContactInfoDto);
  }

}
