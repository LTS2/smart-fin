package com.ysmeta.smartfin.domain.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ysmeta.smartfin.domain.user.service.UserApplicationService;
import com.ysmeta.smartfin.util.helper.error.ErrorMessageHelper;

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
@RequestMapping("/api/users")
@Slf4j
public class UserController {

	private final UserApplicationService userApplicationService;

	@Autowired
	public UserController(UserApplicationService userApplicationService) {
		this.userApplicationService = userApplicationService;
	}

	/**
	 * 사용자 회원가입 요청을 처리하는 메서드입니다.
	 * <p>
	 * 이 메서드는 @Valid 어노테이션을 통해 들어오는 요청 데이터를 유효성 검사하며,
	 * 유효성 검사에 실패할 경우 적절한 오류 메시지를 반환합니다.
	 * 유효성 검사에 통과하면 회원가입 로직을 처리하고, 성공적인 응답을 반환합니다.
	 *
	 * @param createUserRequestDto 유효성 검사를 수행할 사용자 요청 데이터 객체
	 * @param bindingResult        유효성 검사 결과를 담고 있는 BindingResult 객체
	 * @return ResponseEntity 객체로 HTTP 상태 코드와 메시지를 포함한 응답을 반환합니다.
	 */
	@PostMapping
	public ResponseEntity<String> registerUser(@Valid @RequestBody UserDto.CreateUserRequestDto createUserRequestDto,
		BindingResult bindingResult) {
		// 유효성 검사에서 오류가 있는 경우
		if (bindingResult.hasErrors()) {
			String errorMessage = ErrorMessageHelper.getOrDefaultErrorMessage(bindingResult);
			log.info("유효성 검사를 통과하지 못했습니다.: {}", errorMessage);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}

		try {
			userApplicationService.registerUser(createUserRequestDto);
			log.info("사용자 {}가 성공적으로 등록되었습니다.", createUserRequestDto.getEmail());
			return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료");
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 사용자입니다.");
		}
	}
}
