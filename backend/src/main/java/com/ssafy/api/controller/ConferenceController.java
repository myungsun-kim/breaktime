package com.ssafy.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.api.request.ConferenceVO;
import com.ssafy.api.service.ConferenceParticipantService;

import com.ssafy.api.request.ConferenceDTO;
import com.ssafy.api.response.ConferenceMakeResDTO;

import com.ssafy.api.service.ConferenceService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BasicResponse;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.ConferenceCategory;
import com.ssafy.db.repository.ConferenceRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

// 회의방 기능 컨트롤러
@RestController
@RequestMapping("/conference")
@RequiredArgsConstructor
public class ConferenceController {
	
	@Autowired
	ConferenceService conferenceService;
	
	@Autowired
	ConferenceRepository conferenceRepository;
	
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

		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();

		ConferenceCategory category = new ConferenceCategory(); 
		category.setSequence(confer.getCategory_seq()); // 회의방의 카테고리 컬럼을 카테고리 테이블 기본키랑 연결하기 위함

		Conference conference = new Conference(confer.getName(), confer.getOwner(), confer.getOwnerNick(), LocalDateTime.now(), confer.getParticipantLimit(),
						category, confer.getDescription(), confer.getPassword());

		Long seq = conferenceService.create(conference);

		ConferenceMakeResDTO response = new ConferenceMakeResDTO(HttpStatus.OK, SUCCESS_MESSAGE, seq);
		return seq;
	}
	
	@GetMapping("/search/all")
	@ApiOperation(value = "회의방 전체 검색", notes = "현재 생성되어 있는 모든 회의방을 검색한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public List<Conference> searchAll(){
		return conferenceService.findConferences();
	}
	
	@GetMapping("/search/category/{category}")
	@ApiOperation(value = "회의방 카테고리 검색", notes = "<strong>입력 받은 카테고리 번호</strong>를 통해 해당 번호의 카테고리 방을 검색해준다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public List<Conference> searchCategory(@PathVariable("category") String category){
		return conferenceService.findConferences(category);
	}
	
	@GetMapping("/search/name/{name}")
	@ApiOperation(value = "회의방 이름으로 검색", notes = "<strong>입력 받은 검색어</strong>를 통해 검색어를 포함한 모든 회의방을 검색해준다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public List<Conference> searchName(@PathVariable("name") String name) {
		return conferenceService.findOne(name);
	}
	
	@GetMapping("/search/num/{num}") // 회의방 번호 검색 & 회의방 상세보기
	@ApiOperation(value = "회의방 번호 검색 | 회의방 상세보기 ", notes = "<strong>입력 받은 번호</strong>를 통해 번호에 맞는 회의방을 검색해준다. | 해당 번호의 회의방의 상세정보를 볼 수 있다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public Conference searchNum(@PathVariable("num") Long sequence) {
		return conferenceService.findOne(sequence);
	}
	
	@PatchMapping("/{sequence}")
	@ApiOperation(value = "회의방 정보 수정", notes = "<strong>Bearer token와 입력 받은 회의방 수정 정보</strong>를 통해 회의방 정보를 수정한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BasicResponse> updateConference(@ApiIgnore Authentication authentication, @PathVariable("sequence") Long sequence, @RequestBody ConferenceDTO confer){
		
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
        
        BasicResponse response = new BasicResponse(HttpStatus.OK, SUCCESS_MESSAGE);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@DeleteMapping("/{sequence}")
	@ApiOperation(value = "회의방 삭제", notes = "<strong>Bearer token과 입력 받은 회의방 번호</strong>를 통해 회의방을 삭제한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BasicResponse> deleteConference(@ApiIgnore Authentication authentication, @PathVariable("sequence") Long sequence) {

		conferenceRepository.delete(sequence);
		
		BasicResponse response = new BasicResponse(HttpStatus.OK, SUCCESS_MESSAGE);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
