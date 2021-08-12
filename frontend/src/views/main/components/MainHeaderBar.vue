<template>
<div class="header">
  <!-- <h1>
    Hello {{user.name}} 
    <el-button type="danger" @click="clickLogOut" >로그아웃</el-button>
  </h1> -->
  <div class="logo-breaktime">
    <img src="../../../assets/logo_cat.png" alt="" class="logo">
    <img src="../../../assets/breaktime.png" alt="" class="breaktime">
  </div>

  <div class="user">
    <span class="username">Hello {{user.name}}</span>
    
    <button class="logout-button" @click="clickLogOut" >로그아웃</button>
  </div>
</div>
</template>

<script>
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'MainHeaderBar',
  setup() {
    const store = useStore()
    const router = useRouter()

    const user = store.getters['root/getUserInfo']

    const clickLogOut = function () {
      localStorage.removeItem('jwt')
      router.push({name: 'Home'})
    }


    return { store, user, clickLogOut, router}
  }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Crete+Round&display=swap');

.header {
  font-family: 'Jua', sans-serif;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo {
  width: 100px;
  height: 100px;
}

.breaktime {
  width: 200px;
  height: 50px;
}

.username {
  margin-right: 10px;
}

.logout-button {
  
  font-size: 10px;
  border-radius: 5px;
  border: none;
  background-color: #FFEEE4;
  padding: 10px;
}

</style>