<template>
  <ContentWrap>
    <common-search
      :conditions-list="searchConditionsClient"
      :search-model="searchModel"
      @query-data="handleQueryData"
      ref="CommonSearchRef"
    />
  </ContentWrap>
  <ContentWrap>
    <tableModel
      :table-loading="tableLoading"
      :pagination-data="paginationData"
      :table-data-list="tableDataList"
      @handle-pagination="handlePagination"
      @renovate-info="handleRenovateInfo"
    />
  </ContentWrap>
</template>

<script setup lang="ts">
import { reactive, onMounted } from 'vue'
import tableModel from './components/tableModel.vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import { searchConditionsClient } from './data'
import { queryWorkshopList } from '@/api/masterData/workShopSetUp'
// 搜索字段
const searchModel = reactive({
  // 车间编码
  workshopCode: '',
  // 车间名称
  workshopName: '',
  // 负责人
  charge: ''
})
const tableLoading = ref<boolean>(false)

// 分页字段
const paginationData = reactive<Table.Pagination>({
  total: 20, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})

// 表格数据
let tableDataList = reactive([])

// 获取表格数据
const getTableDataList = () => {
  tableLoading.value = true
  const params = {
    pageNo: paginationData.currentPage,
    pageSize: paginationData.pageSize,
    ...searchModel
  }
  queryWorkshopList(params)
    .then((res) => {
      const { list, total } = res || {}
      tableDataList = list
      paginationData.total = total
    })
    .finally(() => {
      tableLoading.value = false
    })
}

// 搜索
const handleQueryData = () => {
  getTableDataList()
}

// 分页事件
const handlePagination = (value) => {
  const { currentPage, pageSize } = value || {}
  paginationData.currentPage = currentPage
  paginationData.pageSize = pageSize
  getTableDataList()
}

const handleRenovateInfo = () => {
  getTableDataList()
}

// 初始化
const init = () => {
  getTableDataList()
}

onMounted(() => {
  init()
})
</script>

<style scoped lang="scss"></style>
