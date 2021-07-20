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
public class File extends BaseEntity{
	private String originalName; // 파일 원본
	private String changeName;
	private String path; // 경로
	private String owner; // 작성자
	private int size; // 크기
	private int roomSequence; // 회의방 번호
	private LocalDateTime time; // 업로드 시간
}
