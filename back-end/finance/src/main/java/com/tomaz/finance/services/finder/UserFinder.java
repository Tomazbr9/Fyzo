package com.tomaz.finance.services.finder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.tomaz.finance.entities.User;
import com.tomaz.finance.exceptions.ResourceNotFoundException;
import com.tomaz.finance.repositories.UserRepository;

@Service
public class UserFinder {
	
	@Autowired
	UserRepository userRepository;
	
	public User findByUsernameOrThrow(UserDetails userDetails) {
		return userRepository.findByUsername(userDetails.getUsername())
		        .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
	}
	
	public User findByIdOrThrow(Long id) {
		return userRepository.findById(id)
		        .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
	}
	
	
	
}
