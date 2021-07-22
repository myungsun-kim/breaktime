package com.ssafy.db.repository;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import com.ssafy.db.entity.Conference;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ConferenceRepository {

	private final EntityManager em; // final 빼면 널포인터 익셉션
	
	public void save(Conference conference) { // 트랜잭션이 종료되는 시점에 DB에 반영
		em.persist(conference);
	}
	
	public Conference findOne(Long sequence) { // 회의방 번호로 찾기
		return em.find(Conference.class, sequence);
	}
	
	public List<Conference> findAll(){ // 모든 회의방 리스트
		return em.createQuery("select c from Conference c", Conference.class).getResultList();
	}
	
	public List<Conference> findByName(String name){ // 특정 이름을 가진 회의방
		return em.createQuery("select c from Conference c where c.name = :name", Conference.class)
				.setParameter("name", name)
				.getResultList();
	}
}
