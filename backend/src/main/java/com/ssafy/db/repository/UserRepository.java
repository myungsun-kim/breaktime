package com.ssafy.db.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.db.entity.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {
	
	private final EntityManager em;
	// 스프링이 엔티티메니저를 만들어서 주입(injection) 해줌
	
	public void save(User user) {
		if(user.getId() == null) {
			em.persist(user);
		}else {
			em.merge(user);
		}
		// JPA 가 이 User 를 저장하는 로직
	}
	
	public User findOne(String id) {
		return em.find(User.class, id);
		// User 를 반환
	}
	
	// findAll 은 jpql 을 사용해야 함
	public List<User> findAll() {
		return em.createQuery("select m from User m", User.class)
		// jpa sql 과 비슷함. sql 로 번역이 되어야 하기 때문
		// 차이점 : sql 은 테이블을 대상으로 쿼리 , jpa 는 entity 객체를 대상으로 쿼리
		// 번역 : User 에 대한 Entity 객체 엘리어스를 m으로 주고 이 엔티티인 User 를 조회해
				.getResultList();
	}
	// 회원 탈퇴
	@Transactional
	public void delete(String id) {
		em.remove(findOne(id));
	}
}
