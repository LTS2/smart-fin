package com.ysmeta.smartfin.domain.user.entity;

import com.ysmeta.BaseEntity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * 고객 엔티티
 *
 * @author : ewjin
 * @fileName CustomerEntity
 * @since : 2024. 7. 29.
 */
@Getter
@Setter
@Entity(name = "USER")
public class UserEntity extends BaseEntity {

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
	String email;

	/**
	 * 토큰
	 */
	String token;
}

