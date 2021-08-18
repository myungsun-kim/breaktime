import $axios from 'axios'

// 로그인관련  axios
export function requestLogin ({ /*state*/ }, payload) {
  const url = '/auth/login'
  let body = payload
  return $axios.post(url, body)
}

// 토큰으로 회원정보 받기 
export function requestUserInfo ({ /*state*/ }, payload) {
  const url = '/user'
  const token = localStorage.getItem('jwt')
  const instance = $axios.create({
    headers: {
      Authorization : "Bearer " + token
    }
  })
  let body = payload
  return instance.get(url, body)
}

// 회원가입 관련 axios
export function requestSignUp ({ /*state*/ }, payload) {
  const url = '/user'
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
export function requestCheckCNumber({ /*state*/}, payload) {
  const phone = payload.phone
  const url = `/auth/${phone}`
  return $axios.post(url)
}

// 회원 정보 수정 관련 axios
// 변경사항
// mutation.js, getters.js MyPage 적용법
export function modifyUserInfo ({ /*state*/}, payload) {
  const url = `/user/modify`
  const token = localStorage.getItem('jwt')
  const instance = $axios.create({
    headers: {
      Authorization : "Bearer " + token
    }
  })
  let body = payload
  return instance.patch(url, body)
}


// 회원 정보 삭제 관련 axios
// const url = `/user/${payload.id}` -> const url = `/user` 로 변경
export function deleteUserInfo ({ /*satate*/}, payload) {
  const url = `/user/${payload.id}`
  const token = localStorage.getItem('jwt')
  const instance = $axios.create({
    headers: {
      Authorization : "Bearer " + token
    }
  })
  let body = payload
  return instance.delete(url)
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

// 방삭제 관련 axios
export function deleteRoom({/*state*/}, payload) {
  const sequence = payload.sequence
  const url = `/conference/delete/${sequence}`
  const token = localStorage.getItem('jwt')
  const instance = $axios.create({
    headers: {
      Authorization : "Bearer " + token
    }
  })
  return instance.get(url)
}