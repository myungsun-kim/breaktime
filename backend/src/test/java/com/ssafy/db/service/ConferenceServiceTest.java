package com.ssafy.db.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.api.service.ConferenceService;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.repository.ConferenceRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ConferenceServiceTest {
	
	@Autowired ConferenceService conferenceService;
	@Autowired ConferenceRepository conferenceRepository;
	
//	@Test
//	@Rollback(false)
//	public void 회의방생성() throws Exception{
//		Conference conference = new Conference();
//		conference.setName("4번째");
//		
//		Long saveSeq = conferenceService.create(conference);
//		
//		assertEquals(conference, conferenceRepository.findOne(saveSeq));
//
//	}
	
//	@Test(expected = IllegalStateException.class)
//	public void 중복회원예외() throws Exception{
//		//given
//		Conference conference = new Conference();
//		conference.setName("A");
//		
//		Conference conference1 = new Conference();
//		conference1.setName("A");
//		// when
//		conferenceService.create(conference);
//		conferenceService.create(conference1);
//		// then
//		fail("예외 발생 실패");
//	}
	
	@Test
	@Rollback(false)
	public void testDelete() {
		// given
		Long sequence = (long) 21;
		// when
//		conferenceService.delete();
		// then
//		assertEquals(conferenceRepository.findOne(sequence), null);
	}

}
