<template>
<div>
  <h1>Hello {{user.name}} </h1>
  <el-button type="danger" @click="clickLogOut" >로그아웃</el-button>
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

    console.log(user)

    return { store, user, clickLogOut, router}
  }
}
</script>