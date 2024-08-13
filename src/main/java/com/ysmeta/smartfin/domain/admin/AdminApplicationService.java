package com.ysmeta.smartfin.domain.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ysmeta.smartfin.domain.admin.cqrs.AdminCommandService;
import com.ysmeta.smartfin.domain.admin.cqrs.AdminQueryService;

/**
 * 여러 책임을 지고 있는 어플리케이션 클래스입니다.
 * 추후 도메인 서비스도 따로 생성할 예정입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 13.
 */
@Service
public class AdminApplicationService {

	private final AdminQueryService adminQueryService;
	private final AdminCommandService adminCommandService;

	@Autowired
	public AdminApplicationService(AdminQueryService adminQueryService, AdminCommandService adminCommandService) {
		this.adminQueryService = adminQueryService;
		this.adminCommandService = adminCommandService;
	}

	@Transactional
	public void registerUser(AdminEntity user) {
		if (adminQueryService.hasUser(user.getEmail())) {
			throw new IllegalStateException("이미 존재하는 사용자입니다.");
		}

		adminCommandService.saveUser(user);
	}
}
