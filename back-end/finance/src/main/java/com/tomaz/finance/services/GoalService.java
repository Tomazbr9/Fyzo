package com.tomaz.finance.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomaz.finance.dto.CategoryUpdateDTO;
import com.tomaz.finance.dto.GoalCreateDTO;
import com.tomaz.finance.dto.GoalResponseDTO;
import com.tomaz.finance.dto.GoalUpdateDTO;
import com.tomaz.finance.entities.Category;
import com.tomaz.finance.entities.Goal;
import com.tomaz.finance.entities.User;
import com.tomaz.finance.enums.TransactionType;
import com.tomaz.finance.repositories.GoalRepository;
import com.tomaz.finance.repositories.UserRepository;

@Service
public class GoalService {
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private GoalRepository goalRepository;
	
	public List<GoalResponseDTO> findAll(String username){
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		
		List<Goal> goals = goalRepository.findByUser(user);
		
		return goals.stream()
				.map(goal -> new GoalResponseDTO(
					goal.getId(),
					goal.getName(),
					goal.getTargetAmount(),
					goal.getSavedAmount(),
					goal.getTargetDate(),
					goal.isCompleted()
				
				)).toList();
	}
	
	public Goal create(GoalCreateDTO dto, String username) {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		
		Goal goal = new Goal();
		
		goal.setName(dto.getName());
		goal.setTargetAmount(dto.getTargetAmount());
		goal.setTargetDate(dto.getTargetDate());
		goal.setUser(user);
		
		return goalRepository.save(goal);
	
	}
	
	public Goal update(Long id, GoalUpdateDTO dto, String username) {

		Goal goal = goalRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Categoria não encontrada."));
		
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
		
		if(!goal.getUser().getId().equals(user.getId())) {
			throw new RuntimeException("Essa meta não pertence a você.");
		}

		if (dto.getName() != null && !dto.getName().isBlank()) {
			goal.setName(dto.getName());
		}

		if (dto.getTargetAmount() != null) {
			goal.setTargetAmount(dto.getTargetAmount());
		}

		if (dto.getSavedAmount() != null) {
			goal.setSavedAmount(dto.getSavedAmount());
		}

		return goalRepository.save(goal);
    }
	
public void delete(Long id, String username) {
		
		Goal goal = goalRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Meta não encontrada"));
		
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		
		if(!goal.getUser().getId().equals(user.getId())) {
			throw new RuntimeException("Essa categoria não pertence a você.");
		}

		goalRepository.deleteById(id);
	}
}
