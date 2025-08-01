package com.fyzo.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fyzo.app.dto.user.UserResponseDTO;
import com.fyzo.app.dto.user.UserUpdateDTO;
import com.fyzo.app.entities.User;
import com.fyzo.app.mapper.UserMapper;
import com.fyzo.app.repositories.RoleRepository;
import com.fyzo.app.repositories.UserRepository;
import com.fyzo.app.security.SecurityConfig;
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
    private PasswordEncoder passwordEncoder;
	
	@Mock
	private SecurityConfig securityConfig;
	
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
	
	@Test
    void shouldUpdateUserWithNewPassword() {
       
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        User user = new User();
        UserUpdateDTO updateDTO = new UserUpdateDTO("Novo Nome", "novo@email.com", "novasenha123");
        UserResponseDTO expectedResponse = new UserResponseDTO("Novo Nome", "novo@email.com");

        when(userFinder.findByUsernameOrThrow(userDetails)).thenReturn(user);
        doNothing().when(userMapper).updateFromDto(updateDTO, user);
        when(securityConfig.passwordEncoder()).thenReturn(passwordEncoder);
        when(passwordEncoder.encode("novasenha123")).thenReturn("senhaCriptografada");
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(expectedResponse);

        UserResponseDTO response = userService.update(updateDTO, userDetails);
        
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertEquals("senhaCriptografada", user.getPassword());

        verify(userFinder).findByUsernameOrThrow(userDetails);
        verify(userMapper).updateFromDto(updateDTO, user);
        verify(securityConfig).passwordEncoder();
        verify(passwordEncoder).encode("novasenha123");
        verify(userRepository).save(user);
        verify(userMapper).toResponse(user);
    }

    @Test
    void shouldUpdateUserWithoutChangingPassword() {
        
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        User user = new User();
        UserUpdateDTO updateDTO = new UserUpdateDTO("Nome Atualizado", "email@atualizado.com", null);
        UserResponseDTO expectedResponse = new UserResponseDTO("Nome Atualizado", "email@atualizado.com");

        when(userFinder.findByUsernameOrThrow(userDetails)).thenReturn(user);
        doNothing().when(userMapper).updateFromDto(updateDTO, user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(expectedResponse);

        
        UserResponseDTO response = userService.update(updateDTO, userDetails);

        
        assertNotNull(response);
        assertEquals(expectedResponse, response);

        verify(userFinder).findByUsernameOrThrow(userDetails);
        verify(userMapper).updateFromDto(updateDTO, user);
        verify(userRepository).save(user);
        verify(userMapper).toResponse(user);

        verify(securityConfig, never()).passwordEncoder();
        verify(passwordEncoder, never()).encode(any());
    }
    
    @Test
    void shouldDeleteAuthenticatedUser() {
       
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        User user = new User();
        user.setId(1L);

        when(userFinder.findByUsernameOrThrow(userDetails)).thenReturn(user);
        
        userService.delete(userDetails);

        verify(userFinder).findByUsernameOrThrow(userDetails);
        verify(userRepository).delete(user);
    }
	
}
