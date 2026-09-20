<template>
  <div class="shared-page">
    <h1 v-if="note">{{ note.title }}</h1>
    <MarkdownRenderer v-if="note" :content="note.content" />
    <el-empty v-else description="加载中或分享不存在" />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { shareApi } from '@/api/share'
import type { Note } from '@/types/Note'
import MarkdownRenderer from '@/components/markdown/MarkdownRenderer.vue'

const route = useRoute()
const note = ref<Note | null>(null)

onMounted(async () => {
  note.value = await shareApi.getByToken(String(route.params.token))
})
</script>

<style scoped>
.shared-page {
  padding: 24px;
  max-width: 820px;
  margin: 0 auto;
}
</style>
