package com.ssafy.api.response;

import org.springframework.http.HttpStatus;

import com.ssafy.common.model.response.BasicResponse;
import com.ssafy.db.entity.User;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원 본인 정보 조회 API ([GET] user/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
public class UserSelectResDTO extends BasicResponse{
	
	User user;

	public UserSelectResDTO(HttpStatus httpStatus, String message, User user) {
		super(httpStatus, message);
		this.user = user;
	}

}
