package com.ssafy.api.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConferenceDTO{
	
	@ApiModelProperty(name = "name", example = "노래하실 분 들어오세요")
	@ApiParam(value = "회의방명", required = true)
	private String name;
	
	@ApiModelProperty(name = "owner", example = "dfkfdfds12")
	@ApiParam(value = "방주인", required = true)
	private String owner;
	
	@ApiModelProperty(name = "ownerNick", example = "브루노마스")
	@ApiParam(value = "방주인 닉네임", required = true)
	private String ownerNick;
	
	@ApiModelProperty(name = "participantLimit", example = "7")
	@ApiParam(value = "참가자 제한 수")
	private int participantLimit;
    
	@ApiModelProperty(name = "category_seq", example = "3")
	@ApiParam(value = "카테고리", required = true)
    private int category_seq; // 회의방 카테고리와 다대일 관계
    
	@ApiModelProperty(name = "description", example = "노래 잘하는 사람이 오는 방입니다.")
	@ApiParam(value = "회의방 설명")
    private String description;

	@ApiModelProperty(name = "password", example = "i5202333")
	@ApiParam(value = "방비밀번호")
    private String password;

}
