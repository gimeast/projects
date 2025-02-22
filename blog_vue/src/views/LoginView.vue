<template>
  <div class="flex items-center justify-center" style="height: calc(90vh - 8rem)">
    <form class="bg-white p-4 rounded shadow-md w-full max-w-xs" @submit.prevent="handleSubmit">
      <h2 class="text-xl font-bold mb-4 text-center">Login</h2>
      <InputComp type="email" id="email" label="Email" v-model="email" required />
      <InputComp type="password" id="password" label="Password" v-model="password" required />
      <ButtonComp type="submit" variant="primary" fullWidth> Login </ButtonComp>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useLoginStore } from '@/store/loginStore'
import { useRouter } from 'vue-router'
import ButtonComp from '@/components/ButtonComp.vue'
import InputComp from '@/components/InputComp.vue'

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
