<template>
<div class="header" >
  <!-- <h1>
    Hello {{user.name}} 
    <el-button type="danger" @click="clickLogOut" >로그아웃</el-button>
  </h1> -->
  <div class="logo-breaktime">
    <img src="../../../assets/logo2_2.png" alt="" class="logo2">
    <!-- <img src="../../../assets/breaktime.png" alt="" class="breaktime"> -->
  </div>

  <div class="user">
    <span class="username">Hello {{user.nickname}}</span>
    
    <button class="logout-button" @click="clickLogOut" >로그아웃</button>
    <button class="mypage-button" @click="clickMyPage" >마이페이지</button>
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

    const clickMyPage = function () {
      router.push({name: 'Mypage'})
    }


    return { store, user, clickLogOut, clickMyPage, router}
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
  background-color: #F6F6F6;
}

.logo {
  width: 100px;
  height: 100px;
}

.logo2 {
  width: 200px;
  height: 80px;
  padding-left: 10px;
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

.mypage-button {
  font-size: 10px;
  border-radius: 5px;
  border: none;
  background-color: #dedcee;
  padding: 10px;
  margin-right: 10px;
}

</style>