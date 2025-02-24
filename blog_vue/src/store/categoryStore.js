import { defineStore } from 'pinia'
export const useCategoryStore = defineStore('category', {
  state: () => ({
    categories: [],
  }),
  actions: {
    async fetchCategories(menuId) {
      try {
        const params = new URLSearchParams({ menuId })
        const response = await fetch(
          `http://localhost:8081/api/public/categories?${params.toString()}`
        )

        if (!response.ok) {
          throw new Error('카테고리 조회 실패')
        }

        const data = await response.json()
        this.categories = data
      } catch (error) {
        console.error('카테고리 조회 중 오류 발생:', error)
      }
    },
  },
})
