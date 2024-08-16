package com.ysmeta.smartfin.config.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// @EnableWebSecurity
public class SecurityConfigTest {

	// private final JwtRequestFilter jwtRequestFilter;

	// @Autowired
	// public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
	// 	this.jwtRequestFilter = jwtRequestFilter;
	// }

	// @Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
			.formLogin(form -> form
				.loginPage("/loginPage")
				.defaultSuccessUrl("/", true)
				
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