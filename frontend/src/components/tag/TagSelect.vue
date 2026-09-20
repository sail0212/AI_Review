<template>
  <el-select
    :model-value="modelValue"
    multiple
    filterable
    placeholder="选择标签"
    @update:model-value="emit('update:modelValue', $event)"
  >
    <el-option v-for="tag in tags.list" :key="tag.id" :label="tag.name" :value="tag.id" />
  </el-select>
</template>

<script setup lang="ts">
import { useTagsStore } from '@/stores/tags'

// TODO: 支持「新建标签」（当前仅支持选择已有标签）
const tags = useTagsStore()

defineProps<{ modelValue: number[] }>()
const emit = defineEmits<{ (e: 'update:modelValue', value: number[]): void }>()
</script>
