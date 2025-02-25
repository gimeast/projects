<template>
  <div class="relative inline-block w-full">
    <textarea
      v-model="content"
      content-type="html"
      :style="{ height: height + 'px' }"
      class="w-full border border-gray-300 rounded-lg p-4 shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
    ></textarea>
    <div
      class="resize-handle absolute bottom-0 right-0 w-4 h-4 bg-blue-500 cursor-se-resize rounded-br-lg"
      @mousedown="initResize"
    ></div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits, ref, watch, onMounted, onBeforeUnmount } from 'vue'

const props = defineProps({
  modelValue: {
    type: String,
    default: '',
  },
  height: {
    type: Number,
    default: 150,
  },
})

const content = ref(props.modelValue)
const height = ref(props.height)

const emit = defineEmits(['update:modelValue'])

watch(content, (newValue) => {
  emit('update:modelValue', newValue)
})

let isResizing = false

const initResize = () => {
  isResizing = true
  document.addEventListener('mousemove', resize)
  document.addEventListener('mouseup', stopResize)
}

const resize = (e) => {
  if (isResizing) {
    height.value = e.clientY - e.target.offsetTop
  }
}

const stopResize = () => {
  isResizing = false
  document.removeEventListener('mousemove', resize)
  document.removeEventListener('mouseup', stopResize)
}

onMounted(() => {
  document.addEventListener('mouseup', stopResize)
})

onBeforeUnmount(() => {
  document.removeEventListener('mouseup', stopResize)
})
</script>

<style scoped>
.resize-handle {
  /* Tailwind CSS 클래스를 사용하여 스타일을 적용합니다. */
}
</style>
