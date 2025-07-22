package com.fyzo.app.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyzo.app.config.TestSecurityConfig;
import com.fyzo.app.dto.user.UserResponseDTO;
import com.fyzo.app.dto.user.UserUpdateDTO;
import com.fyzo.app.entities.Role;
import com.fyzo.app.entities.User;
import com.fyzo.app.enums.RoleName;
import com.fyzo.app.security.entities.UserDetailsImpl;
import com.fyzo.app.security.filter.UserAuthenticationFilter;
import com.fyzo.app.security.jwt.JwtTokenService;
import com.fyzo.app.services.UserService;

@Import(TestSecurityConfig.class)
@WebMvcTest(UserResource.class)
public class UserResourceTest {
	
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
		
		
		UserResponseDTO mockUser = new UserResponseDTO("user", "user@gmail.com");
		
		User existingUser = new User();
	    existingUser.setId(1L);
	    existingUser.setUsername("user");
	    existingUser.setEmail("user@gmail.com");
	    existingUser.setPassword("passowrd");
	    existingUser.setRoles(Collections.singletonList(new Role(1L, RoleName.ROLE_CUSTOMER)));
		
	    UserDetailsImpl userDetails = new UserDetailsImpl(existingUser);
	    
		when(userService.findById(userDetails)).thenReturn(mockUser);
		
		System.out.println(objectMapper.writeValueAsString(mockUser));

		mockMvc.perform(get("/users/me").with(user(userDetails))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("user"))
				.andExpect(jsonPath("$.email").value("user@gmail.com"));
		
		
	}
	
	@Test
	void shouldUpdateUserSuccessFully() throws Exception {
	   
	    UserUpdateDTO updateDTO = new UserUpdateDTO("newUser", "new@gmail.com", "newpassword");
	    
	    User existingUser = new User();
	    existingUser.setId(1L);
	    existingUser.setUsername("oldUser");
	    existingUser.setEmail("old@gmail.com");
	    existingUser.setPassword("oldPassword");
	    existingUser.setRoles(Collections.singletonList(new Role(1L, RoleName.ROLE_CUSTOMER)));
	    
	    UserDetailsImpl userDetails = new UserDetailsImpl(existingUser);
	    
	    mockMvc.perform(patch("/users/update").with(user(userDetails))
	        .contentType(MediaType.APPLICATION_JSON)
	        .content(objectMapper.writeValueAsString(updateDTO))
	        .accept(MediaType.APPLICATION_JSON))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.username").value("newUser"))
	        .andExpect(jsonPath("$.email").value("new@gmail.com"));
	}
	
	@Test
	void shouldDeleteUser() throws Exception {
		User fakeUser = new User();
		fakeUser.setId(1L);
		fakeUser.setUsername("user");
		fakeUser.setEmail("user@gmail.com");
		fakeUser.setPassword("password");
		fakeUser.setRoles(Collections.singletonList(new Role(1L, RoleName.ROLE_CUSTOMER)));
		
		UserDetailsImpl userDetails = new UserDetailsImpl(fakeUser);
		
		doNothing().when(userService).delete(any(UserDetailsImpl.class));
		
		mockMvc.perform(delete("/users/delete").with(user(userDetails))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}
	
}

