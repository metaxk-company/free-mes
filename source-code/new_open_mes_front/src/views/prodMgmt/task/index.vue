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
    </div>

    <common-table
      :loading="loading"
      :isSelection="true"
      :columns="tableColumns"
      :tableData="tableFormList"
      :pagination="pagination"
      @selection-change="handleSelectionChange"
      @pagination-change="handlePagination"
    >
      <template #colorCode="{ scope }">
        <el-color-picker v-model="scope.row.colorCode" disabled />
      </template>
    </common-table>
  </ContentWrap>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, btnConditions, tableColumns } from './data'
import { queryTaskList, deleteTaskList, downloadListData } from '@/api/prodMgmt/task'
import { ElMessage, ElMessageBox } from 'element-plus'
import download from '@/utils/download'

const searchModel = reactive({
  workstationName: '',
  taskCode: '',
  taskName: '',
  workorderCode: '',
  processCode: '',
  processName: '',
  startTime: '',
  endTime: ''
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
let selectionID = ref([])

// 搜索功能
const handleQueryData = () => {
  getTableListInfo()
}

// 按钮点击功能
const handleBtnOperation = (value) => {
  switch (value) {
    case 'remove':
      handleDeleteTable()
      break
    case 'download':
      handleDownload()
      break
  }
}

// 导出功能
const handleDownload = async () => {
  const data = await downloadListData()
  download.excel(data, '工序设置.xls')
}
// 表格选择事件
const handleSelectionChange = (value) => {
  selectionID.value = value.map((item) => item?.id)
  btnConditions[0].disabled = !selectionID.value.length
}

// 删除
const handleDeleteTable = (value?) => {
  const id = value || selectionID.value
  const deleteID = Array.isArray(id) ? id : [id]
  ElMessageBox.confirm('是否删除当前数据?').then(() => {
    deleteTaskList(deleteID).then((res) => {
      ElMessage.success('删除成功')
      getTableListInfo()
    })
  })
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
  queryTaskList(params)
    .then((res) => {
      const { total, list = [] } = res || {}
      pagination.total = total
      tableFormList = list
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
