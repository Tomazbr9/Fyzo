package com.tomaz.finance.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomaz.finance.dto.GoalCreateDTO;
import com.tomaz.finance.dto.GoalResponseDTO;
import com.tomaz.finance.dto.GoalUpdateDTO;
import com.tomaz.finance.entities.Goal;
import com.tomaz.finance.entities.User;
import com.tomaz.finance.mapper.GoalMapper;
import com.tomaz.finance.repositories.GoalRepository;
import com.tomaz.finance.security.entities.UserDetailsImpl;
import com.tomaz.finance.services.finder.GoalFinder;
import com.tomaz.finance.services.finder.UserFinder;

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
	
	public GoalResponseDTO create(GoalCreateDTO dto, UserDetailsImpl userDetails) {
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
