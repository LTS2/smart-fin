package com.ysmeta.smartfin.domain.user;

import com.ysmeta.smartfin.common.AbstractBaseDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 사용자 DTO 클래스입니다.
 *
 * @author ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 8
 */
@Getter
@Setter
public class UserDto extends AbstractBaseDto {

	// CreateUserRequestDto vs SignUpUserRequestDto vs UserCreateRequestDto vs UserSignUpRequestDto 고민
	@Getter
	public static class CreateUserRequestDto {
		@NotBlank(message = "이름은 필수 입력값입니다.")
		private String name;

		@NotBlank(message = "이메일은 필수 입력값입니다.")
		@Email(message = "이메일 형식이 올바르지 않습니다.")
		private String email;

		private String rrn;
		private String phoneNumber;
		private String address;

		// 엔티티를 DTO로 변환하는 메서드
		public UserEntity toEntity() {
			UserEntity userEntity = new UserEntity();
			userEntity.setName(this.name);
			userEntity.setEmail(this.email);
			userEntity.setRrn(this.rrn);
			userEntity.setPhoneNumber(this.phoneNumber);
			userEntity.setAddress(this.address);
			return userEntity;
		}
	}

	@Getter
	@Setter
	public static class CreateUserResponseDto {
		private String name;
		private String email;
		private String rrn;
		private String phoneNumber;
		private String address;

		// 엔티티를 DTO로 변환하는 메서드
		public static CreateUserResponseDto fromEntity(UserEntity userEntity) {
			CreateUserResponseDto userResponseDto = new CreateUserResponseDto();
			userResponseDto.setName(userEntity.getName());
			userResponseDto.setEmail(userEntity.getEmail());
			userResponseDto.setRrn(userEntity.getRrn());
			userResponseDto.setPhoneNumber(userEntity.getPhoneNumber());
			userResponseDto.setAddress(userEntity.getAddress());
			return userResponseDto;
		}
	}
}