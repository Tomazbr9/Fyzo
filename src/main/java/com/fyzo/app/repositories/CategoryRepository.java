package com.fyzo.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fyzo.app.entities.Category;
import com.fyzo.app.entities.User;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	List<Category> findByUser(User user);
	
	List<Category> findByIsDefaultTrue();
}
