package com.ssafy.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
//public interface UserService {
//	User createUser(UserRegisterPostReq userRegisterInfo);
//	User getUserByUserId(String userId);
//}

@Service
@Transactional(readOnly = true)
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	 /**
	  * * 회원가입
	  * */
	@Transactional //변경
	public String join(User user) {
		validateDuplicateUser(user); //중복 회원 검증 userRepository.save(user);
		return user.getId();
	}
	 
    private void validateDuplicateUser(User user) {
 		List<User> findUsers = userRepository.findByName(user.getName());
 		
 		if (!findUsers.isEmpty()) {
 			throw new IllegalStateException("이미 존재하는 회원입니다.");
 		}
	}
	 	
	 /**
	 * 전체 회원 조회
	 */
	 	
	 public List<User> findUsers() {
		 return userRepository.findAll();
		 
	 }
	 public User findOne(String userId) {
		 return userRepository.findOne(userId);
		 
	 }
}