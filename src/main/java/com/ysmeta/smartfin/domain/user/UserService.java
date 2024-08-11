package com.ysmeta.smartfin.domain.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 사용자 관련 서비스 클래스입니다.
 * 사용자 정보를 저장하고 조회하는 기능을 제공합니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 8.
 */
@Service
@Slf4j
public class UserService implements IUserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	/**
	 * UserService 생성자입니다.
	 *
	 * @param userRepository  사용자 저장소 인터페이스
	 * @param passwordEncoder 비밀번호 인코더
	 */
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * 이메일을 통해 사용자가 존재하는지 확인하는 메서드입니다.
	 *
	 * @param email 확인할 사용자의 이메일 주소
	 * @return 이메일에 해당하는 사용자가 존재하면 true, 그렇지 않으면 false
	 */
	@Override
	public boolean isUser(String email) {
		return userRepository.findByEmail(email).isPresent();
	}

	/**
	 * 사용자를 저장하는 메서드입니다.
	 *
	 * @param user 저장할 사용자 엔티티
	 */
	@Override
	public void saveUser(UserEntity user) {
		userRepository.save(user);
	}

	@Override
	public void deleteUser(String user) {
		// userRepository.delete();
	}

}

