package com.ysmeta.smartfin.domain.auth;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 12.
 */
public interface AuthRepository extends JpaRepository<AuthEntity, Long> {
}
