package com.ssafy.db.repository;

import java.util.List;

import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ssafy.db.entity.Conference;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ConferenceRepository{

	private final EntityManager em; // final 빼면 널포인터 익셉션
	
	public void save(Conference conference) { // 트랜잭션이 종료되는 시점에 DB에 반영
		if(conference.getSequence() == null) {
			em.persist(conference);
		} else {
			em.merge(conference);
		}
	}
	
	public List<Conference> findAll(){ // 모든 회의방 리스트
		return em.createQuery("select c from Conference c", Conference.class).getResultList();
	}
	
	public List<Conference> findCategory(String category) { // 카테고리 이름으로 검색
		return em.createQuery("select c from Conference c where c.conferenceCategory.sequence=(select sequence from ConferenceCategory where name like concat('%', :category, '%'))", Conference.class)
				.setParameter("category", category)
				.getResultList();
	}
	
	public List<Conference> findByName(String name){ // 특정 이름을 가진 회의방
		return em.createQuery("select c from Conference c where c.name like concat('%', :name, '%')", Conference.class)
				.setParameter("name", name)
				.getResultList();
	}
	
	public Conference findOne(Long sequence) { // 회의방 번호로 찾기
		System.out.println("회의방찾기");
		return em.find(Conference.class, sequence);
	}
	
	// 회의방 초대하기
	
  
	// 회의방 삭제하기
	@Transactional
	public void delete(Long sequence) {
		em.remove(findOne(sequence));
	}
	
	// 회의방 강퇴하기
	
	// 회의방 입장하기
	
	// 회의방 나가기
}
