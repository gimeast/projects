import {defineStore} from 'pinia'
import {fetchWithToken} from "@/services/api";

export const useMenuStore = defineStore('menu', {
    state: () => ({
        menus: [],
    }),
    actions: {
        async fetchMenus() {
            try {
                const response = await fetchWithToken('/api/public/menus')

                if (!response.ok) {
                    throw new Error('메뉴조회 실패')
                }

                const data = await response.json()
                this.menus = data
            } catch (error) {
                console.error('메뉴조회 중 오류 발생:', error)
            }
        },
    },
})
