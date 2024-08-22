package com.ysmeta.smartfin.domain.jwt;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.ysmeta.smartfin.domain.user.entity.UserEntity;

/**
 * TokenRepository 인터페이스는 TokenEntity를 관리하기 위한 JPA 리포지토리입니다.
 * 사용자와 연관된 리프레시 토큰을 조회하거나 관리하는 메서드를 정의합니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 16.
 */
public interface JwtTokenRepository extends JpaRepository<JwtTokenEntity, Long> {

	/**
	 * 사용자와 연관된 유효한 리프레시 토큰을 조회하는 메서드입니다.
	 *
	 * @param user 조회할 사용자 엔티티
	 * @return 유효한 리프레시 토큰이 있으면 Optional<TokenEntity>를 반환하고, 그렇지 않으면 빈 Optional을 반환합니다.
	 */
	Optional<JwtTokenEntity> findByUserAndRevokedFalse(UserEntity user);

	@Modifying
	@Transactional
	@Query("UPDATE TOKEN j SET j.revoked = true WHERE j.user.id = :userId")
	void revokeTokensByUser(Long userId);
}
