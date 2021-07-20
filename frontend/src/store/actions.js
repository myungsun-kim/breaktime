import $axios from 'axios'

// 로그인관련  axios
export function requestLogin ({ state }, payload) {
  console.log('requestLogin', state, payload)
  const url = '/login'
  let body = payload
  return $axios.post(url, body)
}

// 회원가입 관련 axios
export function requestSignUp ({ state }, payload) {
  console.log('requestSignUp', state, payload)
  const url = '/signup'
  let body = payload
  return $axios.post(url, body)
}