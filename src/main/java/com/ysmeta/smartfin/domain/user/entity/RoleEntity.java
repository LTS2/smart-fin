// package com.ysmeta.smartfin.domain.role.entity;
//
// import static jakarta.persistence.FetchType.*;
//
// import java.util.ArrayList;
// import java.util.List;
//
// import com.ysmeta.smartfin.common.AbstractBaseEntity;
// import com.ysmeta.smartfin.domain.user.entity.UserRoleEntity;
//
// import jakarta.persistence.ConstraintMode;
// import jakarta.persistence.Entity;
// import jakarta.persistence.ForeignKey;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.OneToMany;
// import lombok.AccessLevel;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.experimental.SuperBuilder;
//
// /**
//  * 사용 안 하고 우선 보류
//  *
//  * @author : ewjin
//  * @version : 0.0.1
//  * @since : 2024. 8. 19.
//  */
// @Getter
// @SuperBuilder
// @NoArgsConstructor(access = AccessLevel.PROTECTED)
// @Entity(name = "ROLE")
// public class RoleEntity extends AbstractBaseEntity {
//
// 	@OneToMany(mappedBy = "role", fetch = LAZY)
// 	private List<UserRoleEntity> userRoles = new ArrayList<>();
//
// 	@ManyToOne(fetch = LAZY)
// 	@JoinColumn(name = "CODE", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
// 	private RoleTypeEntity roleType;
//
// }
