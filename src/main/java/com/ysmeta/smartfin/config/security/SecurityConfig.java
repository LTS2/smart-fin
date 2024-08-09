package com.ysmeta.smartfin.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// private final JwtRequestFilter jwtRequestFilter;

	// @Autowired
	// public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
	// 	this.jwtRequestFilter = jwtRequestFilter;
	// }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/api/auth/**").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin(login -> login
				.loginPage("/login").permitAll()
				.loginProcessingUrl("/loginProc").permitAll()
				.defaultSuccessUrl("/", true)
				.failureUrl("/login?error=true")
			)
			.logout(logout -> logout
				.logoutSuccessUrl("/login?logout=true")
				.permitAll()
			)
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			);

		// http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	/**
	 * 비밀번호 암호화 전용 BCryptPasswordEncoder 메서드입니다.
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// @Bean
	// public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws
	// 	Exception {
	// 	return authenticationConfiguration.getAuthenticationManager();
	// }

	// @Bean
	// public PasswordEncoder passwordEncoder() {
	// 	return new BCryptPasswordEncoder();
	// }
}