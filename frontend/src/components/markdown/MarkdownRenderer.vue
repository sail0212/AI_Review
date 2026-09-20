<template>
  <div class="markdown-body" v-html="html"></div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import MarkdownIt from 'markdown-it'
import hljs from 'highlight.js'
import 'highlight.js/styles/github.css'

const props = defineProps<{ content: string }>()

// html: false —— 不渲染原始 HTML，防止 XSS
const md = new MarkdownIt({
  html: false,
  linkify: true,
  typographer: true,
  highlight(code: string, lang: string) {
    if (lang && hljs.getLanguage(lang)) {
      return hljs.highlight(code, { language: lang }).value
    }
    return ''
  },
})

const html = computed(() => md.render(props.content || ''))
</script>

<style scoped>
.markdown-body {
  padding: 16px;
  line-height: 1.7;
  color: var(--fg);
}
.markdown-body :deep(pre) {
  background: #f6f8fa;
  padding: 12px;
  border-radius: 6px;
  overflow: auto;
}
.markdown-body :deep(code) {
  font-family: ui-monospace, Consolas, monospace;
  font-size: 13px;
}
</style>
