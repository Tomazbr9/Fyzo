package com.tomaz.finance.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomaz.finance.dto.UserResponseDTO;
import com.tomaz.finance.entities.User;
import com.tomaz.finance.mapper.UserMapper;
import com.tomaz.finance.repositories.UserRepository;

@Service
public class AdminService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	public List<UserResponseDTO> findAll(){
		
		List<User> users = userRepository.findAll();
		
		return userMapper.usersFromUsersDTO(users);
		
	}
}
