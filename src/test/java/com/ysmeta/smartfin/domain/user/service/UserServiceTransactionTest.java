package com.ysmeta.smartfin.domain.user.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ysmeta.smartfin.domain.user.UserRepository;
import com.ysmeta.smartfin.domain.user.dto.UserDto;
import com.ysmeta.smartfin.domain.user.entity.UserEntity;
import com.ysmeta.smartfin.domain.user.service.cqrs.UserQueryService;

// TODO: WithMockUser 관련 제거 가능 여부 확인
@SpringBootTest
class UserServiceTransactionTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserApplicationService userApplicationService;

	@Autowired
	private UserQueryService userQueryService;

	private UserDto.CreateRequest createRequest;

	@BeforeEach
	void setUp() {
		createRequest = UserDto.CreateRequest.builder()
			.name("Test User")
			.email("test@example.com")
			.companyName("Test Company")
			.password("12341234")
			.build();
	}

	@Test
	@WithMockUser(username = "testUser")
	@DisplayName("회원가입 후 데이터베이스에 저장된 사용자 확인 테스트")
	void testRegisterUserAndCheckDatabase() {
		// Given
		String email = createRequest.getEmail();

		// When
		// userApplicationService.registerUser(createRequest);

		// Then
	}

	@Test
	@WithMockUser(username = "testUser")
	@DisplayName("별도의 트랜잭션에서 사용자 등록 및 확인 테스트")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	void testRegisterUserWithSeparateTransactions() {
		// Given
		String email = "test@example.com";
		UserEntity user = UserEntity.builder()
			.name("Test User")
			.email(email)
			.build();

		// When
		// userApplicationService.registerUser(createRequest);

		// Then
		assertTrue(userQueryService.hasUser(email));

		// 롤백 이후의 확인
		assertFalse(userQueryService.hasUser("nonexistent@example.com"));
	}

	// // TODO: 이 테스트 성공시키기
	// @Test
	// @WithMockUser(username = "testUser")
	// @DisplayName("트랜잭션 격리 수준 테스트")
	// @Transactional(propagation = Propagation.REQUIRES_NEW)
	// void testTransactionalIsolation() throws InterruptedException {
	// 	// Given
	// 	String email = "isolation@example.com";
	// 	UserEntity user = UserEntity.builder()
	// 		.name("Isolation User")
	// 		.email(email)
	// 		.build();
	//
	// 	CountDownLatch latch = new CountDownLatch(1);
	//
	// 	// 첫 번째 트랜잭션 시작
	// 	new Thread(() -> {
	// 		userApplicationService.registerUser(createUserRequestDto);
	// 		latch.countDown(); // 트랜잭션이 끝나면 latch 감소
	// 	}).start();
	//
	// 	// 트랜잭션이 격리되어 있는지 확인하기 위해 latch가 0이 될 때까지 대기
	// 	latch.await();
	//
	// 	// When
	// 	boolean exists = userQueryService.hasUser(email);
	//
	// 	// Then
	// 	assertFalse(exists, "트랜잭션이 격리된 경우 아직 저장되지 않은 사용자가 조회되지 않아야 합니다.");
	//
	// 	// 트랜잭션이 종료된 후 다시 확인
	// 	exists = userQueryService.hasUser(email);
	// 	assertTrue(exists, "트랜잭션이 완료된 후 사용자가 조회되어야 합니다.");
	// }
}
