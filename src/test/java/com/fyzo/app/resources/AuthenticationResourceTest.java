package com.fyzo.app.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyzo.app.dto.user.UserRequestDTO;
import com.fyzo.app.dto.user.UserResponseDTO;
import com.fyzo.app.security.filter.UserAuthenticationFilter;
import com.fyzo.app.security.jwt.JwtTokenService;
import com.fyzo.app.services.AuthenticationService;

@WebMvcTest(AuthenticationResource.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthenticationResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;
    
    @MockBean
	private UserAuthenticationFilter filter;
	
	@MockBean
    private JwtTokenService jwtTokenService;
	

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        reset(authenticationService);
    }

    @Test
    void mustSuccessfullyRegisterTheUser() throws Exception {
        var request = new UserRequestDTO("tomaz", "tomaz@gmail.com", "password", "ROLE_CUSTOMER");
        var response = new UserResponseDTO("tomaz", "tomaz@gmail.com");

        when(authenticationService.registerUser(any())).thenReturn(response);

        mockMvc.perform(post("/auth/register").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.username").value("tomaz"))
            .andExpect(jsonPath("$.email").value("tomaz@gmail.com"));

        verify(authenticationService).registerUser(any());
    }
}

