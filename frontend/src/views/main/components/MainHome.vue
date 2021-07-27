<template>
  <div>
    <search-bar />
    <el-button @click="createRoom">방만들기</el-button>
    <li v-for="i in room" :key="i">
      <h1>i</h1>
      <room-card />
    </li>
  </div>
</template>

<script>
import { reactive, onMounted } from 'vue'
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

    const state = reactive({
      room: [],
    })

    onMounted(() => {
      store.dispatch('root/getRoom')
        .then(function (result) {
          state.room = result.data
        })
        .catch(function (err) {
          console.log(err)
        })
    })

    const createRoom = () => {
      emit('openCreateRoomDialog')
    }

    return { store, createRoom, state }
  },

  created() {
    
  }
}
</script>