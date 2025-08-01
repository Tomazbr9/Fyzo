package com.fyzo.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fyzo.app.entities.Account;
import com.fyzo.app.entities.User;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	List<Account> findByUser(User user);
	
}
