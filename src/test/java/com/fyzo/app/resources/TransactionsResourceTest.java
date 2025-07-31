package com.fyzo.app.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyzo.app.dto.dashboard.BalanceResponseDTO;
import com.fyzo.app.dto.dashboard.CategorySummaryDTO;
import com.fyzo.app.dto.transaction.TransactionRequestDTO;
import com.fyzo.app.dto.transaction.TransactionResponseDTO;
import com.fyzo.app.dto.transaction.TransactionUpdateDTO;
import com.fyzo.app.enums.TransactionType;
import com.fyzo.app.security.filter.UserAuthenticationFilter;
import com.fyzo.app.security.jwt.JwtTokenService;
import com.fyzo.app.services.TransactionService;

@WebMvcTest(TransactionResource.class)
@AutoConfigureMockMvc(addFilters = false)
public class TransactionsResourceTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TransactionService transactionService;
	
	@MockBean
	private UserAuthenticationFilter filter;
		
	@MockBean
	private JwtTokenService jwtTokenService;
		

	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void setup() {
	    reset(transactionService);
	}
	
//	@Test
//	void shouldReturnAListOfTransactions() throws Exception {
//		
//		var transactions = List.of(
//				new Transaction(1L, "Pagamento do aluguel", "", new BigDecimal("850.0"), LocalDate.of(2025, 07, 12)),
//				new Transaction(2L, "Compras do mês", "", new BigDecimal("1050.0"), LocalDate.of(2025, 07, 12))
//		);
//		
//		Page<Transaction> response = new PageImpl<>(transactions);
//		
//		when(transactionService.findAll(any(), any())).thenReturn(response);
//		
//		mockMvc.perform(get("/transactions/me"))
//			.andExpect(status().isOk())
//			.andExpect(jsonPath("$[0].title").value("Pagamento do aluguel"))
//			.andExpect(jsonPath("$[0].description").value(""))
//			.andExpect(jsonPath("$[0].amount").value("850.0"))
//			.andExpect(jsonPath("$[0].date").value("2025-07-12"))
//			.andExpect(jsonPath("$[1].title").value("Compras do mês"))
//			.andExpect(jsonPath("$[2].description").value(""))
//			.andExpect(jsonPath("$[3].amount").value("1050.0"))
//			.andExpect(jsonPath("$[4].date").value("2025-07-12"));
//		
//		verify(transactionService).findAll(any(), any());
//				
//	}
	
	@Test
	void shouldCreateTransactionSuccessfully() throws Exception {
		var request = new TransactionRequestDTO("Pagamento do aluguel", "", new BigDecimal("850.0"), LocalDate.of(2025, 07, 12), TransactionType.EXPENSE, 1L, 2L);
	    var response = new TransactionResponseDTO("Pagamento do aluguel", "", new BigDecimal("850.0"), LocalDate.of(2025, 07, 12));
	
	    when(transactionService.create(any(), any())).thenReturn(response);
	    
	    mockMvc.perform(post("/transactions/create")
	    	.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)))
	    	.andExpect(status().isCreated())
	    	.andExpect(jsonPath("$.title").value("Pagamento do aluguel"))
	    	.andExpect(jsonPath("$.description").value(""))
	    	.andExpect(jsonPath("$.amount").value("850.0"))
	    	.andExpect(jsonPath("$.date").value("2025-07-12"));
	    
	    verify(transactionService).create(any(), any());
	
	
	}
	
	@Test
	void shouldUpdateTransactionSuccessfully() throws Exception {
		
		var transactionId = 1L;
		var request = new TransactionUpdateDTO("Pagamento do aluguel", "", new BigDecimal("850.0"), LocalDate.of(2025, 07, 12), TransactionType.EXPENSE, 1L, 2L);
	    var response = new TransactionResponseDTO("Pagamento do aluguel", "", new BigDecimal("850.0"), LocalDate.of(2025, 07, 12));
	
	    when(transactionService.update(any(), any(), any())).thenReturn(response);
	    
	    mockMvc.perform(patch("/transactions/update/{id}", transactionId)
	    	.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)))
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("$.title").value("Pagamento do aluguel"))
	    	.andExpect(jsonPath("$.description").value(""))
	    	.andExpect(jsonPath("$.amount").value("850.0"))
	    	.andExpect(jsonPath("$.date").value("2025-07-12"));
	    
	    verify(transactionService).update(any(), any(), any());
	
	}
	
	@Test
    void shouldDeleteTransactionSuccessfully() throws Exception {
        Long transactionId = 1L;
        
        doNothing().when(transactionService).delete(any(), any());

        mockMvc.perform(delete("/transactions/delete/{id}", transactionId))
            .andExpect(status().isNoContent());

        verify(transactionService).delete(any(), any());
    }
	
	@Test 
	void shouldReturnBalanceEntriesAndExitsSuccessfully() throws Exception {
		var response = new BalanceResponseDTO(new BigDecimal("500.0"), new BigDecimal("200.00"));
		
		when(transactionService.getUserBalance(any())).thenReturn(response);
		
		mockMvc.perform(get("/transactions/balance"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.totalRevenue").value("500.0"))
			.andExpect(jsonPath("$.totalExpense").value("200.0"));
		
		verify(transactionService).getUserBalance(any());
	}
	
	@Test
	void shouldReturnExpenseSummaryByCategory() throws Exception {
		
		
		var response = List.of(
				new CategorySummaryDTO(1L, "Mercado", new BigDecimal("1050.0")),
				new CategorySummaryDTO(2L, "Aluguel", new BigDecimal("850.0"))
				
		);
		
		when(transactionService.getSummaryByType(any(), any())).thenReturn(response);
		
		mockMvc.perform(get("/transactions/summary/expense"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].categoryName").value("Mercado"))
		.andExpect(jsonPath("$.[0].total").value("1050.0"))
		.andExpect(jsonPath("$[1].categoryName").value("Aluguel"))
		.andExpect(jsonPath("$.[1].total").value("850.0"));
		
		verify(transactionService).getSummaryByType(any(), any());
		
	}

}
