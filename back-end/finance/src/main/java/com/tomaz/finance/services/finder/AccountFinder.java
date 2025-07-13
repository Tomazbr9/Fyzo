package com.tomaz.finance.services.finder;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import com.tomaz.finance.entities.Account;
import com.tomaz.finance.entities.User;
import com.tomaz.finance.repositories.AccountRepository;

@Service
public class AccountFinder {
	
	@Autowired
	AccountRepository accountRepository;
	
	public Account findByIdOrThrow(Long id) {
		return accountRepository.findById(id)
		        .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
	}
	
	public Account findByIdAndUserOrThrow(Long id, User user) {
			
			Account account = findByIdOrThrow(id);
			
			if (!account.getUser().getId().equals(user.getId())) {
		        throw new RuntimeException("Essa conta não pertence a você");
		    }
			
			return account;
		}
}
