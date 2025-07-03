package com.example.easybank.accounts.controller;

import com.example.easybank.accounts.constants.AccountConstants;
import com.example.easybank.accounts.dto.CustomerDto;
import com.example.easybank.accounts.dto.ErrorResponseDto;
import com.example.easybank.accounts.dto.ResponseDto;
import com.example.easybank.accounts.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.example.easybank.accounts.constants.AccountConstants.MESSAGE_201;
import static com.example.easybank.accounts.constants.AccountConstants.STATUS_201;

/**
 * Belyaev.
 * 21.06.2025
 */
@Tag(
    name = "CRUD REST APIs for Accounts in EasyBank",
    description = "CRUD REST APIs in EasyBank to CREATE, UPDATE, FETCH AND DELETE accounts details"
)
@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class AccountController {

  private final AccountService accountService;

  @Operation(
      summary = "Create Account REST API",
      description = "REST API to create new Customer & Account inside EasyBank"
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
  })
  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createAccount(@Validated @RequestBody CustomerDto customerDto) {
    accountService.createAccount(customerDto);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(new ResponseDto(STATUS_201, MESSAGE_201));
  }


  @Operation(
      summary = "Fetch Account Details REST API",
      description = "REST API to fetch Customer & Account details based on a mobile number"
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
  public ResponseEntity<CustomerDto> fetchAccountDetails(
      @Validated
      @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
      @RequestParam String mobileNumber
  ) {
    CustomerDto customerDto = accountService.fetchAccount(mobileNumber);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(customerDto);
  }


  @Operation(
      summary = "Update Account Details REST API",
      description = "REST API to update Customer & Account details based on a account number"
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
  public ResponseEntity<ResponseDto> updateAccountDetails(@Validated @RequestBody CustomerDto customerDto) {
    boolean isUpdated = accountService.updateAccount(customerDto);
    if (isUpdated) {
      return ResponseEntity
          .ok(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
    } else {
      return ResponseEntity
          .internalServerError()
          .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_UPDATE));
    }
  }


  @Operation(
      summary = "Delete Account & Customer Details REST API",
      description = "REST API to delete Customer & Account details based on a mobile number"
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
  public ResponseEntity<ResponseDto> deleteAccountDetails(
      @Validated
      @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
      @RequestParam String mobileNumber
  ) {
    boolean isDeleted = accountService.deleteAccount(mobileNumber);
    if (isDeleted) {
      return ResponseEntity
          .ok(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
    } else {
      return ResponseEntity
          .internalServerError()
          .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_DELETE));
    }
  }
}
