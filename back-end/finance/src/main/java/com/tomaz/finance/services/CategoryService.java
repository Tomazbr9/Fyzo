package com.tomaz.finance.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomaz.finance.dto.CategoryCreateDTO;
import com.tomaz.finance.dto.CategoryResponseDTO;
import com.tomaz.finance.dto.CategoryUpdateDTO;
import com.tomaz.finance.entities.Category;
import com.tomaz.finance.entities.User;
import com.tomaz.finance.mapper.CategoryMapper;
import com.tomaz.finance.repositories.CategoryRepository;
import com.tomaz.finance.services.finder.CategoryFinder;
import com.tomaz.finance.services.finder.UserFinder;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryMapper categoryMapper;
	
	@Autowired
	private UserFinder userFinder;
	
	@Autowired
	private CategoryFinder categoryFinder;

	public List<CategoryResponseDTO> findAll(String username) {
		
		User user = userFinder.findByUsernameOrThrow(username);
		
		List<Category> categories = categoryRepository.findByUser(user);
		
		return categoryMapper.categoriesFromCategoriesDTO(categories);
	}
	
	public CategoryResponseDTO findById(Long id) {
		Optional<Category> obj = categoryRepository.findById(id);

		return categoryMapper.toResponse(obj.get());
	}

	public CategoryResponseDTO create(CategoryCreateDTO dto, String username) {

		User user = userFinder.findByUsernameOrThrow(username);

	    Category category = categoryMapper.toEntity(dto);
	    category.setUser(user);

	    categoryRepository.save(category);
	    return categoryMapper.toResponse(category);
	}

	public CategoryResponseDTO update(Long id, CategoryUpdateDTO dto, String username) {

	    User user = userFinder.findByUsernameOrThrow(username);
	    Category category = categoryFinder.findByIdAndUserOrThrow(id, user);

	    categoryMapper.updateFromDto(dto, category);

	    categoryRepository.save(category);
	    return categoryMapper.toResponse(category);
	}

	public void delete(Long id, String username) {
		
		User user = userFinder.findByUsernameOrThrow(username);
		Category category = categoryFinder.findByIdAndUserOrThrow(id, user);

		categoryRepository.delete(category);
	}

}
