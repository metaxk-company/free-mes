<template>
  <ContentWrap>
    <common-search
      :conditions-list="searchConditions"
      :search-model="searchModel"
      @query-data="handleQueryData"
    />
  </ContentWrap>
  <ContentWrap>
    <!-- <div style="margin-bottom: 20px">
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
    </div> -->

    <common-table
      :loading="loading"
      :isSelection="true"
      :columns="tableColumns"
      :tableData="tableFormList"
      :pagination="pagination"
      @selection-change="handleSelectionChange"
      @pagination-change="handlePagination"
    >
      <template #status="{ scope }">
        <!-- <el-tag :type="produceState(scope.row.status)">
          {{ PRODUCE_STATE[scope.row.status] }}</el-tag
        > -->
        <el-button round :type="produceState(scope.row.status)" @click="handleView(scope.row)">{{
          PRODUCE_STATE[scope.row.status]
        }}</el-button>
      </template>

      <template #operation="{ scope }">
        <div>
          <el-button
            @click="handleSetWorkOrderStatus(scope.row, PRODUCE_PAUSED, '设备已暂停')"
            link
            type="danger"
            v-if="scope.row.status === PRODUCE_STARTED || scope.row.status === PRODUCE_RESUMED"
            >暂停</el-button
          >
          <el-button
            link
            type="warning"
            v-if="scope.row.status.includes(PRODUCE_PAUSED)"
            @click="handleSetWorkOrderStatus(scope.row, PRODUCE_RESUMED, '设备已恢复')"
            >恢复</el-button
          >
          <el-button
            link
            type="success"
            v-if="scope.row.status !== PRODUCE_FINISHED"
            @click="handleSetWorkOrderStatus(scope.row, PRODUCE_FINISHED, '设备已报工')"
            >报工</el-button
          >
          <el-button link type="primary" @click="handleView(scope.row)">查看设备</el-button>
        </div>
      </template>
    </common-table>

    <XModal width="70%" v-model="dialogVisible" title="暂停原因" @before-close="handleClose">
      <el-input type="textarea" :rows="2" placeholder="请输入暂停原因" v-model="textarea" />
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="handleSubmit"> 提交 </el-button>
          <el-button @click="handleClose">返回</el-button>
        </span>
      </template>
    </XModal>
  </ContentWrap>

  <XModal v-model="visible" title="查看生产设备数据明细">
    <el-table border :data="tableDataList">
      <el-table-column align="center" prop="equipmentCode" label="设备编号" />
    </el-table>
    <template #footer>
      <el-divider />
      <el-button @click="visible = false">返回</el-button>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, tableColumns } from './data'
import {
  PRODUCE_STATE,
  PRODUCE_STARTED,
  PRODUCE_PAUSED,
  PRODUCE_RESUMED,
  PRODUCE_FINISHED
} from '@/utils/const'
import {
  getReportWorkList,
  setStateValue,
  downloadListData,
  getReportWorkListInfo
} from '@/api/prodMgmt/reportWork'
import { ElMessage, ElMessageBox } from 'element-plus'
import download from '@/utils/download'
const visible = ref<boolean>(false)
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
let dialogVisible = ref<boolean>(false)
let textarea = ref('')
let stopMessage = reactive<any>({
  tableForm: {},
  status: ''
})
const tableDataList = ref([])
const searchModel = ref({
  // 设备序号
  id: '',
  // 设备状态
  status: '',
  // 报工编号
  feedbackCode: ''
})

const handleView = (value) => {
  getReportWorkListInfo(value.id).then((res) => {
    tableDataList.value = JSON.parse(res.equipmentCode)
    visible.value = true
  })
}

// 状态样式
const produceState = (value) => {
  switch (value) {
    case PRODUCE_STARTED:
      return ''
    case PRODUCE_PAUSED:
      return 'info'
    case PRODUCE_RESUMED:
      return 'success'
    case PRODUCE_FINISHED:
      return 'success'
  }
}

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
  download.excel(data, '报工设备.xls')
}

// 弹框提交事件
const handleSubmit = () => {
  let params = {
    id: stopMessage.tableForm.id,
    remork: textarea.value,
    status: stopMessage.status
  }
  setStateValue(params).then((res) => {
    ElMessage.success('设备已暂停')
    dialogVisible.value = false
    handleClose()
  })
}

// 弹框取消
const handleClose = () => {
  dialogVisible.value = false
  textarea.value = ''
  stopMessage.tableForm = { tableForm: {}, status: '' }
  getTableListInfo()
}

// 表格选择事件
const handleSelectionChange = (value) => {
  selectionID.value = value.map((item) => item?.id)
}

// 表格 - 状态 - 操作
const handleSetWorkOrderStatus = (value, status, mes) => {
  stopMessage.tableForm = value
  stopMessage.status = status
  let params = {
    id: value.id,
    remork: '',
    status
  }
  const confirmMessage = status === PRODUCE_FINISHED ? '是否确认报工？' : null

  const requestSetWorkOrderStatus = () => {
    setStateValue(params).then((res) => {
      ElMessage.success(mes)
      getTableListInfo()
    })
  }

  if (confirmMessage) {
    ElMessageBox.confirm(confirmMessage).then(() => {
      requestSetWorkOrderStatus()
    })
  } else if (status === PRODUCE_PAUSED) {
    dialogVisible.value = true
  } else {
    requestSetWorkOrderStatus()
  }
}

// 表格分页事件
const handlePagination = (value) => {
  pagination = value?.value
  getTableListInfo()
}

// 初始化获取表格数据
const getTableListInfo = () => {
  const params = {
    ...searchModel.value,
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize
  }
  loading.value = true
  getReportWorkList(params)
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
