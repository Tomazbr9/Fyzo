package com.fyzo.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import com.fyzo.app.dto.user.UserResponseDTO;
import com.fyzo.app.entities.User;
import com.fyzo.app.mapper.UserMapper;
import com.fyzo.app.repositories.RoleRepository;
import com.fyzo.app.repositories.UserRepository;
import com.fyzo.app.security.entities.UserDetailsImpl;
import com.fyzo.app.services.UserService;
import com.fyzo.app.services.finder.UserFinder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@InjectMocks
	private UserService userService;
	
	@Mock 
	private RoleRepository roleRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock 
	private UserMapper userMapper;
	
	@Mock 
	private UserFinder userFinder;
	
	@Test
	void shouldReturnUserDataSuccessfully() throws Exception {
		
		UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        UserResponseDTO userResponseDTO = new UserResponseDTO("user", "email");
        User userEntity = new User();

        when(userFinder.findByUsernameOrThrow(userDetails)).thenReturn(userEntity);
        when(userMapper.toResponse(userEntity)).thenReturn(userResponseDTO);
        
   
        UserResponseDTO result = userService.findByUser(userDetails);
        
        assertNotNull(result);
        assertEquals(result.username(), "user");
        assertEquals(result.email(), "email");
		
        verify(userFinder).findByUsernameOrThrow(userDetails);
        verify(userMapper).toResponse(userEntity);
        
        
	}
	
}
