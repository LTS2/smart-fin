package com.ysmeta.smartfin.domain.user;

import com.ysmeta.smartfin.common.AbstractBaseEntity;
import com.ysmeta.smartfin.domain.auth.password.PasswordEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class UserEntity extends AbstractBaseEntity {

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

	/**
	 * 비밀번호 엔티티
	 */
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private PasswordEntity passwordEntity;
}