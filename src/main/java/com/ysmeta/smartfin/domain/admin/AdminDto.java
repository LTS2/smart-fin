package com.ysmeta.smartfin.domain.admin;

import com.ysmeta.smartfin.common.AbstractBaseDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 사용자 DTO 클래스입니다.
 *
 * @author ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 8
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class AdminDto extends AbstractBaseDto {

	// 공통 필드
	private String name;
	private String email;
	private String rrn;
	private String phoneNumber;
	private String address;

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
	@Setter
	@NoArgsConstructor
	@SuperBuilder
	public static class CreateAdminRequestDto extends AdminDto {

		@NotBlank(message = "이름은 필수 입력값입니다.")
		private String name;

		@NotBlank(message = "이메일은 필수 입력값입니다.")
		@Email(message = "이메일 형식이 올바르지 않습니다.")
		private String email;

		// 엔티티를 DTO로 변환하는 메서드
		public AdminEntity toEntity() {
			return AdminEntity.builder()
				.name(this.name)
				.email(this.email)
				.rrn(this.getRrn())
				.phoneNumber(this.getPhoneNumber())
				.address(this.getAddress())
				.build();
		}
	}

	/**
	 * 사용자 응답 DTO 클래스입니다.
	 * <p>
	 * 이 클래스는 사용자 응답에 필요한 필드를 포함합니다.
	 *
	 * @author ewjin
	 * @version : 0.0.1
	 * @since : 2024. 8. 8
	 */
	// @Getter
	// @Setter
	// @SuperBuilder
	// public static class CreateUserResponseDto extends UserDto {
	//
	// 	// 엔티티에서 변환하는 메서드
	// 	public static CreateUserResponseDto fromEntity(UserEntity userEntity) {
	// 		return CreateUserResponseDto.SuperBuilder()
	// 			.name(userEntity.getName())
	// 			.email(userEntity.getEmail())
	// 			.rrn(userEntity.getRrn())
	// 			.phoneNumber(userEntity.getPhoneNumber())
	// 			.address(userEntity.getAddress())
	// 			.build();
	// 	}
	// }

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
	@Setter
	@SuperBuilder
	public static class DeleteUserRequestDto {

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
	@Setter
	@SuperBuilder
	public static class DeleteUserResponseDto {

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
	@Setter // Setter 를 없애고 toEntity 메서드를 없애야하나..?
	@SuperBuilder
	public static class UpdateUserRequestDto {

		@NotBlank(message = "이메일은 필수 입력값입니다.")
		@Email(message = "이메일 형식이 올바르지 않습니다.")
		private String email;

		private String name;
		private String phoneNumber;
		private String address;

		// 엔티티를 DTO로 변환하는 메서드
		public AdminEntity toEntity(AdminEntity existingUser) {
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
	@Setter
	@SuperBuilder
	public static class UpdateAdminResponseDto extends AdminDto {

		// 엔티티에서 변환하는 메서드
		public static UpdateAdminResponseDto fromEntity(AdminEntity adminEntity) {
			return (UpdateAdminResponseDto)UpdateAdminResponseDto.builder()
				.name(adminEntity.getName())
				.email(adminEntity.getEmail())
				.rrn(adminEntity.getRrn())
				.phoneNumber(adminEntity.getPhoneNumber())
				.address(adminEntity.getAddress())
				.build();
		}
	}
}
