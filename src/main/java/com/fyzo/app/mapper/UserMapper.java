package com.fyzo.app.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.fyzo.app.dto.UserCreateDTO;
import com.fyzo.app.dto.UserResponseDTO;
import com.fyzo.app.dto.UserUpdateDTO;
import com.fyzo.app.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	User toEntity(UserCreateDTO dto);
	
	UserResponseDTO toResponse(User entity);
	
	List<UserResponseDTO> usersFromUsersDTO(List<User> goals);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UserUpdateDTO dto, @MappingTarget User entity);
}
