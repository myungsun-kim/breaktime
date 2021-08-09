package com.ssafy.db.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.db.entity.ConferenceParticipant;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ConferenceParticipantRepository {
	private final EntityManager em; //엔티티 관리
	
	public void save(ConferenceParticipant conferenceParticipant) { //트랜잭션 종료되는 시점에 DB 반영
		if(findOne(conferenceParticipant.getUser().getId())==null) {//해당 아이디로 저장된 값이 없다면
			em.persist(conferenceParticipant);
		}else {			
			em.merge(conferenceParticipant);
		}
	}
	
	@Transactional
	public void delete(String userId) {
		em.remove(findOne(userId));
	}
	
	public ConferenceParticipant findOne(String userId) {//해당 시퀀스에 해당하는 참가자 찾기
		return em.find(ConferenceParticipant.class, userId);
	}
}
