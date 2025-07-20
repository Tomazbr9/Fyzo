package com.fyzo.app.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.fyzo.app.dto.goal.GoalRequestDTO;
import com.fyzo.app.dto.goal.GoalResponseDTO;
import com.fyzo.app.dto.goal.GoalUpdateDTO;
import com.fyzo.app.entities.Goal;

@Mapper(componentModel = "spring")
public interface GoalMapper {
	
	Goal toEntity(GoalRequestDTO dto);
	
	GoalResponseDTO toResponse(Goal entity);
	
	List<GoalResponseDTO> goalsFromGoalsDTO(List<Goal> goals);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(GoalUpdateDTO dto, @MappingTarget Goal entity);
}
