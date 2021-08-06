package com.ssafy.db.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class ConferenceParticipant implements Serializable{
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "conference_seq") // 회의방 시퀀스와 조인
	private Conference conference; // 회의방과 다대일 관계

	@Id//식별자
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user; // 회원과 일대일 관계
}
