package com.ysmeta.smartfin.domain.role.entity;

import com.ysmeta.smartfin.common.AbstractBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 20.
 */
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "ROLE_TYPE")
public class RoleTypeEntity extends AbstractBaseEntity {

	// TODO: enum 으로 code 타입 관리
	@Column(nullable = false, unique = true)
	private String code;

	@Column(nullable = false)
	private String name;
}
