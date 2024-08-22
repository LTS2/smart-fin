package com.ysmeta.smartfin.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ysmeta.smartfin.domain.user.entity.UserEntity;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 8.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByEmail(String email);

	// @Query("select u from USER u LEFT JOIN FETCH USER_ROLE ur WHERE u.email = :email")
	// List<UserEntity> findAllByEmailWithRoles(@Param("email") String email);

	// @Query("select u from UserEntity u LEFT JOIN FETCH u.userRoles ur ON ur.user = u WHERE u.email = :email")
	// List<UserEntity> findAllByEmailWithRoles(@Param("email") String email);

	@Query("select u, ur.roleTypeCode from USER u LEFT JOIN FETCH u.userRoles ur WHERE u.email = :email")
	UserEntity findAllByEmailWithRoles(@Param("email") String email);
}
