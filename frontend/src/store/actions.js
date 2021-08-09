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

// ID체크 관련 axios
export function requestCheckId({ /*state*/ }, payload) {
  const id = payload.id
  const url = `/user/${id}`
  return $axios.get(url)
}

// 핸드폰 번호 체크 관련 axios
export function requestCheckCnumber({ /*state*/}, payload) {
  const phone = payload.phone
  const url = `/user/${phone}`
  return $axios.get(url)
}

// 방생성 관련 axios
export function createRoom({ /*state*/ }, payload) {
  const url = '/conference/make'
  const token = localStorage.getItem('jwt')
  const instance = $axios.create({
    headers: {
      Authorization : "Bearer " + token
    }
  })
  let body = payload
  return instance.post(url, body)
}

// 방정보 관련 axios
export function getRoom({/*state*/}) {
  const url = '/conference/search/all'
  return $axios.get(url)
}

// 방검색 관련 axios
export function searchRoom({/*state*/}, payload) {
  const value = payload.value
  let input = payload.input
  let url = ''
  if (value === 1) {
    input = Number(input)
    url = `/conference/search/num/${input}`
  } else {
    url = `/conference/search/name/${input}`
  }
  return $axios.get(url)
}