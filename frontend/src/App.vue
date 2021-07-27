<template>
  <!-- <Home /> -->
  <router-view />
</template>

<script>
// import Home from './views/home/Home.vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'App',
  components: {
    // Home,
  },
  created() {
    const store = useStore()
    const router = useRouter()
    const token = localStorage.getItem('jwt')
    if (token) {
      store.dispatch('root/requestUserInfo', {
          headers: {
            Authorization : "Bearer " + token
          }
        })
        .then(function (result) {
          store.commit('root/setUser', {
            user: result.data
          })
          router.push({name: 'Main'})
        })
        .catch(function (err) {
          alert(err)
      })
    }
  }
}
</script>

<style>

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
