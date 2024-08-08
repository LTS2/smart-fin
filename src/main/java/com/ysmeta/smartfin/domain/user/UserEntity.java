package com.ysmeta.smartfin.domain.user;

import com.ysmeta.smartfin.common.BaseEntity;
import com.ysmeta.smartfin.domain.auth.password.PasswordEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * 고객 엔티티입니다.
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

	/**
	 * 비밀번호 엔티티
	 */
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private PasswordEntity passwordEntity;
}

