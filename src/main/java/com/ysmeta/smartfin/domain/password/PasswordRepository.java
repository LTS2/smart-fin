package com.ysmeta.smartfin.domain.password;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ysmeta.smartfin.domain.user.entity.UserEntity;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 12.
 */
@Repository
public interface PasswordRepository extends JpaRepository<PasswordEntity, Long> {

	PasswordEntity findByUser(UserEntity user);
}
