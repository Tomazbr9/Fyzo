package com.tomaz.finance.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.tomaz.finance.dto.JwtTokenDTO;
import com.tomaz.finance.dto.LoginDTO;
import com.tomaz.finance.dto.UserCreateDTO;
import com.tomaz.finance.dto.UserResponseDTO;
import com.tomaz.finance.dto.UserUpdateDTO;
import com.tomaz.finance.entities.Role;
import com.tomaz.finance.entities.User;
import com.tomaz.finance.enums.RoleName;
import com.tomaz.finance.mapper.UserMapper;
import com.tomaz.finance.repositories.RoleRepository;
import com.tomaz.finance.repositories.UserRepository;
import com.tomaz.finance.security.SecurityConfig;
import com.tomaz.finance.security.entities.UserDetailsImpl;
import com.tomaz.finance.security.jwt.JwtTokenService;
import com.tomaz.finance.services.finder.UserFinder;

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
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
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
	
	public UserResponseDTO create(UserCreateDTO dto) {
	    User user = userMapper.toEntity(dto);

	    RoleName roleName = RoleName.valueOf(dto.role());
	    Role role = roleRepository.findByName(roleName)
	        .orElseThrow(() -> new RuntimeException("Role não encontrada: " + roleName));

	    user.setPassword(securityConfig.passwordEncoder().encode(dto.password()));

	    user.setRoles(List.of(role));

	    userRepository.save(user);
	    return userMapper.toResponse(user);
	    
	}
	
	public UserResponseDTO update(UserUpdateDTO dto, String username) {
		User user = userFinder.findByUsernameOrThrow(username);

	    userMapper.updateFromDto(dto, user);
	    
	    if (dto.password() != null && !dto.password().isBlank()) {
	        user.setPassword(securityConfig.passwordEncoder().encode(dto.password()));
	    }

	    userRepository.save(user);
	    return userMapper.toResponse(user);
	}

	public void delete(String username) {
		User user = userFinder.findByUsernameOrThrow(username);
		userRepository.delete(user);
	}
}
