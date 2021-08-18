package com.ssafy.common.model.response;

import org.springframework.http.HttpStatus;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// 서버 요청에대한 기본 응답값(바디) 정의.

@Getter
@Setter
@ApiModel("BasicResponse")
@AllArgsConstructor
public class BasicResponse {
	
    private final HttpStatus httpStatus;
    private final String message;

}