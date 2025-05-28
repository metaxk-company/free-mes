<template>
  <el-form :model="queryParams" :inline="true" ref="ruleFormRef" style="margin-bottom: 20px">
    <el-form-item label="设备编码" prop="machineryCode">
      <el-input v-model="queryParams.machineryCode" placeholder="请输入设备编码" />
    </el-form-item>
    <el-form-item label="设备名称" prop="machineryName">
      <el-input v-model="queryParams.machineryName" placeholder="请输入设备名称" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="handleSearch">搜索</el-button>
      <el-button @click="handleResetSearch(ruleFormRef)">重置</el-button>
    </el-form-item>
  </el-form>
  <common-table
    ref="multipleTableRef"
    :currentRow="true"
    :pagination="paginationData"
    :tableData="tableData"
    :columns="columnStateMini"
    v-loading="loadingTable"
    @pagination-change="handlePagination"
    @current-change="handleCurrentChange"
  >
    <template #status="{ scope }">
      {{ MAINTAIN_STATE[scope.row.status] }}
    </template>
  </common-table>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { PropType } from 'vue'
import type { FormInstance } from 'element-plus'
import CommonTable from '@/components/CommonTable/index.vue'
import { columnStateMini } from './data'
import { MAINTAIN_STATE } from '@/utils/const'

const props = defineProps({
  queryParams: {
    type: Object,
    default: () => {}
  },
  tableData: {
    type: Array<Object>,
    default: () => Array<Object>
  },
  paginationData: {
    type: Object as PropType<Table.Pagination>,
    default: () => {}
  },
  loadingTable: {
    type: Boolean,
    default: false
  },
  treeMaterialForm: {
    type: Object,
    default: () => {}
  }
})

const emit = defineEmits(['searchParams', 'handlePagination', 'handleCurrentChange', 'resetSearch'])

const ruleFormRef = ref<FormInstance>()
const multipleTableRef = ref<any>(null)

// 搜索功能
const handleSearch = () => {
  emit('searchParams')
}

// 重置功能
const handleResetSearch = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
  props.queryParams.machineryTypeId = ''
  emit('resetSearch')
}

// 分页点击事件
const handlePagination = (value) => {
  emit('handlePagination', value)
}

// 表格当前行数据
const handleCurrentChange = (selection) => {
  emit('handleCurrentChange', selection)
}

// 清空单选数据
const handleClearSelectionData = () => {
  if (!multipleTableRef.value) return
  multipleTableRef.value!.clearCurrentRow()
}

defineExpose({
  handleClearSelectionData
})
</script>

<style scoped lang="scss"></style>
