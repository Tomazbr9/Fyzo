package com.fyzo.app.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.fyzo.app.entities.Account;
import com.fyzo.app.entities.Category;
import com.fyzo.app.entities.Goal;
import com.fyzo.app.entities.Role;
import com.fyzo.app.entities.Transaction;
import com.fyzo.app.entities.User;
import com.fyzo.app.enums.RoleName;
import com.fyzo.app.enums.TransactionType;
import com.fyzo.app.repositories.AccountRepository;
import com.fyzo.app.repositories.CategoryRepository;
import com.fyzo.app.repositories.GoalRepository;
import com.fyzo.app.repositories.RoleRepository;
import com.fyzo.app.repositories.TransactionRepository;
import com.fyzo.app.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired GoalRepository goalRepository;
	
	@Override
	public void run(String... args) throws Exception {
	
		Role adminRole = Role.builder()
			    .name(RoleName.ROLE_ADMIN)
			    .build();

			Role customerRole = Role.builder()
			    .name(RoleName.ROLE_CUSTOMER)
			    .build();

			User u1 = User.builder()
			    .username("Alex")
			    .email("alex@gmail.com")
			    .password("12345")
			    .roles(List.of(adminRole))
			    .build();

			User u2 = User.builder()
			    .username("Rebeca")
			    .email("rebeca@gmail.com")
			    .password("12345")
			    .roles(List.of(customerRole))
			    .build();

			Category c1 = Category.builder()
			    .name("Aluguel")
			    .type(TransactionType.EXPENSE)
			    .color("#FF0000")
			    .isDefault(true)
			    .build();

			Category c2 = Category.builder()
			    .name("Salario")
			    .type(TransactionType.REVENUE)
			    .color("#00FF00")
			    .isDefault(true)
			    .build();
			
			Category c3 = Category.builder()
				    .name("Mercado")
				    .type(TransactionType.REVENUE)
				    .color("#00FF00")
				    .isDefault(true)
				    .build();

			Account a1 = Account.builder()
			    .name("Dinheiro")
			    .user(u1)
			    .build();

			Account a2 = Account.builder()
			    .name("Inter")
			    .user(u2)
			    .build();

			Transaction t1 = Transaction.builder()
			    .title("Salario dia 5")
			    .description("salario")
			    .amount(new BigDecimal("1550.0"))
			    .date(LocalDate.of(2025, 6, 6))
			    .type(TransactionType.REVENUE)
			    .user(u1)
			    .category(c1)
			    .account(a1)
			    .build();

			Transaction t2 = Transaction.builder()
			    .title("Vale")
			    .description("salario")
			    .amount(new BigDecimal("1800.0"))
			    .date(LocalDate.of(2025, 6, 6))
			    .type(TransactionType.REVENUE)
			    .user(u2)
			    .category(c2)
			    .account(a2)
			    .build();

			Goal g1 = Goal.builder()
			    .name("comprar casa")
			    .targetAmount(new BigDecimal("100000"))
			    .targetDate(LocalDate.of(2030, 2, 1))
			    .user(u1)
			    .build();

			Goal g2 = Goal.builder()
			    .name("comprar carro")
			    .targetAmount(new BigDecimal("60000"))
			    .targetDate(LocalDate.of(2030, 2, 1))
			    .user(u2)
			    .build();

			roleRepository.saveAll(Arrays.asList(adminRole, customerRole));
			userRepository.saveAll(Arrays.asList(u1, u2));
			accountRepository.saveAll(Arrays.asList(a1, a2));
			categoryRepository.saveAll(Arrays.asList(c1, c2, c3));
			goalRepository.saveAll(Arrays.asList(g1, g2));
			transactionRepository.saveAll(Arrays.asList(t1, t2));
	}

}
