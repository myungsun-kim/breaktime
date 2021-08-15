package com.ssafy.api.controller;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.api.request.UserLoginDTO;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.service.UserService;
import com.ssafy.common.util.JwtTokenUtil;
import com.ssafy.db.entity.User;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

/**
 * 인증 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@PostMapping("/login")
	@ApiOperation(value = "로그인", notes = "<strong>아이디, 패스워드</strong>를 통해 로그인한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<UserLoginPostRes> login(@RequestBody UserLoginDTO loginInfo) {
		
		String userId = loginInfo.getId();
		String password = loginInfo.getPassword();
		
		User user = userService.findOne(userId);
		
		if(user == null) {
			return ResponseEntity.status(404).body(UserLoginPostRes.of(404, "존재하지 않는 계정입니다.", (String) null));
		}	
		// 로그인 요청한 유저로부터 입력된 패스워드 와 디비에 저장된 유저의 암호화된 패스워드가 같은지 확인.(유효한 패스워드인지 여부 확인)
		if(passwordEncoder.matches(password, user.getPassword())) {
			// 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
			return ResponseEntity.ok(UserLoginPostRes.of(200, "Success", JwtTokenUtil.getToken(userId)));
		}
		// 유효하지 않는 패스워드인 경우, 로그인 실패로 응답.
		return ResponseEntity.status(401).body(UserLoginPostRes.of(401, "잘못된 비밀번호입니다.", (String) null));
	}
	
	@PostMapping("/{phonenumber}")
	@ApiOperation(value = "휴대폰 인증", notes = "<strong>전화번호를 통해 인증 번호</strong>를 받아 인증 코드를 리턴한다. ") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
  	public int smstest (@PathVariable("phonenumber") String phonenumber) {
	    String api_key = "NCSKIUXUNKOUKGXR";
	    String api_secret = "L63V4VH4ABUEOFC0EKFAZXLWQLAEXUEV";
	    Message coolsms = new Message(api_key, api_secret);
	    System.out.println("함수실행");
	    // 버튼을 누르면, 인증번호가 전송이 되고, 입력 후 전송하면 인증이 완료되었다고 뜨게 해야 한다.
    
	    // 4 params(to, from, type, text) are mandatory. must be filled
	    HashMap<String, String> params = new HashMap<String, String>();
	    params.put("to", phonenumber); //수신번호
	    params.put("from", "01091689599"); //발신번호 (무조건 등록된번호)
	     
	    int min = 10;
	    int max = 100;
	    int random = (int) ((Math.random() * (max - min)) + min);
	    System.out.println(random);
	
	    params.put("type", "SMS");
	    params.put("text", "인증번호는  "+ random +" 입니다. "); // 보낼 메세지를 입력하시오.
	    params.put("app_version", "test app 1.2"); // application name and version
	
	    try {
	    	//send() 는 메시지를 보내는 함수  
	      JSONObject obj = (JSONObject) coolsms.send(params);
	      System.out.println(obj.toString());
	    } catch (CoolsmsException e) {
	      System.out.println(e.getMessage());
	      System.out.println(e.getCode());
	      // front 로 인증번호를 return 해주고, front 에서는 인증번호를 받아서 해당 사용자에게 받은 번호와
	      // 같은지 비교 후 승인 or 거부
	    }
	    return random;
	}
}
