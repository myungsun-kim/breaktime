package com.ssafy.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.Forbidden;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.ssafy.common.model.response.BasicResponse;

/*
 * SPA처리를 위한 ControllerAdvice.
 *  요청에 해당하는 Request Mapping이 존재하지 않을 경우 Default로 index.html을 렌더링한다.
 */

@ControllerAdvice
public class ExceptHandler {
//	@Value("${spa.default-file}")
//	String defaultFile;
	private final String USER_NOT_EXIST_MESSAGE = "유저가 존재하지 않습니다.";
	private final String USER_ALREADY_EXIST_MESSAGE = "이미 존재하는 유저입니다.";
	private final String PASS_NOT_MATCH_MESSAGE = "패스워드가 맞지 않습니다.";
	private final String UNAUTHORIZED_MESSAGE = "세션이 유효하지 않습니다.";
	private final String EXPIRED_MESSAGE = "세션이 만료되었습니다.";
	private final String FORBIDDEN_MESSAGE = "접근 권한이 없습니다.";
	private final String DEFAULT_MESSAGE = "요청을 처리할 수 없습니다.";
	 
//	@ExceptionHandler(NoHandlerFoundException.class)
//	public ResponseEntity<String> renderDefaultPage(NoHandlerFoundException ex) {
//		String url = ex.getRequestURL();
//		if(url.startsWith("/api/")) {
//			return ResponseEntity.notFound().build();
//		}else {
//			try {
//				ClassPathResource classPathResource = new ClassPathResource(defaultFile);
//				InputStream inputStream = classPathResource.getInputStream();
//    				String body = StreamUtils.copyToString(inputStream, Charset.defaultCharset());
//			    return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(body);
//			} catch (IOException e) {
//				e.printStackTrace();
//				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There was an error completing the action.");
//			}
//		}
//	}
	
	@ExceptionHandler(UserNotExistException.class)
	public ResponseEntity<Object> UserNotExistExceptionHandle(UserNotExistException e){
		
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		
		BasicResponse basicResponse = new BasicResponse(httpStatus, USER_NOT_EXIST_MESSAGE);
		
		return new ResponseEntity<>(basicResponse, httpStatus);
	}
	
	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<Object> UserAlreadyExistExceptionHandle(UserAlreadyExistException e){
		
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		
		BasicResponse basicResponse = new BasicResponse(httpStatus, USER_ALREADY_EXIST_MESSAGE);
		
		return new ResponseEntity<>(basicResponse, httpStatus);
	}
	
	@ExceptionHandler(PassNotMatchException.class)
	public ResponseEntity<Object> PassNotMatchExceptionHandle(PassNotMatchException e){
		
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		
		BasicResponse basicResponse = new BasicResponse(httpStatus, PASS_NOT_MATCH_MESSAGE);
		
		return new ResponseEntity<>(basicResponse, httpStatus);
	}
	
	@ExceptionHandler({SignatureVerificationException.class, JWTDecodeException.class}) 
    public ResponseEntity<Object> UnAuthorizeHandle(Exception e) { 
        
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
		
		BasicResponse basicResponse = new BasicResponse(httpStatus, UNAUTHORIZED_MESSAGE);
		
		return new ResponseEntity<>(basicResponse, httpStatus);
    }
	
    @ExceptionHandler(TokenExpiredException.class) 
    public ResponseEntity<Object> ExpiredHandle (TokenExpiredException e) { 
        
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
		
		BasicResponse basicResponse = new BasicResponse(httpStatus, EXPIRED_MESSAGE);
		
		return new ResponseEntity<>(basicResponse, httpStatus);
    }
    
    @ExceptionHandler(Forbidden.class) 
    public ResponseEntity<Object> ForbiddenHandle (Forbidden e) { 
        
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		
		BasicResponse basicResponse = new BasicResponse(httpStatus, FORBIDDEN_MESSAGE);
		
		return new ResponseEntity<>(basicResponse, httpStatus);
    }
    
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> DefaultExceptionHandle(Exception e){
		
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		
		BasicResponse basicResponse = new BasicResponse(httpStatus, DEFAULT_MESSAGE);
		
		return new ResponseEntity<>(basicResponse, httpStatus);
	}
}
