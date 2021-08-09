<template>
  <div class="home-bg">
    
  
    <img src="../../assets/logo.png" alt="" class="logo">
  
  
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

<style scoped>
  .home-bg {
    height: 100vh;
    background-color: #FFEEE4;
    
  }

  .logo {
    width: 200px;
    height: 200px;
    margin-top: 20px;
  }
</style>
