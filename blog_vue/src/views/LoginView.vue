<template>
  <div class="flex items-center justify-center" style="height: calc(90vh - 8rem)">
    <form class="bg-white p-4 rounded shadow-md w-full max-w-xs" @submit.prevent="handleSubmit">
      <h2 class="text-xl font-bold mb-4 text-center">Login</h2>
      <div class="mb-3">
        <label for="email" class="block text-sm font-medium text-gray-700">Email</label>
        <input
          type="email"
          id="email"
          v-model="email"
          required
          class="mt-1 block w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-500"
        />
      </div>
      <div class="mb-3">
        <label for="password" class="block text-sm font-medium text-gray-700">Password</label>
        <input
          type="password"
          id="password"
          v-model="password"
          required
          class="mt-1 block w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-500"
        />
      </div>
      <button
        type="submit"
        class="w-full bg-blue-500 text-white font-semibold py-2 rounded hover:bg-blue-600"
      >
        Login
      </button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useLoginStore } from '@/store/loginStore'
import { useRouter } from 'vue-router'

const loginStore = useLoginStore()
const router = useRouter()

const email = ref('')
const password = ref('')

const handleSubmit = async () => {
  await login(email.value, password.value)

  if (loginStore.user) {
    router.push({ name: 'MainView' })
  } else {
    router.push({ name: 'LoginView' })
  }
}

const login = async (email, password) => {
  await loginStore.login(email, btoa(password))
}
</script>

<style scoped></style>
