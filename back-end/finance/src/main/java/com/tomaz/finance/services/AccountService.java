package com.tomaz.finance.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomaz.finance.dto.AccountCreateDTO;
import com.tomaz.finance.dto.AccountDTO;
import com.tomaz.finance.entities.Account;
import com.tomaz.finance.entities.User;
import com.tomaz.finance.repositories.AccountRepository;
import com.tomaz.finance.repositories.UserRepository;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	public List<AccountDTO> findAll(String username){
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		
		List<Account> accounts = accountRepository.findByUser(user);
		
		return accounts.stream()
				.map(account -> new AccountDTO(account.getName(), account.getBalance()))
				.collect(Collectors.toList());
	}
	
	public Account create(AccountCreateDTO dto, String username) {
		
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		Account account = new Account();
		
		account.setName(dto.getName());
		account.setUser(user);
		
		return accountRepository.save(account);
	}
}
