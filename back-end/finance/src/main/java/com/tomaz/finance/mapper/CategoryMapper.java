package com.tomaz.finance.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.tomaz.finance.dto.CategoryCreateDTO;
import com.tomaz.finance.dto.CategoryResponseDTO;
import com.tomaz.finance.dto.CategoryUpdateDTO;
import com.tomaz.finance.entities.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
	
	Category toEntity(CategoryCreateDTO dto);
	
	CategoryResponseDTO toResponse(Category entity);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(CategoryUpdateDTO dto, @MappingTarget Category entity);
}
