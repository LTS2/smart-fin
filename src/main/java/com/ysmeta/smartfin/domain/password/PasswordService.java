package com.ysmeta.smartfin.domain.password;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ysmeta.smartfin.domain.user.entity.UserEntity;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 12.
 */
@Service
public class PasswordService {

	private final PasswordRepository passwordRepository;
	private final PasswordEncoder passwordEncoder;

	public PasswordService(PasswordRepository passwordRepository, PasswordEncoder passwordEncoder) {
		this.passwordRepository = passwordRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public void savePassword(UserEntity user, String plainPassword) {
		String hashedPassword = passwordEncoder.encode(plainPassword);

		// PasswordEntity passwordEntity = new PasswordEntity();
		// passwordEntity.setUser(user);
		// passwordEntity.setHashedPassword(hashedPassword);
		// passwordEntity.setSalt(salt);

		// passwordRepository.save(passwordEntity);
	}

	public boolean verifyPassword(UserEntity user, String plainPassword) {
		PasswordEntity passwordEntity = passwordRepository.findByUser(user);
		String hashedPassword = passwordEncoder.encode(plainPassword);
		return hashedPassword.equals(passwordEntity.getHashedPassword());
	}

}
