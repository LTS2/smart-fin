package com.ysmeta.smartfin.domain.user.service;

import static org.assertj.core.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 14.
 */
@SpringBootTest
class UserApplicationServiceTest {

	@Autowired
	private UserApplicationService userApplicationService;

	@Test
	@DisplayName("Salt 값 생성 테스트")
	void generateSalt() {
		// When: Salt 값을 생성
		try {
			String salt = userApplicationService.generateSalt();

			// 솔트값 어디에 저장하는지 값을 하나만 가지고 있는지 찾아보기

			// Then: Salt 값이 null이 아니고, 16바이트 길이를 가진 Base64 문자열이어야 함
			assertThat(salt).isNotNull();
			byte[] saltBytes = Base64.getDecoder().decode(salt);
			assertThat(saltBytes.length).isEqualTo(16);
		} catch (NoSuchAlgorithmException e) {
			
		}
	}

}