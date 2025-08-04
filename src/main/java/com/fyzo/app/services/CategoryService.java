package com.fyzo.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fyzo.app.dto.category.CategoryRequestDTO;
import com.fyzo.app.dto.category.CategoryResponseDTO;
import com.fyzo.app.dto.category.CategoryUpdateDTO;
import com.fyzo.app.entities.Category;
import com.fyzo.app.entities.User;
import com.fyzo.app.mapper.CategoryMapper;
import com.fyzo.app.repositories.CategoryRepository;
import com.fyzo.app.security.entities.UserDetailsImpl;
import com.fyzo.app.services.finder.CategoryFinder;
import com.fyzo.app.services.finder.UserFinder;

import jakarta.transaction.Transactional;

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

	public List<CategoryResponseDTO> findAll(UserDetailsImpl userDetails) {
		
		User user = userFinder.findByUsernameOrThrow(userDetails);
		
		List<Category> categories = categoryRepository.findByUser(user);
		
		return categoryMapper.categoriesFromCategoriesDTO(categories);
	}
	
	public CategoryResponseDTO findById(Long id) {
		Optional<Category> obj = categoryRepository.findById(id);

		return categoryMapper.toResponse(obj.get());
	}
    
	@Transactional
	public CategoryResponseDTO create(CategoryRequestDTO dto, UserDetailsImpl userDetails) {

		User user = userFinder.findByUsernameOrThrow(userDetails);

	    Category category = categoryMapper.toEntity(dto);
	    category.setUser(user);

	    categoryRepository.save(category);
	    return categoryMapper.toResponse(category);
	}
    
	@Transactional
	public CategoryResponseDTO update(Long id, CategoryUpdateDTO dto, UserDetailsImpl userDetails) {

	    User user = userFinder.findByUsernameOrThrow(userDetails);
	    Category category = categoryFinder.findByIdAndUserOrThrow(id, user);

	    categoryMapper.updateFromDto(dto, category);

	    categoryRepository.save(category);
	    return categoryMapper.toResponse(category);
	}
    
	@Transactional
	public void delete(Long id, UserDetailsImpl userDetails) {
		
		User user = userFinder.findByUsernameOrThrow(userDetails);
		Category category = categoryFinder.findByIdAndUserOrThrow(id, user);

		categoryRepository.delete(category);
	}

}
