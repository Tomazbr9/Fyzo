package com.tomaz.finance.services.finder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomaz.finance.entities.Transaction;
import com.tomaz.finance.entities.User;
import com.tomaz.finance.repositories.TransactionRepository;

@Service
public class TransactionFinder {
	
	@Autowired
	TransactionRepository transactionRepository;
	
	public Transaction findByIdOrThrow(Long id) {
		return transactionRepository.findById(id)
		        .orElseThrow(() -> new RuntimeException("Transação não encontrada"));
	}
	
	public Transaction findByIdAndUserOrThrow(Long id, User user) {
		
		Transaction transaction = findByIdOrThrow(id);
		
		if (!transaction.getUser().getId().equals(user.getId())) {
	        throw new RuntimeException("Essa transação não pertence a você");
	    }
		
		return transaction;
	}
}
