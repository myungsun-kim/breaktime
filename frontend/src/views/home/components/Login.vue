<template>
  <div class="form">
    <el-form :model="state.form" :rules="state.rules" ref="loginForm" label-width="5.5rem">
      <el-form-item prop="id" label="아이디" >
        <el-input v-model="state.form.id" autocomplete="off" placeholder="ID를입력해주세요"></el-input>
      </el-form-item>
      <el-form-item prop="password" label="비밀번호" >
        <el-input v-model="state.form.password" autocomplete="off" show-password placeholder="PW를입력해주세요"
        @keyup.enter="clickLogin"></el-input>
      </el-form-item>
      <el-button class="button-login" @click="clickLogin" >로그인</el-button>
      <br>
      <div class="signup">
        <span>계정이 없으신가요? </span>
        <el-button size="xsmall" class="button-signup" @click="clickSignUp" >가입하기</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import { computed, reactive, ref } from 'vue'
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
          { required: true, message: 'ID를 입력해주세요', trigger: 'blur' },
          {
            validator(rule, value) {
              var errors = []
              if (16 < value.length){
                errors = ['최대 16자까지 입력 가능합니다.']
              }
              console.clear()
              return errors;
            },
          }
        ],
        password: [
          { required: true, message: '비밀번호를 입력해주세요', trigger: 'blur' },
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
            const token = result.data.accessToken
            localStorage.setItem('jwt', token)
            saveUser(token)
          })
          .catch(function (err) {
            alert(err.response.data.message)
          })
        } else {
          alert('조건에 맞게 넣으세요ㅡㅡ')
        }
      });
    }

    const saveUser = function (token) {
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

    const clickSignUp = function () {
      // console.log('회원가입버튼')
      router.push('SignUp')
    }
    return { state , clickLogin, loginForm, clickSignUp, saveUser}
  }
}
</script>

<style scoped>


  .form {
    width: 400px;
    margin: 20px auto;
    background-color: #F6F6F6;
    background-size: 100vh;
    padding: 2rem;
    border-radius: 1rem;
    font-family: 'Sumflower', sans-serify;
  }

  .el-input {
    width: 100%;
  }

  .button-login {
    background-color: #dedcee;
  }
  
  .signup {
    font-size: smaller;
    margin-top: 10px;
  }
  .button-signup {
    color: #30A9DE;
    background-color: #F6F6F6;
    border: 0;
    outline: 0;
  }
</style>
