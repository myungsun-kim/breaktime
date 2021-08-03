package com.ssafy.kurento;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.WebSocketSession;

public class UserRegistry {
	private final ConcurrentHashMap<String, UserSession> usersByName = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, UserSession> usersBySessionId = new ConcurrentHashMap<>();
	
	// 유저 등록
	public void register(UserSession user) {
		usersByName.put(user.getName(), user);
		usersBySessionId.put(user.getSession().getId(), user);
	}
	
	// 해당 이름에 해당하는 유저 세션
	public UserSession getByName(String name) {
		return usersByName.get(name);
	}
	// 해당 세션에 해당하는 유저 세션
	public UserSession getBySession(WebSocketSession session) {
		return usersBySessionId.get(session.getId());
	}
	// 해당 이름 존재하는지 확인
	public boolean exists(String name) {
		return usersByName.keySet().contains(name);
	}
	// 해당 세션 정보 삭제
	public UserSession removeBySession(WebSocketSession session) {
		final UserSession user = getBySession(session);
		usersByName.remove(user.getName());
		usersBySessionId.remove(session.getId());
		return user;
	}
}
