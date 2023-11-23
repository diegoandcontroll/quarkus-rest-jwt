package com.github.diegoandcontroll.services;

import org.eclipse.microprofile.jwt.Claims;
import org.jose4j.jwt.JwtClaims;
import com.github.diegoandcontroll.model.User;
import com.github.diegoandcontroll.utils.TokenUtils;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class TokenService {

	public String generate(User user) {
		try {
			System.out.println("creating account");

			JwtClaims jwtClaims = new JwtClaims();
			jwtClaims.setIssuer("https://quarkus.io/using-jwt-rbac");
			jwtClaims.setJwtId("a-123");
			jwtClaims.setSubject(user.getEmail());
			jwtClaims.setClaim(Claims.upn.name(), user.getEmail());
			jwtClaims.setClaim(Claims.preferred_username.name(), user.getFirstName());
			jwtClaims.setClaim(Claims.groups.name(), user.getRoles());
			jwtClaims.setAudience("using-jwt");
			jwtClaims.setExpirationTimeMinutesInTheFuture(3);

			String token = TokenUtils.generateTokenString(jwtClaims);

			System.out.println(token);

			return token;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Oops!");
		}

	}
}
