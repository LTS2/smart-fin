package com.ysmeta.smartfin.domain.auth.signup;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ysmeta.smartfin.domain.user.UserDto;

import lombok.extern.slf4j.Slf4j;

/**
 * 회원가입 컨트롤러 클래스입니다.
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
	public ResponseEntity<String> signUp(@RequestBody UserDto.CreateUserRequestDto userDto) {
		// boolean isSignedUp = signUpService.signUp(userDto);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("/api/users/123")); // 새로 생성된 리소스의 URL TODO: 이건 뭔지 찾아보기
		// if (isSignedUp) {
		if (true) {
			return ResponseEntity
				.status(HttpStatus.CREATED)
				.headers(headers)
				.body("회원가입 완료");
		}
		// 409 = 이미 존재하는 회원일 경우 에러코드
		return ResponseEntity.status(HttpStatus.CONFLICT)
			.body("이미 존재하는 회원");
		// return ResponseEntity.badRequest().body("User already exists");

	}

	@PostMapping("/d")
	public ResponseEntity<String> signIn(@RequestBody UserDto userDto) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인실패");
	}
}
