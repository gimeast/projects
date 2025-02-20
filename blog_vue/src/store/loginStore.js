import { defineStore } from 'pinia'
export const useLoginStore = defineStore('login', {
  state: () => ({
    accessToken: localStorage.getItem('accessToken') || null,
    refreshToken: localStorage.getItem('refreshToken') || null,
    user: JSON.parse(localStorage.getItem('user')) || null,
    isAuthenticated: !!localStorage.getItem('accessToken'),
  }),
  actions: {
    async login(email, password) {
      try {
        const response = await fetch('http://localhost:8081/api/v1/user/login', {
          method: 'POST',
          body: JSON.stringify({ email, password }),
        })

        if (!response.ok) {
          throw new Error('Login failed')
        }

        const data = await response.json()
        const { accessToken, refreshToken, user } = data

        this.accessToken = accessToken
        this.refreshToken = refreshToken
        this.user = user
        this.isAuthenticated = true

        // 토큰과 사용자 정보를 로컬 스토리지에 저장
        localStorage.setItem('accessToken', accessToken)
        localStorage.setItem('refreshToken', refreshToken)

        await this.fetchUser()
      } catch (error) {
        console.error('로그인 중 오류 발생:', error)
        this.isAuthenticated = false
        this.accessToken = null
        this.refreshToken = null
      }
    },
    async fetchUser() {
      try {
        const response = await fetch('http://localhost:8081/api/v1/user/me', {
          method: 'GET',
          headers: {
            Authorization: `${this.accessToken}`,
          },
        })

        if (!response.ok) {
          throw new Error('Failed to fetch user')
        }

        const user = await response.json()
        this.user = user

        localStorage.setItem('user', JSON.stringify(user))
      } catch (error) {
        console.error('사용자 정보를 가져오는 중 오류 발생:', error)
        this.user = null
      }
    },
    logout() {
      this.accessToken = null
      this.refreshToken = null
      this.user = null
      this.isAuthenticated = false

      localStorage.removeItem('accessToken')
      localStorage.removeItem('refreshToken')
      localStorage.removeItem('user')
    },
  },
})
