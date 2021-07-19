# 파이팅

# Ubuntu - Docker 설치
실행환경 > VirtualBox-6.1.22 Ubuntu-20.04.2.0 //각각 다운로드

- Virtual Box 설치 후 새로 만들기 클릭 후 가상 머신 만들기 (이름은 알아서 설정)
- 가상 머신에 원하는 크기의 메모리 할당
- 하드 디스크 설정 > 새로운 가상 하드 디스크를 만들어 사용
- 하드 디스크 파일 종류 VDI(Virtual Disk Image)
- 물리적 하드 드라이브에 저장 동적할당/고정크기 알아서 설정
- 파일 위치 및 크기 설정
: 가상 머신 생성 완료
<br>


- 다시 Virtual Box 초기 화면으로 돌아와 설정 눌러줌
- 저장소-비어있음의 광학 드라이브에 Ubuntu ISO 파일 설정해준 후 확인
- 다시 Virtual Box의 초기 화면으로 들어와 시작을 누르면, 우분투 ISO 파일을 이용해 우분투로 부팅
- Install Ubuntu로 우분투를 설치해줌(시간 조금 걸려요)
- 설치 후 재부팅
- 명세서 21페이지 참고. Docker와 Docker-Compose 설치
:  Malformed entry 1 in list file /etc/apt/sources.list.d/docker.list 라고 오류가 뜰 겨우
	- sudo -H gedit /etc/apt/sources.list.d/docker.list 명령어를 입력하면 해당 파일이 뜸
	   두번째 줄을 첫 번째 줄로 옮겨준 후 저장
: sudo systemctl status docker를 통해 도커 설치 확인
: docker-compose --version를 통해 도커 컴포저 설치 확인
: Kurento 설치 시 Got permission denied while trying to connect to the Docker daemon socket~ 라고 오류가 뜰 경우
	- sudo usermod -a -G docker $USER 입력 후 비밀번호 입력. 우분투 재부팅 후 다시 설치
<br>
- Coturn 설치 > WebRTC 시그널링을 위한 STUN/TURN 서버로 활용
** AWS EC2는 싸피에서 일괄 배포한다고 합니다! 명세서 22페이지 이후로는 나중에 진행..
