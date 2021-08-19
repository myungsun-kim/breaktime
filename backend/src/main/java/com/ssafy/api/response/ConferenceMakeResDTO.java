package com.ssafy.api.response;

import org.springframework.http.HttpStatus;

import com.ssafy.common.model.response.BasicResponse;

public class ConferenceMakeResDTO extends BasicResponse{
	Long seq;

	public ConferenceMakeResDTO(HttpStatus httpStatus, String message, Long seq) {
		super(httpStatus, message);
		this.seq = seq;
	}
	
}
