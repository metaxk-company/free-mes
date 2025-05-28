<template>
  <ContentWrap>
    <common-search
      :conditions-list="searchConditions"
      :search-model="searchModel"
      @query-data="handleQueryData"
    />
  </ContentWrap>
  <ContentWrap>
    <div style="margin-bottom: 20px">
      <el-button
        style="margin-bottom: 20px"
        plain
        v-for="(item, index) in btnConditions"
        :type="item.type"
        :key="index"
        :icon="item.icon"
        :disabled="item.disabled"
        @click="handleBtnOperation(item.state)"
      >
        {{ item.label }}</el-button
      >
      <el-descriptions class="margin-top" :column="2" border>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">人工总工时</div>
          </template>
          {{ totalWorkhours }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">设备总工时</div>
          </template>
          {{ FacilityWorkhours }}
        </el-descriptions-item>
      </el-descriptions>
    </div>
    <common-table
      :loading="loading"
      :columns="tableColumns"
      :tableData="tableFormList"
      :pagination="pagination"
      @pagination-change="handlePagination"
    />
  </ContentWrap>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, btnConditions, tableColumns } from './data'
import { getWorkHoursList, downloadListData } from '@/api/prodMgmt/workHours'
import download from '@/utils/download'

const searchModel = reactive({
  workstationCode: '',
  workshopCode: '',
  processCode: '',
  userName: '',
  machineryCode: '',
  feedbackTime: '',
  finishedTime: ''
})
// 表格分页
let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const loading = ref<boolean>(false)
let tableFormList = reactive([])
// 总工时
let totalWorkhours = ref<string>('')
// 设备总工时
let FacilityWorkhours = ref<string>('')

// 搜索功能
const handleQueryData = () => {
  getTableListInfo()
}

// 按钮点击功能
const handleBtnOperation = (value) => {
  switch (value) {
    case 'download':
      handleDownload()
      break
  }
}

// 导出功能
const handleDownload = async () => {
  const data = await downloadListData()
  download.excel(data, '工时管理.xls')
}

// 表格分页事件
const handlePagination = (value) => {
  pagination = value?.value
  getTableListInfo()
}

// 初始化获取表格数据
const getTableListInfo = () => {
  const params = {
    ...searchModel,
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize
  }
  loading.value = true
  getWorkHoursList(params)
    .then((res) => {
      const { total, list = [] } = res || {}
      totalWorkhours.value = list[0]?.totalWorkhours
      FacilityWorkhours.value = list[0]?.equipmentWorkhours
      pagination.total = total
      console.log(res)

      tableFormList = list[0].list
    })
    .finally(() => {
      loading.value = false
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
