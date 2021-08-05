<template>
<div id="container">
		<div id="wrapper">
			<div id="join" class="animate join">
				<h1>Join a Room</h1>
				<form @submit="register()" accept-charset="UTF-8">
					<p>
						<input type="text" name="name" value="" id="name"
							placeholder="Username" required>
					</p>
					<p>
						<input type="text" name="room" value="" id="roomName"
							placeholder="Room" required>
					</p>
					<p class="submit">
						<input type="submit" name="commit" value="Join!">
					</p>
				</form>
			</div>
			<div id="room" style="display: none;">
				<h2 id="room-header"></h2>
				<div id="participants"></div>
				<input type="button" id="button-leave" onmouseup="leaveRoom();"
					value="Leave room">
			</div>
		</div>
</div>
</template>

<script>
// import {register} from '@/common/conference/conferenceroom.js'
// import '@/common/conference/participant.js'

export default {
  name: 'ConferenceMain',
	setup() {
		const register = function () {
			name = document.getElementById('name').value;
			var room = document.getElementById('roomName').value;

			document.getElementById('room-header').innerText = 'ROOM ' + room;
			document.getElementById('join').style.display = 'none';
			document.getElementById('room').style.display = 'block';

			var message = {
				id : 'joinRoom',
				name : name,
				room : room,
			}
			// 2. 메세지전송
			sendMessage(message);
		}
		const onNewParticipant = function (request) {
			receiveVideo(request.name);
		}

		const receiveVideoResponse = function (result) {
			participants[result.name].rtcPeer.processAnswer (result.sdpAnswer, function (error) {
				if (error) return console.error (error);
			});
		}

		const callResponse = function (message) {
			if (message.response != 'accepted') {
				console.info('Call not accepted by peer. Closing call');
				stop();
			} else {
				webRtcPeer.processAnswer(message.sdpAnswer, function (error) {
					if (error) return console.error (error);
				});
			}
		}
		const onExistingParticipants = function (msg) {
			var constraints = {
				audio : true,
				video : {
					mandatory : {
						maxWidth : 320,
						maxFrameRate : 15,
						minFrameRate : 15
					}
				}
			};
			console.log(name + " registered in room " + room);
			// 7번 participant.js에서 참가자 만들기
			var participant = new Participant(name);
			participants[name] = participant;
			var video = participant.getVideoElement();

			var options = {
						localVideo: video,
						mediaConstraints: constraints,
						onicecandidate: participant.onIceCandidate.bind(participant)
					}
			participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(options,
				function (error) {
					if(error) {
						return console.error(error);
					}
					this.generateOffer (participant.offerToReceiveVideo.bind(participant));
			});

			msg.data.forEach(receiveVideo);
		}
		const leaveRoom = function () {
			sendMessage({
				id : 'leaveRoom'
			});

			for ( var key in participants) {
				participants[key].dispose();
			}

			document.getElementById('join').style.display = 'block';
			document.getElementById('room').style.display = 'none';

			ws.close();
		}	
		const receiveVideo = function (sender) {
			var participant = new Participant(sender);
			participants[sender] = participant;
			var video = participant.getVideoElement();

			var options = {
					remoteVideo: video,
					onicecandidate: participant.onIceCandidate.bind(participant)
				}

			participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(options,
					function (error) {
						if(error) {
							return console.error(error);
						}
						this.generateOffer (participant.offerToReceiveVideo.bind(participant));
			});;
		}	
		const onParticipantLeft = function (request) {
			console.log('Participant ' + request.name + ' left');
			var participant = participants[request.name];
			participant.dispose();
			delete participants[request.name];
		}
		const sendMessage = function (message) {
			var jsonMessage = JSON.stringify(message);
			console.log('Sending message: ' + jsonMessage);
			ws.send(jsonMessage);
		}
		const Participant = function (name) {
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

		return {register, onNewParticipant, receiveVideoResponse, callResponse, onExistingParticipants, leaveRoom, onParticipantLeft}
	},
	created() {
		var ws = new WebSocket('wss://' + location.host + '/groupcall');
		var participants = {};
		var name;
		const PARTICIPANT_MAIN_CLASS = 'participant main';
		const PARTICIPANT_CLASS = 'participant';

		window.onbeforeunload = function() {
			ws.close();
		};
		// 4번 실행 
		ws.onmessage = function(message) {
			var parsedMessage = JSON.parse(message.data);
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
}
</script>