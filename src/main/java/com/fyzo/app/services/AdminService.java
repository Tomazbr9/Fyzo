package com.fyzo.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fyzo.app.dto.user.UserResponseDTO;
import com.fyzo.app.dto.user.UserUpdateDTO;
import com.fyzo.app.entities.User;
import com.fyzo.app.mapper.UserMapper;
import com.fyzo.app.repositories.UserRepository;
import com.fyzo.app.security.SecurityConfig;
import com.fyzo.app.services.finder.UserFinder;

@Service
public class AdminService {
	
	@Autowired
    private SecurityConfig securityConfig;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserFinder userFinder;
	
	@Autowired
	private UserRepository userRepository;
	
	
	public List<UserResponseDTO> findAll(){
		
		List<User> users = userRepository.findAll();
		
		return userMapper.usersFromUsersDTO(users);
		
	}
	
	public UserResponseDTO update(UserUpdateDTO dto, Long id) {
		User user = userFinder.findByIdOrThrow(id);
		
		userMapper.updateFromDto(dto, user);
	    
	    if (dto.password() != null && !dto.password().isBlank()) {
	        user.setPassword(securityConfig.passwordEncoder().encode(dto.password()));
	    }

	    userRepository.save(user);
	    return userMapper.toResponse(user);
		
	}
	
	public void delete(Long id) {
		
		User user = userFinder.findByIdOrThrow(id);
		userRepository.delete(user);
		
	}

}
