package com.ssafy.api.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.db.entity.Conference;
import com.ssafy.db.repository.ConferenceRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConferenceServiceTest {
	
	@Autowired ConferenceService conferenceService;
	@Autowired ConferenceRepository conferenceRepository;
	
	@Test
	public void 회의방생성() throws Exception{
		Conference conference = new Conference();
		conference.setName("들어와");
		
		Long saveSeq = conferenceService.create(conference);
		
		assertEquals(conference, conferenceRepository.findOne(saveSeq));

	}

}
