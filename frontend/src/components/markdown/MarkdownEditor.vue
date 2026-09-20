<template>
  <textarea
    class="editor"
    :value="modelValue"
    placeholder="开始写作..."
    @input="onInput"
  ></textarea>
</template>

<script setup lang="ts">
defineProps<{ modelValue: string }>()
const emit = defineEmits<{ (e: 'update:modelValue', value: string): void }>()

function onInput(e: Event) {
  emit('update:modelValue', (e.target as HTMLTextAreaElement).value)
}
</script>

<style scoped>
.editor {
  width: 100%;
  height: 100%;
  min-height: 400px;
  padding: 16px;
  border: none;
  resize: none;
  outline: none;
  font-family: ui-monospace, 'SF Mono', Consolas, monospace;
  font-size: 14px;
  line-height: 1.6;
  color: var(--fg);
  background: var(--bg);
}
</style>
