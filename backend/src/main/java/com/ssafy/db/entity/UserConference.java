package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

// 회의방 참여 유저
@Entity
@Getter
@Setter
public class UserConference extends BaseEntity{
    int roomSequence; // 방 번호
    String participantId; // 참가자 Id

}
