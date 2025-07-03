package com.example.easybank.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Belyaev.
 * 21.06.2025
 */
@Data
@AllArgsConstructor
@Schema(
    name = "Response",
    description = "Schema to hold successful response information"
)
public class ResponseDto {

  @Schema(
      description = "Status code in the response"
  )
  private String statusCode;

  @Schema(
      description = "Status message in the response"
  )
  private String statusMsg;
}
