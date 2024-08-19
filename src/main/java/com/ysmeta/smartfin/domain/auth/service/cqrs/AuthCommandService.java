package com.ysmeta.smartfin.domain.auth.service.cqrs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ysmeta.smartfin.domain.auth.password.PasswordEntity;
import com.ysmeta.smartfin.domain.auth.password.PasswordRepository;
import com.ysmeta.smartfin.domain.user.UserDto;
import com.ysmeta.smartfin.domain.user.UserEntity;
import com.ysmeta.smartfin.domain.user.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * AuthCommandService 클래스는 사용자 인증과 관련된 명령(Command) 작업을 처리하는 서비스입니다.
 */
@Slf4j
@Service
public class AuthCommandService {

	private final PasswordRepository passwordRepository;
	private final UserRepository userRepository;

	@Autowired
	public AuthCommandService(PasswordRepository passwordRepository, UserRepository userRepository) {

		this.passwordRepository = passwordRepository;
		this.userRepository = userRepository;
	}

	public Authentication authenticateUser(UserDto.LoginRequest loginRequestDto,
		AuthenticationManager authenticationManager) {
		return authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
	}

	/**
	 * 새로운 사용자를 저장하는 메서드입니다.
	 * <p>
	 * 이 메서드는 사용자 데이터를 데이터베이스에 저장합니다.
	 *
	 * @param user 저장할 사용자 엔티티
	 */
	@Transactional
	public void signUp(UserEntity user, PasswordEntity password) {
		userRepository.save(user);
		passwordRepository.save(password);
	}

}