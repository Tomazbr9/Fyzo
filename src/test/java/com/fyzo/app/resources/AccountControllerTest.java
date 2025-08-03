package com.fyzo.app.resources;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
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
import com.fyzo.app.dto.account.AccountRequestDTO;
import com.fyzo.app.dto.account.AccountResponseDTO;
import com.fyzo.app.dto.account.AccountUpdateDTO;
import com.fyzo.app.security.filter.UserAuthenticationFilter;
import com.fyzo.app.security.jwt.JwtTokenService;
import com.fyzo.app.services.AccountService;

@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AccountControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;
    
    @MockBean
	private UserAuthenticationFilter filter;
	
	@MockBean
    private JwtTokenService jwtTokenService;
	

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        reset(accountService);
    }
    
    @Test
    void shouldReturnAccountListForAuthenticatedUser() throws Exception {
        
        var mockResponse = List.of(
            new AccountResponseDTO("Conta Corrente", new BigDecimal("100.0")),
            new AccountResponseDTO("Conta Poupança", new BigDecimal("50.0"))
        );

        when(accountService.findAll(any())).thenReturn(mockResponse);

        mockMvc.perform(get("/accounts/me"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].name").value("Conta Corrente"))
            .andExpect(jsonPath("$[0].balance").value("100.0"))
            .andExpect(jsonPath("$[1].name").value("Conta Poupança"))
        	.andExpect(jsonPath("$[1].balance").value("50.0"));

        verify(accountService).findAll(any());
    }

    
    @Test
    void shouldCreateAccountSuccessfully() throws Exception {
    	var request = new AccountRequestDTO("Dinheiro");
    	var response = new AccountResponseDTO("Dinheiro", new BigDecimal("0.0"));
    	
    	when(accountService.create(any(), any())).thenReturn(response);
    	
    	mockMvc.perform(post("/accounts/create").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Dinheiro"))
            .andExpect(jsonPath("$.balance").value("0.0"));

        verify(accountService).create(any(), any());
    }
    
    @Test
    void shouldUpdateAccountSuccessfully() throws Exception {
    	Long accountId = 1L;
    	var request = new AccountUpdateDTO("Inter");
    	var response = new AccountResponseDTO("Inter", new BigDecimal("0.0"));
    	
    	when(accountService.update(any(), any(), any())).thenReturn(response);
    	
    	mockMvc.perform(patch("/accounts/update/{id}", accountId).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Inter"))
            .andExpect(jsonPath("$.balance").value("0.0"));

        verify(accountService).update(any(), any(), any());
    }
    
    @Test
    void shouldDeleteAccountSuccessfully() throws Exception {
        Long accountId = 1L;
        doNothing().when(accountService).delete(eq(accountId), any());

        mockMvc.perform(delete("/accounts/delete/{id}", accountId).with(csrf()))
            .andExpect(status().isNoContent());

        verify(accountService).delete(eq(accountId), any());
    }
    
}
