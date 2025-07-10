package com.tomaz.finance.resources;

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

import com.tomaz.finance.dto.BalanceResponseDTO;
import com.tomaz.finance.dto.CategorySummaryDTO;
import com.tomaz.finance.dto.TransactionCreateDTO;
import com.tomaz.finance.dto.TransactionFilterDTO;
import com.tomaz.finance.dto.TransactionUpdateDTO;
import com.tomaz.finance.entities.Transaction;
import com.tomaz.finance.enums.TransactionType;
import com.tomaz.finance.security.entities.UserDetailsImpl;
import com.tomaz.finance.services.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class TransactionResource {
	
	@Autowired
	private TransactionService service;
	
	@GetMapping("/me")
	public Page<Transaction> findAll(@ModelAttribute TransactionFilterDTO dto, @AuthenticationPrincipal UserDetailsImpl userDetails){
		return service.findAll(dto, userDetails.getUsername());
	}
	
	@PostMapping("/create")
	public ResponseEntity<Transaction> create(@Valid @RequestBody TransactionCreateDTO dto, @AuthenticationPrincipal UserDetailsImpl userDetails){
		
		Transaction obj = service.create(dto, userDetails.getUsername());
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
	
	@GetMapping("/balance")
	public ResponseEntity<BalanceResponseDTO> getBalance(@AuthenticationPrincipal UserDetailsImpl userDetails){
		BalanceResponseDTO obj = service.getUserBalance(userDetails.getUsername());
		return ResponseEntity.ok(obj);
	}
	
	@GetMapping("/summary/expense")
	public ResponseEntity<List<CategorySummaryDTO>> getExpenseByCategory(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		List<CategorySummaryDTO> result = service.getSummaryByType(userDetails.getUsername(), TransactionType.EXPENSE.getCode());
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/summary/revenue")
	public ResponseEntity<List<CategorySummaryDTO>> getRevenueByCategory(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		List<CategorySummaryDTO> result = service.getSummaryByType(userDetails.getUsername(), TransactionType.REVENUE.getCode());
		return ResponseEntity.ok(result);
	}
	
}
