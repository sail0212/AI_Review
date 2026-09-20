<template>
  <div class="note-item" @click="$emit('click')">
    <div class="title">
      <span v-if="note.isPinned" class="pin">📌</span>
      {{ note.title || '无标题' }}
    </div>
    <div class="meta">
      <span class="time">{{ note.updatedAt }}</span>
      <el-tag v-for="tag in note.tags" :key="tag.id" size="small" class="tag">{{ tag.name }}</el-tag>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Note } from '@/types/Note'

defineProps<{ note: Note }>()
defineEmits<{ (e: 'click'): void }>()
</script>

<style scoped>
.note-item {
  padding: 12px 16px;
  border: 1px solid #eee;
  border-radius: 6px;
  cursor: pointer;
  transition: border-color 0.2s;
}
.note-item:hover {
  border-color: var(--accent);
}
.title {
  font-size: 15px;
  font-weight: 500;
}
.pin {
  margin-right: 4px;
}
.meta {
  display: flex;
  align-items: center;
  gap: 6px;
  margin-top: 6px;
}
.time {
  font-size: 12px;
  color: #999;
}
</style>
