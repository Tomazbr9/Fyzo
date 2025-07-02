package com.tomaz.finance.services;

import java.util.List;  
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomaz.finance.dto.CategoryCreateDTO;
import com.tomaz.finance.dto.CategoryUpdateDTO;
import com.tomaz.finance.entities.Category;
import com.tomaz.finance.entities.User;
import com.tomaz.finance.enums.TransactionType;
import com.tomaz.finance.repositories.CategoryRepository;
import com.tomaz.finance.repositories.UserRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;

	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	public Category findById(Long id) {
		Optional<Category> obj = categoryRepository.findById(id);

		return obj.get();
	}

	public Category create(CategoryCreateDTO dto, String username) {

		Category category = new Category();

		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		
		category.setName(dto.getName());
		category.setType(TransactionType.valueOf(dto.getType()));
		category.setColor(dto.getColor());
		category.setUser(user);

		return categoryRepository.save(category);
	}

	public Category update(Long id, CategoryUpdateDTO dto, String username) {

		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
		
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		
		if(!category.getUser().getId().equals(user.getId())) {
			throw new RuntimeException("Essa categoria não pertence a você.");
		}

		if (dto.getName() != null && !dto.getName().isBlank()) {
			category.setName(dto.getName());
		}

		if (dto.getType() != null) {
			category.setType(TransactionType.valueOf(dto.getType()));
		}

		if (dto.getColor() != null && dto.getColor().isBlank()) {
			category.setColor(dto.getColor());
		}

		return categoryRepository.save(category);
    }

	public void delete(Long id, String username) {
		
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
		
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		
		if(!category.getUser().getId().equals(user.getId())) {
			throw new RuntimeException("Essa categoria não pertence a você.");
		}

		categoryRepository.deleteById(id);
	}

}
