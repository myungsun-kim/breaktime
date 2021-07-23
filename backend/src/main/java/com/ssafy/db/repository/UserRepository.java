package com.ssafy.db.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ssafy.db.entity.User;

@Repository
public class UserRepository {
	
	@PersistenceContext
	private EntityManager em;
	// 스프링이 엔티티메니저를 만들어서 주입(injection) 해줌
	
	public void save(User User) {
		em.persist(User);
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
	
	public List<User> findByName(String name) {
		return em.createQuery("select m from User m where m.name = :name", User.class)
				.setParameter("name", name)
				.getResultList();
	}
}
