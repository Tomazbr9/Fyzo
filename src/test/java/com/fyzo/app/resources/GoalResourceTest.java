package com.fyzo.app.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
import com.fyzo.app.dto.goal.GoalRequestDTO;
import com.fyzo.app.dto.goal.GoalResponseDTO;
import com.fyzo.app.dto.goal.GoalUpdateDTO;
import com.fyzo.app.security.filter.UserAuthenticationFilter;
import com.fyzo.app.security.jwt.JwtTokenService;
import com.fyzo.app.services.GoalService;

@WebMvcTest(GoalResource.class)
@AutoConfigureMockMvc(addFilters = false)
public class GoalResourceTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean 
	private GoalService goalService;
	
	@MockBean
	private UserAuthenticationFilter filter;
		
	@MockBean
	private JwtTokenService jwtTokenService;
		

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setup() {
	    reset(goalService);
	 }
	
	
	@Test
	void shouldReturnAListOfSuccessfullyTargets() throws Exception {
		var response = List.of(
				new GoalResponseDTO(1L, "Comprar um Carro", new BigDecimal("50.0"), new BigDecimal("30.500"), LocalDate.of(2025, 1, 1), false),
				new GoalResponseDTO(2L, "Comprar uma Casa", new BigDecimal("300.7"), new BigDecimal("80.700"), LocalDate.of(2030, 5, 1), false)
		);
		
		when(goalService.findAll(any())).thenReturn(response);
		
		mockMvc.perform(get("/goals/me"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(2))
			.andExpect(jsonPath("$.[0].name").value("Comprar um Carro"))
			.andExpect(jsonPath("$.[0].targetAmount").value(new BigDecimal("50.0")))
			.andExpect(jsonPath("$.[1].name").value("Comprar uma Casa"))
			.andExpect(jsonPath("$.[1].targetAmount").value(new BigDecimal("300.7")));
		
		verify(goalService).findAll(any());
	}
	
	@Test
	void shouldCreteGoalSuccessfully() throws Exception {
		var request = new GoalRequestDTO("Comprar um Carro", new BigDecimal("50.0"), LocalDate.of(2025, 1, 1));
		var response = new GoalResponseDTO(1L, "Comprar um Carro", new BigDecimal("50.0"), new BigDecimal("0.0"), LocalDate.of(2025, 1, 1), false);
		
		when(goalService.create(any(), any())).thenReturn(response);
		
		mockMvc.perform(post("/goals/create").with(csrf())
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)))
			.andExpect(jsonPath("$.name").value("Comprar um Carro"))
			.andExpect(jsonPath("$.targetAmount").value(new BigDecimal("50.0")));
		
		verify(goalService).create(any(), any());
			
		
	}
	
	@Test
	void shouldUpdateGoalSuccessfully() throws Exception {
		var goalId = 1L;
		var request = new GoalUpdateDTO("Comprar um Carro", new BigDecimal("50.0"),  new BigDecimal("50.0"), LocalDate.of(2025, 1, 1));
		var response = new GoalResponseDTO(1L, "Comprar um Carro", new BigDecimal("50.0"), new BigDecimal("50.0"), LocalDate.of(2025, 1, 1), false);
		
		when(goalService.update(any(), any(), any())).thenReturn(response);
		
		mockMvc.perform(patch("/goals/update/{id}", goalId).with(csrf())
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)))
			.andExpect(jsonPath("$.name").value("Comprar um Carro"))
			.andExpect(jsonPath("$.targetAmount").value(new BigDecimal("50.0")));
		
		verify(goalService).update(any(), any(), any());
			
		
	}
	
	@Test
    void shouldDeleteGoalSuccessfully() throws Exception {
        Long goalId = 1L;
        
        doNothing().when(goalService).delete(any(), any());

        mockMvc.perform(delete("/goals/delete/{id}", goalId).with(csrf()))
            .andExpect(status().isNoContent());

        verify(goalService).delete(any(), any());
    }
	
	
}
