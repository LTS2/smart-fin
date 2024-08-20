package com.ysmeta.smartfin.domain.auth;

import java.util.Collections;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ysmeta.smartfin.domain.auth.service.AuthApplicationService;
import com.ysmeta.smartfin.domain.user.UserDto;
import com.ysmeta.smartfin.util.helper.error.ErrorMessageHelper;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

	private final AuthApplicationService authApplicationService;

	@Autowired
	public AuthController(AuthApplicationService authApplicationService) {
		this.authApplicationService = authApplicationService;
	}

	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@Valid @RequestBody UserDto.CreateRequest userCreateRequestDto,
		BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			String errorMessage = ErrorMessageHelper.getOrDefaultErrorMessage(bindingResult);
			return ResponseEntity.badRequest().body(errorMessage);
		}

		try {
			authApplicationService.signUp(userCreateRequestDto);
			log.info("사용자 {}가 성공적으로 등록되었습니다.", userCreateRequestDto.getEmail());
			return ResponseEntity.status(201).body("회원가입 완료");
		} catch (Exception e) {
			log.error("회원가입 중 오류 발생: ", e);
			return ResponseEntity.status(500).body("회원가입 실패");
		}
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody UserDto.LoginRequest loginRequestDto,
		BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails) {
		log.info("III::: userDetails: {}", String.valueOf(userDetails));
		log.info("III::: userDetails: {}", userDetails);
		if (bindingResult.hasErrors()) {
			String errorMessage = ErrorMessageHelper.getOrDefaultErrorMessage(bindingResult);
			LoginResponse errorResponse = LoginResponse.builder()
				.errorMessage(errorMessage)
				.build();
			return ResponseEntity.badRequest().body(errorResponse);
		}
		try {
			LoginResponse loginResponse = authApplicationService.login(loginRequestDto);

			// JWT 토큰을 쿠키에 저장
			ResponseCookie jwtCookie = ResponseCookie.from("Token", loginResponse.getRefreshToken())
				.httpOnly(true)  // HttpOnly 설정
				.secure(false)  // HTTPS에서만 전송되도록 설정 (배포 환경에서는 true로 설정)
				.sameSite("Strict")  // SameSite 설정
				.path("/")  // 쿠키의 경로 설정
				.maxAge(24 * 60 * 60)  // 쿠키의 유효 기간 설정 (예: 1일)
				.build();

			// 쿠키를 HTTP 응답에 추가하고 로그인 응답 본문을 함께 반환
			return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.getAccessToken())
				.body(loginResponse);
		} catch (Exception e) {
			log.error("로그인 중 오류 발생: ", e);
			LoginResponse errorResponse = LoginResponse.builder()
				.errorMessage("로그인 실패: 이메일 또는 비밀번호가 일치하지 않습니다.")
				.build();
			return ResponseEntity.status(401).body(errorResponse);
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout() {
		// JWT 토큰이 저장된 쿠키 삭제를 위한 설정
		ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", "")
			.httpOnly(true)
			.secure(true)
			.sameSite("Strict")
			.path("/")
			.maxAge(0)  // 쿠키 유효 기간을 0으로 설정하여 삭제
			.build();

		// 쿠키를 삭제하고 200 OK 응답 반환
		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
			.build();
	}

	@PostMapping("/check-email")
	public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestBody Map<String, String> request) {
		String email = request.get("email");
		boolean exists = authApplicationService.checkEmailExists(email);
		return ResponseEntity.ok(Collections.singletonMap("exists", exists));
	}
}
