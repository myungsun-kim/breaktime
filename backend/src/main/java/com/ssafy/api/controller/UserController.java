package com.ssafy.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.api.request.UserModifyDto;
import com.ssafy.api.request.UserRegisterDTO;
import com.ssafy.api.response.UserSelectResDTO;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BasicResponse;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

// 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;

	private final String SUCCESS_MESSAGE = "성공";
	
	@PostMapping
	@ApiOperation(value = "회원 가입", notes = "<strong>아이디, 패스워드, 이메일, 닉네임, 핸드폰 번호</strong>를 통해 회원가입 한다.") 
	@ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "요청을 처리할 수 없습니다.")
    })
	public ResponseEntity<? extends BasicResponse> register(@RequestBody UserRegisterDTO userRegister) {
		
		User user = new User(userRegister.getId(), userRegister.getName(), userRegister.getPassword(),
				userRegister.getNickname(), userRegister.getEmail(), userRegister.getPhone());
		
		userService.join(user);

		BasicResponse response = new BasicResponse(HttpStatus.OK, SUCCESS_MESSAGE);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping
	@ApiOperation(value = "회원 정보 조회", notes = "<strong>Bearer token</strong>을 통해 현재 회원 정보를 가져온다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "세션이 유효하지 않습니다. | 세션이 만료되었습니다."),
        @ApiResponse(code = 403, message = "접근 권한이 없습니다."),
        @ApiResponse(code = 500, message = "요청을 처리할 수 없습니다.")
    })
	public ResponseEntity<UserSelectResDTO> getUserInfo(@ApiIgnore Authentication authentication) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();
		User user = userService.findOne(userId);
		
		UserSelectResDTO response = new UserSelectResDTO(HttpStatus.OK, SUCCESS_MESSAGE, user);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	@ApiOperation(value = " ID 중복 체크", notes = "<strong>입력한 ID</strong>를 통해 DB의 중복된 값이 있는지 확인한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 400, message = "이미 존재하는 유저입니다."),
        @ApiResponse(code = 500, message = "요청을 처리할 수 없습니다.")
    })
	public ResponseEntity<? extends BasicResponse> duplicateId (@PathVariable("userId") String userId ) {

		userService.validateDuplicateUser(userId);
		
		BasicResponse response = new BasicResponse(HttpStatus.OK, SUCCESS_MESSAGE);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}
	
	@DeleteMapping
	@ApiOperation(value = "회원 탈퇴", notes = "<strong>Bearer token</strong>을 통해 가져온 회원을 삭제한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BasicResponse> deleteUser (@ApiIgnore Authentication authentication) {
		SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
		String nowId = userDetails.getUsername();
		
		userRepository.delete(nowId);

		BasicResponse response = new BasicResponse(HttpStatus.OK, SUCCESS_MESSAGE);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PatchMapping("/modify/{nickname}")//회원 수정
	@ApiOperation(value = "회원 정보 수정", notes = "<strong>Bearer token</strong>을 통해 가져온 회원의 정보를 입력받은 정보로 수정한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BasicResponse> modifyUser (Authentication authentication, @PathVariable("nickname") String nickname) {
		SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
		String nowId = userDetails.getUser().getId();
		userService.modify(nowId, nickname);//회원의 정보, 변경할 닉네임
		BasicResponse response = new BasicResponse(HttpStatus.OK, SUCCESS_MESSAGE);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
