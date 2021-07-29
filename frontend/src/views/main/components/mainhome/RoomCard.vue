<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <span>{{state.room.number}}. {{state.room.name}}</span>
      </div>
    </template>
    <el-button @click="goRoom">입장하기</el-button>
  </el-card>
</template>

<script>
import { reactive } from '@vue/reactivity'
import { useRouter } from 'vue-router'

export default {
  name: 'RoomCard',
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
        password: props.item.password
      }
    })

    const goRoom = function () {
      router.push({name: 'Conference', params: { conferemceId : state.room.number }})
    }
    
    return {state, router, goRoom}
  }
}
</script>

<style scoped>

</style>