import $axios from 'axios'

// 로그인관련  axios
export function requestLogin ({ state }, payload) {
  console.log('requestLogin', state, payload)
  const url = '/login'
  let body = payload
  return $axios.post(url, body)
}

// 토큰으로 회원정보 받기 
export function requestUserInfo ({ state }, payload) {
  console.log('requestUserInfo', state, payload)
  const url = '/users/me'
  let body = payload
  return $axios.get(url, body)
}

// 회원가입 관련 axios
export function requestSignUp ({ state }, payload) {
  console.log('requestSignUp', state, payload)
  const url = '/signup'
  let body = payload
  return $axios.post(url, body)
}

// 방생성 관련 axios
export function createRoom({state}, payload) {
  console.log('requestSignUp', state, payload)
  const url = '/room'
  let body = payload
  return $axios.post(url, body)
}

// 방정보 관련 axios
export function getRoom({state}) {
  console.log(state)
  const url = '/conference/search/all'
  return $axios.get(url)
}