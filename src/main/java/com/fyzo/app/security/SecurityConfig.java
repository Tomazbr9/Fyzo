package com.fyzo.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fyzo.app.security.filter.UserAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
    		"/users/login",
    		"/users/register",
    };

    public static final String [] ENDPOINTS_CUSTOMER = {
    		"/users/update",
    		"/users/delete",
    		
    		"/transactions/me",
    		"/transactions/create",
            "/transactions/update/**",
            "/transactions/delete/**",
            "/transactions/balance",
            "/transactions/summary/expense",
            "/transactions/summary/revenue",
            "/transactions/filter",
            
            "/categories/me",
    		"/categories/create",
            "/categories/update/**",
            "/categories/delete/**",
            
            "/accounts/me",
            "/accounts/create",
            "/accounts/update/**",
            "/accounts/delete/**",
            
            "/goals/me",
            "/goals/create",
            "/goals/update/**",
            "/goals/delete/**"
            
    };

    public static final String [] ENDPOINTS_ADMIN = {
            "/admin/users/all",
    		"/admin/users/update/**",
            "/admin/users/delete/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                .requestMatchers(ENDPOINTS_CUSTOMER).hasRole("CUSTOMER")
                .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMIN")

                .anyRequest().denyAll()
            )
           .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}