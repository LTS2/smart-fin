package com.ysmeta.smartfin.domain.auth;

import com.ysmeta.smartfin.common.AbstractBaseDto;
import com.ysmeta.smartfin.domain.user.dto.UserDto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 16.
 */
@Getter
@SuperBuilder
public class LoginResponse extends AbstractBaseDto {
	private String accessToken;
	private String refreshToken;
	private UserDto.LoginResponse user;
	private String errorMessage;
}
