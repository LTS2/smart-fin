package com.ysmeta.smartfin.config.auth.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 20.
 */
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	public LoginFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		log.info("유저 이름: {}", username);
		log.info("유저 비밀번호: {}", password);

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password,
			null);
		return authenticationManager.authenticate(authToken);
	}

	/**
	 * Default behaviour for successful authentication.
	 * <ol>
	 * <li>Sets the successful <tt>Authentication</tt> object on the
	 * {@link SecurityContextHolder}</li>
	 * <li>Informs the configured <tt>RememberMeServices</tt> of the successful login</li>
	 * <li>Fires an {@link InteractiveAuthenticationSuccessEvent} via the configured
	 * <tt>ApplicationEventPublisher</tt></li>
	 * <li>Delegates additional behaviour to the
	 * {@link AuthenticationSuccessHandler}.</li>
	 * </ol>
	 * <p>
	 * Subclasses can override this method to continue the {@link FilterChain} after
	 * successful authentication.
	 *
	 * @param authResult the object returned from the <tt>attemptAuthentication</tt>
	 *                   method.
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		log.info("로그인 success");
		log.info(String.valueOf(request));
		log.info(String.valueOf(response));
		log.info(String.valueOf(chain));
		log.info(String.valueOf(authResult));
		// 여기에 JWT 토큰을 헤더에 넣는다.
		doFilter(request, response, chain);
		super.successfulAuthentication(request, response, chain, authResult);
	}

	/**
	 * Default behaviour for unsuccessful authentication.
	 * <ol>
	 * <li>Clears the {@link SecurityContextHolder}</li>
	 * <li>Stores the exception in the session (if it exists or
	 * <tt>allowSesssionCreation</tt> is set to <tt>true</tt>)</li>
	 * <li>Informs the configured <tt>RememberMeServices</tt> of the failed login</li>
	 * <li>Delegates additional behaviour to the
	 * {@link AuthenticationFailureHandler}.</li>
	 * </ol>
	 *
	 * @param request
	 * @param response
	 * @param failed
	 */
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) throws IOException, ServletException {
		log.info("로그인 fail");
		log.info(String.valueOf(request));
		log.info(String.valueOf(response));
		log.info(String.valueOf(failed));
		super.unsuccessfulAuthentication(request, response, failed);
	}

}
