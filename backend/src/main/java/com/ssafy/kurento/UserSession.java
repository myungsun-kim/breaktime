package com.ssafy.kurento;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.kurento.client.Continuation;
import org.kurento.client.EventListener;
import org.kurento.client.IceCandidate;
import org.kurento.client.IceCandidateFoundEvent;
import org.kurento.client.MediaPipeline;
import org.kurento.client.SdpEndpoint;
import org.kurento.client.WebRtcEndpoint;
import org.kurento.jsonrpc.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.JsonObject;

public class UserSession implements Closeable{

	private static final Logger log = LoggerFactory.getLogger(UserSession.class);
	
	private final String name;
	private final WebSocketSession session;
	
	private final MediaPipeline pipeline;
	
	private final long roomSequence;
	private final WebRtcEndpoint outgoingMedia;//PeerToPeer로 통신하는 WebRTC의 한쪽
	private ConcurrentMap<String, WebRtcEndpoint> incomingMedia = new ConcurrentHashMap<>();

	
	public UserSession(final String name, long room_sequence, final WebSocketSession session, MediaPipeline pipeline) {
		this.name = name;
		this.session = session;
		this.pipeline = pipeline;
		this.roomSequence = room_sequence;
		this.outgoingMedia = new WebRtcEndpoint.Builder(pipeline).build();
		
		this.outgoingMedia.addIceCandidateFoundListener(new EventListener<IceCandidateFoundEvent>() {
		//RTCIceCandidate:RTCPeerConnection을 구축 할 때 사용되는 Internet Connectivity Establishment의 후보군(candidate)
		//addIceCandidateFoundListener를 사용하여 candidate를 찾는 과정
			@Override
			public void onEvent(IceCandidateFoundEvent event) {
				JsonObject response = new JsonObject();
				response.addProperty("id", "iceCandidate");
				response.addProperty("name", name);
				response.add("candidate", JsonUtils.toJsonObject(event.getCandidate()));
				try {
					synchronized(session) {
						session.sendMessage(new TextMessage(response.toString()));
					}
				}catch(IOException e) {
					log.debug(e.getMessage());
				}
			} 
		});
	}

	public WebRtcEndpoint getOutgoingWebRtcPeer() {
	    return outgoingMedia;
	}

	public String getName() {
	    return name;
	}

	public WebSocketSession getSession() {
		return session;
	}

	public long getRoomSeqeuence() {
	    return this.roomSequence;
  	}
	  
	public void receiveVideoFrom(UserSession sender, String sdpOffer) throws IOException{
		//SDP Session Description Protocol 멀티미티어 세션 파라미터를 협상하는 프로토콜
		//sdpOffer: 어떤 방식으로 연결될지 확인
		log.info("USER {}: connection with {} in room {}", this.name, sender.getName(), this.roomSequence);
		// 로그에 유저 정보 / 연결된 유저 정보 / 연결된 방의 정보 표시
		
		log.trace("USER {}: SpdOffer for {} is {}", this.name, sender.getName(), sdpOffer);
		
		final String ipSdpAnswer = this.getEndpointForUser(sender).processOffer(sdpOffer);
		final JsonObject scParams = new JsonObject();
		
		scParams.addProperty("id", "receiveVideoAnswer");
		scParams.addProperty("name", sender.getName());
		scParams.addProperty("spdAnswer", ipSdpAnswer);
		
		log.trace("USER {}: SdpAnswer for {} is {}", this.name, sender.getName(), ipSdpAnswer);
	    this.sendMessage(scParams);
	    log.debug("gather candidates");
	    this.getEndpointForUser(sender).gatherCandidates();
	}
	
