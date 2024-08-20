package com.ysmeta.smartfin.domain.auth.role;

import static jakarta.persistence.FetchType.*;

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
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 19.
 */
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "ROLE")
public class RoleEntity extends AbstractBaseEntity {

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
}
