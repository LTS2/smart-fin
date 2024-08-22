package com.ysmeta.smartfin.domain.auth.service.cqrs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ysmeta.smartfin.domain.password.PasswordEntity;
import com.ysmeta.smartfin.domain.password.PasswordRepository;
import com.ysmeta.smartfin.domain.user.UserRepository;
import com.ysmeta.smartfin.domain.user.dto.UserDto;
import com.ysmeta.smartfin.domain.user.entity.RoleType;
import com.ysmeta.smartfin.domain.user.entity.RoleTypeEntity;
import com.ysmeta.smartfin.domain.user.entity.UserEntity;
import com.ysmeta.smartfin.domain.user.entity.UserRoleEntity;
import com.ysmeta.smartfin.domain.user.repository.RoleTypeRepository;
import com.ysmeta.smartfin.domain.user.repository.UserRoleRepository;
import com.ysmeta.smartfin.domain.user.service.cqrs.UserQueryService;

import lombok.extern.slf4j.Slf4j;

/**
 * AuthCommandService 클래스는 사용자 인증과 관련된 명령(Command) 작업을 처리하는 서비스입니다.
 */
@Slf4j
@Service
public class AuthCommandService {

	private final PasswordRepository passwordRepository;
	private final UserRepository userRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private RoleTypeRepository roleTypeRepository;

	@Autowired
	private UserQueryService userQueryService;

	@Autowired
	public AuthCommandService(PasswordRepository passwordRepository, UserRepository userRepository) {

		this.passwordRepository = passwordRepository;
		this.userRepository = userRepository;
	}

	public Authentication authenticateUser(UserDto.LoginRequest loginRequestDto,
		AuthenticationManager authenticationManager) {
		return authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
	}

	// 회원가입 로직
	public UserEntity signUp(UserEntity user, String encryptedPassword) {
		// 프론트에서 중복 메일 체크해주고 백에서는 안 쓰는 걸로.
		// if (userQueryService.hasUser(user.getEmail())) {
		// 	throw new RuntimeException("이미 존재하는 사용자입니다.");
		// }

		// UserEntity 생성 (UserEntity를 영속화하지 않고)
		UserEntity userEntity = UserEntity.builder()
			.name(user.getName())
			.email(user.getEmail())
			.companyName(user.getCompanyName())
			.build();
		userRepository.save(userEntity);

		// RoleTypeEntity 조회
		RoleTypeEntity roleType = roleTypeRepository.findByCode(RoleType.USER)
			.orElseThrow(() -> new RuntimeException("USER RoleType이 존재하지 않습니다."));

		// UserRoleEntity 생성 및 저장
		UserRoleEntity userRoleEntity = UserRoleEntity.builder()
			.user(userEntity)
			.roleTypeCode(roleType)
			// .roleTypeCode(roleType)
			.build();
		userRoleRepository.save(userRoleEntity);

		// 비밀번호 엔티티 생성 및 저장
		PasswordEntity passwordEntity = PasswordEntity.builder()
			.user(userEntity)  // 영속화된 userEntity를 참조
			.hashedPassword(encryptedPassword)
			.build();
		passwordRepository.save(passwordEntity);

		return userEntity;
	}

}