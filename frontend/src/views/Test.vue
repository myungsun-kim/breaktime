<template>
  <div>
    유저이름: 
    <input
      v-model="userName"
      type="text"
    >
    내용: <input
      v-model="message"
      type="text"
      @keyup="sendMessage"
    >
    <div  
      v-for="(item, idx) in recvList"
      :key="idx"
    >
      <h3>유저이름: {{ item.userName }}</h3>
      <h3>내용: {{ item.content }}</h3>
    </div>
  </div>
</template>

<script>
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
  name: 'ChatSide',

  data() {
    return {
      message: "",
      messages: [],
      participants: [],
      userId: '',
    };
  },

  props: {
    roomId : String,
  },

  created() {
    this.userId = this.getUserId;
    console.log('아이디:'+this.userId);
    console.log('방번호:'+this.roomId);

    this.connect();
    this.connection();
    this.register();
  },

  destroyed() {
    this.leaveRoom();
  },

  computed: {
    ...mapGetters('user', ['getUserId']),
  },

  methods: {
    // WebSocket
    sendMessage() {
      this.ws.send(
        "/pub/chat/message",
        JSON.stringify({
          type: "TALK",
          roomId: this.roomId,
          sender: this.userId,
          message: this.message,
        }),
        {}
      );
      this.message = "";
    },
    recvMessage(recv) {
      this.messages.unshift({
        type: recv.type,
        sender: recv.type == "ENTER" ? "[알림]" : recv.sender,
        message: recv.message,
      });
    },
    connect() {
      // this.ws = new Stomp.over(new SockJS("http://3.35.171.221:8443/ws-stomp));
      this.ws = new Stomp.over(new SockJS("http://localhost:8443/ws-stomp));
      var app = this;

      this.ws.connect({}, function () {
          app.ws.subscribe("/sub/chat/room/" + app.roomId, function (message) {
            var recv = JSON.parse(message.body);
            app.recvMessage(recv);
          });
          app.ws.send("/pub/chat/message",
            JSON.stringify({
              type: "ENTER",
              roomId: app.roomId,
              sender: app.userId,
            }),
            {}
          );
        },
      );
    }
  }
}
</script>