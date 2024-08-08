package com.ysmeta.smartfin.domain.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 8.
 */
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	// public UserEntity saveUser(UserEntity user) {
	// 	user.setPassword(passwordEncoder.encode(user.getPassword()));
	// 	return userRepository.save(user);
	// }
	//
	// public UserEntity getUserByEmail(String email) {
	// 	return userRepository.findByEmail(email);
	// }
}