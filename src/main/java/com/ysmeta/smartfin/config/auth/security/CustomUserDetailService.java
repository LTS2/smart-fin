package com.ysmeta.smartfin.config.auth.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ysmeta.smartfin.domain.auth.password.PasswordEntity;
import com.ysmeta.smartfin.domain.auth.password.PasswordRepository;
import com.ysmeta.smartfin.domain.user.UserEntity;
import com.ysmeta.smartfin.domain.user.service.cqrs.UserQueryService;

/**
 * 스프링 시큐리티 UserDetailService 구현 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 8.
 */
@Service
public class CustomUserDetailService implements UserDetailsService {

	private final UserQueryService userQueryService;
	private final PasswordRepository passwordRepository;

	@Autowired
	public CustomUserDetailService(UserQueryService userQueryService,
		PasswordRepository passwordRepository) {
		this.userQueryService = userQueryService;
		this.passwordRepository = passwordRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userQueryService.findByEmail(username);
		PasswordEntity passwordEntity = passwordRepository.findByUser(user);

		// 사용자 권한 설정
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		// return new CustomUserDetails(user.getEmail(), passwordEntity.getHashedPassword(), authorities);
		return new User(user.getEmail(), passwordEntity.getHashedPassword(), authorities);
	}

	// @Override
	// public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	// 	UserEntity user = userQueryService.findByEmail(username);
	// 	PasswordEntity passwordEntity = passwordRepository.findByUser(user);
	//
	// 	// 사용자 권한 설정
	// 	List<GrantedAuthority> authorities = new ArrayList<>();
	// 	authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
	//
	// 	return new User(user.getEmail(), passwordEntity.getHashedPassword(), authorities);
	// }
}
