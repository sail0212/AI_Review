<template>
  <div v-if="notes.current" class="editor-page">
    <div class="header">
      <el-input v-model="notes.current.title" class="title" placeholder="标题" />
      <TagSelect v-model="tagIds" class="tags" />
      <el-button type="primary" @click="save">保存</el-button>
    </div>

    <div class="panes">
      <MarkdownEditor v-model="notes.current.content" />
      <MarkdownRenderer :content="notes.current.content" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useNotesStore } from '@/stores/notes'
import { useTagsStore } from '@/stores/tags'
import MarkdownEditor from '@/components/markdown/MarkdownEditor.vue'
import MarkdownRenderer from '@/components/markdown/MarkdownRenderer.vue'
import TagSelect from '@/components/tag/TagSelect.vue'

const route = useRoute()
const notes = useNotesStore()
const tags = useTagsStore()
const tagIds = ref<number[]>([])

onMounted(async () => {
  await Promise.all([notes.fetchDetail(Number(route.params.id)), tags.fetchList()])
  tagIds.value = notes.current?.tags.map((t) => t.id) ?? []
})

async function save() {
  if (!notes.current) return
  await notes.update(notes.current.id, {
    title: notes.current.title,
    content: notes.current.content,
    tagIds: tagIds.value,
  })
  ElMessage.success('已保存')
}
</script>

<style scoped>
.editor-page {
  display: flex;
  flex-direction: column;
  height: 100%;
  padding: 16px;
}
.header {
  display: flex;
  gap: 8px;
  margin-bottom: 12px;
}
.title {
  max-width: 320px;
}
.tags {
  flex: 1;
  max-width: 320px;
}
.panes {
  display: flex;
  flex: 1;
  gap: 12px;
  min-height: 0;
}
.panes > * {
  flex: 1;
  border: 1px solid #eee;
  border-radius: 4px;
  overflow: auto;
}
</style>
