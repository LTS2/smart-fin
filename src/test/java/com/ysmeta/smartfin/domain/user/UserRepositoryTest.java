package com.ysmeta.smartfin.domain.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ysmeta.smartfin.domain.user.entity.UserEntity;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 21.
 */
@SpringBootTest
class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Test
	void test() {
		UserEntity a = userRepository.findAllByEmailWithRoles("leewoojin98@naver.com");
		// for (UserEntity userEntity : a) {
		// 	System.out.println(userEntity.getUserRoles().get(0).getUser());
		// 	System.out.println(userEntity.getUserRoles().get(0).getRoleTypeCode());
		// System.out.println(userEntity.get)
		// System.out.println("ddddd");
		// }
	}
}