<template>
  <div class="settings">
    <h2>主题设置</h2>
    <ThemeEditor />
  </div>
</template>

<script setup lang="ts">
import ThemeEditor from '@/components/theme/ThemeEditor.vue'
</script>

<style scoped>
.settings {
  padding: 24px;
  max-width: 480px;
}
</style>
