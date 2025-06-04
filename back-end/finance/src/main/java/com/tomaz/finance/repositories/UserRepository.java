package com.tomaz.finance.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tomaz.finance.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
}
