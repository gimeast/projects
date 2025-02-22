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
          <MenuItem v-for="menu in menus" :key="menu.id" :menu="menu"></MenuItem>
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

        <InputComp type="text" id="search" v-model="search" required />
        <ButtonComp type="button" variant="primary">검색</ButtonComp>
      </div>
    </div>
  </header>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useLoginStore } from '@/store/loginStore'
import { useMenuStore } from '@/store/menuStore.js'
import { useRouter } from 'vue-router'
import MenuItem from './MenuItem.vue'
import InputComp from '../components/InputComp.vue'
import ButtonComp from './ButtonComp.vue'

const search = ref('')
const loginStore = useLoginStore()
const menuStore = useMenuStore()
const router = useRouter()

const logout = () => {
  loginStore.logout()
  router.push({ name: 'MainView' })
}

const user = computed(() => loginStore.user)
const menus = computed(() => menuStore.menus)

const fetchMenus = async () => await menuStore.fetchMenus()
onMounted(fetchMenus)
</script>
