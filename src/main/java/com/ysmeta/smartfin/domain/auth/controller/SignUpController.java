package com.ysmeta.smartfin.domain.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ysmeta.smartfin.domain.auth.service.SignUpService;
import com.ysmeta.smartfin.domain.user.UserDto;

import lombok.extern.slf4j.Slf4j;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 8.
 */
@RestController
@RequestMapping("/api/auth/")
@Slf4j
public class SignUpController {
	private final SignUpService signUpService;

	@Autowired
	public SignUpController(SignUpService signUpService) {
		this.signUpService = signUpService;
	}

	@PostMapping("/")
	public ResponseEntity<String> signUp(@RequestBody UserDto userDto) {
		boolean isSignedUp = signUpService.signUp(userDto);
		log.info("실행됩니다.");
		if (isSignedUp) {
			return ResponseEntity.ok("Sign up successful");
		} else {
			return ResponseEntity.badRequest().body("User already exists");
		}
	}
}
