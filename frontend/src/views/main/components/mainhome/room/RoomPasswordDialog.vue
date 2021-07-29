<template>
  <el-dialog title="비밀방" v-model="state.dialogVisible" @close="handleClose" width="400px">
    <el-form ref="passwordForm" :model="state.form" :rules="state.rules" label-width="80px">
      <el-form-item prop="password" label="비밀번호:" >
        <el-input v-model="state.form.password" autocomplete="off" show-password @keyup.enter="goRoomEmit"></el-input>
      </el-form-item>
      <el-button @click="goRoomEmit">입장하기</el-button>
    </el-form>
  </el-dialog>
</template>

<script>
import { reactive, computed, ref } from 'vue'

export default {
  name: 'RoomPasswordDialog',

  props: {
    open: {
      type: Boolean,
      default: false
    },
    password: {
      type: String,
    }
  },

  setup(props, {emit}) {
    const passwordForm = ref(null)

    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('패스워드를 입력해주세요'))
      } else if (value !== props.password) {
        callback(new Error('비밀번호가 다릅니다'))
      } else {
        callback()
      }
    }
    const state = reactive({
      form: {
        password: '',
      },
      rules: {
        password: [
          { validator: validatePass, trigger: 'blur' }
        ]
      },
      dialogVisible: computed(() => props.open),
    })

    const handleClose = function () {
      state.form.password = ''
      emit('closeroomPasswordDialog')
    }

    const goRoomEmit = function () {
      passwordForm.value.validate((valid) => {
        if (valid) {
          emit('goRoomEmit')
          handleClose()
        }
      })
    }

    return {state, handleClose, passwordForm, validatePass, goRoomEmit}
  }
}
</script>