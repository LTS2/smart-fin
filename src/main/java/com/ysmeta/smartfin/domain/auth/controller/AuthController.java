// package com.ysmeta.smartfin.domain.auth.controller;
//
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.web.bind.annotation.RestController;
//
// import com.ysmeta.smartfin.config.util.JwtUtil;
// import com.ysmeta.smartfin.domain.user.UserService;
//
// /**
//  * 사용자 인증 컨트롤러 클래스입니다.
//  *
//  * @author : ewjin
//  * @version : 0.0.1
//  * @since : 2024. 8. 8.
//  */
// @RestController
// public class AuthController {
//
// 	private final AuthenticationManager authenticationManager;
// 	private final JwtUtil jwtUtil;
// 	private final UserService userService;
//
// 	public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService) {
// 		this.authenticationManager = authenticationManager;
// 		this.jwtUtil = jwtUtil;
// 		this.userService = userService;
// 	}
//
// 	// @PostMapping("/login")
// 	// public String createAuthenticationToken(@RequestBody UserDto UserDto) throws Exception {
// 	// 	try {
// 	// 		authenticationManager.authenticate(
// 	// 			new UsernamePasswordAuthenticationToken(UserDto.getEmail(), UserDto.get())
// 	// 		);
// 	// 	} catch (AuthenticationException e) {
// 	// 		throw new Exception("Incorrect username or password", e);
// 	// 	}
// 	//
// 	// 	final UserEntity user = userService.getUserByEmail(UserDto.getEmail());
// 	// 	return jwtUtil.createToken(user.getEmail());
// 	// }
// }
