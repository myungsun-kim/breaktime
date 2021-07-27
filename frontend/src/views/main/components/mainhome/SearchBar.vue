<template>
  <el-input placeholder="Please input" v-model="input" class="input-with-select">
    <template #prepend>
      <el-select v-model="select" placeholder="Select">
        <el-option label="Restaurant" value="1"></el-option>
        <el-option label="Order No." value="2"></el-option>
        <el-option label="Tel" value="3"></el-option>
      </el-select>
    </template>
    <template #append>
      <el-button icon="el-icon-search"></el-button>
    </template>
  </el-input>
</template>

<script>
import { reactive, ref } from 'vue'
export default {
  name: 'SearchBar',
  setup() {
    return {
      input: ref(''),
      select: ref('')
    }
  }
}
</script>