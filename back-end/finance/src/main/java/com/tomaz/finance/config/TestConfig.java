package com.tomaz.finance.config;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.tomaz.finance.entities.Category;
import com.tomaz.finance.entities.Transaction;
import com.tomaz.finance.entities.User;
import com.tomaz.finance.enums.TransactionType;
import com.tomaz.finance.repositories.CategoryRepository;
import com.tomaz.finance.repositories.TransactionRepository;
import com.tomaz.finance.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		User u1 = new User(null, "Alex", "alex@gmail.com", "12345");
		User u2 = new User(null, "Rebeca", "rebeca@gmail.com", "12345");
		
		Category c1 = new Category(null, "Aluguel", TransactionType.EXPENSE, "", null);
		Category c2 = new Category(null, "Renda", TransactionType.REVENUE, "", null);
		
		Transaction t1 = new Transaction(null, "Salario dia 5", "salario", 1550.0, LocalDate.of(2025, 6, 6), TransactionType.REVENUE, u1, c1);
		Transaction t2 = new Transaction(null, "Vale","salario", 1800.0, LocalDate.of(2025, 6, 6), TransactionType.REVENUE, u2, c2);
		
		categoryRepository.saveAll(Arrays.asList(c1, c2));
		userRepository.saveAll(Arrays.asList(u1, u2));
		transactionRepository.saveAll(Arrays.asList(t1, t2));
		
	}
}
