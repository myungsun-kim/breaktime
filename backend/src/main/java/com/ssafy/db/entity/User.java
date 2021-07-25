package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
@Setter
public class User{
	@Id
	@Column(name = "user_id")
	private String id; // 아이디
	private String name; // 이름

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    
    private String nickname; // 닉네임
    private String emailS; // 이메일 앞
    private String emailE; // 이메일 뒤
    private int phone; // 전화번호
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conference_seq") // 회의방 번호와 조인
    private Conference conference;
    
    @OneToMany(mappedBy = "friend") // 친구리스트
    private List<Friend> friends = new ArrayList<>();
    
    public User() {}
    
	public User(String id, String name, String password, String nickname, String emailS, String emailE, int phone) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.nickname = nickname;
		this.emailS = emailS;
		this.emailE = emailE;
		this.phone = phone;
	}
    
}
