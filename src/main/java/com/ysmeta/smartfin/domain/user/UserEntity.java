package com.ysmeta.smartfin.domain.user;

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
public class UserEntity extends AbstractBaseEntity {

	/**
	 * 이름
	 */
	String name;

	/**
	 * 이메일
	 */
	@Column(unique = true, nullable = false)
	String email;

	String companyName;

	// @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	// private PasswordEntity passwordEntity;

	public static UserEntity fromEmail(String email) {
		return UserEntity.builder().email(email).build();
	}

}