package com.ysmeta.smartfin.common;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PreRemove;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * 공통 사용 컬럼 엔티티 관리 클래스입니다.
 *
 * @author : ewjin
 * @fileName : BaseEntity
 * @since : 2024. 8. 1.
 */
@Slf4j
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
public abstract class AbstractBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 생성 날짜
	 */
	@CreatedDate
	@Column(name = "CREATED_AT", nullable = false, updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	/**
	 * 수정 날짜
	 */
	@LastModifiedDate
	@Column(name = "UPDATED_AT")
	private LocalDateTime updatedAt;

	/**
	 * 삭제 날짜
	 */
	@Column(name = "DELETED_AT")
	private LocalDateTime deletedAt;

	/**
	 * 생성한 사람
	 */
	@CreatedBy
	@Column(name = "CREATED_BY", nullable = false, updatable = false)
	private String createdBy;

	/**
	 * 수정한 사람
	 */
	@LastModifiedBy
	@Column(name = "UPDATED_BY")
	private String updatedBy;

	// /**
	//  * 생성 날짜 업데이트
	//  */
	// @PrePersist
	// protected void onCreate() {
	// 	LocalDateTime now = LocalDateTime.now();
	// 	this.createdAt = now;
	// 	this.updatedAt = now;
	// }
	//
	// /**
	//  * 수정 날짜 업데이트
	//  */
	// @PreUpdate
	// protected void onUpdate() {
	// 	this.updatedAt = LocalDateTime.now();
	// }

	/**
	 * 삭제 날짜 업데이트
	 */
	@PreRemove
	protected void onDelete() {
		this.deletedAt = LocalDateTime.now();
	}
}
