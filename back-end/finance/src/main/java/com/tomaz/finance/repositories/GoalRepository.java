package com.tomaz.finance.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomaz.finance.entities.Goal;
import com.tomaz.finance.entities.User;

public interface GoalRepository extends JpaRepository<Goal, Long> {
	List<Goal> findByUser(User user); 

}
