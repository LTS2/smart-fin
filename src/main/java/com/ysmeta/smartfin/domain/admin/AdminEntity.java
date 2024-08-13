package com.ysmeta.smartfin.domain.admin;

import com.ysmeta.smartfin.common.AbstractBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 고객 엔티티입니다.
 *
 * @author : ewjin
 * @fileName CustomerEntity
 * @since : 2024. 7. 29.
 */
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "USER")
public class AdminEntity extends AbstractBaseEntity {

	/**
	 * 이름
	 */
	String name;

	/**
	 * 주민 등록 번호 (Resident Registration Number)
	 */
	String rrn;
	/**
	 * 전화번호
	 */
	String phoneNumber;

	/**
	 * 주소
	 */
	String address;

	/**
	 * 이메일
	 */
	@Column(unique = true, nullable = false)
	String email;

	/**
	 * 토큰
	 */
	String token;

	public static AdminEntity fromEmail(String email) {
		return AdminEntity.builder().email(email).build();
	}
}