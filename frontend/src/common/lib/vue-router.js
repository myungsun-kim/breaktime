import { createWebHistory, createRouter } from 'vue-router';
import Home from '@/views/home/Home.vue'
import SignUp from '@/views/home/components/SignUp.vue'
import Main from '@/views/main/Main.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/signup',
    name: 'Signup',
    component: SignUp,
  },
  {
    path: '/main',
    name: 'Main',
    component: Main
  }
]


const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router