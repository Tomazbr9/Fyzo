package com.tomaz.finance.security.jwt;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.tomaz.finance.exceptions.InvalidTokenException;
import com.tomaz.finance.exceptions.TokenGenerationException;
import com.tomaz.finance.security.entities.UserDetailsImpl;

@Component
public class JwtTokenService {

	private static final String SECRET_KEY = "4cIKAK2,rW#SFOU~0ccrPU$B";
	private static final String ISSUER = "pizzurg-api";
	
	public String generateToken(UserDetailsImpl user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
			return JWT.create()
					.withIssuer(ISSUER)
					.withIssuedAt(creationDate())
					.withExpiresAt(expirationDate())
					.withSubject(user.getUsername())
					.sign(algorithm);
	    } 
		catch (JWTCreationException exception) {
			throw new TokenGenerationException("Erro ao gerar token");
		}
	}
	
	public String getSubjectFromToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
			return JWT.require(algorithm)
					.withIssuer(ISSUER)
					.build()
					.verify(token)
					.getSubject();
		}
		catch (JWTVerificationException exception) {
			throw new InvalidTokenException("Token invalido ou expirado");
		}
	}
	
	private Instant creationDate() {
		return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
	}
	
	private Instant expirationDate() {
		return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(4).toInstant();
	}
    
	
	
}
