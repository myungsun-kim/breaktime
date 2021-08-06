package com.ssafy.api.controller;

import java.util.HashMap;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.api.request.UserLoginVO;
import com.ssafy.api.request.UserRegisterVO;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.response.UserRes;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.util.JwtTokenUtil;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/signup")
	public ResponseEntity<? extends BaseResponseBody> register(@RequestBody UserRegisterVO userRegister) {
		
		User user = new User(userRegister.getId(), userRegister.getName(), userRegister.getPassword(),
				userRegister.getNickname(), userRegister.getEmailS(), userRegister.getEmailE(), userRegister.getPhone());
		
		String id = userService.join(user);

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}
	
	@GetMapping("/me")
	public ResponseEntity<UserRes> getUserInfo(@ApiIgnore Authentication authentication) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();
		User user = userService.findOne(userId);
		
		return ResponseEntity.status(200).body(UserRes.of(user));
	}
	
	@GetMapping("/{userId}") // 회원가입 ID 중복 체크
	public ResponseEntity<? extends BaseResponseBody> duplicateId (@PathVariable("userId") String userId ) {

		User user = userService.findOne(userId);
		if(user != null) {
			return ResponseEntity.status(409).body(BaseResponseBody.of(409, "이미 존재하는 사용자 ID 입니다.")); 
		}
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "사용자 ID 없음")); 
		
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<? extends BaseResponseBody> deleteUser (Authentication authentication, @PathVariable("userId") String userId) {

		SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
		String nowId = userDetails.getUsername();
		
		userRepository.delete(userId);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "삭제되었습니다"));

	}
	
	@PatchMapping("/modify")
	public ResponseEntity<? extends BaseResponseBody> modifyUser (Authentication authentication, @RequestBody User user) {
		SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
		String nowId = userDetails.getUsername();
		
		userService.modify(user);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "수정되었습니다"));
	
	}
	@GetMapping("/{phonenumber}") // Get으로 해보고, PostMapping 으로 다시 해보기 PathVariable 을 사용못함.
		  	public int smstest (@PathVariable("phonenumber") int phonenumber) {
		    String api_key = "NCSKIUXUNKOUKGXR";
		    String api_secret = "L63V4VH4ABUEOFC0EKFAZXLWQLAEXUEV";
		    Message coolsms = new Message(api_key, api_secret);

		    // 버튼을 누르면, 인증번호가 전송이 되고, 입력 후 전송하면 인증이 완료되었다고 뜨게 해야 한다.
		    
		    
		    // 4 params(to, from, type, text) are mandatory. must be filled
		    HashMap<String, String> params = new HashMap<String, String>();
		    params.put("to", "01091689599"); //발신번호
		    params.put("from", "phonenumber"); //무조건 자기번호 (인증) 수신번호 
		    
		    
		    
		    int min = 10;
		    int max = 100;
		    int random = (int) ((Math.random() * (max - min)) + min);
		    System.out.println(random);

		    params.put("type", "SMS");
		    params.put("text", "인증번호는  " + random + " 입니다. "); // 보낼 메세지를 입력하시오.
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
