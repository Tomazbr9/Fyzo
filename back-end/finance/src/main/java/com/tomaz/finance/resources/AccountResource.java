package com.tomaz.finance.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tomaz.finance.dto.AccountCreateDTO;
import com.tomaz.finance.dto.AccountDTO;
import com.tomaz.finance.entities.Account;
import com.tomaz.finance.security.entities.UserDetailsImpl;
import com.tomaz.finance.services.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountResource {
	
	@Autowired
	private AccountService service;
	
	@GetMapping("/me")
	public List<AccountDTO> findAll(@AuthenticationPrincipal UserDetailsImpl userDetails){
		return service.findAll(userDetails.getUsername());
		
	}
	
	@PostMapping("/create")
	public ResponseEntity<Account> create(@Valid @RequestBody AccountCreateDTO dto, @AuthenticationPrincipal UserDetailsImpl userDetails){
		Account obj = service.create(dto, userDetails.getUsername());
		return ResponseEntity.status(HttpStatus.CREATED).body(obj);
	}
}
