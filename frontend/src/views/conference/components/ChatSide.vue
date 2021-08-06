<template>
<div>
  <h1>순현</h1>
  <div id="socket">
    유저이름:
    <input
      v-model="state.form.userName"
      type="text"
    >
    내용: <input
      v-model="state.form.message"
      type="text"
      @keyup="sendMessage"
    >
    <div
      v-for="(item, idx) in state.form.recvList"
      :key="idx"
    >
      <span> 유저이름: {{ item.userName }}</span>
      <span>내용: {{ item.message }}</span>
    </div>
    <!-- <div>
      유저 이름: {{ state.form.userName}}
      내용 : {{ state.form.message}}
    </div> -->
  </div>
</div>
</template>
<style>
  #socket {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }
</style>
<script>
import { onMounted, reactive } from 'vue'
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
  name: 'ChatSide',
  components: {
  },
  props: {
    conferenceId: Number
  },
  setup (props) {
    const state = reactive({
      conferenceId: '',
      form: {
        // token: localStorage.getItem('jwt'),
        userName: "",
        message: "",
        recvList: [],
        stompClient: "",
      },
    })

    onMounted(() => {
      connect()
    })

    const sendMessage = function (e) {
      console.log('eeeeee', e.keyCode, 'username', state.form.userName, 'msg', state.form.message, 'recvList', state.form.recvList )
      if(e.keyCode === 13 && state.form.userName !== '' && state.form.message !== ''){
        send()
        state.form.message = ''
      }
    }

    const send = function() {
      console.log("Send message:" + state.form.message);
      if (state.form.stompClient && state.form.stompClient.connected) {
        const msg = {
          type: "CHAT",
          roomId: props.conferenceId,
          userName: state.form.userName,
          message: state.form.message,
          // recvList: state.form.recvList,
        };
        console.log('메세지확인', msg)
        state.form.stompClient.send("/pub/chat/message", JSON.stringify(msg), {});
      }
    }

    const connect = function() {
      // "https://3.35.171.221:8443/ws-stomp"
      const serverURL = "https://localhost:8443/ws-stomp"
      let socket = new SockJS(serverURL);
      state.form.stompClient = Stomp.over(socket);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      state.form.stompClient.connect(
        {},
        frame => {
          // 소켓 연결 성공
          state.form.stompClient.connected = true;
          console.log('소켓 연결 성공', frame, 'id', props.conferenceId);
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          state.form.stompClient.subscribe('/sub/chat/room/' + props.conferenceId,
          res => {
            console.log('구독으로 받은 메시지 입니다.', res.body);

            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            state.form.recvList.push(JSON.parse(res.body))
          });
        },
        error => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error);
          state.form.stompClient.connected = false;
        }
      );
    }

    return { state, sendMessage, send, connect}
  }
}
</script>
