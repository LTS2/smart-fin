package com.ysmeta.smartfin.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// private final JwtUtil jwtUtil;
	// // private final UserDetailsService userDetailsService;
	// private final JwtRequestFilter jwtRequestFilter;
	//
	// public SecurityConfig(JwtUtil jwtUtil, JwtRequestFilter jwtRequestFilter) {
	// 	this.jwtUtil = jwtUtil;
	// 	this.jwtRequestFilter = jwtRequestFilter;
	// }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf((csrf) -> csrf.disable())
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/login", "/loginForm").permitAll()
				.anyRequest().permitAll()
			)
			.formLogin(form -> form
					.loginPage("/login").permitAll()
				// .defaultSuccessUrl("/", true)
				// .failureUrl("/login?error=true")
			)
			.logout(logout -> logout
				.logoutSuccessUrl("/login?logout=true")
				.permitAll()
			);

		// http
		// 	.csrf(csrf -> csrf.disable())
		// 	.authorizeHttpRequests(authorize -> authorize
		// 		.requestMatchers("/login").permitAll()
		// 		.requestMatchers("/").permitAll()
		// 		.anyRequest().authenticated()
		// 	)
		// 	.sessionManagement(session -> session
		// 		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		// 	);

		// http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	//
	// @Bean
	// public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws
	// 	Exception {
	// 	return authenticationConfiguration.getAuthenticationManager();
	// }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}