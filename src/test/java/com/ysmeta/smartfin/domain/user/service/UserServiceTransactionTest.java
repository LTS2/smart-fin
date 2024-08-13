package com.ysmeta.smartfin.domain.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ysmeta.smartfin.domain.user.UserEntity;
import com.ysmeta.smartfin.domain.user.UserRepository;
import com.ysmeta.smartfin.domain.user.service.cqrs.UserCommandService;
import com.ysmeta.smartfin.domain.user.service.cqrs.UserQueryService;

@SpringBootTest
class UserServiceTransactionTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserCommandService userCommandService;

	@Autowired
	private UserQueryService userQueryService;

	@Test
	@DisplayName("회원가입 후 데이터베이스에 저장된 사용자 확인 테스트")
	@Commit
	@Transactional
	void testRegisterUserAndCheckDatabase() {
		// Given
		String email = "test@example.com";
		UserEntity user = UserEntity.builder()
			.name("Test User")
			.email(email)
			.phoneNumber("010-1234-5678")
			.build();

		// When
		userCommandService.registerUser(user);

		// Then
		Optional<UserEntity> foundUser = userQueryService.findByEmail(email);
		assertThat(foundUser).isPresent();
		assertThat(foundUser.get().getEmail()).isEqualTo(email);
	}

	@Test
	@DisplayName("별도의 트랜잭션에서 사용자 등록 및 확인 테스트")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	void testRegisterUserWithSeparateTransactions() {
		// Given
		String email = "test@example.com";
		UserEntity user = UserEntity.builder()
			.name("Test User")
			.email(email)
			.phoneNumber("010-1234-5678")
			.build();

		// When
		userCommandService.registerUser(user);

		// Then
		assertTrue(userQueryService.hasUser(email));

		// 롤백 이후의 확인
		assertFalse(userQueryService.hasUser("nonexistent@example.com"));
	}

	@Test
	@DisplayName("트랜잭션 격리 수준 테스트")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	void testTransactionalIsolation() throws InterruptedException {
		// Given
		String email = "isolation@example.com";
		UserEntity user = UserEntity.builder()
			.name("Isolation User")
			.email(email)
			.build();

		CountDownLatch latch = new CountDownLatch(1);

		// 첫 번째 트랜잭션 시작
		new Thread(() -> {
			userCommandService.registerUser(user);
			latch.countDown(); // 트랜잭션이 끝나면 latch 감소
		}).start();

		// 트랜잭션이 격리되어 있는지 확인하기 위해 latch가 0이 될 때까지 대기
		latch.await();

		// When
		boolean exists = userQueryService.hasUser(email);

		// Then
		assertFalse(exists, "트랜잭션이 격리된 경우 아직 저장되지 않은 사용자가 조회되지 않아야 합니다.");

		// 트랜잭션이 종료된 후 다시 확인
		exists = userQueryService.hasUser(email);
		assertTrue(exists, "트랜잭션이 완료된 후 사용자가 조회되어야 합니다.");
	}
}
