<template>
  <el-container>

    <el-header>
      <div class="conference-header">

        <div class="logo-breaktime">
          <img src="../../assets/logo_cat.png" alt="" class="logo">
          <img src="../../assets/breaktime.png" alt="" class="breaktime">
        </div>

        <div>
          
          <p class="title">Room : {{name}}</p>
        </div>

        <div class="room-owner">
          <p>방번호: {{conferenceId}} </p>
          <p>방 장: {{owner}} </p>
        </div>

      </div>
    </el-header>

    <el-container class="conference-box">
      <el-main>
        <conference-main 
          :conferenceId="props.conferenceId"
          :owner="props.owner"
        />
      </el-main>
      <el-aside width="25%" class="chat">
        <chat-side :conferenceId="props.conferenceId"/>
      </el-aside>
    </el-container>

  </el-container>
</template>

<script>
import ChatSide from './components/ChatSide.vue'
import ConferenceMain from './components/ConferenceMain.vue'


export default {
  name: 'Conference',
  components: {
    ChatSide,
    ConferenceMain,
  },
  props: {
    conferenceId: {
      type: Number,
      default: 0
    },
    owner: {
      type: String,
      default: ''
    },
    name: {
      type: String,
      default: ''
    }
  },
  setup(props) {

    return {props}
  }
}

</script>

<style scoped>

  .conference-header {
    font-family: 'Jua', sans-serif;
    display: flex;
    justify-content: space-between;
    align-items: center;
    /* margin-left: 20px; */
    border-bottom: 1px solid;
  }


  .conference-box {
    height: 100%;
  }
  .chat {
    border: 1px solid gray;
    margin: 40px;
  }

  .logo-breaktime {
    margin-left: 20px;
    /* border-bottom: 1px solid; */
  }

  .room-owner {
    margin-right: 40px;
    text-align: left;
  }


</style>