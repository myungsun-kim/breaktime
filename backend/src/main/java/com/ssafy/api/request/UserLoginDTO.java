package com.ssafy.api.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

// 유저 로그인 API ([POST] /auth/login) 요청에 필요한 리퀘스트 바디 정의.
@Getter
@Setter
public class UserLoginDTO {
	
	@ApiModelProperty(name = "id", example = "i5202")
	String id;
	
	@ApiModelProperty(name = "password", example = "abcdef1234&")
	String password;
}
