<template>
  <ContentWrap>
    <common-search
      :conditions-list="searchConditions"
      :search-model="searchModel"
      @query-data="handleQueryData"
    />
  </ContentWrap>
  <ContentWrap>
    <common-table
      :loading="tableLoading"
      :columns="tableColumns"
      :tableData="tableDataList"
      :pagination="pagination"
      @pagination-change="handlePagination"
    >
      <template #number="{ scope }">
        <el-button @click="handleClearInfo(scope.row)" type="primary" link>
          {{ scope.row.number }}</el-button
        >
      </template>
    </common-table>
  </ContentWrap>
  <XModal v-model="detailInfoDialog" title="销售单明细" width="90%">
    <common-table
      :loading="detailInfoLoading"
      :columns="detailTableColumns"
      :tableData="detailTableDataList"
      :pagination="detailPagination"
      @pagination-change="handleDetailPagination"
    >
      <template #number="{ scope }">
        <el-button @click="handleClearInfo(scope.row)" type="primary" link>
          {{ scope.row.number }}</el-button
        >
      </template>
    </common-table>
    <template #footer></template>
  </XModal>
</template>

<script setup lang="ts">
import { ref, onMounted, reactive } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, tableColumns, detailTableColumns } from './data'
import { queryTableList, queryTableListInfo } from '@/api/stock/salesOrderCount'

// 搜索内容值
const searchModel = ref({
  customerNumber: '',
  customerName: ''
})
const detailInfoDialog = ref(false)
const tableLoading = ref(false) // 表格加载
const detailInfoLoading = ref(false) // 表格加载
const tableDataList = ref([]) // 表格数据
const detailTableDataList = ref([]) // 表格数据
const saleNumber = ref()
let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})

let detailPagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})

const handleClearInfo = async (value) => {
  saleNumber.value = value.number
  await queryTableListInfoAPI()
  detailInfoDialog.value = true
}

const queryTableListInfoAPI = async () => {
  const params = {
    saleNumber: saleNumber.value,
    pageNo: detailPagination.currentPage,
    pageSize: detailPagination.pageSize
  }
  detailInfoLoading.value = true
  await queryTableListInfo(params)
    .then((res) => {
      const { list, total } = res || {}
      detailPagination.total = total
      detailTableDataList.value = list
    })
    .finally(() => {
      detailInfoLoading.value = false
    })
}

// 搜索功能
const handleQueryData = () => {
  getTableList()
}

// 分页功能
const handlePagination = (value) => {
  pagination = value?.value
  getTableList()
}

// 分页功能
const handleDetailPagination = (value) => {
  detailPagination = value?.value
  queryTableListInfoAPI()
}

const getTableList = () => {
  tableLoading.value = true
  const params = {
    ...searchModel.value,
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize
  }
  queryTableList(params)
    .then((res) => {
      const { list, total } = res || {}
      pagination.total = total
      tableDataList.value = list
    })
    .finally(() => {
      tableLoading.value = false
    })
}

// 初始化数据
const info = () => {
  getTableList()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
