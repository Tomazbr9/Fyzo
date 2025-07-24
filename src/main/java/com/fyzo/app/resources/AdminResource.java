package com.fyzo.app.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fyzo.app.dto.user.UserResponseDTO;
import com.fyzo.app.dto.user.UserUpdateDTO;
import com.fyzo.app.services.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminResource {
	
	@Autowired
	private AdminService service;
	
	@Operation(
		    summary = "List all users",
		    description = "Retrieves a list of all users. Access restricted to users with ADMIN role",
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Users retrieved successfully",
		            content = @Content(
		                mediaType = "application/json",
		                array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class))
		            )
		        ),
		        @ApiResponse(
		            responseCode = "401",
		            description = "Unauthorized - authentication required",
		            content = @Content
		        ),
		        @ApiResponse(
		            responseCode = "403",
		            description = "Forbidden - user does not have ADMIN role",
		            content = @Content
		        )
		    }
		)
		@PreAuthorize("hasRole('ADMIN')")
		@GetMapping("/users/all")
		public ResponseEntity<List<UserResponseDTO>> findAll() {
		    List<UserResponseDTO> list = service.findAll();
		    return ResponseEntity.ok().body(list);
		}

	
	@Operation(
		    summary = "Update a user",
		    description = "Updates an existing user by its ID. Access restricted to users with ADMIN role",
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
		            responseCode = "403",
		            description = "Forbidden - user does not have ADMIN role",
		            content = @Content
		        ),
		        @ApiResponse(
		            responseCode = "404",
		            description = "User not found",
		            content = @Content
		        )
		    }
		)
		@PreAuthorize("hasRole('ADMIN')")
		@PatchMapping("/users/update/{id}")
		public ResponseEntity<UserResponseDTO> update(
		    @Valid @RequestBody UserUpdateDTO dto,
		    @PathVariable Long id
		) {
		    UserResponseDTO obj = service.update(dto, id);
		    return ResponseEntity.ok(obj);
		}

	
	@Operation(
		    summary = "Delete a user",
		    description = "Deletes a user by its ID. Access restricted to users with ADMIN role",
		    responses = {
		        @ApiResponse(
		            responseCode = "204",
		            description = "User deleted successfully",
		            content = @Content
		        ),
		        @ApiResponse(
		            responseCode = "401",
		            description = "Unauthorized - authentication required",
		            content = @Content
		        ),
		        @ApiResponse(
		            responseCode = "403",
		            description = "Forbidden - user does not have ADMIN role",
		            content = @Content
		        ),
		        @ApiResponse(
		            responseCode = "404",
		            description = "User not found",
		            content = @Content
		        )
		    }
		)
		@PreAuthorize("hasRole('ADMIN')")
		@PatchMapping("/users/delete/{id}")
		public ResponseEntity<Void> delete(@PathVariable Long id) {
		    service.delete(id);
		    return ResponseEntity.noContent().build();
		}

	
	
	
}
