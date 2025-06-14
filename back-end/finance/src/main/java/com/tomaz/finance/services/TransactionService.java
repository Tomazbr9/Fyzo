package com.tomaz.finance.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomaz.finance.dto.TransactionDTO;
import com.tomaz.finance.entities.Category;
import com.tomaz.finance.entities.Transaction;
import com.tomaz.finance.entities.User;
import com.tomaz.finance.repositories.CategoryRepository;
import com.tomaz.finance.repositories.TransactionRepository;
import com.tomaz.finance.repositories.UserRepository;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public List<Transaction> findAll(){
		return transactionRepository.findAll();
	}
	
	public Transaction create(TransactionDTO dto) {	
		
		User user = userRepository.findById(
				dto.getUserId()).orElseThrow(()-> new RuntimeException("Usuário não encontrado")
		);
			
		Category category = categoryRepository.findById(
				dto.getCategoryId()).orElseThrow(()-> new RuntimeException("Categoria não encontrada")
		);
		
		Transaction transaction = new Transaction();
		
		
		transaction.setDescription(dto.getDescription());
		transaction.setValue(dto.getAmount());
		transaction.setType(dto.getType());
		transaction.setDate(dto.getDate());
		transaction.setUser(user);
		transaction.setCategory(category);
		
		
		return transactionRepository.save(transaction);
	}
	
	
	
}
