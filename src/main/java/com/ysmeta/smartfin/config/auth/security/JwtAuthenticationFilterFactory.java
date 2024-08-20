package com.ysmeta.smartfin.config.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

import com.ysmeta.smartfin.config.auth.jwt.JwtTokenProvider;
import com.ysmeta.smartfin.config.auth.jwt.filter.JwtAuthenticationFilter;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 20.
 */
@Component
public class JwtAuthenticationFilterFactory {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	public JwtAuthenticationFilter createFilter() {
		return new JwtAuthenticationFilter(authenticationManager, jwtTokenProvider);
	}
}
