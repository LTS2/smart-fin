package com.ysmeta.smartfin.config.auth.security.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ysmeta.smartfin.domain.role.entity.UserRoleEntity;
import com.ysmeta.smartfin.domain.user.UserEntity;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 20.
 */
public class CustomUserDetails implements UserDetails {
	private final UserEntity userEntity;

	public CustomUserDetails(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		List<UserRoleEntity> userRoles = userEntity.getUserRoles();
		userEntity.getUserRoles().forEach(userRole -> {
			authorities.add(new GrantedAuthority() {
				@Override
				public String getAuthority() {
					return userRole.getRole().getRoleType().getCode();
				}
			});
		});

		return authorities;
	}

	@Override
	public String getPassword() {
		return userEntity.getPasswordEntity().getHashedPassword();
	}

	@Override
	public String getUsername() {
		return userEntity.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
