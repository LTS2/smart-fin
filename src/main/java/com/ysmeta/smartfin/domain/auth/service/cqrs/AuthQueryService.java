package com.ysmeta.smartfin.domain.auth.service.cqrs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
