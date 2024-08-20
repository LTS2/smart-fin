package com.ysmeta.smartfin.domain.auth.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ysmeta.smartfin.domain.auth.LoginResponse;
import com.ysmeta.smartfin.domain.auth.jwt.JwtTokenApplicationService;
import com.ysmeta.smartfin.domain.auth.password.PasswordEntity;
import com.ysmeta.smartfin.domain.auth.service.cqrs.AuthCommandService;
import com.ysmeta.smartfin.domain.auth.service.cqrs.AuthQueryService;
import com.ysmeta.smartfin.domain.user.UserDto;
import com.ysmeta.smartfin.domain.user.UserEntity;
import com.ysmeta.smartfin.domain.user.service.cqrs.UserQueryService;
import com.ysmeta.smartfin.exception.user.UserAlreadyExistsException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthApplicationService {

	private final AuthCommandService authCommandService;
	private final AuthQueryService authQueryService;
	private final UserQueryService userQueryService;
	private final JwtTokenApplicationService jwtTokenApplicationService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	@Autowired
	public AuthApplicationService(AuthCommandService authCommandService, AuthQueryService authQueryService,
		UserQueryService userQueryService, JwtTokenApplicationService jwtTokenApplicationService,
		PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
		this.authCommandService = authCommandService;
		this.authQueryService = authQueryService;
		this.userQueryService = userQueryService;
		this.jwtTokenApplicationService = jwtTokenApplicationService;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}

	@Transactional
	public LoginResponse login(UserDto.LoginRequest loginRequestDto) {
		// 사용자 인증 및 SecurityContext에 설정
		Authentication authentication = authCommandService.authenticateUser(loginRequestDto, authenticationManager);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// 사용자의 정보를 조회
		UserEntity userEntity = userQueryService.findByEmail(loginRequestDto.getEmail());

		// JWT 토큰 생성 및 반환
		return jwtTokenApplicationService.generateJwtTokens((UserDetails)userEntity);
	}

	@Transactional
	public void signUp(UserDto.CreateRequest user) throws NoSuchAlgorithmException {
		String email = user.getEmail();
		String password = user.getPassword();
		UserEntity userEntity = user.toEntity();
		// 회원가입 하려는 이메일이 이미 존재할 경우
		if (userQueryService.hasUser(email)) {
			throw new UserAlreadyExistsException("이미 존재하는 사용자입니다.");
		}
		String encryptedPassword = passwordEncoder.encode(password);

		PasswordEntity passwordEntity = PasswordEntity.builder()
			.user(userEntity)
			.hashedPassword(encryptedPassword)
			.build();

		authCommandService.signUp(userEntity, passwordEntity);
	}

	public boolean checkEmailExists(String email) {
		return userQueryService.hasUser(email);
	}
}
