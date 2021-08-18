<template>
  <el-container class="main">
    <el-header height="100%" class="p-0"> 
      <main-header-bar/>
    </el-header>
    <el-container>
      <el-main>
        <main-home
        @openCreateRoomDialog="onOpenCreateRoomDialog"/>
      </el-main>
    </el-container>
    <el-footer class="footer pt-2 pb-4" height="100%">
      <i class="fab fa-github github-icon fa-3x pt-3 pb-4 px-3" ></i>
      <i class="fab fa-gitlab github-icon fa-3x pt-3 pb-4"></i>
      <br>
      <span>Frontend : 
        <a href="https://github.com/kaso5552">SoonHyeon, </a>
        <a href="https://github.com/HandyQ">KyuJin</a>
      </span> 
      <br>
      <span>Backend : 
        <a href="https://github.com/myungsun-kim">MyungSun, </a>
        <a href="https://github.com/dilution0216">HuiSeok, </a>
        <a href="https://github.com/BEOMKING">BeomJin  </a>
      </span>
    </el-footer>
    <create-room-dialog
      :open="createRoomDialogOpen"
      @closeCreateRoomDialog="onCloseCreateRoomDialog"/>
  </el-container>
  
</template>

<script>
import MainSideBar from './components/MainSideBar.vue'
import MainHeaderBar from './components/MainHeaderBar.vue'
import MainHome from './components/MainHome.vue'
import CreateRoomDialog from './components/mainhome/CreateRoomDialog.vue'


export default {
  name: 'Main',
  components: {
    MainSideBar,
    MainHeaderBar,
    MainHome,
    CreateRoomDialog,
  },
  data () {
    return {
      createRoomDialogOpen: false,
    }
  },
  methods: {
    onOpenCreateRoomDialog () {
      this.createRoomDialogOpen = true
    },
    onCloseCreateRoomDialog () {
      this.createRoomDialogOpen = false
    }
  },
}
</script>

<style scoped>  
  /* .main {
    background-color: #FFEEE4;
  } */

  .footer {
    background-color: #F6F6F6;
    color: gray;
    font-weight: lighter;
  }

  .footer a {
    text-decoration-line: none;
    color: gray;
  }

  .github-icon {
    color: black;
  }
</style>