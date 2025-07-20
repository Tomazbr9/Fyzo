package com.fyzo.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fyzo.app.dto.AccountCreateDTO;
import com.fyzo.app.dto.AccountResponseDTO;
import com.fyzo.app.dto.AccountUpdateDTO;
import com.fyzo.app.entities.Account;
import com.fyzo.app.entities.User;
import com.fyzo.app.mapper.AccountMapper;
import com.fyzo.app.repositories.AccountRepository;
import com.fyzo.app.security.entities.UserDetailsImpl;
import com.fyzo.app.services.finder.AccountFinder;
import com.fyzo.app.services.finder.UserFinder;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountMapper accountMapper;
	
	@Autowired
	private AccountFinder accountFinder;
	
	@Autowired
	private UserFinder userFinder;
	
	public List<AccountResponseDTO> findAll(UserDetailsImpl userDetails){
		
		User user = userFinder.findByUsernameOrThrow(userDetails);
		List<Account> accounts = accountRepository.findByUser(user);
		
		return accountMapper.accountFromAccountDTO(accounts);
	}
	
	public AccountResponseDTO create(AccountCreateDTO dto, UserDetailsImpl userDetails) {
	    
		User user = userFinder.findByUsernameOrThrow(userDetails);
		
	    Account account = accountMapper.toEntity(dto);
	    account.setUser(user);

	    accountRepository.save(account);
	    return accountMapper.toResponse(account);
	}

	public AccountResponseDTO update(Long id, AccountUpdateDTO dto, UserDetailsImpl userDetails) {

	    User user = userFinder.findByUsernameOrThrow(userDetails);
	    Account account = accountFinder.findByIdAndUserOrThrow(id, user);
	    
	    accountMapper.updateFromDto(dto, account);
	    
	    accountRepository.save(account);
	    return accountMapper.toResponse(account);
	}

	public void delete(Long id, UserDetailsImpl userDetails) {
		
		User user = userFinder.findByUsernameOrThrow(userDetails);
		Account account = accountFinder.findByIdAndUserOrThrow(id, user);

		accountRepository.delete(account);
	}
}
