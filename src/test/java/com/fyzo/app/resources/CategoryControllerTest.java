package com.fyzo.app.resources;

import static org.mockito.ArgumentMatchers.any;
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
import com.fyzo.app.dto.category.CategoryRequestDTO;
import com.fyzo.app.dto.category.CategoryResponseDTO;
import com.fyzo.app.dto.category.CategoryUpdateDTO;
import com.fyzo.app.enums.TransactionType;
import com.fyzo.app.security.filter.UserAuthenticationFilter;
import com.fyzo.app.security.jwt.JwtTokenService;
import com.fyzo.app.services.CategoryService;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean 
	private CategoryService categoryService;
	
	@MockBean
	private UserAuthenticationFilter filter;
		
	@MockBean
	private JwtTokenService jwtTokenService;
		

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setup() {
	    reset(categoryService);
	 }
	
	
	@Test
	void shouldReturnAListOfCategoriesSuccessfully() throws Exception {
		var response = List.of(
				new CategoryResponseDTO(1L, "Aluguel", TransactionType.EXPENSE, "#000000"),
				new CategoryResponseDTO(2L, "Salario", TransactionType.REVENUE, "#000000")
		);
		
		when(categoryService.findAll(any())).thenReturn(response);
		
		mockMvc.perform(get("/categories/me"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(2))
			.andExpect(jsonPath("$.[0].name").value("Aluguel"))
			.andExpect(jsonPath("$.[0].type").value("EXPENSE"))
			.andExpect(jsonPath("$.[1].name").value("Salario"))
			.andExpect(jsonPath("$.[1].type").value("REVENUE"));
		
		verify(categoryService).findAll(any());
	}
	
	@Test
	void shouldCreteCategorySuccessfully() throws Exception {
		var request = new CategoryRequestDTO("Aluguel", TransactionType.EXPENSE, "#000000");
		var response = new CategoryResponseDTO(1L, "Aluguel", TransactionType.EXPENSE, "#000000");
		
		when(categoryService.create(any(), any())).thenReturn(response);
		
		mockMvc.perform(post("/categories/create").with(csrf())
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)))
			.andExpect(jsonPath("$.name").value("Aluguel"))
			.andExpect(jsonPath("$.type").value("EXPENSE"));
		
		verify(categoryService).create(any(), any());
			
		
	}
	
	@Test
	void shouldUpdateCategorySuccessfully() throws Exception {
		var categoryId = 1L;  
		var request = new CategoryUpdateDTO("Aluguel", TransactionType.EXPENSE, "#000000");
		var response = new CategoryResponseDTO(1L, "Aluguel", TransactionType.EXPENSE, "#000000");
		
		when(categoryService.update(any(), any(), any())).thenReturn(response);
		
		mockMvc.perform(patch("/categories/update/{id}", categoryId).with(csrf())
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request)))
			.andExpect(jsonPath("$.name").value("Aluguel"))
			.andExpect(jsonPath("$.type").value("EXPENSE"));
		
		verify(categoryService).update(any(), any(), any());
			
		
	}
	
	@Test
    void shouldDeleteCatgorySuccessfully() throws Exception {
        Long categoryId = 1L;
        
        doNothing().when(categoryService).delete(any(), any());

        mockMvc.perform(delete("/categories/delete/{id}", categoryId).with(csrf()))
            .andExpect(status().isNoContent());

        verify(categoryService).delete(any(), any());
    }
	
	
}
