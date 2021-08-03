package com.ssafy.kurento;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.kurento.client.KurentoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class RoomManager {
	private final Logger log = LoggerFactory.getLogger(RoomManager.class);
	
	@Autowired
	private KurentoClient kurento;
	
	private final ConcurrentMap<Long, Room> rooms = new ConcurrentHashMap<>();
	
	// 해당 번호의 룸 찾기
	public Room getRoom(long sequence) {
		log.debug("Searching for room {}", sequence);
		Room room = rooms.get(sequence);
		
		if(room == null) {
			log.debug("Room {} not existent. Will create now!", sequence);
			room = new Room(sequence, kurento.createMediaPipeline());
			rooms.put(sequence, room); //룸 생성
		}
		log.debug("Room {} found!", sequence);
		return room;
	}
	
	// 해당 룸 제거
	public void removeRoom(Room room) {
		this.rooms.remove(room.getSequence());
		room.close();
		log.info("Room {} removed and closed", room.getSequence());
	}
}
