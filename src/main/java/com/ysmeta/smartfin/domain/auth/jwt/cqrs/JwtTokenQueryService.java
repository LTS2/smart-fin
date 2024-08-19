package com.ysmeta.smartfin.domain.auth.jwt.cqrs;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ysmeta.smartfin.domain.auth.jwt.JwtTokenEntity;
import com.ysmeta.smartfin.domain.auth.jwt.JwtTokenRepository;
import com.ysmeta.smartfin.domain.user.UserEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtTokenQueryService {
	private final JwtTokenRepository jwtTokenRepository;

	public JwtTokenQueryService(JwtTokenRepository jwtTokenRepository) {
		this.jwtTokenRepository = jwtTokenRepository;
	}

	public Optional<JwtTokenEntity> findValidRefreshToken(UserEntity userEntity) {
		return jwtTokenRepository.findByUserAndRevokedFalse(userEntity);
	}
}
