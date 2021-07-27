<template>
  <div>
    <h1>쉬는시간</h1>
    <Login />
    <router-view></router-view>
  </div>
</template>

<script>
import Login from './components/Login.vue'

export default {
  name: 'Home',
  components: {
    Login,
  },
  setup() {
    
  },
}
</script>
