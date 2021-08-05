<template>
  <h2>채팅방</h2>
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
    conferenceId : Number,
  },

  created() {
    console.log(props.conferenceId);
    this.userId = this.getUserId;
    console.log('아이디:'+this.userId);
    console.log('방번호:'+this.roomId);

    this.connect();
    this.connection();
    this.register();
  },

  destroyed() {
    this.leaveRoom();
  }
}
</script>