<template>
  <el-container>
    <el-header>
      <main-header-bar/>
    </el-header>
    <el-container>
      <el-aside width="200px">
        <main-side-bar/>
      </el-aside>
      <el-main>
        <main-home
        @openCreateRoomDialog="onOpenCreateRoomDialog"/>
      </el-main>
    </el-container>
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
  }
}
</script>