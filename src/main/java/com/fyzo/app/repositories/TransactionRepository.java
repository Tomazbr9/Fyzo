package com.fyzo.app.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.fyzo.app.entities.Transaction;
import com.fyzo.app.entities.User;
import com.fyzo.app.enums.TransactionType;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
	List<Transaction> findByUser(User user);
	
	List<Transaction> findByUserAndType(User user, TransactionType type);
	
	Page<Transaction> findByUser(User user, Pageable page);
}
