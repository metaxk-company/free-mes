<template>
  <common-search
    :conditions-list="searchProcessConditions"
    :search-model="searchModel"
    @query-data="handleQueryData"
  />
  <br />
  <common-table
    :loading="tableLoading"
    isSelection
    :columns="tableProcessColumns"
    :tableData="tableDataList"
    :pagination="pagination"
    @pagination-change="handlePagination"
    @selection-change="handleSelectionChange"
  >
    <template #enableFlag="{ scope }">
      {{ scope.row.enableFlag === 'Y' ? '是' : '否' }}
    </template>
  </common-table>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'

import CommonTable from '@/components/CommonTable/index.vue'
import { tableProcessColumns, searchProcessConditions } from '../data'
import { getWorkOrder } from '@/api/inspect/otherInspect'

const tableLoading = ref(false) // 表格加载
const tableDataList = ref([]) // 表格数据
let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const selectionValue = ref([])
// 搜索内容值
const searchModel = ref({
  workorderCode: '',
  workorderName: '',
  productCode: ''
})
// 搜索功能
const handleQueryData = () => {
  getCraftListAll()
}

// 分页功能
const handlePagination = (value) => {
  pagination = value?.value
  getCraftListAll()
}

// 表格选择事件
const handleSelectionChange = (value) => {
  if (value.length <= 1) {
    selectionValue.value = value
  } else {
    selectionValue.value = value[value.length - 1]
    ElMessage({
      message: '只能选择一条数据',
      type: 'warning'
    })
  }
}

const getCraftListAll = () => {
  const params = {
    ...searchModel.value,
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize
  }
  tableLoading.value = true
  getWorkOrder(params)
    .then((res) => {
      const { list, total } = res || {}

      tableDataList.value = list
      pagination.total = total
    })
    .finally(() => {
      tableLoading.value = false
    })
}

defineExpose({
  selectionValue
})

onMounted(() => {
  getCraftListAll()
})
</script>

<style scoped lang="scss"></style>
