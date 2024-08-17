package com.ysmeta.smartfin.domain.auth.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ysmeta.smartfin.domain.auth.jwt.JwtResponse;
import com.ysmeta.smartfin.domain.auth.service.cqrs.AuthCommandService;
import com.ysmeta.smartfin.domain.auth.service.cqrs.AuthQueryService;
import com.ysmeta.smartfin.domain.user.UserDto;
import com.ysmeta.smartfin.domain.user.UserEntity;

@Service
public class AuthApplicationService {

	private final AuthCommandService authCommandService;
	private final AuthQueryService authQueryService;

	@Autowired
	public AuthApplicationService(AuthCommandService authCommandService, AuthQueryService authQueryService) {
		this.authCommandService = authCommandService;
		this.authQueryService = authQueryService;
	}

	public JwtResponse login(UserDto.LoginRequest loginRequestDto) {
		// 사용자 인증 및 SecurityContext에 설정
		Authentication authentication = authCommandService.authenticateUser(loginRequestDto);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// 사용자의 정보를 조회
		UserEntity userEntity = authQueryService.findUserByEmail(loginRequestDto.getEmail());

		// JWT 토큰 생성 및 반환
		return authCommandService.generateJwtTokens(userEntity);
	}

	public void signUp(UserDto.CreateRequest userCreateRequestDto) throws NoSuchAlgorithmException {
		authCommandService.signUp(userCreateRequestDto);
	}
}
