<template>
  <div class="home-bg">
    <h1>회원가입</h1>
    <el-form :model="state.form" :rules="state.rules" ref="signUpForm" label-width="7.5rem" class="form">
      <el-form-item prop="id" label="아이디">
        <el-input v-model="state.form.id" autocomplete="off">
          <template #append>
            <el-button @click='checkId'>ID중복확인</el-button>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password" label="비밀번호" >
        <el-input v-model="state.form.password" autocomplete="off" show-password></el-input>
      </el-form-item>
      <el-form-item prop="passwordCheck" label="비밀번호확인" >
        <el-input v-model="state.form.passwordCheck" autocomplete="off" show-password></el-input>
      </el-form-item>
      <el-form-item prop="name" label="이름" >
        <el-input v-model="state.form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="email" label="이메일(선택)" >
        <el-input v-model="state.form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="phone" label="휴대폰" >
        <el-input v-model="state.form.phone" autocomplete="off" placeholder="-없이 입력해주세요">
          <template #append>
            <el-button >인증번호전송</el-button>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="CNumber" label="인증번호" >
        <el-input v-model="state.form.CNumber" autocomplete="off"></el-input>
      </el-form-item>
      <el-button type="danger" @click="clickSignUp">회원가입</el-button>
    </el-form>  
  </div>
</template>

<script>
import { reactive, ref } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'SignUp',
  setup() {
    const store = useStore()
    const router = useRouter()
    const signUpForm = ref(null)

    var validatePass = (rule, value, callback) => {
      var num = value.search(/[0-9]/g);
      var eng = value.search(/[a-z]/ig);
      var spe = value.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
      if (value.length < 9){
        return callback(new Error('최소 9글자를 입력해야 합니다.'))
      } else if (16 < value.length){
        return callback(new Error('최대 16 글자까지 입력가능합니다.'))
      } else if (num < 0 || eng < 0 || spe < 0) {
        return callback(new Error('비밀번호는 영문, 숫자, 특수문자가 조합되어야합니다.'))
      } else {
        callback()
      }
    }

    var validatePass2 = (rule, value, callback) => {
      if (value !== state.form.password ){
        return callback(new Error('비밀번호가 다릅니다'))
      } else{
        callback()
      }
    }

    var validateId = (rule, value, callback) => {
      if (value === '') {
        return callback(new Error('ID를 입력해주세요.'))
      } else if (16 < value.length) {
        return callback(new Error('ID는 최대 16자까지 가능합니다.'))
      } else if (value !== state.checkId) {
        return callback(new Error('ID중복검사를 실시해주세요'))
      } else {
        callback()
      }
    }

    const state = reactive({
      form: {
        id: '',
        password: '',
        passwordCheck: '',
        email: '',
        phone: '',
        name: '',
        CNumber: '',
      },
      checkId: '',
      rules: {
        id: [
          { required: true, validator: validateId, trigger: 'blur'}
        ],
        password: [
          { required: true, validator: validatePass, trigger: 'blur'}
        ],
        passwordCheck: [
          { required: true, validator: validatePass2, trigger: 'change'}
        ],
        name: [
          { required: true, message: '이름을 입력해주세요',trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '핸드폰번호를 입력해주세요',trigger: 'blur'}
        ],
        CNumber: [
          { required: true, message: '인증번호를 입력해주세요',trigger: 'blur'}
        ]
      },
    })

    const checkId = function () {
      store.dispatch('root/requestCheckId', {id: state.form.id})
      .then(function (result) {
        state.checkId = state.form.id
        alert('사용가능한 ID 입니다')
      })
      .catch(function (err) {
        alert(err.response.data.message)
      })
    }

    const clickSignUp = function () {
      // 회원가입 유효성검사후 axios 요청(store - actions으로)
      signUpForm.value.validate((valid) => {
        if (valid) {
          store.dispatch('root/requestSignUp', { 
              id: state.form.id, 
              password: state.form.password,
              passwordCheck: state.form.passwordCheck,
              email: state.form.email,
              phone: state.form.phone,
              name: state.form.name
            }
          )
          .then(function (result) {
            router.push({name: 'Home'})
          })
          .catch(function (err) {
            alert(err.response.data.message)
          })
        } else {
          alert('조건에 맞게 넣으세요ㅡㅡ')
        }
      });
    }
    return { state, signUpForm, clickSignUp, store, router, checkId}
  }
}
</script>

<style scoped>
  .form {
    width: 50%;
    margin: 0 25%;
    background-color: whitesmoke;
    padding: 1rem;
    border-radius: 1rem;
  }
  @media (max-width: 800px) {
    .form {
      width: 80%;
      margin: 0 10%;
    }
  }
  
  .home-bg {
    height: 80vh;
    background: url('../../../assets/coffee.jpg');
  }

</style>