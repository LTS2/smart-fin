package com.ysmeta.smartfin.domain.user.service.cqrs;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ysmeta.smartfin.domain.user.UserRepository;
import com.ysmeta.smartfin.domain.user.entity.UserEntity;

import lombok.extern.slf4j.Slf4j;

/**
 * CQRS 패턴
 * 사용자 조회 서비스 클래스입니다.
 * <p>
 * 이 클래스는 사용자 데이터를 조회하는 작업을 담당합니다.
 * 모든 메서드는 읽기 전용 트랜잭션을 통해 성능을 최적화합니다.
 *
 * @author ewjin
 * @version 0.0.1
 * @since 2024. 8. 8.
 */
@Transactional(readOnly = true)
@Service
@Slf4j
public class UserQueryService {

	private final UserRepository userRepository;

	/**
	 * UserQueryService 생성자입니다.
	 *
	 * @param userRepository 사용자 저장소 인터페이스
	 */
	public UserQueryService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * 이메일을 통해 사용자가 존재하는지 확인하는 메서드입니다.
	 * <p>
	 * 주어진 이메일 주소로 사용자가 존재하는지 확인합니다.
	 *
	 * @param email 확인할 사용자의 이메일 주소
	 * @return 사용자가 존재하면 true, 그렇지 않으면 false를 반환
	 */
	public boolean hasUser(String email) {
		return userRepository.findByEmail(email).isPresent();
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
	public UserEntity findByEmail(String email) {
		return userRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email));
	}
}