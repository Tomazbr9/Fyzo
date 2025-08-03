package com.fyzo.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.fyzo.app.dto.transaction.TransactionFilterDTO;
import com.fyzo.app.dto.transaction.TransactionRequestDTO;
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
import com.fyzo.app.services.TransactionService;
import com.fyzo.app.services.finder.AccountFinder;
import com.fyzo.app.services.finder.CategoryFinder;
import com.fyzo.app.services.finder.TransactionFinder;
import com.fyzo.app.services.finder.UserFinder;
import com.fyzo.app.specification.TransactionSpecification;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @Mock
    private UserFinder userFinder;

    @Mock
    private AccountFinder accountFinder;

    @Mock
    private CategoryFinder categoryFinder;

    @Mock
    private TransactionFinder transactionFinder;

//    @Test
//    void shouldReturnPagedTransactionsWithFilters() {
//        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
//        User user = new User();
//
//        TransactionFilterDTO filterDTO = new TransactionFilterDTO(
//            null, null, null, null, null, null, null, 0, 10
//        );
//
//        Page<Transaction> page = new PageImpl<>(List.of(new Transaction()));
//
//        when(userFinder.findByUsernameOrThrow(userDetails)).thenReturn(user);
//
//        Specification<Transaction> spec = mock(Specification.class);
//        try (MockedStatic<TransactionSpecification> utilities = Mockito.mockStatic(TransactionSpecification.class)) {
//            utilities.when(() -> TransactionSpecification.withFilters(filterDTO, user)).thenReturn(spec);
//            when(transactionRepository.findAll(spec, PageRequest.of(filterDTO.page(), filterDTO.size()))).thenReturn(page);
//
//            Page<Transaction> result = transactionService.findAll(filterDTO, userDetails);
//
//            assertNotNull(result);
//            assertEquals(page, result);
//
//            verify(userFinder).findByUsernameOrThrow(userDetails);
//            utilities.verify(() -> TransactionSpecification.withFilters(filterDTO, user));
//            verify(transactionRepository).findAll(spec, PageRequest.of(filterDTO.page(), filterDTO.size()));
//        }
//    }

 
    @Test
    void shouldCreateTransaction() {
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        User user = new User();
        Category category = new Category();
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(100));

        TransactionRequestDTO dto = new TransactionRequestDTO(
            "Título da Transação", "Descrição aqui",
            BigDecimal.valueOf(50), LocalDate.now(),
            TransactionType.REVENUE, 1L, 1L
        );

        Transaction transaction = new Transaction();
        TransactionResponseDTO responseDTO = new TransactionResponseDTO(
            dto.title(), dto.description(), dto.amount(), dto.date()
        );

        when(userFinder.findByUsernameOrThrow(userDetails)).thenReturn(user);
        when(categoryFinder.findByIdAndUserOrThrow(dto.categoryId(), user)).thenReturn(category);
        when(accountFinder.findByIdAndUserOrThrow(dto.accountId(), user)).thenReturn(account);
        when(transactionMapper.toEntity(dto)).thenReturn(transaction);
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        when(transactionMapper.toResponse(transaction)).thenReturn(responseDTO);

        TransactionResponseDTO response = transactionService.create(dto, userDetails);

        assertNotNull(response);
        assertEquals(dto.title(), response.title());
        assertEquals(dto.description(), response.description());
        assertEquals(dto.amount(), response.amount());
        assertEquals(dto.date(), response.date());
        assertEquals(BigDecimal.valueOf(150), account.getBalance());

        verify(userFinder).findByUsernameOrThrow(userDetails);
        verify(categoryFinder).findByIdAndUserOrThrow(dto.categoryId(), user);
        verify(accountFinder).findByIdAndUserOrThrow(dto.accountId(), user);
        verify(transactionMapper).toEntity(dto);
        verify(transactionRepository).save(transaction);
        verify(transactionMapper).toResponse(transaction);
        verify(accountRepository).save(account);
    }

  
    @Test
    void shouldUpdateTransaction() {
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        User user = new User();
        Long id = 1L;

        Transaction transaction = new Transaction();

        TransactionUpdateDTO dto = new TransactionUpdateDTO(
            "Título Atualizado", "Descrição Atualizada",
            BigDecimal.valueOf(75), LocalDate.now(),
            TransactionType.EXPENSE, 2L, 3L
        );

        Category category = new Category();
        Account account = new Account();

        TransactionResponseDTO responseDTO = new TransactionResponseDTO(
            dto.title(), dto.description(), dto.amount(), dto.date()
        );

        when(userFinder.findByUsernameOrThrow(userDetails)).thenReturn(user);
        when(transactionFinder.findByIdAndUserOrThrow(id, user)).thenReturn(transaction);
        doNothing().when(transactionMapper).updateFromDto(dto, transaction);
        when(categoryFinder.findByIdOrThrow(dto.categoryId())).thenReturn(category);
        when(accountFinder.findByIdOrThrow(dto.accountId())).thenReturn(account);
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        when(transactionMapper.toResponse(transaction)).thenReturn(responseDTO);

        TransactionResponseDTO response = transactionService.update(id, dto, userDetails);

        assertNotNull(response);
        assertEquals(dto.title(), response.title());
        assertEquals(dto.description(), response.description());
        assertEquals(dto.amount(), response.amount());
        assertEquals(dto.date(), response.date());
        assertEquals(category, transaction.getCategory());
        assertEquals(account, transaction.getAccount());

        verify(userFinder).findByUsernameOrThrow(userDetails);
        verify(transactionFinder).findByIdAndUserOrThrow(id, user);
        verify(transactionMapper).updateFromDto(dto, transaction);
        verify(categoryFinder).findByIdOrThrow(dto.categoryId());
        verify(accountFinder).findByIdOrThrow(dto.accountId());
        verify(transactionRepository).save(transaction);
        verify(transactionMapper).toResponse(transaction);
    }

    
    @Test
    void shouldDeleteTransaction() {
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        User user = new User();
        Long id = 1L;
        Transaction transaction = new Transaction();

        when(userFinder.findByUsernameOrThrow(userDetails)).thenReturn(user);
        when(transactionFinder.findByIdAndUserOrThrow(id, user)).thenReturn(transaction);

        transactionService.delete(id, userDetails);

        verify(userFinder).findByUsernameOrThrow(userDetails);
        verify(transactionFinder).findByIdAndUserOrThrow(id, user);
        verify(transactionRepository).delete(transaction);
    }

    @Test
    void shouldCalculateUserBalanceCorrectly() {
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        User user = new User();

        Transaction t1 = new Transaction();
        t1.setType(TransactionType.REVENUE);
        t1.setAmount(BigDecimal.valueOf(200));

        Transaction t2 = new Transaction();
        t2.setType(TransactionType.EXPENSE);
        t2.setAmount(BigDecimal.valueOf(50));

        List<Transaction> transactions = List.of(t1, t2);

        when(userFinder.findByUsernameOrThrow(userDetails)).thenReturn(user);
        when(transactionRepository.findByUser(user)).thenReturn(transactions);

        var balance = transactionService.getUserBalance(userDetails);

        assertEquals(BigDecimal.valueOf(200), balance.totalRevenue());
        assertEquals(BigDecimal.valueOf(50), balance.totalExpense());

        verify(userFinder).findByUsernameOrThrow(userDetails);
        verify(transactionRepository).findByUser(user);
    }

    @Test
    void shouldReturnSummaryGroupedByCategory() {
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        User user = new User();
        TransactionType type = TransactionType.EXPENSE;

        Category cat1 = new Category();
        cat1.setId(1L);
        cat1.setName("Alimentação");

        Category cat2 = new Category();
        cat2.setId(2L);
        cat2.setName("Transporte");

        Transaction tr1 = new Transaction();
        tr1.setCategory(cat1);
        tr1.setAmount(BigDecimal.valueOf(100));

        Transaction tr2 = new Transaction();
        tr2.setCategory(cat1);
        tr2.setAmount(BigDecimal.valueOf(50));

        Transaction tr3 = new Transaction();
        tr3.setCategory(cat2);
        tr3.setAmount(BigDecimal.valueOf(30));

        List<Transaction> transactions = List.of(tr1, tr2, tr3);

        when(userFinder.findByUsernameOrThrow(userDetails)).thenReturn(user);
        when(transactionRepository.findByUserAndType(user, type)).thenReturn(transactions);

        var summary = transactionService.getSummaryByType(userDetails, type);

        assertEquals(2, summary.size());

        var dto1 = summary.stream().filter(s -> s.id() == 1L).findFirst().orElse(null);
        var dto2 = summary.stream().filter(s -> s.id() == 2L).findFirst().orElse(null);

        assertNotNull(dto1);
        assertEquals("Alimentação", dto1.categoryName());
        assertEquals(BigDecimal.valueOf(150), dto1.total());

        assertNotNull(dto2);
        assertEquals("Transporte", dto2.categoryName());
        assertEquals(BigDecimal.valueOf(30), dto2.total());

        verify(userFinder).findByUsernameOrThrow(userDetails);
        verify(transactionRepository).findByUserAndType(user, type);
    }
}
