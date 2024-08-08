package com.ysmeta.smartfin.config.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.ysmeta.smartfin.config.jpa.auditor.BaseAuditorAware;

/**
 * Created_by, Updated_by 를 자동으로 매핑해주는 클래스입니다.
 *
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
