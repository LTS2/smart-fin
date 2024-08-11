package com.ysmeta.smartfin.config.auth.jwt;

import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT 토큰을 생성하고 검증하는 유틸리티 클래스입니다.
 * 이 클래스는 사용자의 이메일을 기반으로 JWT를 생성하고,
 * 토큰에서 사용자 정보를 추출하며, 토큰의 유효성을 검증하는 기능을 제공합니다.
 *
 * @author ewjin
 * @version 0.0.1
 * @since 2024. 8. 11.
 */
@Component
@Slf4j
public class JwtProvider {

	private final String secretKey;
	private final Long expiration;

	/**
	 * JwtProvider 생성자입니다.
	 *
	 * @param secretKey  JWT를 서명하기 위한 비밀 키입니다.
	 * @param expiration JWT의 만료 시간(초 단위)입니다.
	 */
	public JwtProvider(
		@Value("${jwt.secret}") String secretKey,
		@Value("${jwt.expiration}") Long expiration) {
		this.secretKey = secretKey;
		this.expiration = expiration;
	}

	/**
	 * 사용자의 이메일을 기반으로 JWT 토큰을 생성합니다.
	 *
	 * @param email JWT에 포함될 사용자의 이메일입니다.
	 * @return 생성된 JWT 토큰입니다.
	 */
	public String generateToken(String email) {
		return Jwts.builder()
			.setSubject(email)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	/**
	 * JWT 토큰에서 사용자 이메일을 추출합니다.
	 *
	 * @param token JWT 토큰입니다.
	 * @return 토큰에 포함된 사용자 이메일입니다.
	 */
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * JWT 토큰에서 만료 시간을 추출합니다.
	 *
	 * @param token JWT 토큰입니다.
	 * @return 토큰의 만료 시간입니다.
	 */
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	/**
	 * JWT 토큰에서 특정 클레임을 추출합니다.
	 *
	 * @param <T>            클레임의 타입입니다.
	 * @param token          JWT 토큰입니다.
	 * @param claimsResolver 클레임을 추출하기 위한 함수입니다.
	 * @return 추출된 클레임 값입니다.
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * JWT 토큰에서 모든 클레임을 추출합니다.
	 *
	 * @param token JWT 토큰입니다.
	 * @return 토큰에 포함된 모든 클레임입니다.
	 */
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}

	/**
	 * JWT 토큰이 만료되었는지 확인합니다.
	 *
	 * @param token JWT 토큰입니다.
	 * @return 토큰이 만료되었으면 true, 그렇지 않으면 false를 반환합니다.
	 */
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	/**
	 * JWT 토큰이 유효한지 확인합니다.
	 * 토큰의 사용자 이메일과 만료 여부를 검사합니다.
	 *
	 * @param token    JWT 토큰입니다.
	 * @param username 토큰에 포함된 사용자 이메일입니다.
	 * @return 토큰이 유효하면 true, 그렇지 않으면 false를 반환합니다.
	 */
	public Boolean validateToken(String token, String username) {
		final String extractedUsername = extractUsername(token);
		return (extractedUsername.equals(username) && !isTokenExpired(token));
	}
}
