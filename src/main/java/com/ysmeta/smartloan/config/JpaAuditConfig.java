package com.ysmeta.smartloan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.ysmeta.smartloan.config.auditor.BaseAuditorAware;

/**
 * @author : ewjin
 * @fileName : JpaAuditConfig
 * @since : 2024. 8. 2.
 */
@EnableJpaAuditing(
	auditorAwareRef = "baseAuditorAware"
	// dateTimeProviderRef = "baseAuditorAware"
)
@Configuration
public class JpaAuditConfig {
	@Bean
	public AuditorAware<Long> auditorProvider() {
		return new BaseAuditorAware();
	}

}
