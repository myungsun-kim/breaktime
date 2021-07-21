package com.ssafy.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.api.service.ConferenceService;

// 회의방 기능 컨트롤러
@RestController
@RequestMapping("/conference/")
public class ConferenceController {
	
	@Autowired
	ConferenceService conferenceService;
	

	
}
