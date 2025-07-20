package com.fyzo.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fyzo.app.entities.Goal;
import com.fyzo.app.entities.User;

public interface GoalRepository extends JpaRepository<Goal, Long> {
	List<Goal> findByUser(User user); 

}
