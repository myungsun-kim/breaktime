### 목적

회원가입 시 휴대폰 인증서비스 구현

### Cool SMS 란?

문자메시지 웹발송 지원, 2008년부터 국내 최초로 REST API 및 오픈소스 공개한 인증서비스 업체

### 활용방안

회원가입 서비스 구현 후, CoolSMS 로 요청을 보내 SMS 문자 발송

# CoolSMS 서비스 등록 방법

## 1) **CoolSMS 인증서비스 사이트 회원가입**

[세상에서 가장 안정적이고 빠른 메시지](https://coolsms.co.kr/)

![Untitled.png (1480×873)](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/4dc0eba9-baa1-4625-8877-dc7d20e72bf8/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210819%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210819T074554Z&X-Amz-Expires=86400&X-Amz-Signature=0022633ccf3588b1ccb7326ef71c0de81c61209c710ebbe6243e7201126ac883&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22)

가입 후 휴대폰 인증을 통한 홈페이지 내 본인 인증 필수

## 2) **API Key, API Secret 생성**

**로그인 후 , 개발/연동 - API Key 관리에서, `새 API KEY 생성` 으로 API Key, API Secret 생성**

**※ 외부노출 시 캐쉬, 포인트 등 타인이 남용할 수 있으니 API Key, API Secret 보안 유지 각별히 주의**

![Untitled.png (235×696)](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/db14dc92-724f-4232-966a-0bef60a0a43d/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210819%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210819T074610Z&X-Amz-Expires=86400&X-Amz-Signature=f055dd0dcdd6a3f59f68a41a9719db23a1349b0b14838fee7067794787a2dfae&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22)

![Untitled.png](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/aa0abfd9-002e-4861-916e-d70106ab9fc3/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210819%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210819T074346Z&X-Amz-Expires=86400&X-Amz-Signature=30e6b85d4cec2ea7da04abd27a64df50888dff546c3687e7fd67ef39294337bb&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22)

## 3) **발신번호 등록**

**환경설정 - 발신번호 관리에서 발신번호 등록하기 버튼 클릭**

![Untitled.png](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/abc481f6-c64d-4b4f-ad36-decdd91a82dd/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210819%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210819T074319Z&X-Amz-Expires=86400&X-Amz-Signature=f4fc1ce940fe11caf32491160c1725e3e1f375e535c5387c3563ae174bf072f7&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22)

**유효기간 만료 후, 재인증 필요**

**전화 인증 유효기간 - 6개월**

**서류 인증 유효기간 - 12개월**

![Untitled.png (846×583)](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/ff2c929d-c745-4a83-9068-e900becfad90/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210819%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210819T074730Z&X-Amz-Expires=86400&X-Amz-Signature=c4f768a4b54b5986fbf5e44ec70f5c4791b397691b602a424bada3bb01b94793&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22)

**발신번호 등록 완료 화면**

![Untitled.png (1244×264)](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/4710148e-7fd2-4545-805f-25987e2df489/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210819%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210819T074741Z&X-Amz-Expires=86400&X-Amz-Signature=e20e282e7770604f86930950b8c5feb031baf9cacacd1495ae0cc32bb1fb2f0c&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22)

# 전송결과 확인

**메세지전송 - 전송결과에서 요청받은 메세지 건에 대한 처리결과 조회 가능**

![Untitled.png (1224×1179)](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/cef26c9f-e7bc-4400-a221-6281001b4416/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210819%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210819T074209Z&X-Amz-Expires=86400&X-Amz-Signature=076a6c3508a73edc20c1ba2d7005c2cfb9e19498bba155333537075eea426f09&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22)

참고 사이트

https://developer.coolsms.co.kr/developer

https://developer.coolsms.co.kr/SMS_API_v2

https://developer.coolsms.co.kr/Man_Credentials

https://developer.coolsms.co.kr/JAVA_SDK_Start_here

https://jinseongsoft.tistory.com/185