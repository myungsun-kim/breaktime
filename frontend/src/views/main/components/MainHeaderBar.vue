<template>
<div>
  <h1>
    Hello {{user.name}} 
    <el-button type="danger" @click="clickLogOut" >로그아웃</el-button>
    <el-button type="info" @click="clickMyPage" >마이페이지</el-button>
  </h1>
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