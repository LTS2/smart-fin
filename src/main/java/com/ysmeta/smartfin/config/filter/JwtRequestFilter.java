// package com.ysmeta.smartfin.config.filter;
//
// import org.springframework.stereotype.Component;
//
// /**
//  * 클래스입니다.
//  *
//  * @author : ewjin
//  * @version : 0.0.1
//  * @since : 2024. 8. 8.
//  */
// @Component
// // public class JwtRequestFilter extends OncePerRequestFilter {
// public class JwtRequestFilter {
//
// 	// private final JwtUtil jwtUtil;
// 	// private final UserDetailsService userDetailsService;
// 	//
// 	// public JwtRequestFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
// 	// 	this.jwtUtil = jwtUtil;
// 	// 	this.userDetailsService = userDetailsService;
// 	// }
//
// 	// @Override
// 	// protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
// 	// 	ServletException, IOException {
// 	// 	final String requestTokenHeader = request.getHeader("Authorization");
// 	//
// 	// 	String username = null;
// 	// 	String jwtToken = null;
// 	//
// 	// 	if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
// 	// 		jwtToken = requestTokenHeader.substring(7);
// 	// 		try {
// 	// 			username = jwtUtil.extractUsername(jwtToken);
// 	// 		} catch (IllegalArgumentException e) {
// 	// 			System.out.println("Unable to get JWT Token");
// 	// 		} catch (ExpiredJwtException e) {
// 	// 			System.out.println("JWT Token has expired");
// 	// 		} catch (SignatureException e) {
// 	// 			System.out.println("JWT signature does not match locally computed signature");
// 	// 		}
// 	// 	} else {
// 	// 		logger.warn("JWT Token does not begin with Bearer String");
// 	// 	}
// 	//
// 	// 	if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
// 	// 		UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
// 	//
// 	// 		if (jwtUtil.validateToken(jwtToken)) {
// 	// 			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
// 	// 				userDetails, null, userDetails.getAuthorities());
// 	// 			usernamePasswordAuthenticationToken.setDetails(
// 	// 				new WebAuthenticationDetailsSource().buildDetails(request));
// 	// 			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
// 	// 		}
// 	// 	}
// 	// 	chain.doFilter(request, response);
// 	// }
// }