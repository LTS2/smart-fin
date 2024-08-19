package com.ysmeta.smartfin.domain.auth.jwt;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ysmeta.smartfin.domain.auth.jwt.cqrs.JwtTokenCommandService;
import com.ysmeta.smartfin.domain.auth.jwt.cqrs.JwtTokenQueryService;
import com.ysmeta.smartfin.domain.user.UserEntity;

import lombok.extern.slf4j.Slf4j;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 18.
 */
@Slf4j
@Service
public class JwtTokenApplicationService {
	private final JwtTokenCommandService jwtTokenCommandService;
	private final JwtTokenQueryService jwtTokenQueryService;

	public JwtTokenApplicationService(JwtTokenCommandService jwtTokenCommandService,
		JwtTokenQueryService jwtTokenQueryService) {
		this.jwtTokenCommandService = jwtTokenCommandService;
		this.jwtTokenQueryService = jwtTokenQueryService;
	}

	@Transactional
	public JwtTokenResponse generateJwtTokens(UserEntity userEntity) {
		// Query Service를 통해 기존 유효한 토큰을 조회
		Optional<JwtTokenEntity> existingTokenOpt = jwtTokenQueryService.findValidRefreshToken(userEntity);

		// 기존 토큰이 없거나, 만료된 경우 새로 생성 후 저장
		if (existingTokenOpt.isEmpty() || existingTokenOpt.get().getExpiresAt().isBefore(LocalDateTime.now())) {
			return jwtTokenCommandService.generateJwtTokens(userEntity);
		}

		// 이미 유효한 토큰이 있는 경우, 기존 토큰 반환
		JwtTokenEntity existingToken = existingTokenOpt.get();
		String accessToken = jwtTokenCommandService.generateJwtTokens(userEntity).getAccessToken();

		return JwtTokenResponse.builder()
			.accessToken(accessToken)
			.refreshToken(existingToken.getRefreshToken())
			.build();
	}

	public void revokeTokens(UserEntity userEntity) {
		jwtTokenCommandService.revokeTokens(userEntity);
	}
}

