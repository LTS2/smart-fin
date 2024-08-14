package com.ysmeta.smartfin.domain.user.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import com.ysmeta.smartfin.domain.user.UserEntity;
import com.ysmeta.smartfin.domain.user.UserRepository;

@SpringBootTest
class AdminEntityTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	@Transactional
	@WithMockUser(username = "testUser")
	void testAuditingFields() {
		// Given
		UserEntity user = UserEntity.builder()
			.name("Test User")
			.email("test@example.com")
			.build();

		// When
		UserEntity savedUser = userRepository.save(user);

		// Then
		assertThat(savedUser.getCreatedBy()).isEqualTo("testUser");
		assertThat(savedUser.getUpdatedBy()).isEqualTo("testUser");
	}
}