<template>
  <h1>회원가입</h1>
  <el-form :model="state.form">
    <el-form-item prop="id" label="아이디" >
      <el-input v-model="state.form.id" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item prop="password" label="비밀번호" >
      <el-input v-model="state.form.password" autocomplete="off" show-password></el-input>
    </el-form-item>
    <el-form-item prop="passwordCheck" label="비밀번호확인" >
      <el-input v-model="state.form.password" autocomplete="off" show-password></el-input>
    </el-form-item>
  </el-form>  
</template>

<script>
import { reactive } from '@vue/reactivity'
export default {
  name: 'SignUp',
  setup() {
    const state = reactive({
      form: {
        id: '',
        password: '',
        passwordCheck: '',
        email: '',
        phone: '',
        name: ''
      }
    })
    return {state }
  }
}
</script>