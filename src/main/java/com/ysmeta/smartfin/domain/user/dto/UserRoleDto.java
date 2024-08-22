package com.ysmeta.smartfin.domain.user.dto;

import com.ysmeta.smartfin.common.AbstractBaseDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 사용자 DTO 클래스입니다.
 *
 * @author ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 21
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class UserRoleDto extends AbstractBaseDto {
	private UserDto user;
	private RoleTypeDto roleType;
}
