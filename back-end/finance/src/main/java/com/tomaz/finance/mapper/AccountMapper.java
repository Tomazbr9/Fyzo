package com.tomaz.finance.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.tomaz.finance.dto.AccountCreateDTO;
import com.tomaz.finance.dto.AccountUpdateDTO;
import com.tomaz.finance.dto.CategoryResponseDTO;
import com.tomaz.finance.entities.Account;
import com.tomaz.finance.entities.Category;

@Mapper(componentModel = "spring")
public interface AccountMapper {
	
	Account toEntity(AccountCreateDTO dto);
	
	CategoryResponseDTO toResponse(Category entity);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(AccountUpdateDTO dto, @MappingTarget Account entity);
}
