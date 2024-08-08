package com.ysmeta.smartfin.domain.user;

import com.ysmeta.smartfin.common.BaseDto;

import lombok.Getter;
import lombok.Setter;

/**
 * 사용자 DTO 클래스입니다.
 *
 * @author ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 8
 */
@Getter
@Setter
public class UserDto extends BaseDto {

	private String name;
	private String rrn;
	private String phoneNumber;
	private String address;
	private String email;
}