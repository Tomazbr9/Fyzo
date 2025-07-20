package com.fyzo.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fyzo.app.dto.auth.JwtTokenDTO;
import com.fyzo.app.dto.auth.LoginDTO;
import com.fyzo.app.dto.user.UserRequestDTO;
import com.fyzo.app.dto.user.UserResponseDTO;
import com.fyzo.app.dto.user.UserUpdateDTO;
import com.fyzo.app.entities.Category;
import com.fyzo.app.entities.Role;
import com.fyzo.app.entities.User;
import com.fyzo.app.enums.RoleName;
import com.fyzo.app.exceptions.ResourceNotFoundException;
import com.fyzo.app.mapper.UserMapper;
import com.fyzo.app.repositories.CategoryRepository;
import com.fyzo.app.repositories.RoleRepository;
import com.fyzo.app.repositories.UserRepository;
import com.fyzo.app.security.SecurityConfig;
import com.fyzo.app.security.entities.UserDetailsImpl;
import com.fyzo.app.security.jwt.JwtTokenService;
import com.fyzo.app.services.finder.UserFinder;

@Service
public class UserService {
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
    private SecurityConfig securityConfig;
	
	@Autowired
	private JwtTokenService jwtTokenService;
	
	@Autowired 
	RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private UserMapper userMapper;
	
	@Autowired 
	private UserFinder userFinder;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public UserResponseDTO findById(Long id) {
		Optional<User> obj = userRepository.findById(id);
		return userMapper.toResponse(obj.get());
	}
	
	public JwtTokenDTO authenticateUser(LoginDTO dto) {
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(dto.username(), dto.password()); 
	        
			Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
	        
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			
	        return new JwtTokenDTO(jwtTokenService.generateToken(userDetails));
	}
	
	public UserResponseDTO create(UserRequestDTO dto) {
	    User user = userMapper.toEntity(dto);

	    RoleName roleName = RoleName.valueOf(dto.role());
	    Role role = roleRepository.findByName(roleName)
	        .orElseThrow(() -> new ResourceNotFoundException("Role não encontrado"));

	    user.setPassword(securityConfig.passwordEncoder().encode(dto.password()));
	    user.setRoles(List.of(role));

	    userRepository.save(user);
	    copyDefaultCategoriesToUser(user);
	    
	    return userMapper.toResponse(user);
	    
	}
	
	public UserResponseDTO update(UserUpdateDTO dto, UserDetailsImpl userDetails) {
		User user = userFinder.findByUsernameOrThrow(userDetails);
		
	    userMapper.updateFromDto(dto, user);
	    
	    if (dto.password() != null && !dto.password().isBlank()) {
	        user.setPassword(securityConfig.passwordEncoder().encode(dto.password()));
	    }

	    userRepository.save(user);
	    return userMapper.toResponse(user);
	}

	public void delete(UserDetailsImpl userDetails) {
		
		User user = userFinder.findByUsernameOrThrow(userDetails);
		
		userRepository.delete(user);
	}
	
	private void copyDefaultCategoriesToUser(User user) {
		List<Category> defaultCategories = categoryRepository.findByIsDefaultTrue();
		
		List<Category> categoriesToUser = defaultCategories
				.stream()
				.map(category -> {
					Category newCategory = new Category();
					newCategory.setName(category.getName());
					newCategory.setType(category.getType());
					newCategory.setColor(category.getColor());
					newCategory.setDefault(false);
					newCategory.setUser(user);
					return newCategory;
				}).toList();
		
		categoryRepository.saveAll(categoriesToUser);
		
	}
}
