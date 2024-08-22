package com.ysmeta.smartfin.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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
public class RoleTypeEntity {

	@Id
	@Enumerated(EnumType.STRING)  // EnumType.ORDINAL을 사용하면 순서값이 저장됨
	@Column(nullable = false, unique = true)
	private RoleType code;

	@Column(nullable = false)
	private String name;

}
