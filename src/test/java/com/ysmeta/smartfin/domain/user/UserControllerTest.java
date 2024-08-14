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
	@DisplayName("SUCCESS 유저 비밀번호를 Bcrypt 암호화 하여 저장한다")
	void testSaveBcryptPassword() {
		// given
		UserDto userDto = new UserDto();
		
		// when

		// then
	}
}