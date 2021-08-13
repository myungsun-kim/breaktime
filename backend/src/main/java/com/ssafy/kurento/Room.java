package com.ssafy.kurento;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PreDestroy;

import org.kurento.client.Continuation;
import org.kurento.client.MediaPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class Room implements Closeable{
	private final Logger log = LoggerFactory.getLogger(Room.class);
	// ConcurrentMap > 검색과 갱신 전체에 걸쳐 Thread-Safe를 보장하는 Map
	
	private final ConcurrentMap<String, UserSession> participants = new ConcurrentHashMap<>();
	private final MediaPipeline pipeline;
	private final String name;
	
	public String getName() {
		return name;
	}
	
	public Room(String roomName, MediaPipeline pipeline) {
		this.name = roomName;
		this.pipeline = pipeline;
		log.info("ROOM created"+roomName);
	}
	
	@PreDestroy
	private void shutdown() {
		this.close();
	}
	
	// 참가자 등록
	public UserSession join(String userName, boolean videoState, boolean micState, WebSocketSession session) throws IOException{
		log.info("ROOM {}: adding participant {}", this.name, userName);
	    final UserSession participant = new UserSession(userName, this.name, session, this.pipeline, videoState, micState);
	    joinRoom(participant);
	    participants.put(participant.getName(), participant);
	    sendParticipantNames(participant);
	    return participant;
	}

	// 참가자 나감
	public void leave(UserSession user) throws IOException{
		log.debug("PARTICIPANT {}: Leaving room {}", user.getName(), this.name);
		this.removeParticipant(user.getName());
		user.close();
	}

	// 참가자 video on/off
	public void videoState(UserSession user, Boolean state) throws IOException{
		final JsonObject videoState = new JsonObject();
		videoState.addProperty("id", "videoState");
		videoState.addProperty("name", user.getName());
		videoState.addProperty("videoState", !state);
		UserSession currentUser = participants.remove(user.getName());//video on/off를 할 참가자
		currentUser.setVideoState(!state);//현재 유저 Video on/off
		participants.put(user.getName(),currentUser);
		
		for(final UserSession participant : participants.values()) {
			try {
				participant.sendMessage(videoState);
			}catch(final IOException e) {
				System.out.println("실패함");
			}
		}
	}
	
	// 참가자 mic on/off
	public void micState(UserSession user, Boolean state) throws IOException{
		final JsonObject micState = new JsonObject();
		micState.addProperty("id", "micState");
		micState.addProperty("name", user.getName());
		micState.addProperty("micState", !state);
		UserSession currentUser = participants.remove(user.getName());//video on/off를 할 참가자
		currentUser.setMicState(!state);//현재 유저 Video on/off
		participants.put(user.getName(),currentUser);
		
		for(final UserSession participant : participants.values()) {
			try {
				participant.sendMessage(micState);
			}catch(final IOException e) {
				System.out.println("실패함");
			}
		}
	}
	
	// 새 참가자 방에 들어옴
	private Collection<String> joinRoom(UserSession newParticipant) throws IOException{
		final JsonObject newParticipantMsg = new JsonObject();
		newParticipantMsg.addProperty("id", "newParticipantArrived");
		newParticipantMsg.addProperty("name", newParticipant.getName());
		newParticipantMsg.addProperty("videoState", newParticipant.getVideoState());
		newParticipantMsg.addProperty("micState", newParticipant.getMicState());
		
		final List<String> participantsList = new ArrayList<>(participants.values().size());
		log.debug("ROOM {}: notifying other participants of new participant {}", name, newParticipant.getName());
		
		for(final UserSession participant : participants.values()) {
			try {
				participant.sendMessage(newParticipantMsg);
			} catch(final IOException e) {
				log.debug("ROOM {}: participant {} could not be notified", name, participant.getName(), e);
			}
			participantsList.add(participant.getName());
		}
		return participantsList;
	}
	
	// 참가자 삭제
	private void removeParticipant(String name) throws IOException{
		participants.remove(name);
		
		log.debug("ROOM {}: notifying all users that {} is leaving the room", this.name, name);
		
		final List<String> unnotifiedParticipants = new ArrayList<>();
		final JsonObject participantLeftJson = new JsonObject();
		participantLeftJson.addProperty("id", "participantLeft");
		participantLeftJson.addProperty("name", name);
		for(final UserSession participant : participants.values()) {
			try {
				participant.cancelVideoFrom(name);
				participant.sendMessage(participantLeftJson);
			}catch(final IOException e) {
				unnotifiedParticipants.add(participant.getName());
			}
		}
	}
	// 참가자 정보?이름 추가
	public void sendParticipantNames(UserSession user) throws IOException{
		final JsonArray participantsArray = new JsonArray();//참가자 이름 저장
		final JsonArray participantsVideoArray = new JsonArray();//참가자 비디오 상태 저장
		final JsonArray participantsMicArray = new JsonArray();//참가자 마이크 상태 저장
		for(final UserSession participant : this.getParticipants()) {
			if(!participant.equals(user)) {
				final JsonElement participantName = new JsonPrimitive(participant.getName());
				final JsonElement participantVideoState = new JsonPrimitive(participant.getVideoState());
				final JsonElement participantMicState = new JsonPrimitive(participant.getMicState());
				participantsArray.add(participantName);
				participantsVideoArray.add(participantVideoState);
				participantsVideoArray.add(participantMicState);
			}
		}
		
		final JsonObject existingParticipantsMsg = new JsonObject();
		existingParticipantsMsg.addProperty("id", "existingParticipants");
		existingParticipantsMsg.add("data", participantsArray);//프론트로 참가자 이름 데이터 넘김
		existingParticipantsMsg.add("videoState", participantsVideoArray);//참가자 비디오 상태 데이터 넘김
		existingParticipantsMsg.add("micState", participantsMicArray);//참가자 비디오 상태 데이터 넘김
		log.debug("PARTICIPANT{}: sending a list of {} participants", user.getName(), participantsArray.size());
		//참가자와 참가자 목록 사이즈(참가자수)
		user.sendMessage(existingParticipantsMsg);
	}
	// 참가자 목록
	public Collection<UserSession> getParticipants(){
		return participants.values();
	}
	// 해당 이름의 참가자 세션 정보
	public UserSession getParticipant(String name) {
		return participants.get(name);
	}
	// 방 삭제..?
	@Override
	public void close(){
		for(final UserSession user : participants.values()) {
			try {
				user.close();
			}catch(IOException e) {
				log.debug("ROOM {}: Could not invoke close on participant {}", this.name, user.getName(), e);
			}
		}
		
		participants.clear();
		
		pipeline.release(new Continuation<Void>() {

			@Override
			public void onSuccess(Void result) throws Exception {
				log.trace("ROOM {}: Released Pipeline", Room.this.name);
			}

			@Override
			public void onError(Throwable cause) throws Exception {
				log.warn("PARTICIPANT {}: Could not release Pipeline", Room.this.name);
			}	
		});
		log.debug("Room {} closed", this.name);
	}

}
