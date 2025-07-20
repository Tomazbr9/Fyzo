package com.fyzo.app.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fyzo.app.dto.BalanceResponseDTO;
import com.fyzo.app.dto.CategorySummaryDTO;
import com.fyzo.app.dto.TransactionCreateDTO;
import com.fyzo.app.dto.TransactionFilterDTO;
import com.fyzo.app.dto.TransactionResponseDTO;
import com.fyzo.app.dto.TransactionUpdateDTO;
import com.fyzo.app.entities.Transaction;
import com.fyzo.app.enums.TransactionType;
import com.fyzo.app.security.entities.UserDetailsImpl;
import com.fyzo.app.services.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class TransactionResource {
	
	@Autowired
	private TransactionService service;
	
	@GetMapping("/me")
	public Page<Transaction> findAll(@ModelAttribute TransactionFilterDTO dto, @AuthenticationPrincipal UserDetailsImpl userDetails){
		return service.findAll(dto, userDetails);
	}
	
	@PostMapping("/create")
	public ResponseEntity<TransactionResponseDTO> create(@Valid @RequestBody TransactionCreateDTO dto, @AuthenticationPrincipal UserDetailsImpl userDetails){
		
		TransactionResponseDTO obj = service.create(dto, userDetails);
		return ResponseEntity.status(HttpStatus.CREATED).body(obj);
	}
	
	@PatchMapping("/update/{id}")
	public ResponseEntity<TransactionResponseDTO> update(@PathVariable Long id, @RequestBody TransactionUpdateDTO dto, @AuthenticationPrincipal UserDetailsImpl userDetails){
		TransactionResponseDTO obj = service.update(id, dto, userDetails);
		return ResponseEntity.ok(obj);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
		service.delete(id, userDetails);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/balance")
	public ResponseEntity<BalanceResponseDTO> getBalance(@AuthenticationPrincipal UserDetailsImpl userDetails){
		BalanceResponseDTO obj = service.getUserBalance(userDetails);
		return ResponseEntity.ok(obj);
	}
	
	@GetMapping("/summary/expense")
	public ResponseEntity<List<CategorySummaryDTO>> getExpenseByCategory(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		List<CategorySummaryDTO> result = service.getSummaryByType(userDetails, TransactionType.EXPENSE);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/summary/revenue")
	public ResponseEntity<List<CategorySummaryDTO>> getRevenueByCategory(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		List<CategorySummaryDTO> result = service.getSummaryByType(userDetails, TransactionType.REVENUE);
		return ResponseEntity.ok(result);
	}
	
}
