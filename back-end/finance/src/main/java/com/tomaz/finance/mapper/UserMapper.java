package com.tomaz.finance.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.tomaz.finance.dto.UserCreateDTO;
import com.tomaz.finance.dto.UserResponseDTO;
import com.tomaz.finance.dto.UserUpdateDTO;
import com.tomaz.finance.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	User toEntity(UserCreateDTO dto);
	
	UserResponseDTO toResponse(User entity);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UserUpdateDTO dto, @MappingTarget User entity);
}
