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

import com.fyzo.app.dto.CategoryCreateDTO;
import com.fyzo.app.dto.CategoryResponseDTO;
import com.fyzo.app.dto.CategoryUpdateDTO;
import com.fyzo.app.security.entities.UserDetailsImpl;
import com.fyzo.app.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService service;
	
	@GetMapping("/me")
	public ResponseEntity<List<CategoryResponseDTO>> findAll(@AuthenticationPrincipal UserDetailsImpl userDetails){
		
		List<CategoryResponseDTO> list = service.findAll(userDetails);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryResponseDTO> findById(@PathVariable Long id) {
		CategoryResponseDTO obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping("/create")
	public ResponseEntity<CategoryResponseDTO> create(@Valid @RequestBody CategoryCreateDTO dto, @AuthenticationPrincipal UserDetailsImpl userDetails){
		CategoryResponseDTO obj = service.create(dto, userDetails);
		return ResponseEntity.status(HttpStatus.CREATED).body(obj);
	}
	
	@PatchMapping("update/{id}")
	public ResponseEntity<CategoryResponseDTO> update(@PathVariable Long id, @RequestBody  CategoryUpdateDTO dto, @AuthenticationPrincipal UserDetailsImpl userDetails){
		CategoryResponseDTO obj = service.update(id, dto, userDetails);
		return ResponseEntity.ok(obj);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
		service.delete(id, userDetails);
		return ResponseEntity.noContent().build();
	}
}
