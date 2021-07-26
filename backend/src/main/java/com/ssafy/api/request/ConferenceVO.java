package com.ssafy.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConferenceVO{
	
	private String name; // 회의방 이름
	
	private String owner; // 호스트
	
	private int participantLimit; // 참가자 제한 수
    
    private int category_seq; // 회의방 카테고리와 다대일 관계
    
    private String description; // 회의방 설명

    private String password;

	@Override
	public String toString() {
		return "ConfereceVO [name=" + name + ", owner=" + owner + ", participantLimit=" + participantLimit
				+ ", conferenceCategory=" + category_seq + ", description=" + description + ", password="
				+ password + "]";
	}
  
    
}
