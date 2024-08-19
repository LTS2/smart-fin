package com.ysmeta.smartfin.domain.auth.jwt;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ysmeta.smartfin.domain.auth.jwt.cqrs.JwtTokenCommandService;
import com.ysmeta.smartfin.domain.auth.jwt.cqrs.JwtTokenQueryService;
import com.ysmeta.smartfin.domain.user.UserEntity;

import lombok.extern.slf4j.Slf4j;

/**
 * JwtTokenApplicationService는 비즈니스 로직의 흐름을 관리하는 서비스입니다.
 * 이 클래스는 JWT 토큰의 생성, 갱신, 폐기 등의 작업을 조정합니다.
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

	/**
	 * 주어진 사용자 엔티티에 대한 JWT 토큰을 생성하거나 기존의 유효한 토큰을 반환합니다.
	 * <p>
	 * 이 메서드는 기존 리프레시 토큰이 만료되었는지 여부를 확인하고,
	 * 필요 시 새로운 리프레시 토큰을 생성한 후, 새로 생성된 Access Token과 함께 반환합니다.
	 *
	 * @param userEntity JWT 토큰을 생성할 사용자 엔티티.
	 * @return 생성되거나 기존의 유효한 JWT 토큰 응답 객체(JwtTokenResponse).
	 */
	@Transactional
	public JwtTokenResponse generateJwtTokens(UserEntity userEntity) {
		// Query Service를 통해 기존 유효한 토큰을 조회
		Optional<JwtTokenEntity> existingTokenOpt = jwtTokenQueryService.findValidRefreshToken(userEntity);

		// 토큰을 생성하거나 업데이트
		return jwtTokenCommandService.createOrUpdateTokens(userEntity, existingTokenOpt);
	}

	/**
	 * 주어진 사용자 엔티티에 대한 모든 JWT 토큰을 폐기합니다.
	 *
	 * @param userEntity 토큰을 폐기할 사용자 엔티티.
	 */
	public void revokeTokens(UserEntity userEntity) {
		jwtTokenCommandService.revokeTokens(userEntity);
	}
}
