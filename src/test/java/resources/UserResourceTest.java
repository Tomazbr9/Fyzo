package resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyzo.app.dto.user.UserResponseDTO;
import com.fyzo.app.dto.user.UserUpdateDTO;
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
    @WithMockUser(username = "rebecca", roles = {"CUSTOMER"})
    void shouldUpdateUserSuccessfully() throws Exception {
        // Arrange
        UserUpdateDTO updateDTO = new UserUpdateDTO("rebeca", "rebeca@gmail.com", "12345");
        UserResponseDTO responseDTO = new UserResponseDTO("rebeca", "rebeca@gmail.com");

        when(service.update(any(UserUpdateDTO.class), any(UserDetailsImpl.class)))
            .thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(patch("/users/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("rebeca"))
                .andExpect(jsonPath("$.email").value("rebeca@gmail.com"));
    }
}
