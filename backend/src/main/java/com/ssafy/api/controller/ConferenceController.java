package com.ssafy.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.api.request.ConferenceVO;
import com.ssafy.api.service.ConferenceParticipantService;
import com.ssafy.api.service.ConferenceService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.ConferenceCategory;
import com.ssafy.db.entity.ConferenceParticipant;
import com.ssafy.db.repository.ConferenceParticipantRepository;
import com.ssafy.db.repository.ConferenceRepository;

import lombok.RequiredArgsConstructor;

// 회의방 기능 컨트롤러
@RestController
@RequestMapping("/conference")
@RequiredArgsConstructor
public class ConferenceController {
	
	@Autowired
	ConferenceService conferenceService;
	@Autowired
	ConferenceRepository conferenceRepository;
	
	@PostMapping("/make") // 회의방 생성
	public ResponseEntity<? extends BaseResponseBody> make(Authentication authentication, @RequestBody ConferenceVO confer){
		
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();
		
		ConferenceCategory category = new ConferenceCategory(); 
		category.setSequence(confer.getCategory_seq()); // 회의방의 카테고리 컬럼을 카테고리 테이블 기본키랑 연결하기 위함
		
		Conference conference = new Conference();
		
		conference.setConferenceCategory(category);
		conference.setDescription(confer.getDescription());
		conference.setName(confer.getName());
		conference.setOwner(confer.getOwner());
		conference.setOwnerNick(confer.getOwnerNick());
		conference.setParticipantLimit(confer.getParticipantLimit());
		conference.setPassword(confer.getPassword());
		
		conference.setProduceTime(LocalDateTime.now()); // 현재 시간
		
		Long seq = conferenceService.create(conference);
		
		if(seq != null) {
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success", seq));
		}else {
			return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Fail", null));
		}
	}
	
	@GetMapping("/search/name/{name}") // 이름으로 검색
	public List<Conference> searchName(@PathVariable("name") String name) {
		return conferenceService.findOne(name);
	}
	
	@GetMapping("/search/num/{num}") // 회의방 번호 검색 & 회의방 상세보기
	public Conference searchNum(@PathVariable("num") Long sequence) {
		return conferenceService.findOne(sequence);
	}
	
	@GetMapping("/search/all") // 회의방 전체 검색
	public List<Conference> searchAll(){
		return conferenceService.findConferences();
	}
	
	@PostMapping("/update/{sequence}") // 회의방 수정
    public void updateConference(Authentication authentication, @PathVariable("sequence") Long sequence, @RequestBody ConferenceVO confer){
		
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();
		
		Conference conference = new Conference();
		
		ConferenceCategory category = new ConferenceCategory(); 
		category.setSequence(confer.getCategory_seq());
		
		conference.setSequence(sequence);
		conference.setConferenceCategory(category);
		conference.setDescription(confer.getDescription());
		conference.setName(confer.getName());
		conference.setOwner(confer.getOwner());
		conference.setParticipantLimit(confer.getParticipantLimit());
		conference.setPassword(confer.getPassword());
		
        conferenceService.save(conference);

    }
	
	@GetMapping("/delete/{sequence}") // 회의방 삭제
	public void deleteConference(Authentication authentication, @PathVariable("sequence") Long sequence) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();

		conferenceRepository.delete(sequence);
	}
	
//	@PostMapping("/{sequence}") //회의방 입장
//	public void enterRoom(Authentication authentication, @PathVariable("sequence") Long sequence){
//		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
//		Conference conference = conferenceService.findOne(sequence);//해당 시퀀스로 회의방 찾기
//		
//		ConferenceParticipant conferenceParticipant = new ConferenceParticipant(); //회의방 참가자 테이블
//		
//		conferenceParticipant.setConference(conference);
//		conferenceParticipant.setUser(userDetails.getUser());
//
//		conferenceParticipantService.save(conferenceParticipant);
//	}
//	
//	@DeleteMapping("/leave") //회의방 나가기
//	public void leaveRoom(String userId) {//해당 아이디 DB에서 제거
//		conferenceParticipantRepository.delete(userId);
//	}
}
