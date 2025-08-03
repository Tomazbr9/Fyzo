package com.fyzo.app.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fyzo.app.dto.auth.JwtTokenDTO;
import com.fyzo.app.dto.auth.LoginDTO;
import com.fyzo.app.dto.user.UserRequestDTO;
import com.fyzo.app.dto.user.UserResponseDTO;
import com.fyzo.app.services.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(
	    name = "Authentication",
	    description = "Operations for user login and registration"
	)
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService service;
	
	@Operation(
		    summary = "Authenticate user and generate JWT token",
		    description = "Authenticates user credentials and returns a JWT token if successful",
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "User authenticated successfully",
		            content = @Content(
		                mediaType = "application/json",
		                schema = @Schema(implementation = JwtTokenDTO.class)
		            )
		        ),
		        @ApiResponse(
		            responseCode = "400",
		            description = "Invalid login credentials",
		            content = @Content
		        )
		    }
		)
		@PostMapping("/login")
		public ResponseEntity<JwtTokenDTO> authenticateUser(
		    @Valid @RequestBody LoginDTO dto
		) {
		    JwtTokenDTO token = service.authenticateUser(dto);
		    return new ResponseEntity<>(token, HttpStatus.OK);
		}

	
	@Operation(
		    summary = "Register a new user",
		    description = "Creates a new user account with the provided information",
		    responses = {
		        @ApiResponse(
		            responseCode = "201",
		            description = "User registered successfully",
		            content = @Content(
		                mediaType = "application/json",
		                schema = @Schema(implementation = UserResponseDTO.class)
		            )
		        ),
		        @ApiResponse(
		            responseCode = "400",
		            description = "Invalid input data",
		            content = @Content
		        )
		    }
		)
		@PostMapping("/register")
		public ResponseEntity<UserResponseDTO> create(
		    @Valid @RequestBody UserRequestDTO dto
		) {
		    UserResponseDTO obj = service.registerUser(dto);
		    return ResponseEntity.status(HttpStatus.CREATED).body(obj);
		}


}
