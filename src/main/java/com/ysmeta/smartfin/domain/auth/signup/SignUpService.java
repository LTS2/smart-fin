package com.ysmeta.smartfin.domain.auth.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ysmeta.smartfin.domain.user.UserService;

/**
 * 회원가입을 관리하는 서비스 클래스입니다.
 * 회원가입 로직을 처리합니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 8.
 */
@Service
public class SignUpService {

	private final UserService userService;

	/**
	 * SignUpService 생성자입니다.
	 *
	 * @param userService 사용자 관련 서비스 클래스
	 */
	@Autowired
	public SignUpService(UserService userService) {
		this.userService = userService;
	}

	// /**
	//  * 회원가입을 처리하는 메서드입니다.
	//  *
	//  * @param userDto 가입할 사용자 정보
	//  * @return 회원가입 성공 시 true, 이미 존재하는 사용자일 경우 false
	//  */
	// public boolean signUp(UserDto.UserRequestDto userDto) {
	// 	// 사용자가 중복일 경우
	// 	if (userService.isUser(userDto.getEmail())) {
	// 		return false;
	// 	}
	// 	// 새로운 사용자 엔티티 생성 및 저장
	// 	UserEntity newUser = new UserEntity();
	// 	newUser.setEmail(userDto.getEmail());
	// 	newUser.setAddress(userDto.getAddress());
	// 	newUser.setName(userDto.getName());
	// 	newUser.setPhoneNumber(userDto.getPhoneNumber());
	// 	newUser.setRrn(userDto.getRrn());
	//
	// 	userService.saveUser(newUser);
	// 	return true;
	// }
}
