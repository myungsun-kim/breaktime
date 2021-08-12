<template>
<div margin="20px">
  <h1>chat</h1>
    <div id="socket">
      <div id="chatMain">
      <div
        v-for="(item, idx) in state.form.recvList"
        :key="idx" 
      >
      <div class="sentence">
        <div style="float:left;"> {{ item.userName }} : </div>
        <div>{{ item.message }}</div>
      </div>
  
      </div>
      </div>
      <!-- 유저이름:
      <input
        v-model="state.form.userName"
        type="text"
      > -->
      <div id="chatInput">
      Message: <input
        v-model="state.form.message"
        type="text"
        @keyup="sendMessage"
        id="input"
      ></div>
      <!-- <div>
        유저 이름: {{ state.form.userName}}
        내용 : {{ state.form.message}}
      </div> -->
    </div>
</div>
</template>

<style scoped>
  #socket {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: left;
    color: #2c3e50;
    margin-top: 30px;
    margin-left: 30px;
    margin-bottom: 30px;
    /* width: 400px; */
    height: 1000px;
    /* height: 100%; */
  }
  #chatMain{
    height: 90%;
    width:100%;
    overflow: auto;
  }
  #chatInput{
    height: 5%;
    width: 100%;
    margin-top: 5%;
  }
  #input {
    width: 80%;
  }
  span{
    width: 100%;
  }
</style>

<script>
import { onMounted, reactive } from 'vue'
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import { useStore } from 'vuex'

export default {
  name: 'ChatSide',
  components: {
    },
  props: {
    conferenceId: Number
  },
  setup (props) {
    const store = useStore()

    const user = store.getters['root/getUserInfo']

    const state = reactive({
      conferenceId: '',
      form: {
        // token: localStorage.getItem('jwt'),
        userName: user.name,
        message: "",
        recvList: [],
        stompClient: "",
      },
    })

    onMounted(() => {
      connect()
    })

    const sendMessage = function (e) {
      // console.log('eeeeee', e.keyCode, 'username', state.form.userName, 'msg', state.form.message, 'recvList', state.form.recvList )
      if(e.keyCode === 13 && state.form.userName !== '' && state.form.message !== ''){
        send()
        state.form.message = ''
        scrollToEnd()
      }
    }

    const scrollToEnd = function() { //스크롤 맨 아래로 내리게
      const container = document.getElementById("chatMain");
      container.scrollTop = container.scrollHeight;
    }

    const send = function() {
      // console.log("Send message:" + state.form.message);
      if (state.form.stompClient && state.form.stompClient.connected) {
        const msg = {
          type: "CHAT",
          roomId: props.conferenceId,
          userName: state.form.userName,
          message: state.form.message,
          // recvList: state.form.recvList,
        };
        // console.log('메세지확인', msg)
        state.form.stompClient.send("/pub/chat/message", JSON.stringify(msg), {});
      }
    }

    const connect = function() {
      // "https://3.35.171.221:8443/ws-stomp"
      const serverURL = "https://localhost:8443/ws-stomp"
      let socket = new SockJS(serverURL);
      state.form.stompClient = Stomp.over(socket);
      // console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      state.form.stompClient.connect(
        {},
        frame => {
          // 소켓 연결 성공
          state.form.stompClient.connected = true;
          // console.log('소켓 연결 성공', frame, 'id', props.conferenceId);
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          state.form.stompClient.subscribe('/sub/chat/room/' + props.conferenceId,
          res => {
            // console.log('구독으로 받은 메시지 입니다.', res.body);

            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            state.form.recvList.push(JSON.parse(res.body))
          });
        },
        error => {
          // 소켓 연결 실패
          // console.log('소켓 연결 실패', error);
          state.form.stompClient.connected = false;
        }
      );
    }

    return { store, user, state, sendMessage, send, connect}
  }
}
</script>

<style scoped>
  .sentence {
    background-color: #F6F6F6;
    border-radius: 10px;
    margin: 10px 15px 15px 0;
  }
</style>