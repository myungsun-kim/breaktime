<template>
  <div>
    <search-bar />
    <el-button @click="createRoom">방만들기</el-button>
    <li v-for="i in room" :key="i">
      <h1>{{i}}</h1>
      <room-card />
    </li>
  </div>
</template>

<script>
import { reactive } from 'vue'
import { useStore } from 'vuex'
import RoomCard from './mainhome/RoomCard.vue'
import SearchBar from './mainhome/SearchBar.vue'

export default {
  name: 'MainHome',
  components: {
    RoomCard,
    SearchBar,
  },

  setup(props, { emit }) {
    const store = useStore()

    const getRoom = function () {
      store.dispatch('root/getRoom')
        .then(function (result) {
          console.log(result.data)
          return result.data
        })
        .catch(function (err) {
          console.log(err)
      })
    }

    const state = reactive({
      room: getRoom(),
    })

    const createRoom = () => {
      emit('openCreateRoomDialog')
    }

    return { store, createRoom, state, getRoom }
  },
}
</script>