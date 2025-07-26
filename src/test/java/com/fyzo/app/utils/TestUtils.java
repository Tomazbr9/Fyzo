package com.fyzo.app.utils;

import java.util.List;

import com.fyzo.app.entities.Role;
import com.fyzo.app.entities.User;
import com.fyzo.app.enums.RoleName;
import com.fyzo.app.security.entities.UserDetailsImpl;

public class TestUtils {
	
	public static UserDetailsImpl mockCustomerUser() {
		User user = new User();
		user.setUsername("tomaz");
		user.setEmail("tomaz@gmail.com");
		user.setPassword("password");
		user.setRoles(getRole(RoleName.ROLE_CUSTOMER));
		
		return new UserDetailsImpl(user);
	}
	
	private static List<Role> getRole(RoleName role){
		return List.of(
				new Role(1L, role)
		);
		
	}
}
