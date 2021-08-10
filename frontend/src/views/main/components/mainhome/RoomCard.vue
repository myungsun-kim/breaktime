<template>
  <el-card v-if="props.item" class="box-card">
    <template #header>
      <div class="d-flex justify-content-between">
        <span>{{state.room.number}}. {{state.room.name}}</span>
        <span style="overflow:hidden white-space:nowrap">
          <i v-if="state.room.conferenceCategory.sequence === 1" class="el-icon-notebook-1"></i>
          <i v-else-if="state.room.conferenceCategory.sequence === 2" class="el-icon-microphone"></i>
          <i v-else class="el-icon-user"></i>
          <i v-if="state.room.password" class="el-icon-key key"></i>
        </span>
      </div>
    </template>
    <div class="d-flex flex-column align-items-center">
      <span>{{state.room.description}}</span>
      <el-button @click="goRoom">입장하기</el-button>
    </div>
    <room-password-dialog
      :open="state.roomPasswordDialogOpen"
      :password="state.room.password"
      @closeroomPasswordDialog="onCloseroomPasswordDialog"
      @goRoomEmit="goSecretRoom"/>
  </el-card>
  <h2 v-else>방이없습니다!!!</h2>
</template>

<script>
import { reactive } from '@vue/reactivity'
import { useRouter } from 'vue-router'

import RoomPasswordDialog from './room/RoomPasswordDialog.vue'

export default {
  name: 'RoomCard',
  components: {
    RoomPasswordDialog,
  },
  props: {
    item: Object
  },
  setup (props) {
    const router = useRouter()
    const state = reactive({
      room: {
        number: props.item.sequence,
        name: props.item.name,
        owner: props.item.owner,
        participantLimit: props.item.participantLimit,
        password: props.item.password,
        conferenceCategory: props.item.conferenceCategory,
        description: props.item.description
      },
      roomPasswordDialogOpen: false
    })

    // 특정방으로 이동한다.
    const goRoom = function () {
      if (state.room.password) {
        state.roomPasswordDialogOpen = true
      } else {
        // replace는 뒤로가기를 남기지않음
        router.replace({name: 'Conference', params: { 
          conferenceId : state.room.number,
          owner: state.room.owner
        }})
      }
    }

    const goSecretRoom = function () {
      router.replace({name: 'Conference', params: { 
        conferenceId : state.room.number,
        owner: state.room.owner
      }})
    }

    const onCloseroomPasswordDialog = function () {
      state.roomPasswordDialogOpen = false
    }

    return {props, state, router, goRoom, onCloseroomPasswordDialog, goSecretRoom}
  }
}
</script>

<style scoped>
  .key {
    background-color: #E6A23C;
  }
</style>