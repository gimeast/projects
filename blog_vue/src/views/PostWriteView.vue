<template>
  <div class="container">
    <form class="container" @submit.prevent="submitPost">
      <InputComp
        type="text"
        id="title"
        v-model="title"
        label=""
        required
        placeholder="제목을 입력하세요"
      />

      <div class="mt-4">
        <label for="menu">메뉴 선택:</label>
        <select id="menu" v-model="selectedMenu" @change="onMenuChange" required>
          <option v-for="menu in menus" :key="menu.id" :value="menu.id">
            {{ menu.title }}
          </option>
        </select>
      </div>

      <div v-if="subMenus.length > 0" class="mt-4">
        <label for="subMenu">하위 메뉴 선택:</label>
        <select id="subMenu" v-model="selectedSubMenu" @change="onSubMenuChange" required>
          <option v-for="subMenu in subMenus" :key="subMenu.id" :value="subMenu.id">
            {{ subMenu.title }}
          </option>
        </select>
      </div>

      <div class="mt-4">
        <label for="category">카테고리 선택:</label>
        <select id="category" v-model="selectedCategory" required>
          <option v-for="category in categories" :key="category.id" :value="category.id">
            {{ category.name }}
          </option>
        </select>
      </div>

      <TipTapComp v-model="content" />

      <div class="flex justify-center mt-4">
        <ButtonComp type="submit" variant="primary">게시</ButtonComp>
        <ButtonComp type="button" variant="secondary" @click="cancelPost">취소</ButtonComp>
      </div>
    </form>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { usePostStore } from '@/store/postStore'
import { useLoginStore } from '@/store/loginStore'
import { useMenuStore } from '@/store/menuStore'
import { useCategoryStore } from '@/store/categoryStore'
import InputComp from '@/components/InputComp.vue'
import ButtonComp from '@/components/ButtonComp.vue'
import TipTapComp from "@/components/TipTapComp.vue";

const router = useRouter()

const postStore = usePostStore()
const loginStore = useLoginStore()
const menuStore = useMenuStore()
const categoryStore = useCategoryStore()

const title = ref('')
const content = ref('')
const menus = ref([])
const selectedMenu = ref('')
const subMenus = ref([])
const selectedSubMenu = ref('')
const categories = ref([])
const selectedCategory = ref('')

const submitPost = async () => {
  await postStore.savePost(loginStore.user.id, selectedCategory.value, title.value, content.value)
  await router.push(`/menus/${selectedSubMenu.value || selectedMenu.value}`)
}

const cancelPost = () => {
  router.go(-1)
}

const onMenuChange = async () => {
  const selected = menus.value.find((menu) => menu.id === selectedMenu.value)
  subMenus.value = selected ? selected.children : []
  selectedSubMenu.value = ''

  await categoryStore.fetchCategories(selectedMenu.value)
  categories.value = categoryStore.categories
}

const onSubMenuChange = async () => {
  await categoryStore.fetchCategories(selectedSubMenu.value)
  categories.value = categoryStore.categories
}

onMounted(async () => {
  await menuStore.fetchMenus()
  menus.value = menuStore.menus
})
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: 0 auto;
}
</style>
