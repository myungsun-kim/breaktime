<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header d-flex justify-content-between">
        <span>{{state.room.number}}. {{state.room.name}}</span>
        <span style="overflow:hidden white-space:nowrap">
          <span>{{roomKind()}}</span>
          <i v-if="state.room.password" class="el-icon-key key"></i>
        </span>
      </div>
    </template>
    <span>{{state.room.description}}</span>
    <el-button @click="goRoom">입장하기</el-button>
    <room-password-dialog
      :open="state.roomPasswordDialogOpen"
      :password="state.room.password"
      @closeroomPasswordDialog="onCloseroomPasswordDialog"/>
  </el-card>
</template>

<script>
import { reactive } from '@vue/reactivity'
import { useRouter } from 'vue-router'

import RoomPasswordDialog from './RoomPasswordDialog.vue'

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

    const roomKind = function() {
      const kind = state.room.conferenceCategory.sequence
      if (kind == 1) {
        return '공부방'
      } else if (kind == 2) {
        return '노래방'
      } else {
        return '이야기'
      }
    }

    // 특정방으로 이동한다.
    const goRoom = function () {
      if (state.room.password) {
        state.roomPasswordDialogOpen = true
      } else {
        router.push({name: 'Conference', params: { conferemceId : state.room.number }})
      }
    }

    const onCloseroomPasswordDialog = function () {
      state.roomPasswordDialogOpen = false
    }

    return {state, router, goRoom,  roomKind, onCloseroomPasswordDialog}
  }
}
</script>

<style scoped>
  .key {
    background-color: #E6A23C;
  }
</style>