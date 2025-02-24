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
    component: () => import('../views/LoginView.vue'),
  },
  {
    path: '/menus/:menuId',
    name: 'MenuView',
    component: () => import('../views/MenuView.vue'),
    props: true,
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

export default router
