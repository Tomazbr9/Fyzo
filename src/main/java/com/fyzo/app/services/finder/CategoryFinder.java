package com.fyzo.app.services.finder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fyzo.app.entities.Category;
import com.fyzo.app.entities.User;
import com.fyzo.app.exceptions.ResourceNotFoundException;
import com.fyzo.app.exceptions.UnauthorizedResourceAccessException;
import com.fyzo.app.repositories.CategoryRepository;

@Service
public class CategoryFinder {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	public Category findByIdOrThrow(Long id) {
		return categoryRepository.findById(id)
		        .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
	}
	
	public Category findByIdAndUserOrThrow(Long id, User user) {
			
			Category category = findByIdOrThrow(id);
			
			if (!category.getUser().getId().equals(user.getId())) {
		        throw new UnauthorizedResourceAccessException("Essa categoria não pertence a você");
		    }
			
			return category;
		}
}
