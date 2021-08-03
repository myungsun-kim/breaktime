import { createWebHistory, createRouter } from 'vue-router';
import Home from '@/views/home/Home.vue'
import SignUp from '@/views/home/components/SignUp.vue'
import Main from '@/views/main/Main.vue'
import Comference from '@/views/conference/Conference.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    beforeEnter: function (to, from, next) {
      if (localStorage.getItem('jwt')) {
        next({name:'Main'})
      } else {
        next();
      }
    }
  },
  {
    path: '/signup',
    name: 'Signup',
    component: SignUp,
    beforeEnter: function (to, from, next) {
      if (localStorage.getItem('jwt')) {
        next({name:'Main'})
      } else {
        next();
      }
    }
  },
  {
    path: '/main',
    name: 'Main',
    component: Main,
    // url로 접근하는것을 방지, 참고 : https://jamong-icetea.tistory.com/221
    beforeEnter: function (to, from, next) {
      if (localStorage.getItem('jwt')) {
        next()
      } else {
        next({name:'Home'});
      }
    }
  },
  {
    path: '/conferences/:conferemceId',
    name: 'Conference',
    component: Comference,
    beforeEnter: function (to, from, next) {
      if (localStorage.getItem('jwt')) {
        next()
      } else {
        next({name:'Home'});
      }
    }
  },
]


const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router