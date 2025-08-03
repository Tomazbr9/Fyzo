package com.fyzo.app.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fyzo.app.dto.dashboard.BalanceResponseDTO;
import com.fyzo.app.dto.dashboard.CategorySummaryDTO;
import com.fyzo.app.dto.transaction.PageResponseDTO;
import com.fyzo.app.dto.transaction.TransactionFilterDTO;
import com.fyzo.app.dto.transaction.TransactionRequestDTO;
import com.fyzo.app.dto.transaction.TransactionResponseDTO;
import com.fyzo.app.dto.transaction.TransactionUpdateDTO;
import com.fyzo.app.enums.TransactionType;
import com.fyzo.app.security.entities.UserDetailsImpl;
import com.fyzo.app.services.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Transactions Management", description = "Operations to retrieve, update and delete transactions")
@RestController
@RequestMapping("/transactions")
public class TransactionController {
	
	@Autowired
	private TransactionService service;
	
	@Operation(
		    summary = "List all user's transactions with filters and pagination",
		    description = "Retrieves a paginated list of transactions belonging to the authenticated user, filtered by provided parameters.",
		    security = @SecurityRequirement(name = "bearerAuth"),
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Transactions retrieved successfully",
		            content = @Content(
		                mediaType = "application/json",
		                schema = @Schema(implementation = PageResponseDTO.class)
		            )
		        ),
		        @ApiResponse(
		            responseCode = "401",
		            description = "Unauthorized - authentication required",
		            content = @Content
		        )
		    }
		)
		@GetMapping
		public ResponseEntity<PageResponseDTO<TransactionResponseDTO>> findAll(
		    @ModelAttribute TransactionFilterDTO dto,
		    @AuthenticationPrincipal UserDetailsImpl userDetails
		) {
		    PageResponseDTO<TransactionResponseDTO> transactions = service.findAll(dto, userDetails);
		    return ResponseEntity.ok(transactions);
		}

	
	@Operation(
		    summary = "Create a new transaction",
		    description = "Creates a new transaction for the authenticated user with the provided data",
		    security = @SecurityRequirement(name = "bearerAuth"),
		    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
		        description = "Transaction data to be created",
		        required = true,
		        content = @Content(
		            mediaType = "application/json",
		            schema = @Schema(implementation = TransactionRequestDTO.class)
		        )
		    ),
		    responses = {
		        @ApiResponse(
		            responseCode = "201",
		            description = "Transaction created successfully",
		            content = @Content(
		                mediaType = "application/json",
		                schema = @Schema(implementation = TransactionResponseDTO.class)
		            )
		        ),
		        @ApiResponse(
		            responseCode = "400",
		            description = "Invalid input data",
		            content = @Content
		        ),
		        @ApiResponse(
		            responseCode = "401",
		            description = "Unauthorized - authentication required",
		            content = @Content
		        )
		    }
		)
		@PostMapping
		public ResponseEntity<TransactionResponseDTO> create(
		    @Valid @RequestBody TransactionRequestDTO dto,
		    @AuthenticationPrincipal UserDetailsImpl userDetails
		) {
		    TransactionResponseDTO obj = service.create(dto, userDetails);
		    return ResponseEntity.status(HttpStatus.CREATED).body(obj);
		}

	
	@Operation(
		    summary = "Update an existing transaction",
		    description = "Updates a transaction by its ID for the authenticated user using the provided data",
		    security = @SecurityRequirement(name = "bearerAuth"),
		    parameters = {
		        @Parameter(
		            name = "id",
		            description = "ID of the transaction to be updated",
		            required = true,
		            in = ParameterIn.PATH,
		            schema = @Schema(type = "integer", format = "int64")
		        )
		    },
		    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
		        description = "Transaction data to update",
		        required = true,
		        content = @Content(
		            mediaType = "application/json",
		            schema = @Schema(implementation = TransactionUpdateDTO.class)
		        )
		    ),
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Transaction updated successfully",
		            content = @Content(
		                mediaType = "application/json",
		                schema = @Schema(implementation = TransactionResponseDTO.class)
		            )
		        ),
		        @ApiResponse(
		            responseCode = "400",
		            description = "Invalid input data",
		            content = @Content
		        ),
		        @ApiResponse(
		            responseCode = "401",
		            description = "Unauthorized - authentication required",
		            content = @Content
		        ),
		        @ApiResponse(
		            responseCode = "404",
		            description = "Transaction not found",
		            content = @Content
		        )
		    }
		)
		@PatchMapping("/{id}")
		public ResponseEntity<TransactionResponseDTO> update(
		    @PathVariable Long id,
		    @RequestBody @Valid TransactionUpdateDTO dto,
		    @AuthenticationPrincipal UserDetailsImpl userDetails
		) {
		    TransactionResponseDTO obj = service.update(id, dto, userDetails);
		    return ResponseEntity.ok(obj);
		}

	
	@Operation(
		    summary = "Delete a transaction",
		    description = "Deletes a transaction by its ID for the authenticated user",
		    security = @SecurityRequirement(name = "bearerAuth"),
		    parameters = {
		        @Parameter(
		            name = "id",
		            description = "ID of the transaction to be deleted",
		            required = true,
		            in = ParameterIn.PATH,
		            schema = @Schema(type = "integer", format = "int64")
		        )
		    },
		    responses = {
		        @ApiResponse(
		            responseCode = "204",
		            description = "Transaction deleted successfully"
		        ),
		        @ApiResponse(
		            responseCode = "401",
		            description = "Unauthorized - authentication required",
		            content = @Content
		        ),
		        @ApiResponse(
		            responseCode = "403",
		            description = "Forbidden - user does not have permission",
		            content = @Content
		        ),
		        @ApiResponse(
		            responseCode = "404",
		            description = "Transaction not found",
		            content = @Content
		        )
		    }
		)
		@DeleteMapping("/{id}")
		public ResponseEntity<Void> delete(
		    @PathVariable Long id,
		    @AuthenticationPrincipal UserDetailsImpl userDetails
		) {
		    service.delete(id, userDetails);
		    return ResponseEntity.noContent().build();
		}
	
	

	@Operation(
		    summary = "Get user balance",
		    description = "Retrieves the total balance for the authenticated user",
		    security = @SecurityRequirement(name = "bearerAuth"),
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Balance retrieved successfully",
		            content = @Content(
		                mediaType = "application/json",
		                schema = @Schema(implementation = BalanceResponseDTO.class)
		            )
		        ),
		        @ApiResponse(
		            responseCode = "401",
		            description = "Unauthorized - authentication required",
		            content = @Content
		        )
		    }
	)
	@GetMapping("/balance")
	public ResponseEntity<BalanceResponseDTO> getBalance(@AuthenticationPrincipal UserDetailsImpl userDetails){
		BalanceResponseDTO obj = service.getUserBalance(userDetails);
		return ResponseEntity.ok(obj);
	}
	
	@Operation(
		    summary = "Get expense summary by category",
		    description = "Retrieves a summary of expenses grouped by category for the authenticated user",
		    security = @SecurityRequirement(name = "bearerAuth"),
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Expense summary retrieved successfully",
		            content = @Content(
		                mediaType = "application/json",
		                array = @ArraySchema(schema = @Schema(implementation = CategorySummaryDTO.class))
		            )
		        ),
		        @ApiResponse(
		            responseCode = "401",
		            description = "Unauthorized - authentication required",
		            content = @Content
		        )
		    }
	)
	@GetMapping("/summary/expense")
	public ResponseEntity<List<CategorySummaryDTO>> getExpenseByCategory(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		List<CategorySummaryDTO> result = service.getSummaryByType(userDetails, TransactionType.EXPENSE);
		return ResponseEntity.ok(result);
	}
	
	@Operation(
		    summary = "Get revenue summary by category",
		    description = "Retrieves a summary of revenues grouped by category for the authenticated user",
		    security = @SecurityRequirement(name = "bearerAuth"),
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Revenue summary retrieved successfully",
		            content = @Content(
		                mediaType = "application/json",
		                array = @ArraySchema(schema = @Schema(implementation = CategorySummaryDTO.class))
		            )
		        ),
		        @ApiResponse(
		            responseCode = "401",
		            description = "Unauthorized - authentication required",
		            content = @Content
		        )
		    }
	)
	@GetMapping("/summary/revenue")
	public ResponseEntity<List<CategorySummaryDTO>> getRevenueByCategory(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		List<CategorySummaryDTO> result = service.getSummaryByType(userDetails, TransactionType.REVENUE);
		return ResponseEntity.ok(result);
	}
	
}
