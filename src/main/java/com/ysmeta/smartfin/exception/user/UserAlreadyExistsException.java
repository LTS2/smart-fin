package com.ysmeta.smartfin.exception.user;

import lombok.extern.slf4j.Slf4j;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 17.
 */
@Slf4j
public class UserAlreadyExistsException extends RuntimeException {
	/**
	 * Constructs a new runtime exception with {@code null} as its
	 * detail message.  The cause is not initialized, and may subsequently be
	 * initialized by a call to {@link #initCause}.
	 */
	public UserAlreadyExistsException(String message) {
		super(message);
		log.info("GlobalExceptionHandler 안 타는 거 수정해야함.");
	}
}
