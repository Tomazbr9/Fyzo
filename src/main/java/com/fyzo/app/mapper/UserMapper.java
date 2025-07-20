package com.fyzo.app.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.fyzo.app.dto.user.UserRequestDTO;
import com.fyzo.app.dto.user.UserResponseDTO;
import com.fyzo.app.dto.user.UserUpdateDTO;
import com.fyzo.app.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	User toEntity(UserRequestDTO dto);
	
	UserResponseDTO toResponse(User entity);
	
	List<UserResponseDTO> usersFromUsersDTO(List<User> goals);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UserUpdateDTO dto, @MappingTarget User entity);
}
