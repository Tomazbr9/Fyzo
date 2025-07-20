package com.fyzo.app.services.finder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fyzo.app.entities.Account;
import com.fyzo.app.entities.User;
import com.fyzo.app.exceptions.ResourceNotFoundException;
import com.fyzo.app.exceptions.UnauthorizedResourceAccessException;
import com.fyzo.app.repositories.AccountRepository;

@Service
public class AccountFinder {
	
	@Autowired
	AccountRepository accountRepository;
	
	public Account findByIdOrThrow(Long id) {
		return accountRepository.findById(id)
		        .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));
	}
	
	public Account findByIdAndUserOrThrow(Long id, User user) {
			
			Account account = findByIdOrThrow(id);
			
			if (!account.getUser().getId().equals(user.getId())) {
		        throw new UnauthorizedResourceAccessException("Essa conta não pertence a você");
		    }
			
			return account;
		}
}
