<template>
  <div class="home-bg">
    
    <h1>
      <img src="../../assets/latte.png" alt="" class="logo">
      BreakTime
    </h1>
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
    height: 90vh;
    background: url('../../assets/coffee.jpg');
  }

  .logo {
    width: 50px;
    height: 50px;
  }
</style>
