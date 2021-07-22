package com.ssafy.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.api.service.ConferenceService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Conference;

// 회의방 기능 컨트롤러
@RestController
@RequestMapping("/conference")
public class ConferenceController {
	
	@Autowired
	ConferenceService conferenceService;
	
	@PostMapping("/make")
	public ResponseEntity<? extends BaseResponseBody> make(Conference conference){
		Long seq = null;
		seq = conferenceService.create(conference);
		System.out.println(seq);
		if(seq != null) {
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		}else {
			return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Fail"));
		}
	}
	
}
