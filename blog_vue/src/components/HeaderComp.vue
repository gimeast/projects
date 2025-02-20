<template>
  <header class="bg-white shadow fixed top-0 left-0 right-0 z-10">
    <div class="container mx-auto px-4 py-4 flex justify-between items-center">
      <div class="flex items-center">
        <router-link to="/" class="flex items-center">
          <img src="@/assets/logo.png" alt="Logo" class="h-8 w-8 mr-2" />
          <span class="font-semibold text-xl">Blog</span>
        </router-link>
      </div>
      <nav>
        <ul class="flex space-x-4">
          <li class="group relative">
            <a href="#" class="text-gray-700 hover:text-blue-500"
              >메뉴1
              <ul
                class="absolute left-0 mt-2 w-40 bg-white border rounded-md shadow-lg opacity-0 group-hover:opacity-100 transition-opacity duration-300 z-10"
              >
                <li>
                  <a href="#" class="block px-4 py-2 text-gray-700 hover:bg-gray-100"
                    >서브메뉴1-1</a
                  >
                </li>
                <li>
                  <a href="#" class="block px-4 py-2 text-gray-700 hover:bg-gray-100"
                    >서브메뉴1-2</a
                  >
                </li>
              </ul>
            </a>
          </li>
          <li class="group relative">
            <a href="#" class="text-gray-700 hover:text-blue-500">메뉴2</a>
            <ul
              class="absolute left-0 mt-2 w-40 bg-white border rounded-md shadow-lg opacity-0 group-hover:opacity-100 transition-opacity duration-300 z-10"
            >
              <li>
                <a href="#" class="block px-4 py-2 text-gray-700 hover:bg-gray-100">서브메뉴2-1</a>
              </li>
              <li>
                <a href="#" class="block px-4 py-2 text-gray-700 hover:bg-gray-100">서브메뉴2-2</a>
              </li>
            </ul>
          </li>
        </ul>
      </nav>
      <div class="flex items-center space-x-4">
        <router-link
          v-if="!user"
          to="/login"
          class="block px-4 py-2 text-gray-700 hover:bg-gray-100"
          >로그인</router-link
        >
        <button v-else @click="logout" class="block px-4 py-2 text-gray-700 hover:bg-gray-100">
          로그아웃
        </button>
        <input
          type="text"
          class="px-3 py-2 border rounded-md focus:outline-none focus:ring2 focus:ring-blue-500"
        />
        <button class="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">검색</button>
      </div>
    </div>
  </header>
</template>

<script setup>
import { computed } from 'vue'
import { useLoginStore } from '@/store/loginStore'
import { useRouter } from 'vue-router'

const loginStore = useLoginStore()
const router = useRouter()

const logout = () => {
  loginStore.logout()
  router.push({ name: 'MainView' })
}

const user = computed(() => loginStore.user)
</script>
