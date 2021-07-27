package com.ssafy.db.repository;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ConferenceRepositoryTest {
	
	@Autowired ConferenceRepository conferenceRepository;
	
//	@Test
//	public void testSave() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindOne() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindAll() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindByName() {
//		fail("Not yet implemented");
//	}

	@Test
	@Modifying
	@Transactional
	public void testDelete() {
		// given
		Long sequence = (long) 21;
		// when
		
		// then
//		assertEquals(conferenceRepository.findOne(sequence), null);
	}

}
