package com.tomaz.finance.services.finder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomaz.finance.entities.Goal;
import com.tomaz.finance.entities.User;
import com.tomaz.finance.repositories.GoalRepository;

@Service
public class GoalFinder {
	
	@Autowired
	GoalRepository goalRepository;
	
	public Goal findByIdOrThrow(Long id) {
		return goalRepository.findById(id)
		        .orElseThrow(() -> new RuntimeException("Meta não encontrada"));
	}
	
	public Goal findByIdAndUserOrThrow(Long id, User user) {
			
			Goal goal = findByIdOrThrow(id);
			
			if (!goal.getUser().getId().equals(user.getId())) {
		        throw new RuntimeException("Essa meta não pertence a você");
		    }
			
			return goal;
		}
}
