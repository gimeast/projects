<template>
  <div class="flex flex-col min-h-screen">
    <HeaderComp />
    <section class="flex-grow container mx-auto px-4">
      <router-view />
    </section>
    <FooterComp />
  </div>
</template>

<script setup>
import HeaderComp from '@/components/HeaderComp.vue'
import FooterComp from '@/components/FooterComp.vue'
</script>

<style>
body {
  margin: 0;
  padding: 0;
  font-family: 'Inter', sans-serif;
}
.container {
  padding-top: 4rem; /* Header height */
}
</style>