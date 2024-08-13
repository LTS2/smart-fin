package com.ysmeta.smartfin.domain.admin.cqrs;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ysmeta.smartfin.domain.admin.AdminEntity;
import com.ysmeta.smartfin.domain.admin.AdminRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * CQRS 패턴
 * 사용자 명령 서비스 클래스입니다.
 * <p>
 * 이 클래스는 사용자 데이터를 생성, 수정, 삭제하는 작업을 담당합니다.
 * 모든 메서드는 트랜잭션을 통해 데이터의 일관성을 보장합니다.
 *
 * @author ewjin
 * @version 0.0.1
 * @since 2024. 8. 8.
 */
@Transactional
@Service
@Slf4j
public class AdminCommandService {

	private final AdminRepository adminRepository;
	private final AdminQueryService adminQueryService;
	private final PasswordEncoder passwordEncoder;

	/**
	 * UserCommandService 생성자입니다.
	 *
	 * @param adminRepository   사용자 저장소 인터페이스
	 * @param passwordEncoder   비밀번호 인코더
	 * @param adminQueryService 사용자 조회 서비스
	 */
	public AdminCommandService(AdminRepository adminRepository, PasswordEncoder passwordEncoder,
		AdminQueryService adminQueryService) {
		this.adminRepository = adminRepository;
		this.passwordEncoder = passwordEncoder;
		this.adminQueryService = adminQueryService;
	}

	/**
	 * 새로운 사용자를 저장하는 메서드입니다.
	 * <p>
	 * 이 메서드는 사용자 데이터를 데이터베이스에 저장합니다.
	 *
	 * @param user 저장할 사용자 엔티티
	 */
	public void saveUser(AdminEntity user) {
		adminRepository.save(user);
	}

	/**
	 * 사용자 회원가입을 처리하는 메서드입니다.
	 * <p>
	 * 이 메서드는 사용자가 이미 존재하는지 확인한 후, 새로운 사용자를 등록합니다.
	 *
	 * @param user 등록할 사용자 엔티티
	 * @throws IllegalStateException 이미 존재하는 사용자일 경우 발생
	 */
	public void registerUser(AdminEntity user) {
		if (adminQueryService.hasUser(user.getEmail())) {
			throw new IllegalStateException("이미 존재하는 사용자입니다.");
		}
		saveUser(user);
	}

	/**
	 * 사용자를 삭제하는 메서드입니다.
	 * <p>
	 * 이 메서드는 일치하는 사용자를 삭제합니다.
	 *
	 * @param user 삭제할 사용자 정보
	 */
	public void deleteUser(AdminEntity user) {
		adminRepository.delete(user);
	}
}