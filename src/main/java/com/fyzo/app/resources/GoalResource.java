package com.fyzo.app.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.fyzo.app.dto.goal.GoalRequestDTO;
import com.fyzo.app.dto.goal.GoalResponseDTO;
import com.fyzo.app.dto.goal.GoalUpdateDTO;
import com.fyzo.app.security.entities.UserDetailsImpl;
import com.fyzo.app.services.GoalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/goals")
public class GoalResource {
	
	@Autowired
	private GoalService service;
	
	@Operation(
		    summary = "List all user's goals",
		    description = "Retrieves a list of all goals belonging to the authenticated user",
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Goals retrieved successfully",
		            content = @Content(
		                mediaType = "application/json",
		                array = @ArraySchema(schema = @Schema(implementation = GoalResponseDTO.class))
		            )
		        ),
		        @ApiResponse(
		            responseCode = "401",
		            description = "Unauthorized - authentication required",
		            content = @Content
		        )
		    }
		)
		@GetMapping("/me")
		public ResponseEntity<List<GoalResponseDTO>> findAll(
		    @AuthenticationPrincipal UserDetailsImpl userDetails
		) {
		    List<GoalResponseDTO> goals = service.findAll(userDetails);
		    return ResponseEntity.ok().body(goals);
		}

	
	@Operation(
		    summary = "Create a new goal",
		    description = "Creates a new goal for the authenticated user",
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Goal created successfully",
		            content = @Content(
		                mediaType = "application/json",
		                schema = @Schema(implementation = GoalResponseDTO.class)
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
		@PostMapping("/create")
		public ResponseEntity<GoalResponseDTO> create(
		    @Valid @RequestBody GoalRequestDTO dto,
		    @AuthenticationPrincipal UserDetailsImpl userDetails
		) {
		    GoalResponseDTO obj = service.create(dto, userDetails);
		    return ResponseEntity.ok(obj);
		}

	
	@Operation(
		    summary = "Update a goal",
		    description = "Updates an existing goal of the authenticated user by its ID",
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Goal updated successfully",
		            content = @Content(
		                mediaType = "application/json",
		                schema = @Schema(implementation = GoalResponseDTO.class)
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
		            description = "Goal not found or does not belong to the authenticated user",
		            content = @Content
		        )
		    }
		)
		@PatchMapping("update/{id}")
		public ResponseEntity<GoalResponseDTO> update(
		    @PathVariable Long id,
		    @RequestBody GoalUpdateDTO dto,
		    @AuthenticationPrincipal UserDetailsImpl userDetails
		) {
		    GoalResponseDTO obj = service.update(id, dto, userDetails);
		    return ResponseEntity.ok(obj);
		}

	
	@Operation(
		    summary = "Delete a goal",
		    description = "Deletes a goal by its ID, if it belongs to the authenticated user",
		    responses = {
		        @ApiResponse(
		            responseCode = "204",
		            description = "Goal deleted successfully",
		            content = @Content
		        ),
		        @ApiResponse(
		            responseCode = "401",
		            description = "Unauthorized - authentication required",
		            content = @Content
		        ),
		        @ApiResponse(
		            responseCode = "404",
		            description = "Goal not found or does not belong to the authenticated user",
		            content = @Content
		        )
		    }
		)
		@DeleteMapping("delete/{id}")
		public ResponseEntity<Void> delete(
		    @PathVariable Long id,
		    @AuthenticationPrincipal UserDetailsImpl userDetails
		) {
		    service.delete(id, userDetails);
		    return ResponseEntity.noContent().build();
		}


}
