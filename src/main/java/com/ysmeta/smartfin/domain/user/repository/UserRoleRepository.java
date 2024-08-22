package com.ysmeta.smartfin.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ysmeta.smartfin.domain.user.entity.UserRoleEntity;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 21.
 */
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
}
