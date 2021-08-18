package com.ssafy.api.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class UserModifyDto {
	
	@ApiModelProperty(name = "nickname", example = "무케")
    private String nickname;
	
	@ApiModelProperty(name = "email", example = "abcdef@naver.com")
    @ApiParam(value = "이메일")
    private String email;
}
