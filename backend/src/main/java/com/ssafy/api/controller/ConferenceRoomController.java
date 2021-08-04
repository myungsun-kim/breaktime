package com.ssafy.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.api.service.ConferenceParticipantService;
import com.ssafy.api.service.ConferenceService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.ConferenceParticipant;
import com.ssafy.db.repository.ConferenceParticipantRepository;
import com.ssafy.db.repository.ConferenceRepository;

import lombok.RequiredArgsConstructor;

// 회의방 참가,나가기, 수정 등 내부 기능 컨트롤러
@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class ConferenceRoomController {
	@Autowired
	ConferenceService conferenceService;
//	@Autowired
//	ConferenceRepository conferenceRepository;
	@Autowired
	ConferenceParticipantService conferenceParticipantService;
	@Autowired
	ConferenceParticipantRepository conferenceParticipantRepository;
	
	@PostMapping("/enter")
	public ResponseEntity<? extends BaseResponseBody> enterRoom(Authentication authentication, long roomSequence){
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		Conference conference = conferenceService.findOne(roomSequence);//해당 시퀀스로 회의방 찾기
		
		ConferenceParticipant conferenceParticipant = new ConferenceParticipant(); //회의방 참가자 테이블
		
		conferenceParticipant.setConference(conference);
		conferenceParticipant.setUser(userDetails.getUser());
		
		Long seq = conferenceParticipantService.create(conferenceParticipant);
		
		if(seq != null) {
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		}else {
			return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Fail"));
		}
	}
	@DeleteMapping("/leave")
	public void leaveRoom(Long sequence) {//회원 테이블의 시퀀스
		conferenceParticipantRepository.delete(sequence);
	}
}