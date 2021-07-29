package com.ssafy.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.api.service.ConferenceService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.ChatMessage;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ConferenceRepository;
import com.ssafy.db.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Profile("stomp")
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatMessageController {
	
	private final SimpMessagingTemplate template;
	
	@Autowired
	ConferenceService conferenceService;
	@Autowired
	ConferenceRepository conferenceRepository;
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
	
	@MessageMapping("/enter/{sequence}") // 회의방 입장
	public ResponseEntity<? extends BaseResponseBody> enter(Authentication authentication, @PathVariable("sequence") Long sequence, ChatMessage message){
		// JWT 검증
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();
		// 유저 정보
		User user = userService.findOne(userId);
		// 회의방 정보
		Conference conference = conferenceService.findOne(sequence);
		// 회의방 정보 유저에 저장
		user.setConference(conference);
		// 회의방에 유저 정보 추가 (미확인)
		conference.addUser(user);
		
		message.setMessage(message.getWriter() + "님이 입장하셨습니다.");
        template.convertAndSend("/subscribe/chat/room/" + message.getConferenceSeq(), message);
		
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "입장하셨습니다."));
	}

    @MessageMapping("/message")
    public String message(ChatMessage message) {
        template.convertAndSend("/sub" + message.getConferenceSeq(), message);
        return message.getMessage();
    }
}
