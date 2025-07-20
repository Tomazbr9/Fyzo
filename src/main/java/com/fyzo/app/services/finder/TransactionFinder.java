package com.fyzo.app.services.finder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fyzo.app.entities.Transaction;
import com.fyzo.app.entities.User;
import com.fyzo.app.exceptions.ResourceNotFoundException;
import com.fyzo.app.exceptions.UnauthorizedResourceAccessException;
import com.fyzo.app.repositories.TransactionRepository;

@Service
public class TransactionFinder {
	
	@Autowired
	TransactionRepository transactionRepository;
	
	public Transaction findByIdOrThrow(Long id) {
		return transactionRepository.findById(id)
		        .orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada"));
	}
	
	public Transaction findByIdAndUserOrThrow(Long id, User user) {
		
		Transaction transaction = findByIdOrThrow(id);
		
		if (!transaction.getUser().getId().equals(user.getId())) {
	        throw new UnauthorizedResourceAccessException("Essa transação não pertence a você");
	    }
		
		return transaction;
	}
}
