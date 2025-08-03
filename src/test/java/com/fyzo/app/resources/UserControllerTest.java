package com.fyzo.app.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyzo.app.dto.user.UserResponseDTO;
import com.fyzo.app.dto.user.UserUpdateDTO;
import com.fyzo.app.entities.Role;
import com.fyzo.app.entities.User;
import com.fyzo.app.enums.RoleName;
import com.fyzo.app.security.entities.UserDetailsImpl;
import com.fyzo.app.security.filter.UserAuthenticationFilter;
import com.fyzo.app.security.jwt.JwtTokenService;
import com.fyzo.app.services.UserService;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;
	
	@MockBean
	private UserAuthenticationFilter filter;
	
	@MockBean
    private JwtTokenService jwtTokenService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void resetMocks() {
		reset(userService);
	}
	
	@Test
	void shouldReturnUserById() throws Exception {
		
		var response = new UserResponseDTO("user", "user@gmail.com");
	    
		when(userService.findByUser(any())).thenReturn(response);

		mockMvc.perform(get("/users/me").with(csrf())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("user"))
				.andExpect(jsonPath("$.email").value("user@gmail.com"));
		
		verify(userService).findByUser(any());
	}
	
	@Test
	void shouldUpdateUserSuccessFully() throws Exception {
	   
	    var request = new UserUpdateDTO("newUser", "new@gmail.com", "newpassword");
	    var response = new UserResponseDTO("newUser", "new@gmail.com");
	    
	    when(userService.update(any(), any())).thenReturn(response);
	    
	    mockMvc.perform(patch("/users/update").with(csrf())
	        .contentType(MediaType.APPLICATION_JSON)
	        .content(objectMapper.writeValueAsString(request))
	        .accept(MediaType.APPLICATION_JSON))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.username").value("newUser"))
	        .andExpect(jsonPath("$.email").value("new@gmail.com"));
	    
	    verify(userService).update(any(), any());
	}
	
	@Test
	void shouldDeleteUser() throws Exception {
		
		doNothing().when(userService).delete(any());
		
		mockMvc.perform(delete("/users/delete").with(csrf())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
		
		verify(userService).delete(any());
	}
	
}

