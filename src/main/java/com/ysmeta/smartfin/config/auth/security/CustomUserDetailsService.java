package com.ysmeta.smartfin.config.auth.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ysmeta.smartfin.domain.user.entity.UserEntity;
import com.ysmeta.smartfin.domain.user.service.cqrs.UserQueryService;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 20.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserQueryService userQueryService;

	public CustomUserDetailsService(UserQueryService userQueryService) {
		this.userQueryService = userQueryService;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity user = userQueryService.findAllByEmailWithRoles(email);

		return new CustomUserDetails(user);
	}
}
