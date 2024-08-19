package com.ysmeta.smartfin.domain.auth.jwt.cqrs;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ysmeta.smartfin.config.auth.jwt.JwtTokenProvider;
import com.ysmeta.smartfin.domain.auth.jwt.JwtTokenEntity;
import com.ysmeta.smartfin.domain.auth.jwt.JwtTokenRepository;
import com.ysmeta.smartfin.domain.auth.jwt.JwtTokenResponse;
import com.ysmeta.smartfin.domain.user.UserEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtTokenCommandService {
	private final JwtTokenProvider jwtTokenProvider;
	private final JwtTokenRepository jwtTokenRepository;

	public JwtTokenCommandService(JwtTokenProvider jwtTokenProvider, JwtTokenRepository jwtTokenRepository) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.jwtTokenRepository = jwtTokenRepository;
	}

	private static JwtTokenEntity getJwtTokenEntity(UserEntity userEntity, String refreshToken) {
		JwtTokenEntity newToken = JwtTokenEntity.builder()
			.refreshToken(refreshToken)
			.expiresAt(LocalDateTime.now().plusDays(7))
			.revoked(false)
			.user(userEntity)
			.build();
		return newToken;
	}

	private static JwtTokenResponse getTokenResponse(String accessToken, String refreshToken) {
		return JwtTokenResponse.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	@Transactional
	public JwtTokenResponse generateJwtTokens(UserEntity userEntity) {
		// 새로운 Access Token 생성
		String accessToken = jwtTokenProvider.generateAccessToken(userEntity.getEmail());

		// 기존 리프레시 토큰 조회
		Optional<JwtTokenEntity> existingTokenOpt = jwtTokenRepository.findByUserAndRevokedFalse(userEntity);

		if (existingTokenOpt.isPresent()) {
			JwtTokenEntity existingToken = existingTokenOpt.get();
			// 리프레시 토큰이 만료되었는지 확인
			if (existingToken.getExpiresAt().isBefore(LocalDateTime.now())) {
				// 만료된 토큰을 삭제
				jwtTokenRepository.delete(existingToken);

				// 새로운 리프레시 토큰 생성
				String refreshToken = jwtTokenProvider.generateRefreshToken(userEntity.getEmail());
				JwtTokenEntity newToken = getJwtTokenEntity(userEntity, refreshToken);

				jwtTokenRepository.save(newToken);

				// 새로운 토큰 응답 생성
				return getTokenResponse(accessToken, refreshToken);
			} else {
				// 리프레시 토큰이 아직 유효한 경우, 기존 토큰을 반환
				return getTokenResponse(accessToken, existingToken.getRefreshToken());
			}
		} else {
			// 리프레시 토큰이 없는 경우 (최초 로그인 등)
			String refreshToken = jwtTokenProvider.generateRefreshToken(userEntity.getEmail());
			JwtTokenEntity newToken = getJwtTokenEntity(userEntity, refreshToken);

			jwtTokenRepository.save(newToken);

			// 새로운 토큰 응답 생성
			return getTokenResponse(accessToken, refreshToken);
		}
	}

	@Transactional
	public void revokeTokens(UserEntity userEntity) {
		jwtTokenRepository.revokeTokensByUser(userEntity.getId());
	}
}
