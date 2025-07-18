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
import com.tomaz.finance.security.entities.UserDetailsImpl;
import com.tomaz.finance.services.finder.AccountFinder;
import com.tomaz.finance.services.finder.UserFinder;

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
