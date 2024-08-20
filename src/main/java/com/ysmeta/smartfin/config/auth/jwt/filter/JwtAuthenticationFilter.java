package com.ysmeta.smartfin.config.auth.jwt.filter;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.ysmeta.smartfin.config.auth.jwt.JwtTokenProvider;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 16.
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

	private final JwtTokenProvider jwtTokenProvider;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
		super(authenticationManager);
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws ServletException, IOException {

		String jwtToken = null;
		String username = null;

		// 쿠키에서 JWT 토큰 추출
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("jwtToken" .equals(cookie.getName())) {
					jwtToken = cookie.getValue();
					break;
				}
			}
		}

		// JWT 토큰이 존재하는지 확인
		if (jwtToken != null) {
			try {
				username = jwtTokenProvider.extractUsername(jwtToken);
			} catch (IllegalArgumentException e) {
				log.error("JWT 토큰을 추출하는 중 오류 발생", e);
			} catch (ExpiredJwtException e) {
				log.error("JWT 토큰이 만료되었습니다", e);
			}
		}

		// 토큰이 유효한 경우, 스프링 시큐리티 컨텍스트에 설정
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			if (jwtTokenProvider.validateToken(jwtToken, username)) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					username, null, new ArrayList<>());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		chain.doFilter(request, response);
	}
	// @Override
	// protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
	// 	throws ServletException, IOException {
	//
	// 	String token = jwtTokenProvider.resolveToken(request);
	// 	final String requestTokenHeader = request.getHeader("Authorization");
	//
	// 	String username = null;
	// 	String jwtToken = null;
	//
	// 	// JWT 토큰이 존재하는지 확인하고 "Bearer " 부분을 제거
	// 	if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
	// 		jwtToken = requestTokenHeader.substring(7);
	// 		try {
	// 			username = jwtTokenProvider.extractUsername(jwtToken);
	// 		} catch (IllegalArgumentException e) {
	// 			System.out.println("JWT 토큰을 추출하는 중 오류 발생");
	// 		} catch (ExpiredJwtException e) {
	// 			System.out.println("JWT 토큰이 만료되었습니다");
	// 		}
	// 	} else {
	// 		logger.warn("JWT 토큰이 'Bearer '로 시작하지 않습니다");
	// 	}
	//
	// 	// 토큰이 유효한 경우, 스프링 시큐리티 컨텍스트에 설정
	// 	if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	// 		if (jwtTokenProvider.validateToken(jwtToken, username)) {
	// 			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
	// 				username, null, new ArrayList<>());
	// 			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	// 			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	// 		}
	// 	}
	// 	chain.doFilter(request, response);
	// }
}