	private WebRtcEndpoint getEndpointForUser(final UserSession sender) {
		//수신된 세션으로부터 WebRTC의 한쪽을 리턴
		if (sender.getName().equals(name)) {
			log.debug("PARTICIPANT {}: configuring loopback", this.name);
			return outgoingMedia;
		}
		
		log.debug("PARTICIPANT {}: receiving video from{}", this.name, sender.getName());
		
		WebRtcEndpoint incoming = incomingMedia.get(sender.getName());
		if(incoming == null) {
			log.debug("PARTICIPANT {}: creating new endpoint for {}", this.name, sender.getName());
			incoming = new WebRtcEndpoint.Builder(pipeline).build();
			
			incoming.addIceCandidateFoundListener(new EventListener<IceCandidateFoundEvent>() {
				
				@Override
				public void onEvent(IceCandidateFoundEvent event) {
					JsonObject response = new JsonObject();
					response.addProperty("id", "iceCandidate");
					response.addProperty("name", sender.getName());
					response.add("candidate", JsonUtils.toJsonObject(event.getCandidate()));
					try {
						synchronized (session) {
							session.sendMessage(new TextMessage(response.toString()));
						}
					}catch(IOException e) {
						log.debug(e.getMessage());
					}
				}
			});
			
			incomingMedia.put(sender.getName(), incoming);
		}
		
		log.debug("PARTICIPANT {}: obtained endpoint for {}", this.name, sender.getName());
		sender.getOutgoingWebRtcPeer().connect(incoming);
		
		return incoming;
	}
	
	public void cancelVideoFrom(final UserSession sender) {
		this.cancelVideoFrom(sender.getName());
	}
	
	public void cancelVideoFrom(final String senderName) {
		//비디오 연결 해제. 취소
		log.debug("PARTICIPANT {}: canceling video reception from {}", this.name, senderName);
		final WebRtcEndpoint incoming = incomingMedia.remove(senderName);

		log.debug("PARTICIPANT {}: removing endpoint for {}", this.name, senderName);
		incoming.release(new Continuation<Void>() {
		@Override
		public void onSuccess(Void result) throws Exception {
		  log.trace("PARTICIPANT {}: Released successfully incoming EP for {}",
		      UserSession.this.name, senderName);
		}

		@Override
		public void onError(Throwable cause) throws Exception {
		  log.warn("PARTICIPANT {}: Could not release incoming EP for {}", UserSession.this.name,
		      senderName);
			}
		});
	}
	  
	@Override
	public void close() throws IOException {
		// 연결 종료
		log.debug("PARTICIPANT {}: Releasing resources", this.name);
		for (final String remoteParticipantName : incomingMedia.keySet()) {

			log.trace("PARTICIPANT {}: Released incoming EP for {}", this.name, remoteParticipantName);

		    final WebRtcEndpoint ep = this.incomingMedia.get(remoteParticipantName);

		    ep.release(new Continuation<Void>() {

		    @Override
		    public void onSuccess(Void result) throws Exception {
		    log.trace("PARTICIPANT {}: Released successfully incoming EP for {}",
		          UserSession.this.name, remoteParticipantName);
		    }

		    @Override
		    public void onError(Throwable cause) throws Exception {
		      log.warn("PARTICIPANT {}: Could not release incoming EP for {}", UserSession.this.name,
		          remoteParticipantName);
		    }
		  });
		}

		outgoingMedia.release(new Continuation<Void>() {

	    @Override
	    public void onSuccess(Void result) throws Exception {
	      log.trace("PARTICIPANT {}: Released outgoing EP", UserSession.this.name);
	    }

	    @Override
	    public void onError(Throwable cause) throws Exception {
	      log.warn("USER {}: Could not release outgoing EP", UserSession.this.name);
	    }
	  });
	}

	// 메시지 보낸 사용자와 메시지 내용 출력
	public void sendMessage(JsonObject message) throws IOException {
		log.debug("USER {}: Sending message {}", name, message);
	    synchronized (session) {
	      session.sendMessage(new TextMessage(message.toString()));
	    }
	}
	
	//참가자 추가..?
	public void addCandidate(IceCandidate candidate, String name) {	
	    if (this.name.compareTo(name) == 0) {
	      outgoingMedia.addIceCandidate(candidate);
	    } else {
	      WebRtcEndpoint webRtc = incomingMedia.get(name);
	      if (webRtc != null) {
	        webRtc.addIceCandidate(candidate);
	      }
	    }
	  }
	
	public boolean equals(Object obj) {
		if(this==obj) return true;
		if(obj==null || !(obj instanceof UserSession)) return false;
		UserSession other = (UserSession) obj;
		boolean eq = name.contentEquals(other.name);
		eq &= Long.toString(roomSequence).contentEquals(Long.toString(other.roomSequence));
		return eq;
	}
	
	@Override
	public int hashCode() {
		int result = 1;
		result = 31*result + name.hashCode();
		result = 31*result + Long.toString(roomSequence).hashCode();
		return result;
	}
}
