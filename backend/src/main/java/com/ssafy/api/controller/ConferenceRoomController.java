package com.ssafy.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.api.request.ConferenceVO;
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
//	@Autowired
//	ConferenceParticipantRepository conferenceParticipantRepository;
	
	public ResponseEntity<? extends BaseResponseBody> enterRoom(Authentication authentication, long roomSequenece){
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		Conference conference = conferenceService.findOne(roomSequenece);//해당 시퀀스로 회의방 찾기
		
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
}
