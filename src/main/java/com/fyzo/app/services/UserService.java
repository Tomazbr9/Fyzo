package com.fyzo.app.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fyzo.app.dto.user.UserResponseDTO;
import com.fyzo.app.dto.user.UserUpdateDTO;
import com.fyzo.app.entities.User;
import com.fyzo.app.mapper.UserMapper;
import com.fyzo.app.repositories.RoleRepository;
import com.fyzo.app.repositories.UserRepository;
import com.fyzo.app.security.SecurityConfig;
import com.fyzo.app.security.entities.UserDetailsImpl;
import com.fyzo.app.services.finder.UserFinder;

@Service
public class UserService {
	
	@Autowired
    private SecurityConfig securityConfig;
	
	@Autowired 
	RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private UserMapper userMapper;
	
	@Autowired 
	private UserFinder userFinder;
	
	public UserResponseDTO findById(UserDetailsImpl userDetails) {
		User user = userFinder.findByUsernameOrThrow(userDetails);
		return userMapper.toResponse(user);
	}
	
	
	public UserResponseDTO update(UserUpdateDTO dto, UserDetailsImpl userDetails) {
		User user = userFinder.findByUsernameOrThrow(userDetails);
		
	    userMapper.updateFromDto(dto, user);
	    
	    if (dto.password() != null && !dto.password().isBlank()) {
	        user.setPassword(securityConfig.passwordEncoder().encode(dto.password()));
	    }

	    userRepository.save(user);
	    return userMapper.toResponse(user);
	}

	public void delete(UserDetailsImpl userDetails) {
		
		User user = userFinder.findByUsernameOrThrow(userDetails);
		
		userRepository.delete(user);
	}
	
	
}
