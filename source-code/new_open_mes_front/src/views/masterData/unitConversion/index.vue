<template>
  <ContentWrap>
    <search-model
      :search-list="searchList"
      @handle-reset-search="handleResetSearch"
      @handle-search="handleSearch"
    />
  </ContentWrap>
  <ContentWrap>
    <table-model
      :table-data-list="tableDataList"
      :pagination-data="pagination"
      @handle-pagination="handlePagination"
      @handle-renew-data="handleRenewData"
    />
  </ContentWrap>
</template>

<script setup lang="ts">
import { reactive, onMounted } from 'vue'
import searchModel from './components/searchModel.vue'
import tableModel from './components/tableModel.vue'
import { IPagination } from './data'
import * as unitAPI from '@/api/masterData/unitConversion'

onMounted(() => {
  getTableListInfo()
})

// 初始化获取表格数据
const getTableListInfo = () => {
  let params = {
    pageNo: pagination.pageNo,
    pageSize: pagination.pageSize,
    quantityFrom: searchList.quantityFrom,
    unitCode: null,
    quantityTo: searchList.quantityTo,
    unitTo: searchList.unitTo
  }

  unitAPI.queryUnitConversionList(params).then((res) => {
    const { code } = res || {}
    if (code !== 200) return
  })
}

const searchList = reactive({
  // 数值
  quantityFrom: '',
  // 转换后数值
  quantityTo: '',
  // 现单位名称
  unitTo: ''
})

const tableDataList = reactive([
  { quantityFrom: '数据一', unitName: 2, quantityTo: 3, conversionId: 999 },
  { quantityFrom: '数据二', unitName: 2, quantityTo: 3, conversionId: 666 }
])

const pagination = reactive({
  total: 100,
  pageNo: 1,
  pageSize: 10
})

// 重置功能
const handleResetSearch = () => {
  searchList.quantityFrom = ''
  searchList.quantityTo = ''
  searchList.unitTo = ''
  getTableListInfo()
}

// 查询功能
const handleSearch = () => {}

// 分页功能
const handlePagination = (value: IPagination) => {
  pagination.pageNo = value.page
  pagination.pageSize = value.limit
  getTableListInfo()
}

// 表格更新操作
const handleRenewData = () => {
  getTableListInfo()
}
</script>

<style scoped lang="scss"></style>
