package com.ysmeta.smartfin.domain.user.service.cqrs;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ysmeta.smartfin.domain.password.PasswordEntity;
import com.ysmeta.smartfin.domain.password.PasswordRepository;
import com.ysmeta.smartfin.domain.user.UserRepository;
import com.ysmeta.smartfin.domain.user.entity.UserEntity;

import lombok.extern.slf4j.Slf4j;

/**
 * CQRS 패턴
 * 사용자 명령 서비스 클래스입니다.
 * <p>
 * 이 클래스는 사용자 데이터를 생성, 수정, 삭제하는 작업을 담당합니다.
 * 모든 메서드는 트랜잭션을 통해 데이터의 일관성을 보장합니다.
 * 지금은 단순한 메서드만 있어 필요없어 보일 수 있지만 추후 추가적인 요구사항에 유연하게 확장하기 위해 사용
 *
 * @author ewjin
 * @version 0.0.1
 * @since 2024. 8. 8.
 */
@Transactional
@Service
@Slf4j
public class UserCommandService {

	private final UserRepository userRepository;
	private final PasswordRepository passwordRepository;

	/**
	 * UserCommandService 생성자입니다.
	 *
	 * @param userRepository 사용자 저장소 인터페이스
	 */
	public UserCommandService(UserRepository userRepository, PasswordRepository passwordRepository) {
		this.userRepository = userRepository;
		this.passwordRepository = passwordRepository;
	}

	/**
	 * 새로운 사용자를 저장하는 메서드입니다.
	 * <p>
	 * 이 메서드는 사용자 데이터를 데이터베이스에 저장합니다.
	 *
	 * @param user 저장할 사용자 엔티티
	 */
	public void save(UserEntity user, PasswordEntity password) {
		userRepository.save(user);
		passwordRepository.save(password);
	}

	/**
	 * 사용자를 삭제하는 메서드입니다.
	 * <p>
	 * 이 메서드는 일치하는 사용자를 삭제합니다.
	 *
	 * @param user 삭제할 사용자 정보
	 */
	public void deleteUser(UserEntity user) {
		userRepository.delete(user);
	}
}
