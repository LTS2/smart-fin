package com.ysmeta.smartfin.domain.auth.service.cqrs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ysmeta.smartfin.domain.user.UserEntity;
import com.ysmeta.smartfin.domain.user.UserRepository;

/**
 * AuthQueryService 클래스는 사용자 인증과 관련된 조회(Query) 작업을 처리하는 서비스입니다.
 * <p>
 * 이 클래스는 사용자 이메일로 사용자를 조회하고, 존재하지 않을 경우 예외를 던집니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 16.
 */
@Service
public class AuthQueryService {

	private final UserRepository userRepository;

	/**
	 * AuthQueryService 생성자입니다.
	 *
	 * @param userRepository 사용자 엔티티를 조회하는 UserRepository 인스턴스
	 */
	@Autowired
	public AuthQueryService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * 주어진 이메일을 기반으로 사용자를 조회합니다.
	 * <p>
	 * 사용자가 존재하지 않으면 UsernameNotFoundException 예외를 던집니다.
	 *
	 * @param email 조회할 사용자의 이메일 주소
	 * @return 주어진 이메일을 가진 UserEntity 객체
	 * @throws UsernameNotFoundException 사용자가 존재하지 않을 경우 던져지는 예외
	 */
	public UserEntity findUserByEmail(String email) {
		return userRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email));
	}
}
