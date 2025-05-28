<template>
  <el-form ref="ruleFormRef" :inline="true" :model="props.searchList" class="demo-form-inline">
    <el-form-item label="数值">
      <el-input v-model="searchList.quantityFrom" placeholder="请输入数值" />
    </el-form-item>
    <el-form-item label="转换后数值">
      <el-input v-model="searchList.quantityTo" placeholder="请输入转换后数值" />
    </el-form-item>
    <el-form-item label="现单位名称">
      <el-input v-model="searchList.unitTo" placeholder="请输入现单位名称" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="handleSearch" plain>搜索</el-button>
      <el-button @click="handleResetSearch" plain>重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { FormInstance } from 'element-plus'

const ruleFormRef = ref<FormInstance>()

const props = defineProps({
  searchList: {
    type: Object,
    default: () => {}
  }
})

const emit = defineEmits(['handleSearch', 'handleResetSearch'])

// 搜索
const handleSearch = () => {
  emit('handleSearch')
}

// 重置
const handleResetSearch = () => {
  emit('handleResetSearch')
}
</script>

<style scoped lang="scss"></style>
