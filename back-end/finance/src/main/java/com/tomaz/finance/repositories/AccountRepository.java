package com.tomaz.finance.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tomaz.finance.entities.Account;
import com.tomaz.finance.entities.User;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	List<Account> findByUser(User user); 
}
