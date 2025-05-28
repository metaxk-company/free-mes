<!-- eslint-disable vue/no-mutating-props -->
<template>
  <common-search
    :conditions-list="searchConditions"
    :search-model="searchParams"
    @query-data="handleSearch"
  />
</template>

<script setup lang="ts">
import { unknownValid, searchConditions } from '../data'

defineProps({
  searchParams: {
    type: Object,
    default: () => {}
  }
})

const emit = defineEmits(['handleResetSearch', 'handleSearch'])

// 搜索功能
const handleSearch = () => {
  emit('handleSearch')
}

// 重置搜索
const handleResetSearch = () => {
  emit('handleResetSearch')
}
</script>

<style scoped lang="scss"></style>
