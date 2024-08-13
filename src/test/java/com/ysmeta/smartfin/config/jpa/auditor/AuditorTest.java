package com.ysmeta.smartfin.config.jpa.auditor;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import com.ysmeta.smartfin.domain.user.UserEntity;
import com.ysmeta.smartfin.domain.user.UserRepository;

@SpringBootTest
class AuditorTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	@DisplayName("Auditor - 생성한 사람과 수정한 사람 필드 자동 채움 확인")
	@Transactional
	@WithMockUser(username = "testUser")
	void shouldFillCreatedByAndUpdatedByFieldsAutomatically() {
		// Given
		UserEntity user = UserEntity.builder()
			.name("Test User")
			.email("test@example.com")
			.phoneNumber("010-1234-5678")
			.build();

		// When
		UserEntity savedUser = userRepository.save(user);

		// Then
		assertThat(savedUser.getCreatedBy()).isEqualTo("testUser");
		assertThat(savedUser.getUpdatedBy()).isEqualTo("testUser");
	}
}