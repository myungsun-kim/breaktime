package com.ssafy.api.service;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.db.entity.Conference;
import com.ssafy.db.repository.ConferenceRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConferenceService {
	
	private final ConferenceRepository conferenceRepository;
	// 회의방 생성 후 회의방 번호 리턴
	@Transactional(readOnly = false)
	public Long create(Conference conference) { 
		validDuplicateConference(conference);
		conferenceRepository.save(conference);
		return conference.getSequence();
	}
	
	@Transactional(readOnly = false)
	public Long save(Conference conference) { 
		conferenceRepository.save(conference);
		return conference.getSequence();
	}
	
	// 회의방 이름을 이용한 중복 체크
	private void validDuplicateConference(Conference conference) { 
		List<Conference> findConferences = conferenceRepository.findByName(conference.getName());
		if(!findConferences.isEmpty()) throw new IllegalStateException("이미 존재하는 방입니다");
	}
	// 회의방 전체 조회
	public List<Conference> findConferences(){ 
		return conferenceRepository.findAll();
	}
	// 번호로 회의방 찾기
	public Conference findOne(Long sequence) { 
		return conferenceRepository.findOne(sequence);
	}
	// 이름으로 회의방 찾기
	public List<Conference> findOne(String name) {
		return conferenceRepository.findByName(name);
	}
	// 회의방 초대하기
	
	// 회의방 수정하기
	
	// 회의방 카테고리 종류 조회하기
	
	// 회의방 강퇴하기
	
	// 회의방 입장하기
	
	// 회의방 나가기
}
