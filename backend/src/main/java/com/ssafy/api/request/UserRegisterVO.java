package com.ssafy.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterVO {
	private String id; // 아이디
	private String name; // 이름

    private String password;
    
    private String nickname; // 닉네임
    private String emailS; // 이메일 앞
    private String emailE; // 이메일 뒤
    private int phone; // 전화번호
}
