package com.ysmeta.smartfin.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 8.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByEmail(String email);
}
