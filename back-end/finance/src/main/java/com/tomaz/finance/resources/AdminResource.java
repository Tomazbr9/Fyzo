package com.tomaz.finance.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tomaz.finance.dto.UserResponseDTO;
import com.tomaz.finance.services.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminResource {
	
	@Autowired
	private AdminService service;
	
	@GetMapping("/users/all")
	public ResponseEntity<List<UserResponseDTO>> findAll(){
		
		List<UserResponseDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
}
