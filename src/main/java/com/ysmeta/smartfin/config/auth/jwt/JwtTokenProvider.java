package com.ysmeta.smartfin.config.auth.jwt;

import java.util.Date;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT 토큰을 생성하고 검증하는 유틸리티 클래스입니다.
 * 이 클래스는 사용자의 이메일을 기반으로 JWT를 생성하고,
 * 토큰에서 사용자 정보를 추출하며, 토큰의 유효성을 검증하는 기능을 제공합니다.
 */
@Slf4j
@Component
public class JwtTokenProvider {

	private static final String AUTHORITIES_KEY = "auth";
	private static final String BEARER_TYPE = "bearer";
	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 30분
	private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일

	private final String secretKey;

	public JwtTokenProvider(
		@Value("${jwt.secret}") String secretKey) {
		this.secretKey = secretKey;
	}

	public String resolveToken(HttpServletRequest request) {

		return Optional.ofNullable(request.getHeader("Authorization"))
			.filter(bearerToken -> bearerToken.startsWith("Bearer"))
			.map(bearerToken -> bearerToken.substring(7))
			.orElse(null);

		// String bearerToken = request.getHeader("Authorization");
		// if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
		// 	return bearerToken.substring(7);
		// }
		// return null;
	}

	/**
	 * 사용자의 이메일을 기반으로 JWT 엑세스 토큰을 생성합니다.
	 *
	 * @param email JWT에 포함될 사용자의 이메일입니다.
	 * @return 생성된 JWT 엑세스 토큰입니다.
	 */
	public String generateAccessToken(UserDetails userDetails) {
		return generateToken(userDetails, ACCESS_TOKEN_EXPIRE_TIME);
	}

	/**
	 * 사용자의 이메일을 기반으로 JWT 리프레시 토큰을 생성합니다.
	 *
	 * @param email JWT에 포함될 사용자의 이메일입니다.
	 * @return 생성된 JWT 리프레시 토큰입니다.
	 */
	public String generateRefreshToken(UserDetails userDetails) {
		return generateToken(userDetails, REFRESH_TOKEN_EXPIRE_TIME);
	}

	/**
	 * 사용자의 이메일을 기반으로 JWT 토큰을 생성하는 공통 메서드입니다.
	 *
	 * @param email      JWT에 포함될 사용자의 이메일입니다.
	 * @param expireTime 토큰의 유효 기간입니다.
	 * @return 생성된 JWT 토큰입니다.
	 */
	private String generateToken(UserDetails userDetails, long expireTime) {
		String authorities = userDetails.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		return Jwts.builder()
			.setSubject(userDetails.getUsername())
			.claim("roles", authorities)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + expireTime))
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
