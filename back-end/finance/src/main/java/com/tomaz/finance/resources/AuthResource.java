package com.tomaz.finance.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tomaz.finance.dto.UserCreateDTO;
import com.tomaz.finance.entities.User;
import com.tomaz.finance.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthResource {
	
	@Autowired
	private UserService service;
	
	@PostMapping("/register")
	public ResponseEntity<User> register(@Valid @RequestBody UserCreateDTO dto){
		User obj = service.create(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(obj);
	}
	
}
