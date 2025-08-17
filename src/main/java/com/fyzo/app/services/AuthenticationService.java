package com.fyzo.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fyzo.app.dto.auth.JwtTokenDTO;
import com.fyzo.app.dto.auth.LoginDTO;
import com.fyzo.app.dto.user.UserRequestDTO;
import com.fyzo.app.dto.user.UserResponseDTO;
import com.fyzo.app.entities.Account;
import com.fyzo.app.entities.Category;
import com.fyzo.app.entities.Role;
import com.fyzo.app.entities.User;
import com.fyzo.app.enums.RoleName;
import com.fyzo.app.exceptions.EmailAlreadyExistsException;
import com.fyzo.app.exceptions.InvalidCredentialsException;
import com.fyzo.app.exceptions.ResourceNotFoundException;
import com.fyzo.app.exceptions.UsernameAlreadyExistsException;
import com.fyzo.app.mapper.UserMapper;
import com.fyzo.app.repositories.AccountRepository;
import com.fyzo.app.repositories.CategoryRepository;
import com.fyzo.app.repositories.RoleRepository;
import com.fyzo.app.repositories.UserRepository;
import com.fyzo.app.security.SecurityConfig;
import com.fyzo.app.security.entities.UserDetailsImpl;
import com.fyzo.app.security.jwt.JwtTokenService;

import jakarta.transaction.Transactional;

@Service
public class AuthenticationService {
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
    private SecurityConfig securityConfig;
	
	@Autowired 
	RoleRepository roleRepository;
	
	@Autowired 
	private UserMapper userMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtTokenService jwtTokenService;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	public JwtTokenDTO authenticateUser(LoginDTO dto) {
		
		try {
			
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(dto.username(), dto.password()); 
	        
			Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
	        
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			
	        return new JwtTokenDTO(jwtTokenService.generateToken(userDetails));
	        
		 } catch (InternalAuthenticationServiceException e) {
		        throw new InvalidCredentialsException("Credenciais inválidas");
		 }
    }
	
	@Transactional
	public UserResponseDTO registerUser(UserRequestDTO dto) {
	    User user = userMapper.toEntity(dto);
	    
	    if(userRepository.existsByUsername(dto.username())) {
	    	throw  new UsernameAlreadyExistsException("Nome de usuário ja existente");
	    }
	    
	    if(userRepository.existsByEmail(dto.email())) {
	    	throw new EmailAlreadyExistsException("Email já existente");
	    }

	    RoleName roleName = RoleName.valueOf(dto.role());
	    Role role = roleRepository.findByName(roleName)
	        .orElseThrow(() -> new ResourceNotFoundException("Role não encontrado"));

	    user.setPassword(securityConfig.passwordEncoder().encode(dto.password()));
	    user.setRoles(List.of(role));

	    userRepository.save(user);
	    copyDefaultCategoriesToUser(user);
	    copyDefaultAccountsToUser(user);
	    
	    return userMapper.toResponse(user);
	    
	}
	
	private void copyDefaultCategoriesToUser(User user) {
		List<Category> defaultCategories = categoryRepository.findByIsDefaultTrue();
		
		List<Category> categoriesToUser = defaultCategories
				.stream()
				.map(category -> {
					Category newCategory = new Category();
					newCategory.setName(category.getName());
					newCategory.setType(category.getType());
					newCategory.setColor(category.getColor());
					newCategory.setIcon(category.getIcon());
					newCategory.setDefault(false);
					newCategory.setUser(user);
					return newCategory;
				}).toList();
		
		categoryRepository.saveAll(categoriesToUser);
		
	}
	
	private void copyDefaultAccountsToUser(User user) {
		
		List<Account> defaultAccounts = accountRepository.findByIsDefaultTrue();
		
		List<Account> accountsToUser = defaultAccounts
				.stream()
				.map(account -> {
					Account newAccount = new Account ();
					newAccount.setName(account.getName());
					newAccount.setImageUrl(account.getImageUrl());
					newAccount.setDefault(false);
					newAccount.setUser(user);
					return newAccount;
				}).toList();
		
		accountRepository.saveAll(accountsToUser);
		
	}
	
}
