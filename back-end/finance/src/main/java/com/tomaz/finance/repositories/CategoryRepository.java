package com.tomaz.finance.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tomaz.finance.entities.Category;
import com.tomaz.finance.entities.User;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	List<Category> findByUser(User user);
	
	List<Category> findByIsDefaultTrue();
}
