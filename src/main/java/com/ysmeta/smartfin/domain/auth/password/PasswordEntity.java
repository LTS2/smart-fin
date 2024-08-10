package com.ysmeta.smartfin.domain.auth.password;

import com.ysmeta.smartfin.common.AbstractBaseEntity;
import com.ysmeta.smartfin.domain.user.UserEntity;

import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * 비밀번호 엔티티 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 8.
 */
@Getter
@Setter
@Entity(name = "PASSWORD")
public class PasswordEntity extends AbstractBaseEntity {

	/**
	 * 유저 아이디 외래키
	 * cascade = 유저가 생성, 삭제될 때 비밀번호도 함께 생성, 삭제되도록 설정
	 */
	@OneToOne
	@JoinColumn(name = "USER_ID", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private UserEntity user;

	/**
	 * SHA-256로 암호화된 비밀번호
	 */
	private String hashedPassword;

	/**
	 * 솔트값
	 */
	private String salt;
}
