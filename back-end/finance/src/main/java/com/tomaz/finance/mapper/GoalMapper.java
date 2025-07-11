package com.tomaz.finance.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.tomaz.finance.dto.GoalCreateDTO;
import com.tomaz.finance.dto.GoalResponseDTO;
import com.tomaz.finance.dto.GoalUpdateDTO;
import com.tomaz.finance.entities.Goal;

@Mapper(componentModel = "spring")
public interface GoalMapper {
	
	Goal toEntity(GoalCreateDTO dto);
	
	GoalResponseDTO toResponse(Goal entity);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(GoalUpdateDTO dto, @MappingTarget Goal entity);
}
