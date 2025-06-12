package com.tomaz.finance.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomaz.finance.entities.Transaction;
import com.tomaz.finance.repositories.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
//	@Autowired
//	private CategoryService categoryService;
//	
//	@Autowired
//	private UserService userService;
	
	public List<Transaction> findAll(){
		return transactionRepository.findAll();
	}
	
//	public Transaction create(Transaction dto) {
//		Transaction transaction = new Transaction();
//		
//		
//		User user = userService.findById(dto.getUser());
//		
//		
//		
//		
//		Category category = categoryService.findById(dto.getCategory());
//		
//		
//		transaction.setDescription(dto.getDescription());
//		transaction.setValue(dto.getValue());
//		transaction.setType(dto.getType());
//		transaction.setDate(dto.getDate());
//		transaction.setCategory(dto.getCategory());
//		transaction.setUser(dto.getUser());
//		
//		return transactionRepository.save(transaction);
//	}
//	
	
	
}
