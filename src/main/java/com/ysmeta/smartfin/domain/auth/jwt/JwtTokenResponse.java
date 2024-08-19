package com.ysmeta.smartfin.domain.auth.jwt;

import com.ysmeta.smartfin.common.AbstractBaseDto;

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
public class JwtTokenResponse extends AbstractBaseDto {
	private String accessToken;
	private String refreshToken;
	private String errorMessage;
}
