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
// import { onMounted } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchProcessConditions, tableProcessColumns } from '../data'
import { queryCraftListAll } from '@/api/masterData/craftCourse'

// 搜索内容值
const searchModel = ref({
  processCode: '',
  processName: ''
})
const tableLoading = ref(false) // 表格加载
const tableDataList = ref([]) // 表格数据
let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 15, 20], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const selectionValue = ref([])

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
  selectionValue.value = value
}

const getCraftListAll = () => {
  const params = {
    ...searchModel.value,
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize
  }
  tableLoading.value = true
  queryCraftListAll(params)
    .then((res) => {
      tableDataList.value = res.list
      pagination.total = res.total
    })
    .finally(() => {
      tableLoading.value = false
    })
}

getCraftListAll()

defineExpose({
  selectionValue
})

// onMounted(() => {
// })
</script>

<style scoped lang="scss"></style>
