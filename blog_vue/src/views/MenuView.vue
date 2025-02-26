<template>
  <div class="container mx-auto px-4 py-8">
    <h1 class="text-2xl font-bold mb-6">게시물 목록</h1>

    <div class="grid grid-cols-1 gap-6">
      <div
        v-for="post in posts"
        :key="post.id"
        class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow duration-200"
      >
        <!-- 제목 -->
        <h2 class="text-xl font-semibold text-gray-800 mb-2">
          {{ post.title }}
        </h2>

        <!-- 작성자 정보와 날짜 -->
        <div class="flex items-center text-sm text-gray-600 mb-4">
          <span class="mr-4">
            <i class="fas fa-user mr-1"></i>
            <!-- Font Awesome 아이콘 사용 시 -->
            {{ post.memberVo?.nickname || '작성자' }}
          </span>
          <span>
            <i class="fas fa-clock mr-1"></i>
            {{ new Date(post.regDate).toLocaleDateString() }}
          </span>
        </div>

        <!-- 내용 미리보기 -->
        <p class="text-gray-600 mb-4 line-clamp-2">
          {{ post.content }}
        </p>

        <!-- 하단 메타 정보 -->
        <div class="flex justify-between items-center text-sm text-gray-500">
          <div class="flex items-center space-x-4">
            <span>
              <i class="fas fa-eye mr-1"></i>
              조회 {{ post.viewCount || 0 }}
            </span>
            <span>
              <i class="fas fa-comment mr-1"></i>
              댓글 {{ post.commentCount || 0 }}
            </span>
          </div>

          <!-- 자세히 보기 버튼 -->
          <button
            class="px-4 py-2 text-blue-600 hover:text-blue-800 font-medium transition-colors duration-200"
          >
            자세히 보기
          </button>
        </div>
      </div>
    </div>

    <!-- 게시물이 없을 때 -->
    <div v-if="posts.length === 0" class="text-center py-12 text-gray-500">게시물이 없습니다.</div>
  </div>
</template>

<script setup>
import { defineProps, onMounted, ref } from 'vue'
import { usePostStore } from '@/store/postStore'

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
