package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

// 회의방 모델
@Entity
@Getter
@Setter
public class Conference extends BaseEntity{
    String name; // 회의방 이름
    String owner; // 호스트
    String produceTime; // 생성 시간
    int participantLimit; // 참가자 제한 수
    String category; // 회의방 카테고리
    String description; // 회의방 설명

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // 생성 요청을 처리할 때만 사용
    String password;
}
