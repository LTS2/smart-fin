package com.ysmeta.smartfin.domain.password;

import com.ysmeta.smartfin.common.AbstractBaseDto;
import com.ysmeta.smartfin.domain.user.UserDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 비밀번호 관련 DTO의 공통 클래스를 정의합니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 8.
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
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

	/**
	 * 요청 DTO 클래스입니다.
	 */
	@Getter
	@Setter
	@SuperBuilder
	public static class PasswordRequestDto extends PasswordDto {
		// 추가적인 요청 관련 필드가 있다면 이곳에 추가할 예정입니다.
	}

	/**
	 * 응답 DTO 클래스입니다.
	 */
	@Getter
	@Setter
	@SuperBuilder
	public static class PasswordResponseDto extends PasswordDto {
		// 추가적인 응답 관련 필드가 있다면 이곳에 추가할 예정입니다.

	}
}
