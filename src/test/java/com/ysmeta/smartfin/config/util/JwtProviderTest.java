package com.ysmeta.smartfin.config.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.ysmeta.smartfin.config.auth.jwt.JwtProvider;

import io.jsonwebtoken.Jwts;

/**
 * JWT 토큰 관리 테스트 클래스입니다.
 * <p>
 * 이 클래스는 JWT 토큰의 생성과 복호화 기능을 테스트합니다.
 * 각 테스트 메서드는 JWT 토큰을 생성하고, 생성된 토큰에서 클레임들을 추출하여
 * 예상된 값들과 일치하는지 검증합니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 9.
 */
@SpringBootTest
class JwtProviderTest {

	private JwtProvider jwtProvider;

	/**
	 * 각 테스트 메서드가 실행되기 전에 호출됩니다.<br>
	 * 이 메서드는 테스트에서 사용할 JWT 유틸리티 인스턴스를 설정합니다.
	 */
	@BeforeEach
	void setUp() {
		String secretKey = "testSecretKey";
		Long expiration = 3600000L; // 1시간

		jwtProvider = new JwtProvider(secretKey, expiration);
	}

	/**
	 * JWT 토큰 생성 기능을 테스트합니다.
	 *
	 * <p>
	 * 이 메서드는 다음 단계를 검증합니다:
	 * <ul>
	 * <li>JWT 토큰이 정상적으로 생성되는지</li>
	 * <li>생성된 토큰에서 subject(이메일)를 올바르게 추출할 수 있는지</li>
	 * </ul>
	 * </p>
	 */
	@Test
	@DisplayName("JWT 토큰 생성")
	void generateToken() {
		// given: 토큰 생성에 사용할 이메일 주소가 주어졌을 때
		String email = "test@example.com";

		// when: 이메일을 기반으로 JWT 토큰을 생성할 때
		String token = jwtProvider.generateToken(email);

		// then: 토큰이 null 또는 비어 있지 않고, subject로부터 올바른 이메일을 추출할 수 있어야 함
		assertNotNull(token, "JWT 토큰은 null이 아니어야 합니다.");
		assertFalse(token.isEmpty(), "JWT 토큰은 비어 있지 않아야 합니다.");

		// 토큰에서 subject(이메일)를 추출
		String extractedEmail = Jwts.parser()
			.setSigningKey("testSecretKey")
			.parseClaimsJws(token)
			.getBody()
			.getSubject();

		// 추출된 이메일이 원래의 이메일과 동일한지 확인
		assertEquals(email, extractedEmail, "추출된 이메일이 원래의 이메일과 일치해야 합니다.");
	}

	/**
	 * JWT 토큰에서 모든 클레임을 추출하는 기능을 테스트합니다.
	 * private 메서드를 테스트 하는 것을 지양해야하기 때문에 해당 테스트 주석처리
	 * private 메서드를 테스트 하게 되면 외부에 노출되어 결합도가 증가하기 때문.
	 * <p>
	 * 이 메서드는 다음 단계를 검증합니다:
	 * <ul>
	 * <li>JWT 토큰에서 모든 클레임이 정상적으로 추출되는지</li>
	 * <li>추출된 클레임들이 예상된 값들과 일치하는지</li>
	 * </ul>
	 * </p>
	 */
	// @Test
	// @DisplayName("JWT 토큰에서 모든 클레임 추출")
	// void extractAllClaimsFromToken() {
	// 	// given: 토큰 생성에 사용할 이메일 주소가 주어졌을 때
	// 	String email = "test@example.com";
	// 	String token = jwtUtil.generateToken(email);
	//
	// 	// when: 토큰에서 모든 클레임을 추출할 때
	// 	Claims claims = jwtUtil.extractAllClaims(token);
	//
	// 	// then: 추출된 클레임들이 null이 아니고 예상된 값과 일치해야 함
	// 	assertNotNull(claims, "클레임은 null이 아니어야 합니다.");
	//
	// 	// 추출된 클레임들에서 각 값들을 확인
	// 	String extractedEmail = claims.getSubject();
	// 	Date issuedAt = claims.getIssuedAt();
	// 	Date expiration = claims.getExpiration();
	//
	// 	log.info("extracted email: {}", extractedEmail);
	// 	log.info("issued at: {}", issuedAt);
	// 	log.info("expiration: {}", expiration);
	//
	// 	// 클레임들이 예상된 값과 일치하는지 확인
	// 	assertEquals(email, extractedEmail, "추출된 이메일이 원래의 이메일과 일치해야 합니다.");
	// 	assertNotNull(issuedAt, "발급 시간은 null이 아니어야 합니다.");
	// 	assertNotNull(expiration, "만료 시간은 null이 아니어야 합니다.");
	//
	// 	// 만료 시간이 현재 시간보다 나중인지 확인
	// 	assertTrue(expiration.after(new Date()), "만료 시간은 현재 시간보다 나중이어야 합니다.");
	//
	// 	// 발급 시간과 만료 시간이 논리적으로 일치하는지 확인
	// 	assertTrue(issuedAt.before(new Date()), "발급 시간은 현재 시간보다 이전이어야 합니다.");
	// 	assertTrue(expiration.after(issuedAt), "만료 시간은 발급 시간보다 이후여야 합니다.");
	// }
}