import { createStore } from "vuex";
import root from '@/store';
// 로그인시에 user정보가 새로고침시 날라가는 현상 방지용 https://www.npmjs.com/package/vuex-persistedstate
import createPersistedState from "vuex-persistedstate";

export default createStore({
  modules: { root },
  plugins: [createPersistedState()],
});