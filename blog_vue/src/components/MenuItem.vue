<template>
  <li class="group relative">
    <router-link :to="`/posts/menus/${props.menu.id}`" class="text-gray-700 hover:text-blue-500">{{
        props.menu.title
      }}
    </router-link>
    <ul
        v-if="menu.children && menu.children.length"
        class="absolute left-0 mt-2 w-40 bg-white border rounded-md shadow-lg opacity-0 group-hover:opacity-100 transition-opacity duration-300 z-10"
    >
      <MenuItem v-for="child in menu.children" :key="child.id" :menu="child"></MenuItem>
    </ul>
  </li>
</template>

<script setup>
import { defineProps } from 'vue'

const props = defineProps({
  menu: Object,
})
</script>
