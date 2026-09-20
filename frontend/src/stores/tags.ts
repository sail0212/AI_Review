import { defineStore } from 'pinia'
import { ref } from 'vue'
import { tagApi } from '@/api/tag'
import type { Tag } from '@/types/Tag'

export const useTagsStore = defineStore('tags', () => {
  const list = ref<Tag[]>([])

  async function fetchList() {
    list.value = await tagApi.list()
  }

  return { list, fetchList }
})
