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
      <el-tooltip
        class="box-item"
        effect="dark"
        content="同步第三方订单时使用"
        placement="top-start"
      >
        <el-button
          type="primary"
          :icon="'RefreshLeft'"
          @click="handleSyncData"
          :loading="btnLoading"
          >同步订单</el-button
        >
      </el-tooltip>
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
import { searchConditions, tableColumns } from './data'
import { syncTableList, syncDataInfo } from '@/api/prodMgmt/synchronize'
import { ElMessage } from 'element-plus'

const searchModel = reactive({
  workorderCode: '',
  startTime: '',
  requestDate: '',
  productName: ''
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
let btnLoading = ref<boolean>(false)

// 搜索功能
const handleQueryData = () => {
  getTableListInfo()
}

// 同步订单功能
const handleSyncData = () => {
  btnLoading.value = true
  syncDataInfo()
    .then((res) => {
      const data = getTableListInfo()
      data.then(() => {
        ElMessage.success('同步成功')
      })
    })
    .finally(() => {
      btnLoading.value = false
    })
}

// 表格分页事件
const handlePagination = (value) => {
  pagination = value?.value
  getTableListInfo()
}

// 初始化获取表格数据
const getTableListInfo = () => {
  return new Promise<void>((resolve, reject) => {
    const params = {
      ...searchModel,
      pageNo: pagination.currentPage,
      pageSize: pagination.pageSize
    }
    loading.value = true
    syncTableList(params)
      .then((res) => {
        const { total, list = [] } = res || {}
        resolve(res)
        pagination.total = total
        tableFormList = list
      })
      .finally(() => {
        loading.value = false
      })
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
