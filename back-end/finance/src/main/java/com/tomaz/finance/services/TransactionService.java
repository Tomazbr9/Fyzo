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
import com.tomaz.finance.repositories.TransactionRepository;
import com.tomaz.finance.security.entities.UserDetailsImpl;
import com.tomaz.finance.services.finder.AccountFinder;
import com.tomaz.finance.services.finder.CategoryFinder;
import com.tomaz.finance.services.finder.TransactionFinder;
import com.tomaz.finance.services.finder.UserFinder;
import com.tomaz.finance.specification.TransactionSpecification;

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

	
	public TransactionResponseDTO create(TransactionCreateDTO dto, UserDetailsImpl userDetails) {
		
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
