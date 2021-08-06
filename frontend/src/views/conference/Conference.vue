<template>
  <el-container>
    <el-header>Header</el-header>
    <el-container class="conference-box">
      <el-main>
        <conference-main :conferenceId="props.conferenceId"/>
      </el-main>
      <el-aside width="300px" class="chat">
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
    conferenceId: Number
  },
  setup(props) {
    console.log(props.conferenceId)
    return {props}
  }
}
</script>

<style scoped>
  .conference-box {
    height: 100%;
  }
  .chat {
    border: 1px solid gray;
  }
</style>