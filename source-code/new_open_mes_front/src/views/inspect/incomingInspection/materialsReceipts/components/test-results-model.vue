<template>
  <!-- 检验单据 -->
  <el-descriptions title="来料检验结果基本信息" :column="3">
    <el-descriptions-item label="检验单号">{{
      testResultsList.obj.recordCode
    }}</el-descriptions-item>
    <el-descriptions-item label="质检员">{{
      testResultsList.obj.inspectUser
    }}</el-descriptions-item>
    <el-descriptions-item label="检查方式">{{
      MARRY_INSPECTION_METHOD[testResultsList.obj.inspectWay]
    }}</el-descriptions-item>

    <el-descriptions-item label="检验开始时间">{{
      testResultsList.obj.inspectStartTime
    }}</el-descriptions-item>
    <el-descriptions-item label="检验完成时间">{{
      testResultsList.obj.inspectEndTime
    }}</el-descriptions-item>
  </el-descriptions>

  <el-divider content-position="center">来料检验结果统计明细</el-divider>
  <common-table
    :pagination="pagination"
    :tableData="tableData"
    :columns="columnTakeNotes"
    v-loading="loadingTable"
    :isShowPagination="false"
    @pagination-change="handlePagination"
  >
    <!-- <template #quantity="{ scope }">{{
      scope.row.qualityInspectionFrom.receiveRecord.inspectFlag == '1'
        ? scope.row.qualityInspectionFrom.receiveRecord.checkNumber
        : scope.row.qualityInspectionFrom.receiveRecord.quantity
    }}</template> -->

    <template #passRate="{ scope }"> {{ scope.row.passRate }}% </template>

    <template #failureRate="{ scope }"> {{ scope.row.failureRate }}% </template>
  </common-table>
</template>

<script setup lang="ts">
import commonTable from '@/components/CommonTable/index.vue'
import { ref, onMounted } from 'vue'
import { MARRY_INSPECTION_METHOD } from '@/utils/const'
import { columnTakeNotes } from '../data'

const props = defineProps({
  testResultsList: {
    type: Object,
    default: () => {}
  }
})

let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
let loadingTable = ref(false)
const tableData = ref(props.testResultsList.list)

const handlePagination = (value) => {
  pagination = value
}

onMounted(() => {})
</script>

<style scoped lang="scss"></style>
