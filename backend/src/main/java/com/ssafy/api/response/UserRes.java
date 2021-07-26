package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 본인 정보 조회 API ([GET] user/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
public class UserRes{
	private String userId;
	private String name;
	private String nickname;
	private String emailS; // 이메일 앞
    private String emailE; // 이메일 뒤
    private int phone; // 전화번호
	
	public static UserRes of(User user) {
		UserRes res = new UserRes(user.getId(), user.getName(), user.getNickname(), user.getEmailS(),
				user.getEmailE(), user.getPhone());
		return res;
	}
	
	public UserRes() {}
	
	public UserRes(String userId, String name, String nickname, String emailS, String emailE, int phone) {
		super();
		this.userId = userId;
		this.name = name;
		this.nickname = nickname;
		this.emailS = emailS;
		this.emailE = emailE;
		this.phone = phone;
	}
	
	
}
