package com.ysmeta.smartfin.domain.password;

import com.ysmeta.smartfin.common.AbstractBaseDto;
import com.ysmeta.smartfin.domain.user.UserDto;

import lombok.Getter;
import lombok.Setter;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 8.
 */
@Getter
@Setter
public class PasswordDto extends AbstractBaseDto {

	/**
	 * 유저 아이디 외래키
	 */
	private UserDto user;
	/**
	 * SHA-256로 암호화된 비밀번호
	 */
	private String hashedPassword;

	/**
	 * 솔트값
	 */
	private String salt;
}
