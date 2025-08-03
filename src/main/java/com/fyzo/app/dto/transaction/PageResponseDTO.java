package com.fyzo.app.dto.transaction;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Paginated response of transactions")
public record PageResponseDTO<T>(
		 @Schema(description = "List of transactions in this page")
		  List<TransactionResponseDTO> content,

		 @Schema(description = "Current page number (zero-based)")
		 int page,

		 @Schema(description = "Number of elements per page")
		 int size,

		 @Schema(description = "Total number of elements")
		 long totalElements,

		 @Schema(description = "Total number of pages")
		 int totalPages,

		 @Schema(description = "Whether this is the last page")
		 boolean last
) {}
