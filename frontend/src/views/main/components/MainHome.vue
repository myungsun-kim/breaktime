<template>
  <div>
    <el-button @click="createRoom">방만들기</el-button>
  </div>
</template>

<script>
import { useStore } from 'vuex'

export default {
  name: 'MainHome',
  setup(props, { emit }) {
    const store = useStore()

    const createRoom = () => {
      emit('openCreateRoomDialog')
    }

    return { store, createRoom }
  },
  created() {
    this.store.dispatch('root/getRoom')
    .then(function (result) {
      console.log(result)
    })
    .catch(function (err) {
      console.log(err)
    })
  }
}
</script>