package com.fyzo.app.resources;

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

import com.fyzo.app.dto.JwtTokenDTO;
import com.fyzo.app.dto.LoginDTO;
import com.fyzo.app.dto.UserCreateDTO;
import com.fyzo.app.dto.UserResponseDTO;
import com.fyzo.app.dto.UserUpdateDTO;
import com.fyzo.app.security.entities.UserDetailsImpl;
import com.fyzo.app.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@PostMapping("/login")
	public ResponseEntity<JwtTokenDTO> authenticateUser(@Valid @RequestBody LoginDTO dto){
		JwtTokenDTO token = service.authenticateUser(dto);
		return new ResponseEntity<>(token, HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserCreateDTO dto){
		UserResponseDTO obj = service.create(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(obj);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
		UserResponseDTO obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PatchMapping("/update")
	public ResponseEntity<UserResponseDTO> update(@Valid @RequestBody UserUpdateDTO dto, @AuthenticationPrincipal UserDetailsImpl userDetails){
			
		UserResponseDTO obj = service.update(dto, userDetails);
		return ResponseEntity.ok(obj);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Void> delete(@AuthenticationPrincipal UserDetailsImpl userDetails){
		service.delete(userDetails);
		return ResponseEntity.noContent().build();
	}
}
