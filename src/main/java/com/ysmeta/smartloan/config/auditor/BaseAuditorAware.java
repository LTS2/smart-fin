package com.ysmeta.smartloan.config.auditor;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * @author : ewjin
 * @fileName : baseAuditorAware
 * @since : 2024. 8. 2.
 */
@Component
public class BaseAuditorAware implements AuditorAware<Long> {
	/**
	 * @return
	 */
	@Override
	public Optional<Long> getCurrentAuditor() {
		return Optional.empty();
	}
}
