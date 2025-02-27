<template>
  <button
    :type="props.type"
    :class="[
      'font-semibold rounded',
      sizeClasses,
      fullWidthClass,
      {
        'bg-blue-500 text-white hover:bg-blue-600': variant === 'primary',
        'bg-gray-500 text-white hover:bg-gray-600': variant === 'secondary',
        'bg-red-500 text-white hover:bg-red-600': variant === 'danger'
      },
    ]"
    @click="handleClick"
  >
    <slot></slot>
  </button>
</template>

<script setup>
import { defineProps, defineEmits, computed } from 'vue'

const props = defineProps({
  type: {
    type: String,
    default: 'button',
  },
  variant: {
    type: String,
    default: 'primary',
  },
  size: {
    type: String,
    default: 'md', // 기본 크기: 중간
  },
  fullWidth: {
    type: Boolean,
    default: false,
  },
})

const sizeClasses = computed(() => {
  switch (props.size) {
    case 'sm':
      return 'py-1 px-2 text-sm'
    case 'lg':
      return 'py-3 px-6 text-lg'
    default:
      return 'py-2 px-4 text-md'
  }
})

const fullWidthClass = computed(() => {
  return props.fullWidth ? 'w-full' : ''
})

const emit = defineEmits(['click'])

const handleClick = (event) => {
  emit('click', event)
}
</script>
