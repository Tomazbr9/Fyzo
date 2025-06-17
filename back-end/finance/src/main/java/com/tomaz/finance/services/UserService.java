package com.tomaz.finance.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomaz.finance.dto.UserCreateDTO;
import com.tomaz.finance.dto.UserUpdateDTO;
import com.tomaz.finance.entities.User;
import com.tomaz.finance.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	public User findById(Long id) {
		Optional<User> obj = userRepository.findById(id);
		
		return obj.get();
	}
	
	public User create(UserCreateDTO dto) {
		User user = new User();
		
		user.setUsername(dto.getUsername());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		
		return userRepository.save(user);
	}
	
	public User update(Long id, UserUpdateDTO dto) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		
		user.setUsername(dto.getUsername());
		user.setEmail(dto.getEmail());
		
		return userRepository.save(user);
	}
}
