<template>
  <div class="note-list">
    <div class="toolbar">
      <el-input
        v-model="notes.query.keyword"
        placeholder="搜索标题或正文"
        clearable
        @keyup.enter="refresh"
        @clear="refresh"
      />
      <el-button type="primary" @click="createNote">新建</el-button>
    </div>

    <NoteItem v-for="note in notes.list" :key="note.id" :note="note" @click="open(note.id)" />
    <el-empty v-if="!notes.list.length" description="暂无笔记" />
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useNotesStore } from '@/stores/notes'
import NoteItem from '@/components/note/NoteItem.vue'

const notes = useNotesStore()
const router = useRouter()

onMounted(() => notes.fetchList())

function refresh() {
  notes.fetchList()
}

function open(id: number) {
  router.push(`/notes/${id}`)
}

async function createNote() {
  const note = await notes.create({ title: '无标题', content: '' })
  router.push(`/notes/${note.id}`)
}
</script>

<style scoped>
.note-list {
  display: flex;
  flex-direction: column;
  gap: 8px;
  padding: 16px;
}
.toolbar {
  display: flex;
  gap: 8px;
}
</style>
