package com.ysmeta.smartfin.domain.auth.jwt;

import static jakarta.persistence.FetchType.*;

import java.time.LocalDateTime;

import com.ysmeta.smartfin.common.AbstractBaseEntity;
import com.ysmeta.smartfin.domain.user.UserEntity;

import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * TokenEntity 클래스는 리프레시 토큰과 관련된 정보를 저장하는 엔티티입니다.
 * 이 클래스는 특정 사용자와 연결된 리프레시 토큰의 상태 및 만료 시간 등을 관리합니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 16.
 */
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "TOKEN")
public class JwtTokenEntity extends AbstractBaseEntity {

	/**
	 * 리프레시 토큰을 저장하는 필드입니다.
	 * 이 필드는 각 사용자가 인증을 갱신하기 위해 사용하는 고유한 토큰 문자열을 저장합니다.
	 */
	String refreshToken;

	/**
	 * 리프레시 토큰의 만료 시간을 저장하는 필드입니다.
	 * 이 필드는 토큰이 언제 만료되는지를 나타내며, 만료된 후에는 더 이상 사용할 수 없습니다.
	 */
	LocalDateTime expiresAt;

	/**
	 * 리프레시 토큰이 철회되었는지를 나타내는 필드입니다.
	 * 이 필드는 토큰이 더 이상 유효하지 않은 경우(예: 로그아웃 시) "true" 값을 가집니다.
	 */
	boolean revoked;

	/**
	 * 사용자가 접속한 기기에 대한 정보를 담는 필드입니다.
	 * 존재 이유는 각 기기별로(모바일, 데스크탑, 태블릿) 로그아웃을 시킬 수 있는 기능을 위해서 넣어놨습니다.
	 */
	String deviceId;

	/**
	 * 이 리프레시 토큰이 속한 사용자 엔티티를 참조하는 필드입니다.
	 * 다대일(Many-to-One) 관계를 나타내며, 한 명의 사용자가 여러 개의 리프레시 토큰을 가질 수 있습니다.
	 * `fetch = LAZY` 설정은 이 필드를 지연 로딩하여 필요할 때만 로딩되도록 합니다.
	 */
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "USER_ID", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	UserEntity user;
}