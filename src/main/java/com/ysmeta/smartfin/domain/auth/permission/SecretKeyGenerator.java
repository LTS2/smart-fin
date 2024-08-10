package com.ysmeta.smartfin.domain.auth.permission;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 9.
 */
public class SecretKeyGenerator {
	public static void main(String[] args) {
		SecureRandom secureRandom = new SecureRandom(); // 안전한 랜덤 생성기
		byte[] key = new byte[48]; // 256비트 키
		secureRandom.nextBytes(key);
		String base64Key = Base64.getEncoder().encodeToString(key);
		System.out.println("Generated Secret Key: " + base64Key);
	}
}