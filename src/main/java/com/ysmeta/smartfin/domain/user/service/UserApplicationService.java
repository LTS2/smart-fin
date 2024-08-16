package com.ysmeta.smartfin.domain.user.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

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

	/**
	 * 사용자를 등록하는 메서드입니다.
	 * 이메일을 통해 사용자가 이미 존재하는지 확인하고, 존재하지 않는 경우 새 사용자를 등록합니다.
	 * 비밀번호는 bcrypt로 암호화되어 저장됩니다.
	 *
	 * @param user 사용자 등록 요청 DTO
	 */
	@Transactional
	public void registerUser(UserDto.CreateRequest user) throws NoSuchAlgorithmException {
		// 사용자가 이미 존재하는지 확인
		if (userQueryService.hasUser(user.getEmail())) {
			throw new IllegalStateException("이미 존재하는 사용자입니다.");
		}

		// DTO를 엔티티로 변환
		UserEntity userEntity = user.toEntity();

		// 비밀번호 암호화
		String encryptedPassword = passwordEncoder.encode(user.getPassword());

		// Salt 생성
		String salt = generateSalt();

		// 비밀번호 엔티티 생성
		PasswordEntity passwordEntity = PasswordEntity.builder()
			.user(userEntity)
			.hashedPassword(encryptedPassword)
			.salt(salt)
			.build();

		// 사용자 엔티티와 비밀번호 엔티티 저장
		userCommandService.save(userEntity, passwordEntity);
	}

	/**
	 * 랜덤 Salt 값을 생성하는 메서드입니다.
	 * SecureRandom 클래스를 사용하여 16바이트 길이의 예측할 수 없는 Salt를 생성합니다.
	 *
	 * @return Base64로 인코딩된 Salt 문자열
	 * @throws NoSuchAlgorithmException 암호학적으로 안전한 랜덤 알고리즘이 없을 경우 발생
	 */
	public String generateSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstanceStrong(); // 암호학적으로 안전한 알고리즘 사용

		// Salt 바이트 배열 생성 (16바이트)
		byte[] salt = new byte[16];
		sr.nextBytes(salt); // 랜덤 바이트로 배열 채우기

		// 바이트 배열을 Base64 문자열로 변환하여 반환
		return Base64.getEncoder().encodeToString(salt);
	}
}
