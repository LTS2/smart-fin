package com.ysmeta.smartfin.domain.auth.service.cqrs;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ysmeta.smartfin.config.auth.jwt.JwtTokenProvider;
import com.ysmeta.smartfin.domain.auth.jwt.JwtEntity;
import com.ysmeta.smartfin.domain.auth.jwt.JwtRepository;
import com.ysmeta.smartfin.domain.auth.jwt.JwtResponse;
import com.ysmeta.smartfin.domain.auth.password.PasswordEntity;
import com.ysmeta.smartfin.domain.auth.password.PasswordRepository;
import com.ysmeta.smartfin.domain.user.UserDto;
import com.ysmeta.smartfin.domain.user.UserEntity;
import com.ysmeta.smartfin.domain.user.UserRepository;

/**
 * AuthCommandService 클래스는 사용자 인증과 관련된 명령(Command) 작업을 처리하는 서비스입니다.
 */
@Service
public class AuthCommandService {

	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManager authenticationManager;
	private final JwtRepository jwtRepository;
	private final PasswordRepository passwordRepository;
	private final UserRepository userRepository;

	@Autowired
	public AuthCommandService(JwtTokenProvider jwtTokenProvider,
		AuthenticationManager authenticationManager,
		JwtRepository jwtRepository, PasswordRepository passwordRepository,
		UserRepository userRepository) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.authenticationManager = authenticationManager;
		this.jwtRepository = jwtRepository;
		this.passwordRepository = passwordRepository;
		this.userRepository = userRepository;
	}

	public Authentication authenticateUser(UserDto.LoginRequest loginRequestDto) {
		return authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
	}

	public JwtResponse generateJwtTokens(UserEntity userEntity) {
		String accessToken = jwtTokenProvider.generateAccessToken(userEntity.getEmail());
		String refreshToken = jwtRepository.findByUserAndRevokedFalse(userEntity)
			.map(JwtEntity::getRefreshToken)
			.orElseGet(() -> jwtTokenProvider.generateRefreshToken(userEntity.getEmail()));

		JwtEntity jwtEntity = JwtEntity.builder()
			.refreshToken(refreshToken)
			.expiresAt(LocalDateTime.now().plusDays(7))
			.revoked(false)
			.user(userEntity)
			.build();
		jwtRepository.save(jwtEntity);

		return JwtResponse.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	/**
	 * 새로운 사용자를 저장하는 메서드입니다.
	 * <p>
	 * 이 메서드는 사용자 데이터를 데이터베이스에 저장합니다.
	 *
	 * @param user 저장할 사용자 엔티티
	 */
	public void signUp(UserEntity user, PasswordEntity password) {
		userRepository.save(user);
		passwordRepository.save(password);
	}
}