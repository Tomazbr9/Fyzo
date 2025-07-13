package com.tomaz.finance.services.finder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomaz.finance.entities.User;
import com.tomaz.finance.repositories.UserRepository;

@Service
public class UserFinder {
	
	@Autowired
	UserRepository userRepository;
	
	public User findByUsernameOrThrow(String username) {
		return userRepository.findByUsername(username)
		        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
	}
}
