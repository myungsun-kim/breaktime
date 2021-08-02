package com.ssafy.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

// 회의방 참가자 테이블
@Entity
@Getter
@Setter
public class ConferenceParticipant {
	@Id @GeneratedValue()
	@Column(name="participant_seq")
	private int sequence; //참가자 번호
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "conference_seq")
//	private Long conferenceSequence; // 회의방과 다대일 관계
//	
//	@OneToOne
//	@JoinColumn(name="user_id")
//	private String participantId; // 회원과 일대일 관계
}
