package com.tomaz.finance.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomaz.finance.dto.UserResponseDTO;
import com.tomaz.finance.dto.UserUpdateDTO;
import com.tomaz.finance.entities.User;
import com.tomaz.finance.mapper.UserMapper;
import com.tomaz.finance.repositories.UserRepository;
import com.tomaz.finance.security.SecurityConfig;
import com.tomaz.finance.security.entities.UserDetailsImpl;
import com.tomaz.finance.services.finder.UserFinder;

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
