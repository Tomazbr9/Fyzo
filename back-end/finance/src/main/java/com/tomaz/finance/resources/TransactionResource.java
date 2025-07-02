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

import com.tomaz.finance.dto.TransactionCreateDTO;
import com.tomaz.finance.dto.TransactionUpdateDTO;
import com.tomaz.finance.entities.Transaction;
import com.tomaz.finance.security.entities.UserDetailsImpl;
import com.tomaz.finance.services.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class TransactionResource {
	
	@Autowired
	private TransactionService service;
	
	@GetMapping
	public ResponseEntity<List<Transaction>> findAll(){
		
		List<Transaction> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@PostMapping("/create")
	public ResponseEntity<Transaction> create(@Valid @RequestBody TransactionCreateDTO dto, @AuthenticationPrincipal UserDetailsImpl userDetails){
		
		String username = userDetails.getUsername();
		
		Transaction obj = service.create(dto, username);
		return ResponseEntity.status(HttpStatus.CREATED).body(obj);
	}
	
	@PatchMapping("/update/{id}")
	public ResponseEntity<Transaction> update(@PathVariable Long id, @RequestBody TransactionUpdateDTO dto, @AuthenticationPrincipal UserDetailsImpl userDetails){
		Transaction obj = service.update(id, dto, userDetails.getUsername());
		return ResponseEntity.ok(obj);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
		service.delete(id, userDetails.getUsername());
		return ResponseEntity.noContent().build();
	}
}
