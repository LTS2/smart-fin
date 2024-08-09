package com.ysmeta.smartfin.config.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JwtUtil을 관리하는 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 8.
 */
@Component
public class JwtUtilCopy {
	private final String secret = "secretKey";
	private final long validityInMilliseconds = 3600 * 1000; // 1시간

	public String createToken(String username) {
		Claims claims = Jwts.claims().setSubject(username);

		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(validity)
			.signWith(SignatureAlgorithm.HS256, secret)
			.compact();
	}

	public String extractUsername(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
