<template>
  <ContentWrap>
    <!-- <searchMode
      :search-params="searchParams"
      @handle-search="handleSearch"
      @handle-reset-search="handleResetSearch"
    /> -->
    <common-search
      :conditions-list="searchConditionsClient"
      :search-model="searchModel"
      @query-data="handleQueryData"
      ref="CommonSearchRef"
    />
  </ContentWrap>
  <ContentWrap>
    <table-model
      @renewal-data="handleUpdateData"
      :table-loading="tableLoading"
      :table-form-list="tableFormList"
      :pagination-data="pagination"
      @handle-pagination="handlePagination"
    />
  </ContentWrap>
</template>

<script setup lang="ts">
import { reactive, onMounted } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import tableModel from '../supplierManage/components/tableModel.vue'
import { searchConditionsClient } from './data'
import { queryVendorList } from '@/api/masterData/supplierManage'

const tableLoading = ref<boolean>(false)
const searchModel = reactive({
  // 供应商编码
  vendorCode: '',
  // 供应商名称
  vendorName: '',
  // 供应商简称
  vendorNick: '',
  // 客户英文名称
  vendorEn: '',
  // 是否启用
  enableFlag: '',
  // 供应商电话
  tel: '',
  // 供应商等级
  vendorLevel: ''
})

const pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  currentPage: 1, // 当前页数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100] //每页显示个数选择器的选项设置
})

// 表格参数值
let tableFormList = reactive([])

// 搜索功能
const handleQueryData = () => {
  getTableListInfo()
}

const handleUpdateData = () => {
  getTableListInfo()
}

// 分页功能
const handlePagination = (value) => {
  const { currentPage, pageSize, pageSizes, total } = value
  pagination.currentPage = currentPage
  pagination.pageSize = pageSize
  pagination.pageSizes = pageSizes
  pagination.total = total
  getTableListInfo()
}

// 获取表格参数
const getTableListInfo = () => {
  tableLoading.value = true
  const params = {
    ...searchModel,
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize
  }
  queryVendorList(params)
    .then((res) => {
      const { list, total } = res || {}
      tableFormList = list
      pagination.total = total
    })
    .finally(() => {
      tableLoading.value = false
    })
}

const info = () => {
  getTableListInfo()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
