package com.ssafy.db.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.ssafy.db.entity.ConferenceParticipant;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ConferenceParticipantRepository {
	private final EntityManager em; //엔티티 관리
	
	public void save(ConferenceParticipant conferenceParticipant) { //트랜잭션 종료되는 시점에 DB 반영
		if(conferenceParticipant.getSequence()==null) {
			em.persist(conferenceParticipant);
		}else {
			em.merge(conferenceParticipant);			
		}
	}
}
