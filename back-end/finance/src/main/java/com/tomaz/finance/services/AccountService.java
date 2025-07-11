package com.tomaz.finance.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomaz.finance.dto.AccountCreateDTO;
import com.tomaz.finance.dto.AccountResponseDTO;
import com.tomaz.finance.dto.AccountUpdateDTO;
import com.tomaz.finance.entities.Account;
import com.tomaz.finance.entities.User;
import com.tomaz.finance.mapper.AccountMapper;
import com.tomaz.finance.repositories.AccountRepository;
import com.tomaz.finance.repositories.UserRepository;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountMapper accountMapper;
	
	public List<AccountResponseDTO> findAll(String username){
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		
		List<Account> accounts = accountRepository.findByUser(user);
		
		return accounts.stream()
				.map(account -> new AccountResponseDTO(account.getName(), account.getBalance()))
				.toList();
	}
	
	public Account create(AccountCreateDTO dto, String username) {
	    User user = userRepository.findByUsername(username)
	        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

	    Account account = accountMapper.toEntity(dto);
	    account.setUser(user);

	    return accountRepository.save(account);
	}

	public Account update(Long id, AccountUpdateDTO dto, String username) {

	    Account account = accountRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Conta não encontrada."));
	    
	    User user = userRepository.findByUsername(username)
	            .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
	    
	    if (!account.getUser().getId().equals(user.getId())) {
	        throw new RuntimeException("Essa conta não pertence a você.");
	    }
	    
	    accountMapper.updateFromDto(dto, account);

	    
	    return accountRepository.save(account);
	}

	public void delete(Long id, String username) {
		
		Account account = accountRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Conta não encontrada"));
		
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		
		if(!account.getUser().getId().equals(user.getId())) {
			throw new RuntimeException("Essa categoria não pertence a você.");
		}

		accountRepository.deleteById(id);
	}
}
