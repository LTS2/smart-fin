package com.ysmeta.smartfin.common;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 공통 사용 컬럼 DTO 관리 클래스입니다.
 *
 * @author : ewjin
 * @fileName : BaseDto
 * @since : 2024. 8. 8.
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public abstract class AbstractBaseDto {

	private Long id;

	/**
	 * 생성 날짜
	 */
	private LocalDateTime createdAt;

	/**
	 * 수정 날짜
	 */
	private LocalDateTime updatedAt;

	/**
	 * 삭제 날짜
	 */
	private LocalDateTime deletedAt;

	/**
	 * 생성한 사람
	 */
	private String createdBy;

	/**
	 * 수정한 사람
	 */
	private String updatedBy;
}