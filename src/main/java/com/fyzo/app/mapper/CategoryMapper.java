package com.fyzo.app.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.fyzo.app.dto.CategoryCreateDTO;
import com.fyzo.app.dto.CategoryResponseDTO;
import com.fyzo.app.dto.CategoryUpdateDTO;
import com.fyzo.app.entities.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
	
	Category toEntity(CategoryCreateDTO dto);
	
	CategoryResponseDTO toResponse(Category entity);
	
	List<CategoryResponseDTO> categoriesFromCategoriesDTO(List<Category> catgories);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(CategoryUpdateDTO dto, @MappingTarget Category entity);
}
