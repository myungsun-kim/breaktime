<template>
  <h2>로그인</h2>
  <el-form :model="state.form" :rules="state.rules" ref="loginForm">
    <el-form-item prop="id" label="아이디" >
      <el-input v-model="state.form.id" autocomplete="off" placeholder="ID를입력해주세요"></el-input>
    </el-form-item>
    <el-form-item prop="password" label="비밀번호" >
      <el-input v-model="state.form.password" autocomplete="off" show-password placeholder="PW를입력해주세요"></el-input>
    </el-form-item>
  </el-form>
  <el-button type="primary" @click="clickLogin" >로그인</el-button>
  <el-button type="danger" @click="clickSignUp" >회원가입</el-button>
</template>

<script>
import { reactive, ref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'Login',
  setup() {
    const store = useStore()
    const router = useRouter()
    const loginForm = ref(null)

    const state = reactive({
      form: {
        id: '',
        password: '',
      },
      rules: {
        // 콘솔창 waring을 어떻게 제거할까..
        id:[
          { required: true, message: '필수 입력 항목입니다', trigger: 'blur' },
          {
            validator(rule, value) {
              var errors = []
              if (16 < value.length){
                errors = ['최대 16자까지 입력 가능합니다.']
              }
              return errors;
            },
          }
        ],
        password: [
          { required: true, message: '필수 입력 항목입니다.', trigger: 'blur' },
                    {
            validator(rule, value) {
              var errors = []
              var num = value.search(/[0-9]/g);
              var eng = value.search(/[a-z]/ig);
              var spe = value.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
              if (value.length < 9){
                errors = ['최소 9글자를 입력해야 합니다.']
              } else if (16 < value.length){
                errors = ['최대 16 글자까지 입력가능합니다.']
              } else if (num < 0 || eng < 0 || spe < 0) {
                errors = ['비밀번호는 영문, 숫자, 특수문자가 조합되어야합나다.']
              }
              console.clear()
              return errors;
            },
          }
        ]
      },
    })

    const clickLogin = function () {
      // 로그인 클릭 시 validate 체크 후 그 결과 값에 따라, 로그인 API 호출 또는 경고창 표시
      loginForm.value.validate((valid) => {
        if (valid) {
          store.dispatch('root/requestLogin', { id: state.form.id, password: state.form.password })
          .then(function (result) {
            localStorage.setItem('jwt', result.data.accessToken)
          })
          .catch(function (err) {
            alert(err)
          })
        } else {
          alert('조건에 맞게 넣으세요ㅡㅡ')
        }
      });
    }

    const clickSignUp = function () {
      // console.log('회원가입버튼')
      router.push('SignUp')
    }
    return { state , clickLogin, loginForm, clickSignUp}
  }
}
</script>