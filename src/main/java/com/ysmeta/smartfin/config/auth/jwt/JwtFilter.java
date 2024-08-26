package com.ysmeta.smartfin.config.auth.jwt;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 20.
 */
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
	private final JwtTokenProvider jwtTokenProvider;

	public JwtFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request,
		@NonNull HttpServletResponse response,
		@NonNull FilterChain filterChain)
		throws ServletException, IOException {

		final String authorization = request.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION);
		final String authorization1 = response.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION);
		log.info("Authorization: {}", authorization);
		log.info("Authorization1: {}", authorization1);

		// 토큰이 없거나 Bearer 형태로 보내지 않으면 필터링을 중단하고 다음 필터로 넘어갑니다.
		if (authorization == null || !authorization.startsWith("Bearer ")) {
			log.error("Authorization 헤더가 없습니다.");
			filterChain.doFilter(request, response);
			return;
		}

		// Bearer {token} 형식에서 실제 토큰만 추출
		String token = authorization.split(" ")[1];

		// 토큰 만료 여부 확인
		if (jwtTokenProvider.isTokenExpired(token)) {
			log.error("Token이 만료되었습니다.");
			filterChain.doFilter(request, response);
			return;
		}

		// 토큰에서 사용자명과 역할 정보 추출
		String username = jwtTokenProvider.extractUsername(token);
		List<String> roles = jwtTokenProvider.extractRoles(token);

		// Spring Security의 유저 정보 설정
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
			username, null, roles.stream()
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toList())
		);

		// SecurityContext에 유저 정보 설정
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// 다음 필터로 요청 전달
		filterChain.doFilter(request, response);
	}
}
