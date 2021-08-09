<template>
  <div class="home-bg">
    <img src="../../../assets/logo.png" alt="" class="logo">
    <el-form :model="state.form" :rules="state.rules" ref="signUpForm" label-width="7.5rem" class="form">
      <el-form-item prop="id" label="아이디" class="formIn">
        <el-input v-model="state.form.id" autocomplete="off">
          <template #append>
            <el-button @click='checkId'>ID중복확인</el-button>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password" label="비밀번호" class="formIn" >
        <el-input v-model="state.form.password" autocomplete="off" show-password></el-input>
      </el-form-item>
      <el-form-item prop="passwordCheck" label="비밀번호확인" class="formIn">
        <el-input v-model="state.form.passwordCheck" autocomplete="off" show-password></el-input>
      </el-form-item>
      <el-form-item prop="name" label="이름" class="formIn">
        <el-input v-model="state.form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="email" label="이메일(선택)" class="formIn">
        <el-input v-model="state.form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="phone" label="휴대폰" class="formIn">
        <el-input v-model="state.form.phone" autocomplete="off" placeholder="-없이 입력">
          <template #append>
            <el-button class="phone_certify">인증번호전송</el-button>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="CNumber" label="인증번호" class="formIn">
        <el-input v-model="state.form.CNumber" autocomplete="off"></el-input>
      </el-form-item>
      <el-button type="info" @click="clickSignUp">회원가입</el-button>
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
  // 인증번호 유효성검사
    // 검증하기
    // 
    // rule , value callback 기본값 (있어야 함수 정의 가능)
    // 사용 프레임워크 엘리먼트 플러스
    var validateCnumber = (rule, value, callback) => {
      if (value === '') {
        return callback(new Error('인증번호를 입력해주세요.'))
      }
      else if (value !==  state.form.CNumber) {
        return callback(new Error('잘못된 인증번호 입니다.'))
      } else{
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
          { required: true, validator: validateCnumber ,trigger: 'blur'}
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

    // 변경사항
    // 목표기능
    // Backend 에서 return 받은 random 값이, 사용자가 입력한 값과 같은지 검증 후
    // 맞으면 -> 휴대폰 인증 완료
    // 틀리면 -> " 인증번호가 올바르지 않습니다. " 출력


    
    
    const checkCnumber = function () {
      store.dispatch('root/requestCheckCNumber', {phone: state.form.phone})
      .then(function (result) {
        // state.checkCNumber = state.form.CNumber
        console.log(result) 
        alert('인증이 완료되었습니다.')
      })
      .catch(function (err) {
       alert(' 잘못된 인증번호 입니다. 다시 확인하세요.')})
    }




    // 변경사항 끝
    // 물어볼 것
    // 유효성검사에 CNumber 추가해야하는지?
    // Back 에서 return 받은 random 값을 CNumber 로 받아오고, 인식하려면?



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
    return { state, signUpForm, clickSignUp, store, router, checkId, checkCnumber} 
  }
}
</script>

<style scoped>
  


  .form {
    /* width: 50%;
    margin: 100px;
    background-color: whitesmoke;
    padding: 1rem;
    border-radius: 1rem; */
    
    margin: 20px auto;
    width: 450px;
    padding: 50px;
    border-radius: 30px;
    background-color: #F6F6F6;
    font-family: 'Noto Sans KR', sans-serif;
  }

  .formIn {
    text-align:center;  
  }

  @media screen and (max-width: 800px) {
    .form {
      width: 80%;
      margin: 10%;
    }
  }

  .logo {
    width: 200px;
    height: 200px;
    margin-top: 20px;
  }
  

  .home-bg {

    background-color: #FFEEE4;
    height: 100%;
    
  }

  .phone_certify {
    width: 110px;
    text-align: center;
  }


</style>