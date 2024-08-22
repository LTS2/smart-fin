package com.ysmeta.smartfin.domain.jwt.cqrs;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ysmeta.smartfin.config.auth.jwt.JwtTokenProvider;
import com.ysmeta.smartfin.domain.auth.LoginResponse;
import com.ysmeta.smartfin.domain.jwt.JwtTokenEntity;
import com.ysmeta.smartfin.domain.jwt.JwtTokenRepository;
import com.ysmeta.smartfin.domain.user.entity.UserEntity;

import lombok.extern.slf4j.Slf4j;

/**
 * JwtTokenCommandService는 JWT 토큰의 생성, 갱신, 폐기 등의 명령을 처리하는 서비스입니다.
 * 이 클래스는 JWT 토큰과 관련된 주요 비즈니스 로직을 담당합니다.
 */
@Slf4j
@Service
public class JwtTokenCommandService {
	private final JwtTokenProvider jwtTokenProvider;
	private final JwtTokenRepository jwtTokenRepository;

	/**
	 * JwtTokenCommandService 생성자.
	 *
	 * @param jwtTokenProvider   JWT 토큰 생성 및 검증을 담당하는 프로바이더.
	 * @param jwtTokenRepository JWT 토큰 데이터를 관리하는 레포지토리.
	 */
	public JwtTokenCommandService(JwtTokenProvider jwtTokenProvider, JwtTokenRepository jwtTokenRepository) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.jwtTokenRepository = jwtTokenRepository;
	}

	/**
	 * 주어진 사용자 엔티티와 리프레시 토큰을 바탕으로 새로운 JwtTokenEntity 객체를 생성하고 저장합니다.
	 *
	 * @param userEntity   사용자 엔티티.
	 * @param refreshToken 생성할 리프레시 토큰.
	 */
	private void saveNewRefreshToken(UserDetails userDetails, String refreshToken) {
		JwtTokenEntity newToken = JwtTokenEntity.builder()
			.refreshToken(refreshToken)
			.expiresAt(LocalDateTime.now().plusDays(7))
			.revoked(false)
			.user(UserEntity.fromEmail(userDetails.getUsername()))
			.build();

		jwtTokenRepository.save(newToken);
	}

	/**
	 * <pre>
	 * TODO: 람다식으로 바꾸기.
	 * 주어진 사용자 엔티티에 대한 새로운 Access Token과 Refresh Token을 생성합니다.
	 *
	 * 리프레시 토큰이 만료되었거나 없는 경우, 새로운 리프레시 토큰을 생성하고 저장합니다.
	 * 기존 리프레시 토큰이 유효한 경우, 새로 생성된 Access Token과 기존 리프레시 토큰을 반환합니다.
	 * </pre>
	 *
	 * @param userEntity       JWT 토큰을 생성할 사용자 엔티티.
	 * @param existingTokenOpt 기존의 리프레시 토큰 엔티티.
	 * @return 생성된 JWT 토큰 응답 객체(JwtTokenResponse).
	 */
	@Transactional
	public LoginResponse createOrUpdateTokens(UserDetails userDetails, Optional<JwtTokenEntity> existingTokenOpt) {
		// 새로운 Access Token 생성
		String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
		String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);

		if (existingTokenOpt.isPresent()) {
			JwtTokenEntity existingToken = existingTokenOpt.get();
			if (existingToken.getExpiresAt().isBefore(LocalDateTime.now())) {
				// 만료된 토큰을 삭제하고 새로 생성
				jwtTokenRepository.delete(existingToken);
				saveNewRefreshToken(userDetails, refreshToken);
			} else {
				// 기존 리프레시 토큰이 유효한 경우
				refreshToken = existingToken.getRefreshToken();
			}
		} else {
			// 기존 리프레시 토큰이 없으면 새로 생성
			saveNewRefreshToken(userDetails, refreshToken);
		}

		return LoginResponse.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	/**
	 * 주어진 사용자 엔티티에 대한 모든 JWT 토큰을 폐기합니다.
	 *
	 * @param userEntity 토큰을 폐기할 사용자 엔티티.
	 */
	@Transactional
	public void revokeTokens(UserEntity userEntity) {
		jwtTokenRepository.revokeTokensByUser(userEntity.getId());
	}
}
