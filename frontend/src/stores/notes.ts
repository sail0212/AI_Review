import { defineStore } from 'pinia'
import { ref, reactive } from 'vue'
import { noteApi } from '@/api/note'
import type { Note, NotePayload, NoteQuery } from '@/types/Note'

export const useNotesStore = defineStore('notes', () => {
  const list = ref<Note[]>([])
  const total = ref(0)
  const current = ref<Note | null>(null)
  const query = reactive<NoteQuery>({ page: 1, size: 20 })

  async function fetchList() {
    const res = await noteApi.list(query)
    list.value = res.records
    total.value = res.total
  }

  async function fetchDetail(id: number) {
    current.value = await noteApi.detail(id)
  }

  async function create(payload: NotePayload) {
    return noteApi.create(payload)
  }

  async function update(id: number, payload: NotePayload) {
    current.value = await noteApi.update(id, payload)
  }

  async function remove(id: number) {
    await noteApi.remove(id)
    await fetchList()
  }

  async function togglePin(id: number) {
    await noteApi.togglePin(id)
    await fetchList()
  }

  async function toggleArchive(id: number) {
    await noteApi.toggleArchive(id)
    await fetchList()
  }

  return { list, total, current, query, fetchList, fetchDetail, create, update, remove, togglePin, toggleArchive }
})
