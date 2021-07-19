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
public class ConferenceCategory extends BaseEntity{
    String name; // 카테고리 이름
    String background; // 배경
    String atomosphere; // 분위기

}
