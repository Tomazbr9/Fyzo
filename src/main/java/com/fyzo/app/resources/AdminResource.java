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

import com.fyzo.app.dto.UserResponseDTO;
import com.fyzo.app.dto.UserUpdateDTO;
import com.fyzo.app.services.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminResource {
	
	@Autowired
	private AdminService service;
	
	@GetMapping("/users/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserResponseDTO>> findAll(){
		
		List<UserResponseDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@PatchMapping("/users/update/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserResponseDTO> update(@Valid @RequestBody UserUpdateDTO dto, @PathVariable Long id){
			
		UserResponseDTO obj = service.update(dto, id);
		return ResponseEntity.ok(obj);
	}
	
	@PatchMapping("/users/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable Long id){
			
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
	
}
