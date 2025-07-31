package com.fyzo.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fyzo.app.dto.auth.JwtTokenDTO;
import com.fyzo.app.dto.auth.LoginDTO;
import com.fyzo.app.dto.user.UserRequestDTO;
import com.fyzo.app.dto.user.UserResponseDTO;
import com.fyzo.app.entities.Role;
import com.fyzo.app.entities.User;
import com.fyzo.app.enums.RoleName;
import com.fyzo.app.mapper.UserMapper;
import com.fyzo.app.repositories.CategoryRepository;
import com.fyzo.app.repositories.RoleRepository;
import com.fyzo.app.repositories.UserRepository;
import com.fyzo.app.security.SecurityConfig;
import com.fyzo.app.security.entities.UserDetailsImpl;
import com.fyzo.app.security.jwt.JwtTokenService;
import com.fyzo.app.services.AuthenticationService;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private SecurityConfig securityConfig;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenService jwtTokenService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    void shouldAuthenticateUserAndReturnToken() {
       
        String username = "user";
        String password = "pass";
        LoginDTO loginDTO = new LoginDTO(username, password);

        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(username, password);

        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(authRequest)).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtTokenService.generateToken(userDetails)).thenReturn("mocked-jwt-token");

        JwtTokenDTO result = authenticationService.authenticateUser(loginDTO);

        assertNotNull(result);
        assertEquals("mocked-jwt-token", result.token());

        verify(authenticationManager).authenticate(authRequest);
        verify(jwtTokenService).generateToken(userDetails);
    }
    
    @Test
    void shouldRegisterUserSuccessfully() {
        
        String password = "123456";
        String encodedPassword = "encoded_123456";
        RoleName roleName = RoleName.ROLE_CUSTOMER;

        UserRequestDTO dto = new UserRequestDTO("John", "john@example.com", password, "ROLE_CUSTOMER");
        User userEntity = new User();
        UserResponseDTO userResponseDTO = new UserResponseDTO("John", "john@example.com");

        Role role = new Role(1L, roleName);

        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

        when(userMapper.toEntity(dto)).thenReturn(userEntity);
        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(role));
        when(securityConfig.passwordEncoder()).thenReturn(passwordEncoder);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toResponse(userEntity)).thenReturn(userResponseDTO);

        
        UserResponseDTO result = authenticationService.registerUser(dto);

        
        assertNotNull(result);
        assertEquals("John", result.username());
        assertEquals("john@example.com", result.email());

        verify(userMapper).toEntity(dto);
        verify(roleRepository).findByName(roleName);
        verify(securityConfig).passwordEncoder();
        verify(passwordEncoder).encode(password);
        verify(userRepository).save(userEntity);
        verify(userMapper).toResponse(userEntity);
    }
}
