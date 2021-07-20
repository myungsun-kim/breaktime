<template>
  <el-container>
    <el-header>
      <main-header-bar/>
    </el-header>
    <el-container>
      <el-aside width="200px">
        <main-side-bar/>
      </el-aside>
      <el-main>
        <main-home/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import MainSideBar from './components/MainSideBar.vue'
import MainHeaderBar from './components/MainHeaderBar.vue'
import MainHome from './components/MainHome.vue'

export default {
  name: 'Main',
  components: {
    MainSideBar,
    MainHeaderBar,
    MainHome,
  }
}
</script>