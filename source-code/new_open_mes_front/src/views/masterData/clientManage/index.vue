<template>
  <ContentWrap>
    <search-model
      :search-params="searchParams"
      @handle-search="handleSearch"
      @handle-reset-search="handleResetSearch"
    />
  </ContentWrap>
  <ContentWrap>
    <table-model
      :table-form-list="tableFormList"
      :pagination-data="pagination"
      @handle-pagination="handlePagination"
      @up-date-table-list="handleUpDateTableList"
      :table-loading="tableLoading"
    />
  </ContentWrap>
</template>

<script setup lang="ts">
import { reactive, onMounted } from 'vue'
import searchModel from './components/searchModel.vue'
import tableModel from './components/tableModel.vue'
import { emptyValue } from '@/utils'
import { getListClientList } from '@/api/masterData/clientManage'

onMounted(() => {
  getTableListInfo()
})

const searchParams = reactive({
  // 客户编码
  clientCode: '',
  // 客户名称
  clientName: '',
  // 客户简称
  clientNick: '',
  // 客户英文名称
  clientEn: '',
  // 是否启用
  enableFlag: '',
  //客户名称
  searchParams: '',
  // 客户电话
  tel: ''
})

const pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const tableLoading = ref<boolean>(false)
// 表格参数值
let tableFormList = ref([])

// 表单提交更新数据
const handleUpDateTableList = () => {
  getTableListInfo()
}

// 初始化获取表格内容
const getTableListInfo = () => {
  const params = {
    ...searchParams,
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize
  }
  tableLoading.value = true
  getListClientList(params)
    .then((res) => {
      tableFormList.value = res.list
      pagination.total = res.total
    })
    .finally(() => {
      tableLoading.value = false
    })
}

// 搜索功能
const handleSearch = () => {
  getTableListInfo()
}

// 重置功能
const handleResetSearch = () => {
  clearSearchData()
  getTableListInfo()
}

// 清空搜索内容
const clearSearchData = () => {
  for (const key in searchParams) {
    searchParams[key] = emptyValue(searchParams[key])
  }
}

// 分页功能
const handlePagination = (value) => {
  const { currentPage, pageSize } = value?.value || {}
  pagination.currentPage = currentPage
  pagination.pageSize = pageSize
  getTableListInfo()
}
</script>

<style scoped lang="scss"></style>
