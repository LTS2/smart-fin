package com.ysmeta.smartfin.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ysmeta.smartfin.domain.user.service.UserApplicationService;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 14.
 */
@SpringBootTest
class UserControllerTest {
	@Autowired
	private UserController userController;

	@Autowired
	private UserApplicationService userApplicationService;

	@Test
	@DisplayName("1. SUCCESS 로그인 시 엑세스 토큰과 리프레시 토큰 발급")
	void testSaveBcryptPassword() {
		// given
		UserDto userDto = new UserDto();

		// when

		// then
	}
}