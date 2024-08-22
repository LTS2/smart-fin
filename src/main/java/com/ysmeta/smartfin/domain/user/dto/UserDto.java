package com.ysmeta.smartfin.domain.user.dto;

import java.util.List;

import com.ysmeta.smartfin.common.AbstractBaseDto;
import com.ysmeta.smartfin.domain.user.entity.RoleType;
import com.ysmeta.smartfin.domain.user.entity.UserEntity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 사용자 DTO 클래스입니다.
 *
 * @author ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 8
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class UserDto extends AbstractBaseDto {

	// 공통 필드
	private String name;
	private String email;
	private String companyName;

	/**
	 * 사용자 생성 요청 DTO 클래스입니다.
	 * <p>
	 * 이 클래스는 사용자 생성 요청에 필요한 필드를 포함합니다.
	 *
	 * @author ewjin
	 * @version : 0.0.1
	 * @since : 2024. 8. 8
	 */
	@Getter
	@NoArgsConstructor
	@SuperBuilder
	public static class CreateRequest {

		@NotBlank(message = "이름은 필수 입력값입니다.")
		private String name;

		@NotBlank(message = "이메일은 필수 입력값입니다.")
		@Email(message = "이메일 형식이 올바르지 않습니다.")
		private String email;

		@NotBlank(message = "회사명은 필수 입력값입니다.")
		private String companyName;

		@NotBlank(message = "비밀번호는 필수 입력값입니다.")
		// @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$",
		// message = "비밀번호는 최소 8자 이상, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다.") 더 강력한 조건
		@Pattern(regexp = "^(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,64}$",
			message = "비밀번호는 최소 8자 이상, 최대 64자 이하이어야 하며, 특수문자 1개 이상을 포함해야 합니다.")
		private String password;

		// 엔티티를 DTO로 변환하는 메서드
		public UserEntity toEntity() {
			return UserEntity.builder()
				.name(this.name)
				.email(this.email)
				.companyName(this.companyName)
				.build();
		}
	}

	/**
	 * 로그인 요청 DTO 클래스입니다.
	 * <p>
	 * 이 클래스는 로그인 시 사용자가 입력한 이메일과 비밀번호를 포함합니다.
	 *
	 * @author : ewjin
	 * @version : 0.0.1
	 * @since : 2024. 8. 11.
	 */
	@Getter
	@NoArgsConstructor
	@SuperBuilder
	public static class LoginRequest {

		@NotBlank(message = "이메일은 필수 입력값입니다.")
		@Email(message = "이메일 형식이 올바르지 않습니다.")
		private String email;

		@NotBlank(message = "비밀번호는 필수 입력값입니다.")
		private String password;

		// 엔티티를 DTO로 변환하는 메서드
		public UserEntity toEntity() {
			return UserEntity.builder()
				.email(this.email)
				.build();
		}
	}

	/**
	 * 로그인 응답 DTO 클래스입니다.
	 * <p>
	 * 이 클래스는 로그인 시 사용자가 입력한 이메일과 비밀번호를 포함합니다.
	 *
	 * @author : ewjin
	 * @version : 0.0.1
	 * @since : 2024. 8. 19.
	 */
	@Getter
	@NoArgsConstructor
	@SuperBuilder
	public static class LoginResponse extends UserDto {

		private List<RoleType> roles;

		// 엔티티를 DTO로 변환하는 메서드
		public UserEntity toEntity() {
			return UserEntity.builder()
				.email(super.email)
				.build();
		}
	}

	/**
	 * 사용자 삭제 요청 DTO 클래스입니다.
	 * <p>
	 * 이 클래스는 사용자 삭제 요청에 필요한 필드를 포함합니다.
	 *
	 * @author ewjin
	 * @version : 0.0.1
	 * @since : 2024. 8. 8
	 */
	@Getter
	@SuperBuilder
	public static class DeleteRequest {

		@NotBlank(message = "이메일은 필수 입력값입니다.")
		@Email(message = "이메일 형식이 올바르지 않습니다.")
		private String email;
	}

	/**
	 * 사용자 삭제 응답 DTO 클래스입니다.
	 * <p>
	 * 이 클래스는 사용자 삭제 응답에 필요한 필드를 포함합니다.
	 *
	 * @author ewjin
	 * @version : 0.0.1
	 * @since : 2024. 8. 8
	 */
	@Getter
	@SuperBuilder
	public static class DeleteResponse {

		private String message;
	}

	/**
	 * 사용자 업데이트 요청 DTO 클래스입니다.
	 * <p>
	 * 이 클래스는 사용자 업데이트 요청에 필요한 필드를 포함합니다.
	 *
	 * @author ewjin
	 * @version : 0.0.1
	 * @since : 2024. 8. 8
	 */
	@Getter
	@SuperBuilder
	public static class UpdateRequest {

		@NotBlank(message = "이메일은 필수 입력값입니다.")
		@Email(message = "이메일 형식이 올바르지 않습니다.")
		private String email;

		private String name;
		private String phoneNumber;
		private String address;

		// 엔티티를 DTO로 변환하는 메서드
		public UserEntity toEntity(UserEntity existingUser) {
			// existingUser.setName(this.name != null ? this.name : existingUser.getName());
			// existingUser.setPhoneNumber(this.phoneNumber != null ? this.phoneNumber : existingUser.getPhoneNumber());
			// existingUser.setAddress(this.address != null ? this.address : existingUser.getAddress());
			return existingUser;
		}
	}

	/**
	 * 사용자 업데이트 응답 DTO 클래스입니다.
	 * <p>
	 * 이 클래스는 사용자 업데이트 응답에 필요한 필드를 포함합니다.
	 *
	 * @author ewjin
	 * @version : 0.0.1
	 * @since : 2024. 8. 8
	 */
	@Getter
	@SuperBuilder
	public static class UpdateResponse extends UserDto {

		// 엔티티에서 변환하는 메서드
		public static UpdateResponse fromEntity(UserEntity userEntity) {
			return (UpdateResponse)UpdateResponse.builder()
				.name(userEntity.getName())
				.email(userEntity.getEmail())
				.build();
		}
	}
}
