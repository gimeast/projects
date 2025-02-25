import { defineStore } from 'pinia'

export const usePostStore = defineStore('post', {
  state: () => ({
    posts: [],
    post: {},
    count: 0,
  }),
  actions: {
    async fetchPosts(menuId) {
      try {
        const response = await fetch(`http://localhost:8081/api/v1/posts/${menuId}`, {
          headers: {
            'Content-Type': 'application/json',
            Authorization: localStorage.getItem('accessToken'),
          },
          method: 'GET',
        })

        if (!response.ok) {
          throw new Error('게시물 목록 조회 실패')
        }

        const data = await response.json()
        this.posts = data
      } catch (error) {
        console.error('게시물 목록 조회 중 오류 발생:', error)
      }
    },
    async fetchPost(postId) {
      try {
        const response = await fetch(`http://localhost:8081/api/v1/posts/${postId}`)

        if (!response.ok) {
          throw new Error('게시물 조회 실패')
        }

        const data = await response.json()
        this.post = data
      } catch (error) {
        console.error('게시물 조회 중 오류 발생:', error)
      }
    },
    async savePost(userId, categoryId, title, content) {
      try {
        const response = await fetch('http://localhost:8081/api/v1/posts', {
          headers: {
            'Content-Type': 'application/json',
            Authorization: localStorage.getItem('accessToken'),
          },
          method: 'POST',
          body: JSON.stringify({ memberId: userId, categoryId, title, content }),
        })

        if (!response.ok) {
          throw new Error('게시물 저장 실패')
        }

        const data = await response.json()
        this.post = data
      } catch (error) {
        console.error('게시물 저장 중 오류 발생:', error)
      }
    },
  },
})
