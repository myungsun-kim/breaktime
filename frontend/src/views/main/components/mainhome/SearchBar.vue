<template>
<div>
  <el-input placeholder="Please input" v-model="state.input">
    <template #prepend>
      <el-select v-model="state.value" placeholder="Select">
        <el-option
          v-for="item in state.options"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
    </template>
    <template #append>
      <el-button icon="el-icon-search" @click="searchRoom"></el-button>
    </template>
  </el-input>
</div>
</template>

<script>
import { reactive } from 'vue'

export default ({
  name: 'SearchBar',
  setup(props, { emit }) {
    const state = reactive({
      options: [{
          value: 1,
          label: '방번호'
        }, {
          value: 2,
          label: '방제목'
      }],
      value: 1,
      input: ''
    })
    const searchRoom = function () {
      emit('searchRoom', state)
    }
    return { state, searchRoom }
  }
})
</script>

<style scoped>
  .el-select {
    width: 110px;
  }
</style>