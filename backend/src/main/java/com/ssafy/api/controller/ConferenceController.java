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

<<<<<<< Updated upstream
import com.ssafy.api.request.ConferenceVO;
import com.ssafy.api.service.ConferenceParticipantService;
=======
import com.ssafy.api.request.ConferenceDTO;
import com.ssafy.api.response.ConferenceMakeResDTO;
>>>>>>> Stashed changes
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
	
<<<<<<< Updated upstream
	@PostMapping("/make") // 회의방 생성
	public ResponseEntity<? extends BaseResponseBody> make(Authentication authentication, @RequestBody ConferenceVO confer){
		
=======
	private final String SUCCESS_MESSAGE = "성공";
	
	@PostMapping
	@ApiOperation(value = "회의방 생성", notes = "<strong>Bearer token과 입력 받은 회의방 정보</strong>를 통해 회의방을 생성한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public Long make(@ApiIgnore Authentication authentication, @RequestBody ConferenceDTO confer){
//		return new ResponseEntity<>(response, HttpStatus.OK);
>>>>>>> Stashed changes
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();
		
		ConferenceCategory category = new ConferenceCategory(); 
		category.setSequence(confer.getCategory_seq()); // 회의방의 카테고리 컬럼을 카테고리 테이블 기본키랑 연결하기 위함
		
<<<<<<< Updated upstream
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
=======
		Conference conference = new Conference(confer.getName(), confer.getOwner(), confer.getOwnerNick(), LocalDateTime.now(), confer.getParticipantLimit(),
				category, confer.getDescription(), confer.getPassword());
		
		Long seq = conferenceService.create(conference);
		System.out.println(seq);
		ConferenceMakeResDTO response = new ConferenceMakeResDTO(HttpStatus.OK, SUCCESS_MESSAGE, seq);
		System.out.println(response);
//		return new ResponseEntity<>(response, HttpStatus.OK);
		return seq;
>>>>>>> Stashed changes
	}
	
	@GetMapping("/search/all") // 회의방 전체 검색
	public List<Conference> searchAll(){
		return conferenceService.findConferences();
	}
	
	@GetMapping("/search/category/{category}") // 회의방 카테고리 검색
	public List<Conference> searchCategory(@PathVariable("category") String category){
		return conferenceService.findConferences(category);
	}
	
	@GetMapping("/search/name/{name}") // 이름으로 검색
	public List<Conference> searchName(@PathVariable("name") String name) {
		return conferenceService.findOne(name);
	}
	
	@GetMapping("/search/num/{num}") // 회의방 번호 검색 & 회의방 상세보기
	public Conference searchNum(@PathVariable("num") Long sequence) {
		return conferenceService.findOne(sequence);
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
