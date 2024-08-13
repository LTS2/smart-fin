package com.ysmeta.smartfin.config.jpa.auditor;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * AuditorAware 구현체로, 현재 사용자 ID를 반환합니다.
 */
@Component
public class BaseAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return Optional.empty();
		}

		Object principal = authentication.getPrincipal();

		if (principal instanceof UserDetails) {
			return Optional.of(((UserDetails)principal).getUsername());
		} else {
			return Optional.of(principal.toString());
		}
	}
}
