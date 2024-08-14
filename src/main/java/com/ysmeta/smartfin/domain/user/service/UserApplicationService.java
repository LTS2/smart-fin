package com.ysmeta.smartfin.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ysmeta.smartfin.domain.auth.password.PasswordEntity;
import com.ysmeta.smartfin.domain.user.UserDto;
import com.ysmeta.smartfin.domain.user.UserEntity;
import com.ysmeta.smartfin.domain.user.service.cqrs.UserCommandService;
import com.ysmeta.smartfin.domain.user.service.cqrs.UserQueryService;

/**
 * 여러 책임을 지고 있는 어플리케이션 클래스입니다.
 * 추후 도메인 서비스도 따로 생성할 예정입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 13.
 */
@Service
public class UserApplicationService {

	private final UserQueryService userQueryService;
	private final UserCommandService userCommandService;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserApplicationService(UserQueryService userQueryService, UserCommandService userCommandService,
		PasswordEncoder passwordEncoder) {
		this.userQueryService = userQueryService;
		this.userCommandService = userCommandService;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public void registerUser(UserDto.CreateUserRequestDto user) {
		if (userQueryService.hasUser(user.getEmail())) {
			throw new IllegalStateException("이미 존재하는 사용자입니다.");
		}
		UserEntity userEntity = user.toEntity();
		// 비밀번호 암호화
		String encryptedPassword = passwordEncoder.encode(user.getPassword());

		PasswordEntity passwordEntity = PasswordEntity.builder()
			.user(userEntity)
			.hashedPassword(encryptedPassword)
			.salt("test")
			.build();

		userCommandService.save(userEntity, passwordEntity);
	}
}
