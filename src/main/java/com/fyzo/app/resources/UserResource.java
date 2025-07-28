package com.fyzo.app.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fyzo.app.dto.user.UserResponseDTO;
import com.fyzo.app.dto.user.UserUpdateDTO;
import com.fyzo.app.security.entities.UserDetailsImpl;
import com.fyzo.app.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Users Management", description = "Operations to retrieve, update and delete authenticated users")
@RestController
@RequestMapping("/users")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@Operation(
		    summary = "Get current user profile",
		    description = "Retrieves the complete profile information of the currently authenticated user",
		    security = @SecurityRequirement(name = "bearerAuth"),
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "User profile retrieved successfully",
		            content = @Content(
		                mediaType = "application/json",
		                schema = @Schema(implementation = UserResponseDTO.class)
		            )
		        ),
		        @ApiResponse(
		            responseCode = "401",
		            description = "Unauthorized - authentication required",
		            content = @Content
		        ),
		        @ApiResponse(
		            responseCode = "404",
		            description = "User not found - account may have been deleted",
		            content = @Content
		        )
		    }
		)
		@GetMapping("/me")
		public ResponseEntity<UserResponseDTO> findById(
		    @Parameter(hidden = true) 
		    @AuthenticationPrincipal UserDetailsImpl userDetails) {
		    
		    UserResponseDTO obj = service.findByUser(userDetails);
		    return ResponseEntity.ok().body(obj);
		}
	
	
	@Operation(
		    summary = "Update authenticated user's information",
		    description = "Partially updates the currently authenticated user's data. Only provided fields will be updated.",
		    security = @SecurityRequirement(name = "bearerAuth"),
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "User updated successfully",
		            content = @Content(
		                mediaType = "application/json",
		                schema = @Schema(implementation = UserResponseDTO.class)
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
		            description = "User not found",
		            content = @Content
		        )
		        
		    }
		)
		@PatchMapping("/update")
		public ResponseEntity<UserResponseDTO> update(
		    @Parameter(description = "User data for update", required = true)
		    @Valid @RequestBody UserUpdateDTO dto,
		    
		    @Parameter(hidden = true)
		    @AuthenticationPrincipal UserDetailsImpl userDetails) {
		    
		    UserResponseDTO obj = service.update(dto, userDetails);
		    return ResponseEntity.ok(obj);
		}
	
	@Operation(
		    summary = "Delete authenticated user account",
		    description = "Permanently deletes the currently authenticated user's account and all associated data",
		    security = @SecurityRequirement(name = "bearerAuth"),
		    responses = {
		        @ApiResponse(
		            responseCode = "204",
		            description = "User account deleted successfully (no content)"
		        ),
		        @ApiResponse(
		            responseCode = "401",
		            description = "Unauthorized - authentication required",
		            content = @Content
		        ),
		        @ApiResponse(
		            responseCode = "404",
		            description = "User not found",
		            content = @Content
		        )
		    }
		)
		@DeleteMapping("/delete")
		public ResponseEntity<Void> delete(
		    @Parameter(hidden = true) // Hidden from documentation as it's handled internally
		    @AuthenticationPrincipal UserDetailsImpl userDetails) {
		    
		    service.delete(userDetails);
		    return ResponseEntity.noContent().build();
		}
}
