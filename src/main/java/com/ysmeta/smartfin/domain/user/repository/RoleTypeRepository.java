package com.ysmeta.smartfin.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ysmeta.smartfin.domain.user.entity.RoleType;
import com.ysmeta.smartfin.domain.user.entity.RoleTypeEntity;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 21.
 */
public interface RoleTypeRepository extends JpaRepository<RoleTypeEntity, Long> {
	Optional<RoleTypeEntity> findByCode(RoleType code);
}
