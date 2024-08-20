package com.ysmeta.smartfin.domain.jwt.cqrs;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ysmeta.smartfin.domain.jwt.JwtTokenEntity;
import com.ysmeta.smartfin.domain.jwt.JwtTokenRepository;
import com.ysmeta.smartfin.domain.user.UserEntity;

import lombok.extern.slf4j.Slf4j;

/**
 * JwtTokenQueryService는 JWT 토큰과 관련된 조회 작업을 처리하는 서비스입니다.
 * 이 클래스는 주로 데이터베이스에서 특정 조건에 맞는 JWT 토큰을 검색하는 역할을 합니다.
 */
@Slf4j
@Service
public class JwtTokenQueryService {

	private final JwtTokenRepository jwtTokenRepository;

	/**
	 * JwtTokenQueryService 생성자.
	 *
	 * @param jwtTokenRepository JWT 토큰 데이터를 관리하는 레포지토리.
	 */
	public JwtTokenQueryService(JwtTokenRepository jwtTokenRepository) {
		this.jwtTokenRepository = jwtTokenRepository;
	}

	/**
	 * 주어진 사용자 엔티티에 대해 유효한 리프레시 토큰을 조회합니다.
	 * <pre>
	 * 이 메서드는 주어진 사용자의 토큰이 취소(revoked)되지 않은 상태에서 존재하는지 확인합니다.
	 * 사용 가능 상태 = revoked = 0
	 * 취소 상태 = revoked = 1
	 * </pre>
	 *
	 * @param userEntity 유효한 리프레시 토큰을 찾을 사용자 엔티티.
	 * @return 유효한 리프레시 토큰이 존재하면 해당 토큰 엔티티를 반환하고, 그렇지 않으면 빈 Optional을 반환합니다.
	 */
	public Optional<JwtTokenEntity> findValidRefreshToken(UserEntity userEntity) {
		return jwtTokenRepository.findByUserAndRevokedFalse(userEntity);
	}
}
