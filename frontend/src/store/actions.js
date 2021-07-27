import $axios from 'axios'

// 로그인관련  axios
export function requestLogin ({ /*state*/ }, payload) {
  const url = '/auth/login'
  let body = payload
  return $axios.post(url, body)
}

// 토큰으로 회원정보 받기 
export function requestUserInfo ({ /*state*/ }, payload) {
  const url = '/user/me'
  let body = payload
  return $axios.get(url, body)
}

// 회원가입 관련 axios
export function requestSignUp ({ /*state*/ }, payload) {
  const url = '/user/signup'
  let body = payload
  return $axios.post(url, body)
}

// 방생성 관련 axios
export function createRoom({ /*state*/ }, payload) {
  const url = '/conference/make'
  let body = payload
  return $axios.post(url, body)
}

// 방정보 관련 axios
export function getRoom({/*state*/}) {
  const url = '/conference/search/all'
  return $axios.get(url)
}