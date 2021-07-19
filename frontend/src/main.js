import { createApp } from 'vue'
import ElementPlus from 'element-plus';
import VueAxios from './common/lib/axios'
import axios from './common/lib/axios'
import store from './common/lib/store'
import router from './common/lib/vue-router'
import App from './App.vue'

import 'element-plus/lib/theme-chalk/index.css';

const app = createApp(App)
app.use(VueAxios, axios)
app.use(ElementPlus)
app.use(store)
app.use(router)
app.mount('#app')
