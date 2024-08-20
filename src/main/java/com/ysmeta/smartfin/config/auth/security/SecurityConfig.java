package com.ysmeta.smartfin.config.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ysmeta.smartfin.config.auth.jwt.JwtTokenProvider;
import com.ysmeta.smartfin.config.auth.jwt.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// 순환 의존성 문제 때문에 우선 메서드 매개변수 주입으로 함
	// private final JwtAuthenticationFilter jwtAuthenticationFilter;
	// private final UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;

	// @Autowired
	// public SecurityConfig(@Lazy JwtAuthenticationFilter jwtAuthenticationFilter) {
	// 	this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	// this.userDetailsService = userDetailsService;
	// }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws
		Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/", "/api/auth/login", "/api/auth/signup", "/api/auth/check-email", "/notice",
					"/support")
				.permitAll()
				.requestMatchers("/mypage", "/customer-list", "usage").hasRole("USER")
				.requestMatchers("/admin").hasRole("ADMIN")
				.requestMatchers("/real-estate-search").hasRole("SEARCH_REAL_ESTATE") // 부동산 조회
				.requestMatchers("/car-search").hasAuthority("SEARCH_CAR") // 자동차 조회
				.requestMatchers("/personal-recovery-search").hasAuthority("SEARCH_PERSONAL_RECOVERY") // 개인 회생 조회
				.anyRequest().authenticated()
			)
			.formLogin(login -> login
				.usernameParameter("email")
				.passwordParameter("password"))
			.addFilterAt(new LoginFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
			// .userDetailsService(userDetailsService)

			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.maximumSessions(1) // 세션 최대 허용 수
				.maxSessionsPreventsLogin(false) // false: 중복 로그인 하면 이전 로그인이 풀림
			);

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	/**
	 * 비밀번호 암호화 전용 BCryptPasswordEncoder 메서드입니다.
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws
		Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationManager authenticationManager,
		JwtTokenProvider jwtTokenProvider) {
		return new JwtAuthenticationFilter(authenticationManager, jwtTokenProvider);
	}

}