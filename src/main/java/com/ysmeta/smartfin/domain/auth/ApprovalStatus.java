package com.ysmeta.smartfin.domain.auth;

import lombok.Getter;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 12.
 */
@Getter
public enum ApprovalStatus {
	WAIT("대기중"), // 첫 회원가입 신청시 대기상태
	APPROVED("승인됨"), // 승인 상태
	REJECTED("거부됨"); // 승인 거부 상태
	private final String description;

	ApprovalStatus(String description) {
		this.description = description;
	}
}
