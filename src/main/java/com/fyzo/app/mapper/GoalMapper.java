package com.fyzo.app.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.fyzo.app.dto.GoalCreateDTO;
import com.fyzo.app.dto.GoalResponseDTO;
import com.fyzo.app.dto.GoalUpdateDTO;
import com.fyzo.app.entities.Goal;

@Mapper(componentModel = "spring")
public interface GoalMapper {
	
	Goal toEntity(GoalCreateDTO dto);
	
	GoalResponseDTO toResponse(Goal entity);
	
	List<GoalResponseDTO> goalsFromGoalsDTO(List<Goal> goals);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(GoalUpdateDTO dto, @MappingTarget Goal entity);
}
