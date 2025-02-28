<template>
  <div class="mb-3">
    <label :for="id" class="block text-sm font-medium text-gray-700">{{ label }}</label>
    <input
        :type="type"
        :id="id"
        :value="modelValue"
        @input="updateValue"
        :required="required"
        :placeholder="placeholder"
        class="mt-1 block w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-500"
    />
  </div>
</template>

<script setup>
import {defineProps, defineEmits} from 'vue'

defineProps({
  type: {
    type: String,
    default: 'text',
  },
  id: {
    type: String,
    required: true,
  },
  label: {
    type: String,
    required: true,
  },
  modelValue: {
    type: String,
    default: '',
  },
  required: {
    type: Boolean,
    default: false,
  },
  placeholder: {
    type: String,
    default: '',
  },
})

const emit = defineEmits(['update:modelValue'])

const updateValue = (e) => {
  emit('update:modelValue', e.target.value)
}
</script>
