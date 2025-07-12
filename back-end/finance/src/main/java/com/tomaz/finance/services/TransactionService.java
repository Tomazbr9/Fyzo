package com.tomaz.finance.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tomaz.finance.dto.BalanceResponseDTO;
import com.tomaz.finance.dto.CategorySummaryDTO;
import com.tomaz.finance.dto.TransactionCreateDTO;
import com.tomaz.finance.dto.TransactionFilterDTO;
import com.tomaz.finance.dto.TransactionResponseDTO;
import com.tomaz.finance.dto.TransactionUpdateDTO;
import com.tomaz.finance.entities.Account;
import com.tomaz.finance.entities.Category;
import com.tomaz.finance.entities.Transaction;
import com.tomaz.finance.entities.User;
import com.tomaz.finance.enums.TransactionType;
import com.tomaz.finance.mapper.TransactionMapper;
import com.tomaz.finance.repositories.AccountRepository;
import com.tomaz.finance.repositories.CategoryRepository;
import com.tomaz.finance.repositories.TransactionRepository;
import com.tomaz.finance.repositories.UserRepository;
import com.tomaz.finance.specification.TransactionSpecification;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private TransactionMapper transactionMapper;
	
	public Page<Transaction> findAll(TransactionFilterDTO dto, String username){
		
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        Specification<Transaction> specification = TransactionSpecification.withFilters(dto, user);
        Pageable pageable = PageRequest.of(dto.page(), dto.size());
        
        return transactionRepository.findAll(specification, pageable);
	}

	
	public TransactionResponseDTO create(TransactionCreateDTO dto, String username) {
		
	    User user = userRepository.findByUsername(username)
	        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

	    Category category = categoryRepository.findById(dto.categoryId())
	        .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

	    Account account = accountRepository.findById(dto.accountId())
	        .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

	    if (!account.getUser().getId().equals(user.getId())) {
	        throw new RuntimeException("Essa conta não pertence a você");
	    }
	    
	    BigDecimal balance = account.getBalance();
	    if (dto.type() == TransactionType.REVENUE) {
	        balance = balance.add(dto.amount());
	        account.setBalance(balance);
	        accountRepository.save(account);
	    } else if (dto.type() == TransactionType.EXPENSE) {
	        balance = balance.subtract(dto.amount());
	        account.setBalance(balance);
	        accountRepository.save(account);
	    } else {
	        throw new IllegalArgumentException("Tipo de transação inválido.");
	    }
	    
	    Transaction transaction = transactionMapper.toEntity(dto);

	    transaction.setType(dto.type());
	    transaction.setUser(user);
	    transaction.setCategory(category);
	    transaction.setAccount(account);

	    transactionRepository.save(transaction);
	    return transactionMapper.toResponse(transaction);
	    
	}

	public TransactionResponseDTO update(Long id, TransactionUpdateDTO dto, String username) {
	    User user = userRepository.findByUsername(username)
	        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

	    Transaction transaction = transactionRepository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Transação não encontrada"));

	    if (!transaction.getUser().getId().equals(user.getId())) {
	        throw new RuntimeException("Essa transação não pertence a você.");
	    }

	    transactionMapper.updateFromDto(dto, transaction);

	 
	    if (dto.categoryId() != null) {
	        Category category = categoryRepository.findById(dto.categoryId())
	            .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
	        transaction.setCategory(category);
	    }

	    if (dto.accountId() != null) {
	        Account account = accountRepository.findById(dto.accountId())
	            .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

	        if (!account.getUser().getId().equals(user.getId())) {
	            throw new RuntimeException("Essa conta não pertence a você.");
	        }

	        transaction.setAccount(account);
	    }

	    transactionRepository.save(transaction);
	    return transactionMapper.toResponse(transaction);
	}

	
	public void delete(Long id, String username) {
		
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		
		Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transação não encontrada"));
		
		if(!transaction.getUser().getId().equals(user.getId())) {
			throw new RuntimeException("Essa transação não pertence a você.");
		}
		
		transactionRepository.deleteById(id);
	}
	
	public BalanceResponseDTO getUserBalance(String username) {
		
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuário não encontrato"));
		
		List<Transaction> transactions = transactionRepository.findByUser(user);
		
		BigDecimal totalRevenue = transactions.stream()
				.filter(t -> t.getType() == TransactionType.REVENUE)
				.map(Transaction::getAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		BigDecimal totalExpense = transactions.stream()
				.filter(t -> t.getType() == TransactionType.EXPENSE)
				.map(Transaction::getAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		return new BalanceResponseDTO(totalRevenue, totalExpense);
	}
	
    public List<CategorySummaryDTO> getSummaryByType(String username, TransactionType type){
    	
    	User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    	
    	List<Transaction> transactions = transactionRepository.findByUserAndType(user, type);
    	
    	Map<Category, BigDecimal> groupedByCategory = transactions
    			.stream()
    			.collect(
    					Collectors.groupingBy(
    							Transaction::getCategory,
    							Collectors.mapping(
    									Transaction::getAmount, 
    									Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
    							)
    					)
    			);
    	
        
    	return groupedByCategory
    			.entrySet().stream()
    			.map(entry -> new CategorySummaryDTO(
    					entry.getKey().getId(),
    					entry.getKey().getName(),
    					entry.getValue()
    					
    			))
    			.toList();
    					
    }
		
}
