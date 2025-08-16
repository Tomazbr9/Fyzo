//package com.fyzo.app.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import com.fyzo.app.dto.account.AccountRequestDTO;
//import com.fyzo.app.dto.account.AccountResponseDTO;
//import com.fyzo.app.dto.account.AccountUpdateDTO;
//import com.fyzo.app.entities.Account;
//import com.fyzo.app.entities.User;
//import com.fyzo.app.mapper.AccountMapper;
//import com.fyzo.app.repositories.AccountRepository;
//import com.fyzo.app.security.entities.UserDetailsImpl;
//import com.fyzo.app.services.AccountService;
//import com.fyzo.app.services.finder.AccountFinder;
//import com.fyzo.app.services.finder.UserFinder;
//
//@ExtendWith(MockitoExtension.class)
//class AccountServiceTest {
//
//    @InjectMocks
//    private AccountService accountService;
//
//    @Mock
//    private AccountRepository accountRepository;
//
//    @Mock
//    private AccountMapper accountMapper;
//
//    @Mock
//    private AccountFinder accountFinder;
//
//    @Mock
//    private UserFinder userFinder;
//
//    @Test
//    void shouldReturnAllAccountsForAuthenticatedUser() {
//        
//        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
//        User user = new User();
//        List<Account> accounts = List.of(
//                new Account(1L, "Carteira", new BigDecimal("100.0"), user),
//                new Account(2L, "Banco", new BigDecimal("1000.0"), user)
//        );
//
//        List<AccountResponseDTO> expectedDTOs = List.of(
//                new AccountResponseDTO("Carteira", new BigDecimal("100.0")),
//                new AccountResponseDTO("Banco", new BigDecimal("1000.0"))
//        );
//
//        when(userFinder.findByUsernameOrThrow(userDetails)).thenReturn(user);
//        when(accountRepository.findByUser(user)).thenReturn(accounts);
//        when(accountMapper.accountFromAccountDTO(accounts)).thenReturn(expectedDTOs);
//
//        
//        List<AccountResponseDTO> result = accountService.findAll(userDetails);
//        
//        
//        assertNotNull(result);
//        assertEquals(expectedDTOs, result);
//
//        verify(userFinder).findByUsernameOrThrow(userDetails);
//        verify(accountRepository).findByUser(user);
//        verify(accountMapper).accountFromAccountDTO(accounts);
//    }
//
//    @Test
//    void shouldCreateAccountForAuthenticatedUser() {
//        
//        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
//        User user = new User();
//        AccountRequestDTO dto = new AccountRequestDTO("Banco");
//        Account account = new Account();
//        account.setName("Banco");
//        account.setBalance(new BigDecimal("1500.0"));
//        AccountResponseDTO expectedResponse = new AccountResponseDTO("Banco", new BigDecimal("1500.0"));
//
//        when(userFinder.findByUsernameOrThrow(userDetails)).thenReturn(user);
//        when(accountMapper.toEntity(dto)).thenReturn(account);
//        when(accountRepository.save(account)).thenReturn(account);
//        when(accountMapper.toResponse(account)).thenReturn(expectedResponse);
//
//        AccountResponseDTO response = accountService.create(dto, userDetails);
//
//        assertNotNull(response);
//        assertEquals(expectedResponse, response);
//
//        verify(userFinder).findByUsernameOrThrow(userDetails);
//        verify(accountMapper).toEntity(dto);
//        verify(accountRepository).save(account);
//        verify(accountMapper).toResponse(account);
//    }
//
//    @Test
//    void shouldUpdateAccountWithAuthenticatedUser() {
//        
//        Long id = 1L;
//        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
//        User user = new User();
//        AccountUpdateDTO dto = new AccountUpdateDTO("Atualizado");
//        Account account = new Account();
//        account.setId(id);
//        account.setName("Antigo");
//        account.setBalance(new BigDecimal("1000.0"));
//
//        AccountResponseDTO expectedResponse = new AccountResponseDTO("Atualizado", new BigDecimal("2000.0"));
//
//        when(userFinder.findByUsernameOrThrow(userDetails)).thenReturn(user);
//        when(accountFinder.findByIdAndUserOrThrow(id, user)).thenReturn(account);
//        doNothing().when(accountMapper).updateFromDto(dto, account);
//        when(accountRepository.save(account)).thenReturn(account);
//        when(accountMapper.toResponse(account)).thenReturn(expectedResponse);
//
//        AccountResponseDTO response = accountService.update(id, dto, userDetails);
// 
//        assertNotNull(response);
//        assertEquals(expectedResponse, response);
//
//        verify(userFinder).findByUsernameOrThrow(userDetails);
//        verify(accountFinder).findByIdAndUserOrThrow(id, user);
//        verify(accountMapper).updateFromDto(dto, account);
//        verify(accountRepository).save(account);
//        verify(accountMapper).toResponse(account);
//    }
//
//    @Test
//    void shouldDeleteAccountWithAuthenticatedUser() {
//        
//        Long id = 1L;
//        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
//        User user = new User();
//        Account account = new Account();
//
//        when(userFinder.findByUsernameOrThrow(userDetails)).thenReturn(user);
//        when(accountFinder.findByIdAndUserOrThrow(id, user)).thenReturn(account);
//
//        accountService.delete(id, userDetails);
//
//        verify(userFinder).findByUsernameOrThrow(userDetails);
//        verify(accountFinder).findByIdAndUserOrThrow(id, user);
//        verify(accountRepository).delete(account);
//    }
//}
