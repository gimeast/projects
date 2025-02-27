import { createRouter, createWebHistory } from 'vue-router'
import MainView from '../views/MainView.vue'

const routes = [
  {
    path: '/',
    name: 'MainView',
    component: MainView,
  },
  {
    path: '/login',
    name: 'LoginView',
    component: () => import('@/views/LoginView.vue'),
  },
  {
    path: '/posts/menus/:menuId',
    name: 'PostListView',
    component: () => import('@/views/PostListView.vue'),
    props: true,
  },
  {
    path: '/posts/:postId',
    name: 'PostDetailView',
    component: () => import('@/views/PostDetailView.vue'),
    props: true,
  },
  {
    path: '/posts/write',
    name: 'PostWriteView',
    meta: { requiresAuth: true },
    component: () => import('@/views/PostWriteView.vue'),
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

router.beforeEach((to, from, next) => {
  const accessToken = localStorage.getItem('accessToken')

  if (to.meta.requiresAuth && !accessToken) {
    next({ name: 'LoginView' })
  } else {
    next()
  }
})

export default router
