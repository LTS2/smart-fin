package com.ysmeta.smartfin.domain.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * UserController 클래스는 사용자와 관련된 HTTP 요청을 처리하는 컨트롤러입니다.
 * <p>
 * 이 클래스는 사용자의 회원가입 요청을 처리하며, 유효성 검사를 통해 적절한 응답을 반환합니다.
 * 회원가입 요청이 성공적으로 처리될 경우, 생성된 상태 코드를 반환하고, 유효성 검사에 실패할 경우
 * 적절한 오류 메시지를 포함한 응답을 반환합니다.
 * <p>
 * 이 클래스는 @RestController로 선언되어 있으며, "/api/users" 경로로 들어오는 요청을 처리합니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 11.
 */
@RestController
@RequestMapping("/api/admin")
@Slf4j
public class AdminController {

	private final AdminApplicationService adminApplicationService;

	@Autowired
	public AdminController(AdminApplicationService adminApplicationService) {
		this.adminApplicationService = adminApplicationService;
	}

}
