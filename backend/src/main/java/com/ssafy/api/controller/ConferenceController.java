package com.ssafy.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResponseEntity<? extends BaseResponseBody> make(Conference input){
		System.out.println(input.toString());
		Conference conference = new Conference();
		conference.setConferenceCategory(input.getConferenceCategory());
		conference.setDescription(input.getDescription());
		conference.setName(input.getName());
		conference.setOwner(input.getOwner());
		conference.setParticipantLimit(input.getParticipantLimit());
		conference.setPassword(input.getPassword());
		conference.setProduceTime(input.getProduceTime());
		
		
		Long seq = conferenceService.create(conference);

		if(seq != null) {
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		}else {
			return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Fail"));
		}
	}
	
	@GetMapping("/search/name/{name}")
	public List<Conference> search(@PathVariable("name") String name) {
		return conferenceService.findOne(name);
	}
	
	@GetMapping("/search/num/{num}")
	public Conference search(@PathVariable("num") Long sequence) {
		return conferenceService.findOne(sequence);
	}
	
	@GetMapping("/search/all")
	public List<Conference> searchAll(){
		return conferenceService.findConferences();
	}
}
