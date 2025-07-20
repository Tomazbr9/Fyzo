package com.fyzo.app.services.finder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fyzo.app.entities.Goal;
import com.fyzo.app.entities.User;
import com.fyzo.app.exceptions.ResourceNotFoundException;
import com.fyzo.app.exceptions.UnauthorizedResourceAccessException;
import com.fyzo.app.repositories.GoalRepository;

@Service
public class GoalFinder {
	
	@Autowired
	GoalRepository goalRepository;
	
	public Goal findByIdOrThrow(Long id) {
		return goalRepository.findById(id)
		        .orElseThrow(() -> new ResourceNotFoundException("Meta não encontrada"));
	}
	
	public Goal findByIdAndUserOrThrow(Long id, User user) {
			
			Goal goal = findByIdOrThrow(id);
			
			if (!goal.getUser().getId().equals(user.getId())) {
		        throw new UnauthorizedResourceAccessException("Essa meta não pertence a você");
		    }
			
			return goal;
		}
}
