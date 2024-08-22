package com.ysmeta.smartfin.domain.user.dto;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 21.
 */

import com.ysmeta.smartfin.common.AbstractBaseDto;
import com.ysmeta.smartfin.domain.user.entity.RoleType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 사용자 권한 DTO 클래스입니다.
 *
 * @author ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 8
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class RoleTypeDto extends AbstractBaseDto {
	private RoleType code;
	private String name;
}
