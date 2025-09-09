package com.example.easybank.cards.controller;

import com.example.easybank.cards.constatnts.CardConstants;
import com.example.easybank.cards.dto.CardDto;
import com.example.easybank.cards.dto.CardsContactInfoDto;
import com.example.easybank.cards.dto.ErrorResponseDto;
import com.example.easybank.cards.dto.ResponseDto;
import com.example.easybank.cards.service.CardService;
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
    name = "CRUD REST APIs for Cards in EasyBank",
    description = "CRUD REST APIs in EasyBank to CREATE, UPDATE, FETCH AND DELETE card details"
)
@RestController
@RequestMapping(path ="/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@RequiredArgsConstructor
public class CardController {

  private static final Logger logger = LoggerFactory.getLogger(CardController.class);

  private final CardService cardService;

  @Value("${build.version}")
  private String buildVersion;

  private final Environment environment;

  private final CardsContactInfoDto cardsContactInfoDto;

  @Operation(
      summary = "Create Card REST API",
      description = "REST API to create new Card inside EazyBank"
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
  public ResponseEntity<ResponseDto> createCard(
      @Validated @RequestParam @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
      String mobileNumber
  ) {
    cardService.createCard(mobileNumber);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(new ResponseDto(CardConstants.STATUS_201, CardConstants.MESSAGE_201));
  }


  @Operation(
      summary = "Fetch Card Details REST API",
      description = "REST API to fetch card details based on a mobile number"
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
  public ResponseEntity<CardDto> fetchCardDetails(
      @RequestHeader("easyBank-correlation-id") String correlationId,
      @Validated @RequestParam @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
      String mobileNumber
  ) {
    logger.debug("easyBank-correlation-id found: {}", correlationId);
    CardDto cardDto = cardService.fetchCard(mobileNumber);
    return ResponseEntity.ok(cardDto);
  }

  @Operation(
      summary = "Update Card Details REST API",
      description = "REST API to update card details based on a card number"
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
  public ResponseEntity<ResponseDto> updateCardDetails(@Validated @RequestBody CardDto cardsDto) {
    boolean isUpdated = cardService.updateCard(cardsDto);
    if (isUpdated) {
      return ResponseEntity
          .status(HttpStatus.OK)
          .body(new ResponseDto(CardConstants.STATUS_200, CardConstants.MESSAGE_200));
    } else {
      return ResponseEntity
          .status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseDto(CardConstants.STATUS_417, CardConstants.MESSAGE_417_UPDATE));
    }
  }


  @Operation(
      summary = "Delete Card Details REST API",
      description = "REST API to delete Card details based on a mobile number"
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
  public ResponseEntity<ResponseDto> deleteCardDetails(
      @Validated @RequestParam
      @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
      String mobileNumber
  ) {
    boolean isDeleted = cardService.deleteCard(mobileNumber);
    if (isDeleted) {
      return ResponseEntity
          .status(HttpStatus.OK)
          .body(new ResponseDto(CardConstants.STATUS_200, CardConstants.MESSAGE_200));
    } else {
      return ResponseEntity
          .status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseDto(CardConstants.STATUS_417, CardConstants.MESSAGE_417_DELETE));
    }
  }

  @Operation(
      summary = "Get Build information",
      description = "Get Build information that is deployed into cards microservice"
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
      description = "Get Java versions details that is installed into cards microservice"
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
  public ResponseEntity<CardsContactInfoDto> getContactInfo() {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(cardsContactInfoDto);
  }
}
