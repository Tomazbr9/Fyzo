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

import com.fyzo.app.dto.category.CategoryRequestDTO;
import com.fyzo.app.dto.category.CategoryResponseDTO;
import com.fyzo.app.dto.category.CategoryUpdateDTO;
import com.fyzo.app.security.entities.UserDetailsImpl;
import com.fyzo.app.services.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@Tag(
	    name = "Category Management",
	    description = "Operations for creating, retrieving, updating, and deleting user categories"
	)
@RestController
@RequestMapping("/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService service;
	
	
	@Operation(
		    summary = "List all user's categories",
		    description = "Retrieves a list of all categories belonging to the authenticated user",
		    security = @SecurityRequirement(name = "bearerAuth"),
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Categories retrieved successfully",
		            content = @Content(
		                mediaType = "application/json",
		                array = @ArraySchema(schema = @Schema(implementation = CategoryResponseDTO.class))
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
		public ResponseEntity<List<CategoryResponseDTO>> findAll(
		    @Parameter(hidden = true)
		    @AuthenticationPrincipal UserDetailsImpl userDetails) {
		    
		    List<CategoryResponseDTO> list = service.findAll(userDetails);
		    return ResponseEntity.ok().body(list);
		}

	

	@Operation(
		    summary = "Find category by ID",
		    description = "Retrieves a category by its ID, if it belongs to the authenticated user",
		    security = @SecurityRequirement(name = "bearerAuth"),
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Category retrieved successfully",
		            content = @Content(
		                mediaType = "application/json",
		                schema = @Schema(implementation = CategoryResponseDTO.class)
		            )
		        ),
		        @ApiResponse(
		            responseCode = "404",
		            description = "Category not found or does not belong to the authenticated user",
		            content = @Content
		        ),
		        @ApiResponse(
		            responseCode = "401",
		            description = "Unauthorized - authentication required",
		            content = @Content
		        )
		    }
		)
		@GetMapping("/{id}")
		public ResponseEntity<CategoryResponseDTO> findById(@PathVariable Long id) {
		    CategoryResponseDTO obj = service.findById(id);
		    return ResponseEntity.ok().body(obj);
		}

	
	@Operation(
		    summary = "Create a new category",
		    description = "Creates a new category for the authenticated user",
		    security = @SecurityRequirement(name = "bearerAuth"),
		    responses = {
		        @ApiResponse(
		            responseCode = "201",
		            description = "Category created successfully",
		            content = @Content(
		                mediaType = "application/json",
		                schema = @Schema(implementation = CategoryResponseDTO.class)
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
		public ResponseEntity<CategoryResponseDTO> create(
		    @Valid @RequestBody CategoryRequestDTO dto,
		    @AuthenticationPrincipal UserDetailsImpl userDetails
		) {
		    CategoryResponseDTO obj = service.create(dto, userDetails);
		    return ResponseEntity.status(HttpStatus.CREATED).body(obj);
		}

	
	@Operation(
		    summary = "Update a category",
		    description = "Updates an existing category of the authenticated user by its ID",
		    security = @SecurityRequirement(name = "bearerAuth"),
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Category updated successfully",
		            content = @Content(
		                mediaType = "application/json",
		                schema = @Schema(implementation = CategoryResponseDTO.class)
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
		            description = "Category not found or does not belong to the authenticated user",
		            content = @Content
		        )
		    }
		)
		@PatchMapping("/{id}")
		public ResponseEntity<CategoryResponseDTO> update(
		    @PathVariable Long id,
		    @RequestBody CategoryUpdateDTO dto,
		    @AuthenticationPrincipal UserDetailsImpl userDetails
		) {
		    CategoryResponseDTO obj = service.update(id, dto, userDetails);
		    return ResponseEntity.ok(obj);
		}

	
	@Operation(
		    summary = "Delete a category",
		    description = "Deletes a category by its ID, if it belongs to the authenticated user",
		    security = @SecurityRequirement(name = "bearerAuth"),
		    responses = {
		        @ApiResponse(
		            responseCode = "204",
		            description = "Category deleted successfully",
		            content = @Content
		        ),
		        @ApiResponse(
		            responseCode = "401",
		            description = "Unauthorized - authentication required",
		            content = @Content
		        ),
		        @ApiResponse(
		            responseCode = "404",
		            description = "Category not found or does not belong to the authenticated user",
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
