package com.ssafy.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.db.entity.ConferenceParticipant;
import com.ssafy.db.repository.ConferenceParticipantRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConferenceParticipantService {
	private final ConferenceParticipantRepository conferenceParticipantRepository;
	
//	// 회의방 참가 후 참가자 번호 리턴
//	@Transactional(readOnly = false)
//	public Long create(ConferenceParticipant conferenceParticipant) {
//		conferenceParticipantRepository.save(conferenceParticipant);
//		return conferenceParticipant.getSequence();
//	}
	
	// 회의방 나가기
	public void leaveRoom(String userId) {
		conferenceParticipantRepository.delete(userId);
	}
}
