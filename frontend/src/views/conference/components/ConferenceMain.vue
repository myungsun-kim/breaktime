<template>
	<div id="container">
			<h1>main</h1>
			<div id="participants"></div>
	</div>
</template>

<script>
import { onMounted, reactive, ref } from 'vue'
import {useStore} from 'vuex'
import kurentoUtils from 'kurento-utils'
// import Participant from '@/assets/participant.js'

// var kurentoUtils = require('kurento-utils');

export default {
  name: 'ConferenceMain',
	props: {
    conferenceId: Number
	},
	setup(props) {
		// const ws = new WebSocket('wss://' + 'location:8443' + '/groupcall');
		const store = useStore()
		const user = store.getters['root/getUserInfo']
		const PARTICIPANT_MAIN_CLASS = 'participant main';
		const PARTICIPANT_CLASS = 'participant';
		// const kurentoUtils = require('kurento-utils');

		let participants = {};
		const state = reactive({
			ws: null,
			name: user.name,
			room: props.conferenceId
		})

		const connect = function() {
			state.ws = new WebSocket('wss://' + location.host + '/groupcall');
			state.ws.onopen = function(event) {
				console.log(event)
				console.log("Successfully connected to the echo websocket server...")
				register()
			}
			state.ws.onmessage = function(message) {
				let parsedMessage = JSON.parse(message.data);
				console.info('Received message: ' + message.data);
	
				switch (parsedMessage.id) {
				// 5번 회의방을 만듬
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
					participants[parsedMessage.name].rtcPeer.addIceCandidate(parsedMessage.candidate, function (error) {
								if (error) {
								console.error("Error adding candidate: " + error);
								return;
								}
						});
						break;
				default:
					console.error('Unrecognized message', parsedMessage);
				}
			}
		}
		onMounted(() => {
			console.log('onMounted')
			connect()
		})
		
		window.onbeforeunload = function() {
			state.ws.close();
		};
		// 4번 실행 
		// 1. 회의방 참가 
		const register = function() {
			let message = {		
				id : 'joinRoom',		
				name : state.name,
				room : state.room,
			}
			// 2. 메세지전송
			sendMessage(message);
		}
		const onNewParticipant = function(request) {
			receiveVideo(request.name);
		}
		const receiveVideoResponse = function(result) {
			participants[result.name].rtcPeer.processAnswer (result.sdpAnswer, function (error) {
				if (error) return console.error (error);
			});
		}
		const callResponse = function(message) {
			if (message.response != 'accepted') {
				console.info('Call not accepted by peer. Closing call');
				stop();
			} else {
				webRtcPeer.processAnswer(message.sdpAnswer, function (error) {
					if (error) return console.error (error);
				});
			}
		}
		
		// 6번 회의방 만들기 시작
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
			
			let participant = new Participant(state.name);
			console.log(state.name)
			participants[state.name] = participant;
			console.log(participant)
			let video = participant.getVideoElement();

			let options = {
						localVideo: video,
						mediaConstraints: constraints,
						onicecandidate: participant.onIceCandidate.bind(participant)
					}
			participant.rtcPeer = kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(options, function (error) {
					if(error) return console.error(error);
					this.generateOffer (participant.offerToReceiveVideo.bind(participant));
			});

			msg.data.forEach(receiveVideo);
		}
		
		const receiveVideo = function(sender) {
			let participant = new Participant(sender);
			participants[sender] = participant;
		
			let video = participant.getVideoElement();

			let options = {
					remoteVideo: video,
					onicecandidate: participant.onIceCandidate.bind(participant)
				}

			participant.rtcPeer = kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(options,
					function (error) {
						if(error) {
							return console.error(error);
						}
						this.generateOffer (participant.offerToReceiveVideo.bind(participant));
			});

		}

		const onParticipantLeft = function(request) {
			console.log('Participant ' + request.name + ' left');
		
			let participant = participants[request.name];
			participant.dispose();
			delete participants[request.name];
		}
	

		// 3번  메세지를받음 -> onmessage로
		const sendMessage = function(message) {
			let jsonMessage = JSON.stringify(message);
			console.log('Sending message: ' + jsonMessage);
			state.ws.send(jsonMessage);
		}

		const Participant = function(name) {
			this.name = name;
			var container = document.createElement('div');
			container.className = isPresentMainParticipant() ? PARTICIPANT_CLASS : PARTICIPANT_MAIN_CLASS;
			container.id = name;
			var span = document.createElement('span');
			var video = document.createElement('video');
			var rtcPeer;

			container.appendChild(video);
			container.appendChild(span);
			container.onclick = switchContainerClass;
			document.getElementById('participants').appendChild(container);

			span.appendChild(document.createTextNode(name));

			video.id = 'video-' + name;
			video.autoplay = true;
			video.controls = false;


			this.getElement = function() {
				return container;
			}

			this.getVideoElement = function() {
				return video;
			}

			function switchContainerClass() {
				if (container.className === PARTICIPANT_CLASS) {
					var elements = Array.prototype.slice.call(document.getElementsByClassName(PARTICIPANT_MAIN_CLASS));
					elements.forEach(function(item) {
							item.className = PARTICIPANT_CLASS;
						});

						container.className = PARTICIPANT_MAIN_CLASS;
					} else {
					container.className = PARTICIPANT_CLASS;
				}
			}

			function isPresentMainParticipant() {
				return ((document.getElementsByClassName(PARTICIPANT_MAIN_CLASS)).length != 0);
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
					console.log("Local candidate" + JSON.stringify(candidate));

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
				console.log('Disposing participant ' + this.name);
				this.rtcPeer.dispose();
				container.parentNode.removeChild(container);
			};
		}
		return {participants, connect, state, register, sendMessage, onParticipantLeft, receiveVideo,onExistingParticipants, callResponse, Participant}
		},
}
</script>