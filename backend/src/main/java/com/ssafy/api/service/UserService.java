package com.ssafy.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
	
	@Autowired
	private final UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	 /**
	  * * 회원가입
	  * */
	@Transactional //변경
	public String join(User user) {
		validateDuplicateUser(user); //중복 회원 검증 userRepository.save(user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return user.getId();
	}
	
	@Transactional // 수정
	public void modify(User user) {
		User findUser = userRepository.findOne(user.getId());
		findUser.setNickname(user.getNickname());
		findUser.setEmailS(user.getEmailS());
		findUser.setEmailE(user.getEmailE());
		findUser.setPhone(user.getPhone());
	}
	 
    private void validateDuplicateUser(User user) {
 		User findUser = userRepository.findOne(user.getId());
 		if (findUser != null) throw new IllegalStateException("이미 존재하는 회원입니다.");
	}
	 	
	public List<User> findUsers() {
		return userRepository.findAll();
		 
	}
	
	public User findOne(String userId) {
		return userRepository.findOne(userId);
		 
	}
}