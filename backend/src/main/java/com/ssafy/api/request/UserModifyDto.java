package com.ssafy.api.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class UserModifyDto {
	
	@ApiModelProperty(name = "nickname", example = "무케")
    private String nickname;
	
	@ApiModelProperty(name = "emailS", example = "abcdef")
    @ApiParam(value = "이메일 @ 앞")
    private String emailS;
	
	@ApiModelProperty(name = "emailE", example = "naver.com")
    @ApiParam(value = "이메일 @ 뒤")
    private String emailE;
}
