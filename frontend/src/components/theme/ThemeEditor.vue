<template>
  <el-form label-position="top" class="theme-editor">
    <el-form-item label="主题名">
      <el-input v-model="form.name" placeholder="我的主题" />
    </el-form-item>
    <el-form-item label="背景色">
      <el-color-picker v-model="form.bg" />
    </el-form-item>
    <el-form-item label="前景色">
      <el-color-picker v-model="form.fg" />
    </el-form-item>
    <el-form-item label="强调色">
      <el-color-picker v-model="form.accent" />
    </el-form-item>
    <el-form-item label="字体">
      <el-input v-model="form.font" placeholder="system-ui" />
    </el-form-item>
    <el-form-item>
      <el-switch v-model="form.isDefault" active-text="设为默认主题" />
    </el-form-item>
    <el-button type="primary" @click="save">保存主题</el-button>
  </el-form>
</template>

<script setup lang="ts">
import { reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { themeApi } from '@/api/theme'
import { useThemeStore } from '@/stores/theme'

const themeStore = useThemeStore()

const form = reactive({
  name: '',
  bg: '#ffffff',
  fg: '#222222',
  accent: '#409eff',
  font: 'system-ui',
  isDefault: false,
})

onMounted(() => themeStore.fetchList())

async function save() {
  await themeApi.create({
    name: form.name,
    isDefault: form.isDefault,
    config: JSON.stringify({
      bg: form.bg,
      fg: form.fg,
      accent: form.accent,
      font: form.font,
    }),
  })
  ElMessage.success('已保存')
  themeStore.fetchList()
}
</script>

<style scoped>
.theme-editor {
  width: 100%;
}
</style>
