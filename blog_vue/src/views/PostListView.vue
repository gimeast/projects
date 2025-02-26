<template>
  <div class="container mx-auto px-4 py-8 max-w-6xl">
    <div class="flex justify-between items-center mb-8">
      <h1 class="text-3xl font-bold text-gray-800 border-b-4 border-blue-500 inline-block pb-2">
        게시물 목록
      </h1>
    </div>

    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <div
          v-for="post in posts"
          :key="post.id"
          class="bg-white rounded-xl shadow-sm hover:shadow-md transition-all duration-300 border border-gray-100 overflow-hidden group"
      >
        <!-- 카드 컨텐츠 -->
        <div class="p-6">
          <!-- 제목 영역 -->
          <h2 class="text-xl font-semibold text-gray-800 mb-3 group-hover:text-blue-600 transition-colors duration-200 line-clamp-2">
            {{ post.title }}
          </h2>

          <!-- 메타 정보 -->
          <div class="flex items-center space-x-4 text-sm text-gray-500 mb-4">
            <div class="flex items-center">
              <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                      d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
              </svg>
              <span>{{ new Date(post.regDate).toLocaleDateString() }}</span>
            </div>
          </div>

          <!-- 버튼 영역 -->
          <div class="mt-4">
            <router-link :to="`/posts/${post.id}`" class="block">
              <ButtonComp
                  type="button"
                  variant="primary"
                  class="w-full text-center py-2 px-4 rounded-lg bg-blue-600 hover:bg-blue-700
                       text-white font-medium transition-colors duration-200"
              >
                자세히 보기
              </ButtonComp>
            </router-link>
          </div>
        </div>
      </div>
    </div>

    <!-- 게시물이 없을 때 -->
    <div
        v-if="posts.length === 0"
        class="flex flex-col items-center justify-center py-16 px-4 bg-gray-50 rounded-lg mt-8"
    >
      <svg
          class="w-16 h-16 text-gray-400 mb-4"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
      >
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
      </svg>
      <p class="text-lg text-gray-600 font-medium">게시물이 없습니다</p>
      <p class="text-sm text-gray-500 mt-2">첫 번째 게시물을 작성해보세요!</p>
    </div>
  </div>
</template>
<script setup>
import { defineProps, onMounted, ref } from 'vue'
import { usePostStore } from '@/store/postStore'
import ButtonComp from '../components/ButtonComp.vue'

const postStore = usePostStore()
const posts = ref([])

const props = defineProps({
  menuId: {
    type: String,
    required: true,
  },
})

const fetchPosts = async () => {
  await postStore.fetchPosts(props.menuId)
  posts.value = postStore.posts
}

onMounted(fetchPosts)
</script>
