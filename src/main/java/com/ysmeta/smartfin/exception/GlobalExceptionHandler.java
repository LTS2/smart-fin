package com.ysmeta.smartfin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ysmeta.smartfin.exception.user.UserAlreadyExistsException;

import lombok.extern.slf4j.Slf4j;

/**
 * 에러 전역 핸들러 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 17.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ExceptionResponse> handleUserAlreadyExistsException(
		UserAlreadyExistsException euserAlreadyExistsException) {
		log.info("이거 써야되는데 안 타네 이거 수정하기.");
		log.info("이미 존재하는 사용자 예외 처리");
		ExceptionResponse response = new ExceptionResponse(HttpStatus.CONFLICT,
			euserAlreadyExistsException.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleGeneralException(Exception exception) {
		log.error("예상치 못한 오류 처리", exception);
		ExceptionResponse response = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}
