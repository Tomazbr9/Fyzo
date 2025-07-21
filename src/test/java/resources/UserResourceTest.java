package resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyzo.app.dto.user.UserResponseDTO;
import com.fyzo.app.dto.user.UserUpdateDTO;
import com.fyzo.app.entities.User;
import com.fyzo.app.resources.UserResource;
import com.fyzo.app.security.entities.UserDetailsImpl;
import com.fyzo.app.services.UserService;

@WebMvcTest(UserResource.class)
public class UserResourceTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserService service;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	
	@Test
	void shouldReturnUserById() throws Exception {
		
		Long id = 1L;
		UserResponseDTO mockUser = new UserResponseDTO("user", "user@gmail.com");
		
		when(service.findById(id)).thenReturn(mockUser);
		
		mockMvc.perform(get("/users/{id}", id)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("user"))
				.andExpect(jsonPath("$.email").value("user@gmail.com"));
	}
	
	@Test
	void shouldUpdateUserSuccessFully() throws Exception {
		UserUpdateDTO updateDTO = new UserUpdateDTO("newUser", "new@gmail.com", "newpassword");
		
		User fakeUser = new User();
		fakeUser.setId(1L);
		fakeUser.setUsername("user");
		fakeUser.setEmail("user@gmail.com");
		fakeUser.setPassword("password");
		
		UserDetailsImpl userDetails = new UserDetailsImpl(fakeUser);
		UserResponseDTO responseDTO = new UserResponseDTO("newUser", "new@gmail.com");
		
		when(service.update(any(UserUpdateDTO.class), any(UserDetailsImpl.class))).thenReturn(responseDTO);
		
		mockMvc.perform(patch("/users/update").with(user(userDetails))
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(updateDTO))
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.username").value("newUser"))
			.andExpect(jsonPath("$.email").value("new@gmail.com"))
			.andExpect(jsonPath("$.password").value("newPassword"));
	}
	
	@Test
	void shouldDeleteUser() throws Exception {
		User fakeUser = new User();
		fakeUser.setId(1L);
		fakeUser.setUsername("user");
		fakeUser.setEmail("user@gmail.com");
		fakeUser.setPassword("password");
		
		UserDetailsImpl userDetails = new UserDetailsImpl(fakeUser);
		
		doNothing().when(service).delete(any(UserDetailsImpl.class));
		
		mockMvc.perform(delete("/users/delete").with(user(userDetails))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}
	
}
