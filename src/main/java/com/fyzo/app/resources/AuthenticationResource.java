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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationResource {
	
	@Autowired
	private AuthenticationService service;
	
	@PostMapping("/login")
	public ResponseEntity<JwtTokenDTO> authenticateUser(@Valid @RequestBody LoginDTO dto){
		JwtTokenDTO token = service.authenticateUser(dto);
		return new ResponseEntity<>(token, HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO dto){
		System.out.println(dto);
		UserResponseDTO obj = service.registerUser(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(obj);
	}

}
