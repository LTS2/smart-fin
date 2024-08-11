package com.ysmeta.smartfin.domain.user;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 11.
 */
public interface IUserService {
	boolean isUser(String email);

	void saveUser(UserEntity user);

	default void deleteUser(String user) {
		System.out.println("default Test");
	}

}
