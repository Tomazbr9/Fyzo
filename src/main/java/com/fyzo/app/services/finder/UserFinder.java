package com.fyzo.app.services.finder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fyzo.app.entities.User;
import com.fyzo.app.repositories.UserRepository;

@Service
public class UserFinder {
	
	@Autowired
	UserRepository userRepository;
	
	public User findByUsernameOrThrow(UserDetails userDetails) {
		return userRepository.findByUsername(userDetails.getUsername())
		        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
	}
	
	public User findByIdOrThrow(Long id) {
		return userRepository.findById(id)
		        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
	}
	
	
	
}
