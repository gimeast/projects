import { refreshToken } from './auth.js'

// API 요청을 위한 함수
export const fetchWithToken = async (url, options = {}) => {
  // 옵션에 헤더가 없으면 빈 객체 생성
  options.headers = options.headers || {}

  // 로컬 스토리지에서 액세스 토큰 가져오기
  let accessToken = localStorage.getItem('accessToken')

  // 요청 헤더에 액세스 토큰 추가
  if (accessToken) {
    options.headers['Authorization'] = accessToken
  }

  // API 요청 보내기
  let response = await fetch(url, options)

  // 액세스 토큰이 만료된 경우
  if (response.status === 403) {
    // 토큰 갱신
    accessToken = await refreshToken()

    // 갱신된 토큰으로 헤더 업데이트
    options.headers['Authorization'] = accessToken

    // 원래 요청 다시 시도
    response = await fetch(url, options)
  }

  return response
}
