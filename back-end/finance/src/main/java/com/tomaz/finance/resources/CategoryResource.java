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

import com.tomaz.finance.dto.CategoryCreateDTO;
import com.tomaz.finance.dto.CategoryDTO;
import com.tomaz.finance.dto.CategoryUpdateDTO;
import com.tomaz.finance.entities.Category;
import com.tomaz.finance.security.entities.UserDetailsImpl;
import com.tomaz.finance.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService service;
	
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll(@AuthenticationPrincipal UserDetailsImpl userDetails){
		
		List<CategoryDTO> list = service.findAll(userDetails.getUsername());
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Category> findById(@PathVariable Long id) {
		Category obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping("/create")
	public ResponseEntity<Category> create(@Valid @RequestBody CategoryCreateDTO dto, @AuthenticationPrincipal UserDetailsImpl userDetails){
		Category obj = service.create(dto, userDetails.getUsername());
		return ResponseEntity.status(HttpStatus.CREATED).body(obj);
	}
	
	@PatchMapping("update/{id}")
	public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody  CategoryUpdateDTO dto, @AuthenticationPrincipal UserDetailsImpl userDetails){
		Category obj = service.update(id, dto, userDetails.getUsername());
		return ResponseEntity.ok(obj);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
		service.delete(id, userDetails.getUsername());
		return ResponseEntity.noContent().build();
	}
}
