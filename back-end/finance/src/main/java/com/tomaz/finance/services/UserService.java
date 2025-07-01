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
import com.tomaz.finance.dto.UserUpdateDTO;
import com.tomaz.finance.entities.Role;
import com.tomaz.finance.entities.User;
import com.tomaz.finance.enums.RoleName;
import com.tomaz.finance.repositories.RoleRepository;
import com.tomaz.finance.repositories.UserRepository;
import com.tomaz.finance.security.SecurityConfig;
import com.tomaz.finance.security.entities.UserDetailsImpl;
import com.tomaz.finance.security.jwt.JwtTokenService;

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
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	public User findById(Long id) {
		Optional<User> obj = userRepository.findById(id);
		
		return obj.get();
	}
	
	public JwtTokenDTO authenticateUser(LoginDTO dto) {
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()); 
	        
			Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
	        
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			
	        return new JwtTokenDTO(jwtTokenService.generateToken(userDetails));
	}
	
	public User create(UserCreateDTO dto) {
		User user = new User();

		RoleName roleName = RoleName.valueOf(dto.getRole());
		Role role = roleRepository.findByName(roleName)
		    .orElseThrow(() -> new RuntimeException("Role não encontrada: " + roleName));
		
		user.setUsername(dto.getUsername());
		user.setEmail(dto.getEmail());
		user.setPassword(securityConfig.passwordEncoder().encode(dto.getPassword()));
		user.setRoles(List.of(role));
		
		
		return userRepository.save(user);
	}
	
	public User update(Long id, UserUpdateDTO dto) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		
		if(dto.getUsername() != null && !dto.getUsername().isBlank()) {
			user.setUsername(dto.getUsername());
		}
		
		if(dto.getEmail() != null && !dto.getEmail().isBlank()) {
			user.setEmail(dto.getEmail());
		}
		
		return userRepository.save(user);
	}
	
	public void delete(Long id) {
		if(!userRepository.existsById(id)) {
			throw new RuntimeException("Usuário não encontrado");
		}
		
		userRepository.deleteById(id);
	}
}
