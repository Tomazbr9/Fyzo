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
import com.fyzo.app.security.SecurityConfig;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private SecurityConfig config;
	
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
			    .username("tomaz")
			    .email("tomaz@gmail.com")
			    .password(config.passwordEncoder().encode("12345"))
			    .roles(List.of(customerRole))
			    .build();

			User u2 = User.builder()
			    .username("Rebeca")
			    .email("rebeca@gmail.com")
			    .password(config.passwordEncoder().encode("12345"))
			    .roles(List.of(customerRole))
			    .build();

			Category c1 = Category.builder()
			    .name("Aluguel")
			    .type(TransactionType.EXPENSE)
			    .icon("home")
			    .color("#ff1a1a")
			    .isDefault(true)
			    .build();

			Category c2 = Category.builder()
			    .name("Salario")
			    .type(TransactionType.REVENUE)
			    .icon("money")
			    .color("#33cc33")
			    .isDefault(true)
			    .build();
			
			Category c3 = Category.builder()
				    .name("Mercado")
				    .type(TransactionType.REVENUE)
				    .icon("shopping_cart")
				    .color("#1a1aff")
				    .isDefault(true)
				    .build();

			Account a1 = Account.builder()
			    .name("Dinheiro")
			    .imageUrl("https://media.istockphoto.com/id/1384322683/pt/foto/many-hundred-and-fifty-reais-banknotes-brazilian-money-grand-prize-payment-salary-on-isolated.jpg?s=612x612&w=0&k=20&c=N8nkbOgAyvamQXfkTSTQyWp_l7meGHjwgo-htv5-83I=")
			    .user(u1)
			    .isDefault(true)
			    .build();

			Account a2 = Account.builder()
			    .name("Inter")
			    .user(u2)
			    .build();

			Transaction t1 = Transaction.builder()
			    .title("Aluguel")
			    .description("")
			    .amount(new BigDecimal("850.0"))
			    .date(LocalDate.of(2025, 8, 5))
			    .type(TransactionType.EXPENSE)
			    .user(u1)
			    .category(c1)
			    .account(a1)
			    .build();

			Transaction t2 = Transaction.builder()
			    .title("Vale")
			    .description("")
			    .amount(new BigDecimal("970.0"))
			    .date(LocalDate.of(2025, 8, 20))
			    .type(TransactionType.REVENUE)
			    .user(u1)
			    .category(c2)
			    .account(a1)
			    .build();
			
			Transaction t3 = Transaction.builder()
				    .title("Mercado")
				    .description("")
				    .amount(new BigDecimal("700.0"))
				    .date(LocalDate.of(2025, 8, 1))
				    .type(TransactionType.EXPENSE)
				    .user(u1)
				    .category(c2)
				    .account(a1)
				    .build();
			
			Transaction t4 = Transaction.builder()
				    .title("Salario")
				    .description("")
				    .amount(new BigDecimal("1500.0"))
				    .date(LocalDate.of(2025, 8, 5))
				    .type(TransactionType.REVENUE)
				    .user(u1)
				    .category(c2)
				    .account(a1)
				    .build();
			
			Transaction t5 = Transaction.builder()
				    .title("Internet")
				    .description("")
				    .amount(new BigDecimal("200.0"))
				    .date(LocalDate.of(2025, 8, 4))
				    .type(TransactionType.EXPENSE)
				    .user(u1)
				    .category(c2)
				    .account(a1)
				    .build();


			roleRepository.saveAll(Arrays.asList(adminRole, customerRole));
			userRepository.saveAll(Arrays.asList(u1, u2));
			accountRepository.saveAll(Arrays.asList(a1, a2));
			categoryRepository.saveAll(Arrays.asList(c1, c2, c3));
			transactionRepository.saveAll(Arrays.asList(t1, t2, t3, t4, t5));
	}

}
