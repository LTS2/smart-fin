package com.ysmeta.smartfin.domain.auth;

import static jakarta.persistence.FetchType.*;

import com.ysmeta.smartfin.common.AbstractBaseEntity;
import com.ysmeta.smartfin.domain.user.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 권한 엔티티 클래스입니다.
 * <p>
 * 이 클래스는 사용자의 권한을 나타내며, 사용자와 권한 간의 관계를 매핑합니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 8.
 */
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "AUTH")
public class AuthEntity extends AbstractBaseEntity {

	/**
	 * 유저 ID 외래키
	 * <p>
	 * 이 필드는 AUTH 테이블과 USER 테이블 간의 연관관계를 나타냅니다.
	 * 각 권한은 하나의 사용자와 연결되며, 권한 엔티티가 소유하는 다대일(ManyToOne) 관계를 정의합니다.
	 * 외래키 제약 조건을 비활성화하려면 ConstraintMode.NO_CONSTRAINT를 사용합니다.
	 * <p>
	 * 또한, 이 필드는 지연 로딩(FetchType.LAZY)을 사용하여 필요할 때만 연관된 사용자 엔티티를 로드합니다.
	 */
	@ManyToOne(fetch = LAZY)
	@JoinColumn(nullable = false, name = "USER_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private UserEntity user;

	/**
	 * 관리자 권한(슈퍼 유저)
	 * <p>
	 * 이 필드는 사용자가 관리자 권한을 가지고 있는지를 나타냅니다.
	 * 값이 true이면 관리자인 경우를 의미하고, false이면 관리자가 아님을 의미합니다.
	 * 데이터베이스에 저장될 때, TINYINT(1) 형식으로 저장됩니다.
	 */
	@Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
	private boolean isAdmin;

	/**
	 * 매니저 권한
	 * <p>
	 * 이 필드는 사용자가 매니저 권한을 가지고 있는지를 나타냅니다.
	 * 값이 true이면 매니저인 경우를 의미하고, false이면 매니저가 아님을 의미합니다.
	 * 데이터베이스에 저장될 때, TINYINT(1) 형식으로 저장됩니다.
	 */
	@Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
	private boolean isManager;

	/**
	 * 유저 권한
	 * <p>
	 * 이 필드는 사용자가 일반 유저 권한을 가지고 있는지를 나타냅니다.
	 * 값이 true이면 일반 유저인 경우를 의미하고, false이면 일반 유저가 아님을 의미합니다.
	 * 데이터베이스에 저장될 때, TINYINT(1) 형식으로 저장됩니다.
	 */
	@Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
	private boolean isUser;

	/**
	 * 회원가입 승인 상태
	 * <pre>
	 * 이 필드는 사용자의 회원가입이 승인된 상태를 나타냅니다.
	 * ApprovalStatus 열거형(Enum)을 사용하여 회원가입 승인 상태를 관리합니다.
	 * - WAIT: 회원가입 신청 시 대기 상태
	 * - APPROVED: 승인된 상태
	 * - REJECTED: 승인 거부 상태
	 * 데이터베이스에는 문자열 형식(EnumType.STRING)으로 저장됩니다.
	 * </pre>
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ApprovalStatus approvalStatus = ApprovalStatus.WAIT;
}
