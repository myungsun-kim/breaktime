package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel("UserRegisterDTO")
public class UserRegisterDTO {
	
	@ApiModelProperty(name = "id", example = "i5202")
    @ApiParam(value = "ID", required = true)
	private String id;
	
	@ApiModelProperty(name = "name", example = "김삽휘")
	private String name;
	
	@ApiModelProperty(name = "password", example = "abcdef12345^")
    private String password;
    
	@ApiModelProperty(name = "nickname", example = "무케")
    private String nickname;
	
	@ApiModelProperty(name = "email", example = "abcdef@naver.com")
    @ApiParam(value = "이메일")
    private String email;
	
	@ApiModelProperty(name = "phone", example = "01012345678")
    private int phone;
}
