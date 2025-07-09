package com.tomaz.finance.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomaz.finance.dto.GoalCreateDTO;
import com.tomaz.finance.entities.Goal;
import com.tomaz.finance.entities.User;
import com.tomaz.finance.repositories.GoalRepository;
import com.tomaz.finance.repositories.UserRepository;

@Service
public class GoalService {
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private GoalRepository goalRepository;
	
	public Goal create(GoalCreateDTO dto, String username) {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		
		Goal goal = new Goal();
		
		goal.setName(dto.getName());
		goal.setTargetAmount(dto.getTargetAmount());
		goal.setTargetDate(dto.getTargetDate());
		goal.setUser(user);
		
		return goalRepository.save(goal);
	
	}
}
