package com.tomaz.finance.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomaz.finance.dto.TransactionCreateDTO;
import com.tomaz.finance.dto.TransactionUpdateDTO;
import com.tomaz.finance.entities.Category;
import com.tomaz.finance.entities.Transaction;
import com.tomaz.finance.entities.User;
import com.tomaz.finance.enums.TransactionType;
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
	
	public Transaction create(TransactionCreateDTO dto, String username) {
		
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
			
		Category category = categoryRepository.findById(
				dto.getCategoryId()).orElseThrow(()-> new RuntimeException("Categoria não encontrada")
		);
		
		Transaction transaction = new Transaction();
		
		transaction.setTitle(dto.getTitle());
		transaction.setDescription(dto.getDescription());
		transaction.setAmount(dto.getAmount());
		
		try {
			transaction.setType(TransactionType.valueOf(dto.getType()));
		}
		catch (IllegalArgumentException e) {
	        throw new RuntimeException("Tipo de transação inválido.");
	    }
		
		if(dto.getDate() == null) {
			transaction.setDate(LocalDate.now());
		}
		else {
			transaction.setDate(dto.getDate());
		}
		
		transaction.setUser(user);
		transaction.setCategory(category);
		
		return transactionRepository.save(transaction);
	}
	
	public Transaction update(Long id, TransactionUpdateDTO dto, String username) {
		
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		
		Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transação não encontrada"));
		
		if(!transaction.getUser().getId().equals(user.getId())) {
			throw new RuntimeException("Essa transação não pertence a você.");
		}
		
		if(dto.getTitle() != null && !dto.getTitle().isBlank()) {
			transaction.setTitle(dto.getTitle());
		}
		
		if(dto.getDescription() != null && !dto.getDescription().isBlank()) {
			transaction.setDescription(dto.getDescription());
		}
		
		if(dto.getAmount() != null) {
			transaction.setAmount(dto.getAmount());
		}
		
		if(dto.getType() != null) {
			try {
				transaction.setType(TransactionType.valueOf(dto.getType()));
			}
			catch (IllegalArgumentException e) {
		        throw new RuntimeException("Tipo de transação inválido.");
		    }
		}
		
		if(dto.getDate() != null) {
			transaction.setDate(dto.getDate());
		}
		
		if(dto.getCategoryId() != null) {
			Category category = categoryRepository.findById(
					dto.getCategoryId()).orElseThrow(()-> new RuntimeException("Categoria não encontrada")
			);
			
			transaction.setCategory(category);
		}
		
		return transactionRepository.save(transaction);
	}
	
	public void delete(Long id, String username) {
		
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		
		Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transação não encontrada"));
		
		if(!transaction.getUser().getId().equals(user.getId())) {
			throw new RuntimeException("Essa transação não pertence a você.");
		}
		
		transactionRepository.deleteById(id);
	}
		
}
