package com.ssafy.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.api.request.UserModifyDto;
import com.ssafy.common.exception.handler.PassNotMatchException;
import com.ssafy.common.exception.handler.UserAlreadyExistException;
import com.ssafy.common.exception.handler.UserNotExistException;
import com.ssafy.common.util.JwtTokenUtil;
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

	@Transactional //변경
	public void join(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	@Transactional // 수정
	public void modify(String userId, UserModifyDto userModifyDto) {
		User findUser = userRepository.findOne(userId);
		findUser.setNickname(userModifyDto.getNickname());
		findUser.setEmailE(userModifyDto.getEmailE());
		findUser.setEmailS(userModifyDto.getEmailS());
		
	}
	
    public void validateDuplicateUser(String id) { //중복 회원 검증
 		User findUser = userRepository.findOne(id);
 		if (findUser != null) throw new UserAlreadyExistException();
	}
	 	
	public List<User> findUsers() {
		return userRepository.findAll(); 
	}
	
	public User findOne(String userId) {
		
		User user = userRepository.findOne(userId);
		if(user == null) throw new UserNotExistException();
		
		return user;
		 
	}
	
	public String login(String userId, String userPass) {
		
		User user = findOne(userId);
		// 유효한 패스워드인지 여부 확인
		if(!passwordEncoder.matches(userPass, user.getPassword())) {
			throw new PassNotMatchException();
		}
		
		String token = JwtTokenUtil.getToken(userId);
		
		return token;
	}
}