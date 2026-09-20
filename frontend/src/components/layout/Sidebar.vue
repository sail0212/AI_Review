<template>
  <aside class="sidebar">
    <div class="brand">📝 备忘录</div>

    <el-button type="primary" class="new-btn" @click="createNote">新建笔记</el-button>

    <div class="section">
      <div class="section-title">标签</div>
      <el-tag
        v-for="tag in tags.list"
        :key="tag.id"
        class="tag"
        @click="filter(tag.id)"
      >
        {{ tag.name }}
      </el-tag>
    </div>

    <div class="footer">
      <router-link to="/settings">设置</router-link>
      <a class="link" @click="logout">退出登录</a>
    </div>
  </aside>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useTagsStore } from '@/stores/tags'
import { useNotesStore } from '@/stores/notes'
import { useAuthStore } from '@/stores/auth'

const tags = useTagsStore()
const notes = useNotesStore()
const auth = useAuthStore()
const router = useRouter()

onMounted(() => tags.fetchList())

function filter(tagId: number) {
  notes.query.tagId = tagId
  notes.fetchList()
  router.push('/notes')
}

async function createNote() {
  const note = await notes.create({ title: '无标题', content: '' })
  router.push(`/notes/${note.id}`)
}

function logout() {
  auth.logout()
  router.push('/login')
}
</script>

<style scoped>
.sidebar {
  display: flex;
  flex-direction: column;
  width: 220px;
  padding: 16px;
  border-right: 1px solid #eee;
  background: #fafafa;
}
.brand {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 16px;
}
.new-btn {
  margin-bottom: 24px;
}
.section {
  flex: 1;
}
.section-title {
  font-size: 13px;
  color: #999;
  margin-bottom: 8px;
}
.tag {
  margin: 0 4px 8px 0;
  cursor: pointer;
}
.footer {
  display: flex;
  justify-content: space-between;
  font-size: 13px;
}
.link {
  cursor: pointer;
  color: #666;
}
</style>
