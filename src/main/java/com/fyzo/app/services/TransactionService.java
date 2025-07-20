package com.fyzo.app.services;

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

import com.fyzo.app.dto.dashboard.BalanceResponseDTO;
import com.fyzo.app.dto.dashboard.CategorySummaryDTO;
import com.fyzo.app.dto.transaction.TransactionRequestDTO;
import com.fyzo.app.dto.transaction.TransactionFilterDTO;
import com.fyzo.app.dto.transaction.TransactionResponseDTO;
import com.fyzo.app.dto.transaction.TransactionUpdateDTO;
import com.fyzo.app.entities.Account;
import com.fyzo.app.entities.Category;
import com.fyzo.app.entities.Transaction;
import com.fyzo.app.entities.User;
import com.fyzo.app.enums.TransactionType;
import com.fyzo.app.mapper.TransactionMapper;
import com.fyzo.app.repositories.AccountRepository;
import com.fyzo.app.repositories.TransactionRepository;
import com.fyzo.app.security.entities.UserDetailsImpl;
import com.fyzo.app.services.finder.AccountFinder;
import com.fyzo.app.services.finder.CategoryFinder;
import com.fyzo.app.services.finder.TransactionFinder;
import com.fyzo.app.services.finder.UserFinder;
import com.fyzo.app.specification.TransactionSpecification;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private TransactionMapper transactionMapper;
	
	@Autowired
	private UserFinder userFinder;
	
	@Autowired
	private AccountFinder accountFinder;
	
	@Autowired
	private CategoryFinder categoryFinder;
	
	@Autowired
	private TransactionFinder transactionFinder;
	
	public Page<Transaction> findAll(TransactionFilterDTO dto, UserDetailsImpl userDetails){
		
		User user = userFinder.findByUsernameOrThrow(userDetails);
        Specification<Transaction> specification = TransactionSpecification.withFilters(dto, user);
        Pageable pageable = PageRequest.of(dto.page(), dto.size());
        
        return transactionRepository.findAll(specification, pageable);
	}

	
	public TransactionResponseDTO create(TransactionRequestDTO dto, UserDetailsImpl userDetails) {
		
	    User user = userFinder.findByUsernameOrThrow(userDetails);
	    Category category = categoryFinder.findByIdAndUserOrThrow(dto.categoryId(), user);
	    Account account = accountFinder.findByIdAndUserOrThrow(dto.accountId(), user);
	    
	    Transaction transaction = transactionMapper.toEntity(dto);
	    
	    updateBalance(account, dto.type(), dto.amount());

	    transaction.setType(dto.type());
	    transaction.setUser(user);
	    transaction.setCategory(category);
	    transaction.setAccount(account);	    

	    transactionRepository.save(transaction);
	    return transactionMapper.toResponse(transaction);
	    
	}

	public TransactionResponseDTO update(Long id, TransactionUpdateDTO dto, UserDetailsImpl userDetails) {
		
		User user = userFinder.findByUsernameOrThrow(userDetails);
	    Transaction transaction = transactionFinder.findByIdAndUserOrThrow(id, user);

	    transactionMapper.updateFromDto(dto, transaction);

	 
	    if (dto.categoryId() != null) {
	    	Category category = categoryFinder.findByIdOrThrow(dto.categoryId());
	        transaction.setCategory(category);
	    }

	    if (dto.accountId() != null) {
	    	Account account = accountFinder.findByIdOrThrow(id);
	        transaction.setAccount(account);
	    }

	    transactionRepository.save(transaction);
	    return transactionMapper.toResponse(transaction);
	}

	
	public void delete(Long id, UserDetailsImpl userDetails) {
		User user = userFinder.findByUsernameOrThrow(userDetails);
		Transaction transaction = transactionFinder.findByIdAndUserOrThrow(id, user);
		transactionRepository.delete(transaction);
	}
	
	public BalanceResponseDTO getUserBalance(UserDetailsImpl userDetails) {
		
		User user = userFinder.findByUsernameOrThrow(userDetails);
		
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
	
    public List<CategorySummaryDTO> getSummaryByType(UserDetailsImpl userDetails, TransactionType type){
    	
    	User user = userFinder.findByUsernameOrThrow(userDetails);
    	
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
    
    private void updateBalance(Account account, TransactionType type, BigDecimal amount) {
    	BigDecimal balance = account.getBalance();
    	
    	if(type == TransactionType.REVENUE) {
    		
    		balance = balance.add(amount);
    		account.setBalance(balance);
    	}
    	else if(type == TransactionType.EXPENSE) {
    		balance = balance.subtract(amount);
    		account.setBalance(balance);
    	}
    	else {
    		throw new RuntimeException("Tipo Invalido");
    	}
    	
    	accountRepository.save(account);
    }
		
}
