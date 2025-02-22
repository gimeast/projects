import { defineStore } from 'pinia'
export const useMenuStore = defineStore('menu', {
  state: () => ({
    menus: [],
  }),
  actions: {
    async fetchMenus() {
      try {
        const response = await fetch('http://localhost:8081/api/public/menus')

        if (!response.ok) {
          throw new Error('메뉴조회 실패')
        }

        const data = await response.json()

        console.log('data:', data)
        this.menus = data
        console.log('this.menus:', this.menus)
      } catch (error) {
        console.error('메뉴조회 중 오류 발생:', error)
      }
    },
  },
})
