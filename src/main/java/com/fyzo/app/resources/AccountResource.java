package com.fyzo.app.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fyzo.app.dto.account.AccountRequestDTO;
import com.fyzo.app.dto.account.AccountResponseDTO;
import com.fyzo.app.dto.account.AccountUpdateDTO;
import com.fyzo.app.security.entities.UserDetailsImpl;
import com.fyzo.app.services.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(
	    name = "Accounts Management",
	    description = "Operations for managing user accounts"
	)
@RestController
@RequestMapping("/accounts")
public class AccountResource {
	
	@Autowired
	private AccountService service;
	
	@Operation(
		    summary = "List all user's accounts",
		    description = "Retrieves a list of all accounts belonging to the authenticated user",
		    security = @SecurityRequirement(name = "bearerAuth"),
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Accounts retrieved successfully",
		            content = @Content(
		                mediaType = "application/json",
		                array = @ArraySchema(schema = @Schema(implementation = AccountResponseDTO.class))
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
		public ResponseEntity<List<AccountResponseDTO>> findAll(
		    @AuthenticationPrincipal UserDetailsImpl userDetails
		) {
		    List<AccountResponseDTO> accounts = service.findAll(userDetails);
		    return ResponseEntity.ok(accounts);
		}
	
	@Operation(
		    summary = "Create a new account",
		    description = "Creates a new account for the authenticated user",
		    security = @SecurityRequirement(name = "bearerAuth"),
		    responses = {
		        @ApiResponse(
		            responseCode = "201",
		            description = "Account created successfully",
		            content = @Content(
		                mediaType = "application/json",
		                schema = @Schema(implementation = AccountResponseDTO.class)
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
		public ResponseEntity<AccountResponseDTO> create(
		    @Valid @RequestBody AccountRequestDTO dto,
		    @AuthenticationPrincipal UserDetailsImpl userDetails
		) {
		    AccountResponseDTO obj = service.create(dto, userDetails);
		    return ResponseEntity.status(HttpStatus.CREATED).body(obj);
		}

	
	@Operation(
		    summary = "Update an account",
		    description = "Updates an existing account of the authenticated user by its ID",
		    security = @SecurityRequirement(name = "bearerAuth"),
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Account updated successfully",
		            content = @Content(
		                mediaType = "application/json",
		                schema = @Schema(implementation = AccountResponseDTO.class)
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
		            description = "Account not found or does not belong to the authenticated user",
		            content = @Content
		        )
		    }
		)
		@PatchMapping("/{id}")
		public ResponseEntity<AccountResponseDTO> update(
		    @PathVariable Long id,
		    @RequestBody @Valid AccountUpdateDTO dto,
		    @AuthenticationPrincipal UserDetailsImpl userDetails
		) {
		    AccountResponseDTO obj = service.update(id, dto, userDetails);
		    return ResponseEntity.ok(obj);
		}

	
	@Operation(
		    summary = "Delete an account",
		    description = "Deletes an account by its ID, if it belongs to the authenticated user",
		    security = @SecurityRequirement(name = "bearerAuth"),
		    responses = {
		        @ApiResponse(
		            responseCode = "204",
		            description = "Account deleted successfully",
		            content = @Content
		        ),
		        @ApiResponse(
		            responseCode = "401",
		            description = "Unauthorized - authentication required",
		            content = @Content
		        ),
		        @ApiResponse(
		            responseCode = "404",
		            description = "Account not found or does not belong to the authenticated user",
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

}
