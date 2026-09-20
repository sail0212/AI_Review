import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { authApi, type LoginPayload, type RegisterPayload } from '@/api/auth'
import type { LoginResponse, User } from '@/types/User'

export const useAuthStore = defineStore('auth', () => {
  const token = ref<string>(localStorage.getItem('token') || '')
  const user = ref<User | null>(null)

  const isLoggedIn = computed(() => !!token.value)

  function setSession(data: LoginResponse) {
    token.value = data.token
    user.value = { id: data.userId, username: data.username, nickname: data.nickname }
    localStorage.setItem('token', data.token)
  }

  async function login(payload: LoginPayload) {
    const data = await authApi.login(payload)
    setSession(data)
  }

  async function register(payload: RegisterPayload) {
    const data = await authApi.register(payload)
    setSession(data)
  }

  async function fetchMe() {
    user.value = await authApi.me()
  }

  function logout() {
    token.value = ''
    user.value = null
    localStorage.removeItem('token')
  }

  return { token, user, isLoggedIn, login, register, fetchMe, logout }
})
