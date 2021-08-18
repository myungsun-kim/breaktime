<template>
  <div>
    <router-view />
  </div>
  <!-- <Home /> -->

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
            user: result.data.user
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
@import url('https://fonts.googleapis.com/css2?family=Crete+Round&display=swap');


#app {
  font-family: 'Jua', sans-serif;
  font-weight: bold;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color:#333333;
  /* margin-top: 60px; */
}
</style>


