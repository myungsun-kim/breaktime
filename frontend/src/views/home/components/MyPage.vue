<template>
  <div class="home-bg" float:left>
    <center>
    <br>
    
    <p>
      
      <label for="title"> <i class="el-icon-user-solid"></i> 정보 수정 </label>
      
    </p>
    <p>
      <label for="id" style="text-align:left"> ID {{user.userId}}  </label>
    </p>
    <p>
      <lable for="phone"> phone 0{{user.phone}} </lable>
    </p>
    <p>
      <label for="email"> email {{user.email}} </label>
    </p>
    <p>
      <label for="nickname"> NickName </label>
    &nbsp;
      <input type="text" id="nickname" v-model="state.name" name="nickname" required minlength="2" maxlenth="12" size="10"> 
    </p>
    
    <el-button class="button-login" @click="clickModify" > 정보수정 </el-button>
    <el-button class="button-signup" @click="clickDeleteUser" > 회원탈퇴 </el-button>
    <br>
    <br>
    </center>
  </div>
</template>

<script>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'

export default {
  name: 'MyPage',
  setup() {
    const store = useStore()
    const user = store.getters['root/getUserInfo']
    // 유저 정보 받아오기
    console.log(user)
    const router = useRouter()

    const state = reactive({
      name : user.name,
      phone : user.phone
    })
    
    const clickModify = function () {
          store.dispatch('root/modifyUserInfo', { 
              name: state.name
            }
          )
          .then(function (result) {
            console.log(result)
            // router.push({name: 'Home'})
          })
          .catch(function (err) {
            alert(err.response.data.message)
          })
        }
        
    const clickDeleteUser = function () {
      store.dispatch('root/deleteUserInfo', {
        id: user.id
        }
      )
      // 성공하면 then
      // 실패하면 catch
      .then(function (result) {
        localStorage.removeItem('user') // key 에 맞는 값을 삭제한다.
        // localStorage.clear() 로컬 스토리지에 저장된 모든 값을 삭제한다.
        // 모두 삭제해야하는지? 혹은 특정 키값(유저) ?

        router.push({name: 'Login'})
        // 삭제 후 로그인으로 이동
        console.log(result)
      })
      .catch(function (err) {
            alert(err.response.data.message)
          })
    }
    return { state, clickModify, clickDeleteUser,  store, router ,user} 
  }       // 상태 , 수정폼, 클릭수정 , 저장, 라우터, ID 체크 , 인증번호 체크
}         

  // 끝

</script>

<style scoped>

  .home-bg {
    margin : 30px;
    justify-content: center;
    text-align: left;
    
    
  }
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

  @media screen and (max-width: 600px) {
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