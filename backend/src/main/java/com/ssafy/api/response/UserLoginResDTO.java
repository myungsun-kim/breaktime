package com.ssafy.api.response;

import org.springframework.http.HttpStatus;

import com.ssafy.common.model.response.BasicResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

// 유저 로그인 API ([POST] /auth/login) 요청에 대한 응답값 정의.

@Getter
@Setter
@ApiModel("UserLoginResDTO")
public class UserLoginResDTO extends BasicResponse{

	@ApiModelProperty(name="JWT 인증 토큰", example="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN...")
	private final String accessToken;

	public UserLoginResDTO(HttpStatus httpStatus, String message, String accessToken) {
		super(httpStatus, message);
		this.accessToken = accessToken;
	}
	
}
