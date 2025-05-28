<template>
  <ContentWrap>
    <common-search
      :conditions-list="searchConditions"
      :search-model="searchModel"
      @query-data="handleQueryData"
  /></ContentWrap>
  <ContentWrap>
    <statistical-charts />
  </ContentWrap>
  <ContentWrap>
    <common-table
      :pagination="pagination"
      :tableData="tableDataList"
      :columns="columnState"
      :loading="tableLoading"
    >
      <template #operate="{ scope }">
        <el-button type="" @click="handleViewDetails(scope.row)">查看详情</el-button>
      </template>
    </common-table>
  </ContentWrap>
  <XModal v-model="visible" title="查看报工明细" width="60%">
    <WorkerReport :visible="'view'" :form-data="formData" />
    <template #footer></template>
  </XModal>
</template>

<script setup lang="ts">
import commonSearch from '@/components/CommonSearch/index.vue'
import statisticalCharts from './components/statisticalCharts.vue'
import commonTable from '@/components/CommonTable/index.vue'
import { searchConditions, columnState } from './data'
import { ref } from 'vue'
import WorkerReport from '@/views/workerReport/index.vue'

const formData = ref({})
const searchModel = ref({
  nameOfPerson: '',
  personnelNo: '',
  startDate: ''
})
const visible = ref<boolean>(false)
const pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const tableDataList = ref([
  {
    taskOrder: '4',
    startDate: '12312',
    nameOfPerson: '4',
    personnelNo: '4',
    workingType: '3',
    hoursWork: 0,
    productionQuantities: 0,
    Workbench: '2',
    remark: '1'
  }
  // {
  //   taskOrder: '3333',
  //   startDate: '3333'
  // }
])
const tableLoading = ref<boolean>(false)

// 搜索
const handleQueryData = () => {}

const handleViewDetails = (value) => {
  formData.value = value
  visible.value = true
  console.log(formData.value)
}
</script>
