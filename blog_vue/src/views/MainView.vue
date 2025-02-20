<template>
  <div class="container mx-auto px-4 py-8">
    <nav class="flex mb-4" aria-label="Breadcrumb">
      <ol class="inline-flex items-center space-x-1 md:space-x-3">
        <li class="inline-flex itemx-center">
          <a href="#" class="text-gray-700 hover:text-blue-500 flex items-center">
            <svg class="w-4 h-4 mr-2" fill="currentColor" viewBox="0 0 20 20">
              <path d="M10 20l-6-6h4V8a2 2 0 114 0v6h4l-6 6z" />
            </svg>
            홈
          </a>
        </li>
        <li>
          <div class="flex items-center">
            <svg class="w-6 h-6 text-gray-400" fill="currentColor" viewBox="0 0 20 20">
              <path
                fill-rule="evenodd"
                d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z"
                clip-rule="evenodd"
              />
              <a
                href="#"
                class="ml-1 text-sm font-medium text-gray-700 hover:text-blue-500 md:ml-2"
              >
                대시보드
              </a>
            </svg>
          </div>
        </li>
      </ol>
    </nav>
    <h1 class="text-3xl font-bold mt-8 mb-4">블로그 웹 애플리케이션</h1>
    <p class="text-gray-700 mb-6">이곳은 웹 애플리케이션의 주요 정보를 표시하는 영역입니다.</p>
  </div>
</template>

<script setup></script>
