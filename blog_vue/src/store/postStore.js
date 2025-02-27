import {defineStore} from 'pinia'
import {fetchWithToken} from '@/services/api'

export const usePostStore = defineStore('post', {
    state: () => ({
        posts: [],
        post: {},
        currentMenuId: 0,
        count: 0,
    }),
    actions: {
        async fetchPosts(menuId) {
            try {
                const response = await fetchWithToken(`/api/v1/posts/menus/${menuId}`, {
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    method: 'GET',
                })

                if (!response.ok) {
                    throw new Error('게시물 목록 조회 실패')
                }

                const data = await response.json()
                this.posts = data
                this.currentMenuId = menuId
            } catch (error) {
                console.error('게시물 목록 조회 중 오류 발생:', error)
            }
        },
        async fetchPost(postId) {
            try {
                const response = await fetchWithToken(`/api/v1/posts/${postId}`)

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
                const response = await fetchWithToken('/api/v1/posts', {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: localStorage.getItem('accessToken'),
                    },
                    method: 'POST',
                    body: JSON.stringify({memberId: userId, categoryId, title, content}),
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
        async deletePost(postId) {
            try {
                const response = await fetchWithToken(`/api/v1/posts/${postId}`, {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: localStorage.getItem('accessToken'),
                    },
                    method: 'DELETE',
                })

                if (!response.ok) {
                    throw new Error('게시물 삭제 실패')
                }
            } catch (error) {
                console.error('게시물 삭제 중 오류 발생:', error)
            }
        }
    },
})
