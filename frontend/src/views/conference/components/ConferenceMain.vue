<template>
	<div id="container">
			<h1>main</h1>
			<!-- <div id="participants"></div> -->
			<div v-for="participant in state.participants" :key="participant.name" :id="participant.name">
				<video :id="'video-' + participant.name" autoplay></video>
				<span>{{participant.name}}</span>
			</div>
			<el-button type="primary" round @click="videoOnOff">비디오On/Off</el-button>
			<el-button type="danger" round @click="goMain">방나가기</el-button>
	</div>
</template>

<script>
import { onMounted, reactive, ref } from 'vue'
import {useStore} from 'vuex'
import {useRouter} from 'vue-router'
import kurentoUtils from 'kurento-utils'

export default {
  name: 'ConferenceMain',
  props: {
    conferenceId: {
      type: Number,
      default: 0
    },
    owner: {
      type: String,
      default: ''
    },
  },
	setup(props) {
		// const ws = new WebSocket('wss://' + 'location:8443' + '/groupcall');
		const store = useStore()
		const router = useRouter()
		const user = store.getters['root/getUserInfo']
		// const PARTICIPANT_MAIN_CLASS = 'participant main';
		// const PARTICIPANT_CLASS = 'participant';
		const state = reactive({
			ws: null,
			name: user.name,
			room: props.conferenceId,
			videoState: true,
			participants: {},
		})

		const connect = function() {
			state.ws = new WebSocket('wss://' + location.host + '/groupcall');
			state.ws.onopen = function(event) {
				// console.log("Successfully connected to the echo websocket server...")
				register()
			}
			state.ws.onmessage = function(message) {
				let parsedMessage = JSON.parse(message.data);
				// console.info('Received message: ' + message.data);
	
				switch (parsedMessage.id) {
				case 'existingParticipants':
					onExistingParticipants(parsedMessage);
					break;
				case 'newParticipantArrived':
					onNewParticipant(parsedMessage);
					break;
				case 'participantLeft':
					onParticipantLeft(parsedMessage);
					break;
				case 'receiveVideoAnswer':
					receiveVideoResponse(parsedMessage);
					break;
				case 'iceCandidate':
					state.participants[parsedMessage.name].rtcPeer.addIceCandidate(parsedMessage.candidate, function (error) {
								if (error) {
								console.error("Error adding candidate: " + error);
								return;
								}
						});
						break;
				case 'videoState':
					state.videoState = parsedMessage.videoState
					let participant = state.participants[parsedMessage.name];
					participant.switchVideoOnOff()
					break;
				default:
					console.error('Unrecognized message', parsedMessage);
				}
			}
		}
		onMounted(() => {
			connect()
			window.addEventListener('beforeunload', function() {
				state.ws.close();
			});
		})
		// onBeforeUnmount(() => {
		// 	window.removeEventListener('beforeunload', function(e) {
    //   	console.log('새로고침')
    //   	e.preventDefault()
		// 		e.returnValue = ''
    // 	})
		// })

		
		// 4번 실행 
		// 1. 회의방 참가 
		const register = function() {
			let message = {		
				id : 'joinRoom',		
				name : state.name,
				room : state.room,
				videoState : true,
			}
			// 2. 메세지전송
			sendMessage(message);
		}
		const onNewParticipant = function(request) {
			receiveVideo(request.name);
		}
		const receiveVideoResponse = function(result) {
			state.participants[result.name].rtcPeer.processAnswer(result.spdAnswer, function (error) {
				if (error) return console.error (error);
			});
		}
		const callResponse = function(message) {
			console.log('callResponse', message.response)
			if (message.response != 'accepted') {
				// console.info('Call not accepted by peer. Closing call');
				stop();
			} else {
				webRtcPeer.processAnswer(message.spdAnswer, function (error) {
					if (error) return console.error (error);
				});
			}
		}

		const onExistingParticipants = function(msg) {
			let constraints = {
				audio : true,
				video : {
					mandatory : {
						maxWidth : 320,
						maxFrameRate : 15,
						minFrameRate : 15
					}
				}
			};

			// console.log(name + " registered in room " + room);
			// 7번 participant.js에서 참가자 만들기
			
			var participant = new Participant(state.name);
			state.participants[state.name] = participant;
			setTimeout(() => {
				let video = participant.getVideoElement();
	
				let options = {
							localVideo: video,
							mediaConstraints: constraints,
							onicecandidate: participant.onIceCandidate.bind(participant)
						}
				participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(options, function (error) {
						if(error) return console.error(error);
						this.generateOffer (participant.offerToReceiveVideo.bind(participant));
				});

			msg.data.forEach(receiveVideo);
			}, 100)
		}

		// 비디오 on/off관련 함수
		const videoOnOff = function () {
			let message = {		
				id : 'videoOnOff',		
				name : state.name,
				room : state.room,
				videoState : state.videoState
			}

			sendMessage(message)
		}

		// 방 나기가 관련 함수
		const leaveRoom = function() {
			sendMessage({
				id : 'leaveRoom'
			});

			for ( var key in state.participants) {
				state.participants[key].dispose();
			}

			state.ws.close();
			router.replace({name: 'Main'})
		}

		// 방나가기 버튼 클릭시 동작함수
		const goMain = function () {
			if (user.name === props.owner) {
				if(confirm('당신은호스트입니다 \n호스트가 회의를 종료하면 방은 삭제됩니다 \n진짜나가시겠습니까?')){
					store.dispatch('root/deleteRoom', {
						sequence: props.conferenceId
          })
          .then(function () {
          })
          .catch(function (err) {
            alert(err.response.data.message)
          })
					leaveRoom()
				}
			} else {
				if(confirm('화상회의를 종료하시겠습니까?')) {
					leaveRoom()
				}
			}
		}
		const receiveVideo = function(sender) {
			let participant = new Participant(sender);
			state.participants[sender] = participant;
			setTimeout(() => {
				let video = participant.getVideoElement();
	
				let options = {
						remoteVideo: video,
						onicecandidate: participant.onIceCandidate.bind(participant)
					}
	
				participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(options,
						function (error) {
							if(error) {
								return console.error(error);
							}
							this.generateOffer (participant.offerToReceiveVideo.bind(participant));
				});
			}, 100)

		}

		const onParticipantLeft = function(request) {
			// console.log('Participant ' + request.name + ' left');

			let participant = state.participants[request.name];
			participant.dispose();
			delete state.participants[request.name];

			if (request.name === props.owner) {
				alert('방장이 방삭제함')
				state.ws.close()
				router.replace({name: 'Main'})
			}
		}
	

		// 메세지를 backend단으로 이동 (CallHandler.java로 이동)
		const sendMessage = function(message) {
			let jsonMessage = JSON.stringify(message);
			// console.log('Sending message: ' + jsonMessage);
			state.ws.send(jsonMessage);
		}

		const Participant = function(name) {
			this.name = name;
			// var container = document.createElement('div');
			// container.className = isPresentMainParticipant() ? PARTICIPANT_CLASS : PARTICIPANT_MAIN_CLASS;
			// container.id = name;
			// var span = document.createElement('span');
			// var video = document.createElement('video');
			var rtcPeer;

			// container.appendChild(video);
			// container.appendChild(span);
			// container.onclick = switchContainerClass;
			// document.getElementById('participants').appendChild(container);

			// span.appendChild(document.createTextNode(name));

			// video.id = 'video-' + name;
			// video.className = isVideoState() ? 'd-inline' : 'd-none'
			// video.autoplay = true
			// video.controls = false;

			this.getElement = function() {
				return container;
			}

			this.getVideoElement = function() {
				let video = document.getElementById('video-' + name)
				// let videoId = 'video-' + name
				// console.log(videoId)
				// console.log(this.$refs.videoId.focus())
				// let video = document.getElementById('video-' + name)
				// console.log(video)
				return video;
			}

			// function switchContainerClass() {
			// 	if (container.className === PARTICIPANT_CLASS) {
			// 		var elements = Array.prototype.slice.call(document.getElementsByClassName(PARTICIPANT_MAIN_CLASS));
			// 		elements.forEach(function(item) {
			// 				item.className = PARTICIPANT_CLASS;
			// 			});

			// 			container.className = PARTICIPANT_MAIN_CLASS;
			// 		} else {
			// 		container.className = PARTICIPANT_CLASS;
			// 	}
			// }

			// function isPresentMainParticipant() {
			// 	return ((document.getElementsByClassName(PARTICIPANT_MAIN_CLASS)).length != 0);
			// }

			// 비디오 on / off 관련함수
			this.switchVideoOnOff = function () {
				if (video.className === 'd-inline') {
					video.className = 'd-none'
				} else {
					video.className = 'd-inline'
				}
			}

			function isVideoState () {
				return (state.videoState)
			}
			// 9번 실행 같이
			this.offerToReceiveVideo = function(error, offerSdp, wp){
				if (error) return console.error ("sdp offer error")
				console.log('Invoking SDP offer callback function');
				var msg =  { id : "receiveVideoFrom",
						sender : name,
						sdpOffer : offerSdp
					};
				sendMessage(msg);
			}
			// 9번 이거 실행
			this.onIceCandidate = function (candidate, wp) {
					// console.log("Local candidate" + JSON.stringify(candidate));

					var message = {
						id: 'onIceCandidate',
						candidate: candidate,
						name: name
					};
					sendMessage(message);
			}
			// Object.defineProperty() 정적 메서드는 객체에 직접 새로운 속성을 정의하거나 이미 존재하는 속성을 수정한 후, 그 객체를 반환합니다.
			
			Object.defineProperty(this, 'rtcPeer', { writable: true});

			this.dispose = function() {
				// console.log('Disposing participant ' + this.name);
				this.rtcPeer.dispose();
				// container.parentNode.removeChild(container);
			};
		}
		return {videoOnOff, goMain, router, connect, state, register, sendMessage, onParticipantLeft, receiveVideo,onExistingParticipants, callResponse, Participant, leaveRoom}
		},
}
</script>