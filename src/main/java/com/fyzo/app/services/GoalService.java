package com.fyzo.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fyzo.app.dto.goal.GoalRequestDTO;
import com.fyzo.app.dto.goal.GoalResponseDTO;
import com.fyzo.app.dto.goal.GoalUpdateDTO;
import com.fyzo.app.entities.Goal;
import com.fyzo.app.entities.User;
import com.fyzo.app.mapper.GoalMapper;
import com.fyzo.app.repositories.GoalRepository;
import com.fyzo.app.security.entities.UserDetailsImpl;
import com.fyzo.app.services.finder.GoalFinder;
import com.fyzo.app.services.finder.UserFinder;

@Service
public class GoalService {
	
	@Autowired
	private GoalRepository goalRepository;
	
	@Autowired
	private GoalMapper goalMapper;
	
	@Autowired
	private UserFinder userFinder;
	
	@Autowired
	private GoalFinder goalFinder;
	
	public List<GoalResponseDTO> findAll(UserDetailsImpl userDetails){
		
		User user = userFinder.findByUsernameOrThrow(userDetails);
		
		List<Goal> goals = goalRepository.findByUser(user);
		
		return goalMapper.goalsFromGoalsDTO(goals);
	}
	
	public GoalResponseDTO create(GoalRequestDTO dto, UserDetailsImpl userDetails) {
		User user = userFinder.findByUsernameOrThrow(userDetails);

	    Goal goal = goalMapper.toEntity(dto);
	    goal.setUser(user);

	    goalRepository.save(goal);
	    return goalMapper.toResponse(goal);
	}
	
	public GoalResponseDTO update(Long id, GoalUpdateDTO dto, UserDetailsImpl userDetails) {

	    User user = userFinder.findByUsernameOrThrow(userDetails);
	    Goal goal = goalFinder.findByIdAndUserOrThrow(id, user);

	    goalMapper.updateFromDto(dto, goal);

	    goalRepository.save(goal);
	    return goalMapper.toResponse(goal);
	}
	
public void delete(Long id, UserDetailsImpl userDetails) {
		
		User user = userFinder.findByUsernameOrThrow(userDetails);
		Goal goal = goalFinder.findByIdAndUserOrThrow(id, user);

		goalRepository.delete(goal);
	}
}
