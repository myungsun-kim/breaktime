<template>
  <el-dialog title="방만들기" v-model="state.dialogVisible"  @close="handleClose" width="400px">
    <el-form ref="createRoomForm" :model="state.form" :rules="state.rules" label-width="80px">
      <el-form-item label="방제목" prop="name">
        <el-input v-model="state.form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="방설명" prop="description">
        <el-input type="textarea" show-word-limit maxlength="30" v-model="state.form.description" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="제한인원" prop="participant_limit">
        <el-input-number v-model="state.form.participant_limit" :min="1" :max="10"></el-input-number>
      </el-form-item>
      <el-form-item label="방종류" prop="option">
        <el-select v-model="state.form.option" placeholder="Select">
          <el-option
            v-for="item in state.options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select> 
      </el-form-item>
      <el-form-item label="비밀방">
        <el-switch v-model="state.value"></el-switch>
      </el-form-item>
      <el-form-item v-if="state.value" prop="password" label="비밀번호" >
        <el-input v-model="state.form.password" autocomplete="off" show-password></el-input>
      </el-form-item>
      <hr>
      <el-button @click="createRoom">생성하기</el-button>
    </el-form>
  </el-dialog>
</template>

<script>
import { reactive, computed, ref } from 'vue'
import { useStore } from 'vuex'
export default {
  name: 'CreateRoomDialog',

  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const createRoomForm = ref(null)
    const store = useStore()
    const user = store.getters['root/getUserInfo']

    const state = reactive({
      form: {
        name: '',
        description: '',
        participant_limit: 1,
        option: 1,
        password: '',
      },
      rules: {
        name: [
          { required: true, message: '방제를입력해주세요', trigger: 'blur'},
          { min: 2, max: 10, message: '2 ~ 10글자까지 가능합니다', trigger: 'blur'}
        ],
        password: [
          { required: true, message: '비밀번호를 입력해주세요', trigger: 'blur'},
        ],
      },
      options: [{
          value: 1,
          label: '1.공부방'
        }, {
          value: 2,
          label: '2.노래방'
        }, {
          value: 3,
          label: '3.이야기'
      }],
      value: false,
      dialogVisible: computed(() => props.open),
    })

    const createRoom = function () {
      createRoomForm.value.validate((valid) => {
        if (valid) {
          if (!state.value) {
            state.form.password = ''
          }
          store.dispatch('root/createRoom', {
            category_seq: state.form.option,
            description: state.form.description,
            name: state.form.name,
            owner: user.userId,
            password: state.form.password,
            participantLimit: state.form.participant_limit,
          })
          .then(function (result) {
            console.log(result)
            handleClose()
          })
          .catch(function (err) {
            alert(err)
          })
        } else {
          alert('조건에 맞게 넣으세요ㅡㅡ')
        }
      });
    }

    const handleClose = function () {
      state.form.name = ''
      state.form.participant_limit = 0
      state.form.option = ''
      state.form.password = ''
      emit('closeCreateRoomDialog')
    } 

    return { state, handleClose, createRoomForm, createRoom, store, user}
  },
}
</script>