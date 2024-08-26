package com.ysmeta.smartfin.config.jpa.auditor;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * 테스트 환경에서 사용하는 AuditorAware 구현체
 */
@Component
@Profile("test")
public class AuditorAwareTest implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		// 테스트 환경에서 사용할 기본 값을 반환합니다.
		return Optional.of("test_user");
	}
}
