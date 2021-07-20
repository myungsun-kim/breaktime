package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

// 회의방 모델
@Entity
@Getter
@Setter
public class Message extends BaseEntity{
	private String writer; // 작성자
	private String message; // 내용
	private LocalDateTime time; // 작성 시간
	private int roomSequence; // 회의방 번호
}
