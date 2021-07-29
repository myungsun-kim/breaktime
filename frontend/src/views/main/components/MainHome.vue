<template>
  <div>
    <search-bar @searchRoom="searchRoom"/>
    <el-button @click="createRoom">방만들기</el-button>
    <div class="container">
      <div class="row room">
        <div v-for="item in state.room" :key="item" class="col-12 col-sm-6 col-md-4 col-lg-3">
          <room-card :item="item"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity'
// import { reactive, computed } from 'vue'
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

    const getRoom = function () {
      store.dispatch('root/getRoom')
      .then(function (result) {
        state.room = result.data
      })
      .catch(function (err) {
        console.log(err)
      })
    }

    getRoom()

    const searchRoom = function (value) {
      console.log(value)
    }

    const createRoom = () => {
      emit('openCreateRoomDialog')
    }

    return { store, createRoom, state, getRoom, searchRoom }
  },
}
</script>

<style scoped>
  .room {
    margin-bottom: 10px;
  }
</style>