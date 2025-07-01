package com.tomaz.finance.resources;

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

import com.tomaz.finance.dto.JwtTokenDTO;
import com.tomaz.finance.dto.LoginDTO;
import com.tomaz.finance.dto.UserCreateDTO;
import com.tomaz.finance.dto.UserUpdateDTO;
import com.tomaz.finance.entities.User;
import com.tomaz.finance.security.entities.UserDetailsImpl;
import com.tomaz.finance.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@GetMapping
	public ResponseEntity<List<User>> findAll(){
		
		List<User> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtTokenDTO> authenticateUser(@Valid @RequestBody LoginDTO dto){
		JwtTokenDTO token = service.authenticateUser(dto);
		return new ResponseEntity<>(token, HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<User> create(@Valid @RequestBody UserCreateDTO dto){
		User obj = service.create(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(obj);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		User obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PatchMapping("/update")
	public ResponseEntity<User> update(@Valid @RequestBody UserUpdateDTO dto, @AuthenticationPrincipal UserDetailsImpl userDetails){
		
		String username = userDetails.getUsername();
		
		
		User obj = service.update(dto, username);
		return ResponseEntity.ok(obj);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
