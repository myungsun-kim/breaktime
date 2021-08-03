<template>
  <el-container>
    <el-header>Header</el-header>
    <el-container class="conference-box">
      <el-main>
        <conference-main/>
      </el-main>
      <el-aside width="300px" class="chat">
        <chat-side/>
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