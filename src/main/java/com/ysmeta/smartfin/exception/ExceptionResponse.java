package com.ysmeta.smartfin.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * 예외 응답 반환용 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 17.
 */
@Getter
public class ExceptionResponse {
	private final HttpStatus status;
	private final String message;
	private final LocalDateTime timestamp;

	public ExceptionResponse(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
		this.timestamp = LocalDateTime.now();
	}

}
