<template>
  <div class="container mx-auto max-w-4xl px-4 py-8">
    <div class="bg-white rounded-lg shadow-md p-6">
      <!-- 제목 영역 -->
      <h1 class="text-2xl font-bold mb-4">{{ post.title }}</h1>

      <!-- 메타 정보 영역 -->
      <div class="flex justify-between text-sm text-gray-600 border-b pb-4 mb-6">
        <div class="flex items-center gap-4">
          <span>작성자: {{ post.memberId }}</span>
        </div>
        <span>{{ formatDate(post.regDate) }}</span>
      </div>

      <!-- 내용 영역 -->
      <div class="prose max-w-none" v-html="post.content"></div>

      <!-- 버튼 영역 -->
      <div class="flex justify-end gap-3 mt-8">
        <ButtonComp
            type="button"
            variant="primary"
            @click="handleEdit"
            v-if="isAuthor"
        >
          수정하기
        </ButtonComp>
        <ButtonComp
            type="button"
            variant="danger"
            @click="handleDelete"
            v-if="isAuthor"
        >
          삭제하기
        </ButtonComp>
        <ButtonComp
            type="button"
            variant="secondary"
            @click="handleBack"
        >
          목록으로
        </ButtonComp>
      </div>
    </div>
  </div>
</template>

<script setup>
import {computed, defineProps, onMounted} from 'vue'
import {useRouter} from 'vue-router'
import {usePostStore} from '@/store/postStore'
import {useLoginStore} from '@/store/loginStore'
import ButtonComp from '@/components/ButtonComp.vue'

const router = useRouter()
const postStore = usePostStore()
const loginStore = useLoginStore()
const props = defineProps({
  postId: {
    type: Number,
    required: true
  }
})

// 게시글 조회
const fetchPost = async () => await postStore.fetchPost(props.postId)
onMounted(fetchPost)

const post = computed(() => postStore.post)

// 작성자 본인 여부 확인
const isAuthor = computed(() => {
  return loginStore.user?.id === post.value?.memberId
})

// 날짜 포맷 함수
const formatDate = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return new Intl.DateTimeFormat('ko-KR', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  }).format(date)
}

// 버튼 핸들러
const handleEdit = () => {
  router.push(`/posts/${post.value.id}/edit`)
}

const handleDelete = async () => {
  if (confirm('정말 삭제하시겠습니까?')) {
    try {
      await postStore.deletePost(post.value.id)
      router.push(`/posts/menus/${postStore.currentMenuId}`)
    } catch (error) {
      console.error('게시글 삭제 실패:', error)
    }
  }
}

const handleBack = () => {
  router.go(-1)
}
</script>

<style scoped>
.container {
  min-height: calc(100vh - 200px);
}

:deep(.prose) {
  min-height: 200px;
  line-height: 1.8;
}
</style>