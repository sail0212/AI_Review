import { defineStore } from 'pinia'
import { ref } from 'vue'
import { themeApi } from '@/api/theme'
import type { Theme } from '@/types/Theme'

export const useThemeStore = defineStore('theme', () => {
  const list = ref<Theme[]>([])
  const current = ref<Theme | null>(null)

  async function fetchList() {
    list.value = await themeApi.list()
    current.value = list.value.find((t) => t.isDefault) ?? list.value[0] ?? null
    apply(current.value)
  }

  function apply(theme: Theme | null) {
    if (!theme?.config) return
    try {
      const config = JSON.parse(theme.config)
      const root = document.documentElement
      if (config.bg) root.style.setProperty('--bg', config.bg)
      if (config.fg) root.style.setProperty('--fg', config.fg)
      if (config.accent) root.style.setProperty('--accent', config.accent)
      if (config.font) root.style.setProperty('--font', config.font)
    } catch {
      // 非法 JSON 忽略
    }
  }

  return { list, current, fetchList, apply }
})
