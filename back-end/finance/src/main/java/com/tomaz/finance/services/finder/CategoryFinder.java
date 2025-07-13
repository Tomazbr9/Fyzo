package com.tomaz.finance.services.finder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomaz.finance.entities.Category;
import com.tomaz.finance.entities.User;
import com.tomaz.finance.repositories.CategoryRepository;

@Service
public class CategoryFinder {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	public Category findByIdOrThrow(Long id) {
		return categoryRepository.findById(id)
		        .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
	}
	
	public Category findByIdAndUserOrThrow(Long id, User user) {
			
			Category category = findByIdOrThrow(id);
			
			if (!category.getUser().getId().equals(user.getId())) {
		        throw new RuntimeException("Essa categoria não pertence a você");
		    }
			
			return category;
		}
}
