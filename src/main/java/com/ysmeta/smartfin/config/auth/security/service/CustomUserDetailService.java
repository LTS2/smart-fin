// package com.ysmeta.smartfin.config.security.service;
//
// import java.util.ArrayList;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;
//
// import com.ysmeta.smartfin.domain.user.UserEntity;
// import com.ysmeta.smartfin.domain.user.UserRepository;
//
// /**
//  * 스프링 시큐리티 UserDetailService 구현 클래스입니다.
//  *
//  * @author : ewjin
//  * @version : 0.0.1
//  * @since : 2024. 8. 8.
//  */
// @Service
// public class CustomUserDetailService implements UserDetailsService {
//
// 	private final UserRepository userRepository;
//
// 	@Autowired
// 	public CustomUserDetailService(UserRepository userRepository) {
// 		this.userRepository = userRepository;
// 	}
//
// 	@Override
// 	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
// 		UserEntity user = userRepository.findByEmail(username)
// 			.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
// 		return new org.springframework.security.core.userdetails.User(user.getEmail(),
// 			user.getPasswordEntity().getHashedPassword(), new ArrayList<>());
// 	}
// }
