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
        v-hasPermi="[item.authority]"
        :disabled="item.disabled"
        @click="handleBtnOperation(item.state)"
      >
        {{ item.label }}</el-button
      >
    </div> -->

    <el-row>
      <el-col
        :span="8"
        style="text-align: center"
        v-for="(item, index) in orderQuantity"
        :key="index"
      >
        <div style="margin-bottom: 12px; display: block; font-size: 24px">{{ item.title }}</div>
        <div class="grid-content ep-bg-purple">
          <el-progress type="dashboard" :percentage="item.percentage" :color="colors">
            <template #default="{ percentage }">
              <CountTo class="text-20px" :start-val="0" :end-val="percentage" :duration="3600" />
            </template>
          </el-progress>
        </div>
      </el-col>
    </el-row>
    <el-divider />
    <common-table
      :loading="loading"
      :columns="tableColumns"
      :tableData="tableFormList"
      :pagination="pagination"
      @pagination-change="handlePagination"
    >
      <template #pauseReason="{ scope }">
        <el-input v-model="scope.row.pauseReason" type="textarea" disabled />
      </template>

      <template #status="{ scope }">
        <el-tag :type="produceState(scope.row.status)">
          {{ PRODUCE_STATE[scope.row.status] }}</el-tag
        >
        <!-- <el-button :type="produceState(scope.row.status)" round>
          {{ PRODUCE_STATE[scope.row.status] }}</el-button
        > -->
      </template>
      <template #reportingProgress="{ scope }">
        <el-progress
          :color="customColorMethod"
          :text-inside="true"
          :stroke-width="26"
          :percentage="
            isNaN(parseInt(scope.row.reportingProgress)) ? 0 : parseInt(scope.row.reportingProgress)
          "
        />
      </template>
      <template #operation="{ scope }">
        <div>
          <el-button
            size="large"
            round
            type="primary"
            v-if="scope.row.status.includes(PRODUCE_PAUSED)"
            @click="handleSetWorkOrderStatus(scope.row, PRODUCE_RESUMED, '恢复成功')"
            >恢复</el-button
          >
          <el-button
            @click="handleSetWorkOrderStatus(scope.row, PRODUCE_PAUSED, '暂停成功')"
            size="large"
            round
            type="danger"
            v-if="scope.row.status === PRODUCE_STARTED || scope.row.status === PRODUCE_RESUMED"
            >暂停</el-button
          >
          <el-button
            size="large"
            round
            type="success"
            v-if="scope.row.status !== PRODUCE_FINISHED && scope.row.status !== NO_STARTED"
            @click="handleSetWorkOrderStatus(scope.row, PRODUCE_FINISHED, '报工成功')"
            >报工</el-button
          >
          <el-button
            size="large"
            round
            type="success"
            v-if="scope.row.status == NO_STARTED"
            @click="handleSetWorkOrderStatus(scope.row, PRODUCE_STARTED, '开工成功')"
            >开工</el-button
          >
          <!-- <el-button
            link
            type="warning"
            v-if="scope.row.status !== PRODUCE_FINISHED"
            @click="handleTableUpDate(scope.row)"
            >修改</el-button
          > -->
          <el-button @click="handleReadProcessInfo(scope.row)" type="primary" size="large" round
            >查看</el-button
          >
        </div>
      </template>
    </common-table>
  </ContentWrap>

  <XModal
    draggable
    width="50%"
    v-model="dialogVisible"
    :title="dialogTitle"
    @before-close="handleClose"
  >
    <el-form :model="processForm" status-icon :rules="rules" ref="ruleFormRef" label-width="170px">
      <el-row>
        <el-col :span="11">
          <el-form-item label="任务单号" prop="taskCode">
            <el-input
              ref="taskCodeRef"
              show-word-limit
              disabled
              v-model="processForm.taskCode"
              @change="handleTaskCodeEnter"
            /> </el-form-item
        ></el-col>
        <el-col :span="11">
          <el-form-item label="数量">
            <el-input-number
              ref="quantityRef"
              v-model="processForm.quantity"
              :min="1"
              :max="9999"
              :disabled="viewState == 'view'"
            /> </el-form-item
        ></el-col>
        <!-- <el-col :span="8">
          <el-form-item label="工时">
            <el-input-number
              ref="workHourRef"
              v-model="processForm.workHour"
              :min="0"
              :max="9999"
              :disabled="viewState == 'view'"
            /> </el-form-item
        ></el-col> -->
      </el-row>
    </el-form>
    <CommonTable
      :columns="PRODUCE_COLUMNS"
      :tableData="produceTableData"
      :pagination="producePagination"
      :isSinglePage="true"
    >
      <template #equipmentCode="{ scope }">
        <!-- <el-input
          v-if="viewState != 'view'"
          clearable
          placeholder="请输入设备编号"
          v-model="scope.row.equipmentCode"
        /> -->
        <el-select
          v-if="viewState != 'view'"
          v-model="scope.row.equipmentCode"
          placeholder="请选择设备编号"
        >
          <el-option
            v-for="item in availableOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <div v-else>
          {{ scope.row.equipmentCode }}
        </div>
      </template>

      <template #operate="{ scope }" v-if="viewState != 'view'">
        <el-button @click="handleAdd" link type="primary"> 添加</el-button>
        <el-button @click="handleDelete(scope)" link type="danger">删除</el-button>
      </template>
    </CommonTable>

    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="handleSubmit" v-if="viewState != 'view'">
          提交
        </el-button>
        <el-button @click="handleClose">返回</el-button>
      </span>
    </template>
  </XModal>

  <XModal
    draggable
    title="暂停原因"
    v-model="suspendVisible"
    width="25%"
    @before-close="handleSuspendClose"
  >
    <el-form ref="ruleFormRef" :model="suspendForm" :rules="rules" status-icon>
      <el-form-item label="暂停原因" prop="suspendMessage">
        <!-- <el-input v-model="suspendForm.suspendMessage" /> -->
        <el-select v-model="suspendForm.suspendMessage" placeholder="请选择暂停原因">
          <el-option
            v-for="item in suspendDataList"
            :key="item.label"
            :label="item.label"
            :value="item.label"
          />
        </el-select>
      </el-form-item>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="handleSuspendSubmit"> 提交 </el-button>
        <el-button @click="handleSuspendClose">返回</el-button>
      </span>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, tableColumns, PRODUCE_COLUMNS } from './data'
import type { FormInstance, FormRules } from 'element-plus'
import {
  PRODUCE_STATE,
  PRODUCE_STARTED,
  PRODUCE_PAUSED,
  PRODUCE_RESUMED,
  PRODUCE_FINISHED,
  NO_STARTED
} from '@/utils/const'
import {
  getProductionList,
  queryProductionListInfo,
  setWorkOrderStatus,
  getStopReason,
  getQuantityNotStarted,
  getQuantityCompleted,
  getQuantityPaused
} from '@/api/prodMgmt/reportWorkTwo'
import { ElMessage, ElMessageBox } from 'element-plus'
import { queryDeviceType } from '@/api/masterData/workstation'

const colors = [
  { color: '#f56c6c', percentage: 20 },
  { color: '#e6a23c', percentage: 40 },
  { color: '#5cb87a', percentage: 60 },
  { color: '#1989fa', percentage: 80 },
  { color: '#6f7ad3', percentage: 100 }
]

const ruleFormRef = ref<FormInstance>()
const taskCodeRef = ref<any>(null)
const quantityRef = ref<any>(null)
const rules = reactive<FormRules>({
  // taskCode: [{ required: true, message: '请输入任务单号', trigger: 'blur' }]
  suspendMessage: [{ required: true, message: '请选择暂停原因', trigger: 'blur' }]
})
const searchModel = reactive({
  status: '',
  workorderCode: '',
  taskCode: '',
  itemName: '',
  userName: ''
})
// 表格分页
let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})

let producePagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const loading = ref<boolean>(false)
const dialogVisible = ref<boolean>(false)
let tableFormList = reactive([])
let dialogTitle = ref<string>('')
let selectionID = ref([])
let processForm = ref({
  taskCode: '',
  quantity: 1,
  workHour: 0,
  id: ''
})
let viewState = ref('')
let produceTableData = ref<any>([{ equipmentCode: '' }])
let facilitySelectList = ref<any>([])
// 暂停对话框
let suspendVisible = ref()
let suspendForm = ref({
  suspendMessage: ''
})

/*
{
  notWork: 0,
  fulfilWork: 0,
  suspendWork: 0
}
*/

// 订单数量汇总
const orderQuantity = ref([
  {
    percentage: 0,
    title: '未开工订单数量'
  },
  {
    percentage: 0,
    title: '已报工订单数量'
  },
  {
    percentage: 0,
    title: '已暂停订单数量'
  }
])

const customColorMethod = (percentage: number) => {
  if (percentage < 30) {
    return '#909399'
  }
  if (percentage < 70) {
    return '#e6a23c'
  }
  return '#67c23a'
}

// 获取订单数据
const getOrderQuantity = async () => {
  try {
    const results = await Promise.all([
      getQuantityNotStarted(),
      getQuantityCompleted(),
      getQuantityPaused()
    ])

    orderQuantity.value[0].percentage = results[0].totalQuentity
    orderQuantity.value[1].percentage = results[1].totalQuentity
    orderQuantity.value[2].percentage = results[2].totalQuentity
  } catch (error) {
    console.error('获取订单数据失败', error)
  }
}

const availableOptions = computed(() => {
  const selectedValues = produceTableData.value.map((table) => table.equipmentCode)
  return facilitySelectList.value.filter((option) => !selectedValues.includes(option.value))
})

// 搜索功能
const handleQueryData = () => {
  getTableListInfo()
}

// 状态样式
const produceState = (value) => {
  switch (value) {
    case PRODUCE_STARTED:
      return 'success'
    case PRODUCE_PAUSED:
      return 'info'
    case PRODUCE_RESUMED:
      return 'success'
    case PRODUCE_FINISHED:
      return 'success'
  }
}

// 按钮点击功能
// const handleBtnOperation = (value) => {
//   switch (value) {
//     case 'save':
//       addProcessForm()
//       break
//     case 'edit':
//       handleTableUpDate()
//       break
//     case 'remove':
//       handleDeleteTable()
//       break
//   }
// }

// 表格选择事件
// const handleSelectionChange = (value) => {
//   selectionID.value = value.map((item) => item?.id)
//   btnConditions[1].disabled = !selectionID.value.length || selectionID.value.length >= 2
//   btnConditions[2].disabled = !selectionID.value.length || selectionID.value.length >= 2
// }

const requestSetWorkOrderStatus = (params, msg) => {
  return new Promise<void>((resolve, reject) => {
    setWorkOrderStatus(params).then((res) => {
      ElMessage.success(msg)
      getTableListInfo()
      getOrderQuantity()
      resolve(res)
    })
  })
}

let paramSubmit = ref({})
// 表格 - 状态 - 操作
const handleSetWorkOrderStatus = (tableValue, status, msg) => {
  let params = {
    status,
    taskCode: tableValue.taskCode,
    userName: JSON.parse(localStorage.userForm).username
  }

  paramSubmit.value = params

  switch (status) {
    case PRODUCE_PAUSED:
      suspendVisible.value = true
      break
    case PRODUCE_FINISHED:
      processForm.value.taskCode = tableValue.taskCode
      produceTableData.value = JSON.parse(tableValue.equipmentCode)
      dialogTitle.value = '生产报工'
      dialogVisible.value = true
      break
    default:
      requestSetWorkOrderStatus(params, msg)
      break
  }

  // if (confirmMessage) {
  //   processForm.value.taskCode = taskCode
  //   dialogTitle.value = '报工生产报工'
  //   dialogVisible.value = true
  //   // ElMessageBox.confirm(confirmMessage).then(() => {
  //   //   requestSetWorkOrderStatus()
  //   // })
  // } else {
  //   requestSetWorkOrderStatus()
  // }
}

// 暂停提交
const handleSuspendSubmit = () => {
  if (!ruleFormRef.value) return
  ruleFormRef.value.validate((valid, fields) => {
    if (valid) {
      const params = { ...paramSubmit.value, pauseReason: suspendForm.value.suspendMessage }
      requestSetWorkOrderStatus(params, '暂停成功').then((res) => {
        handleSuspendClose()
      })
    }
  })
}

// 暂停弹框的返回
const handleSuspendClose = () => {
  if (!ruleFormRef.value) return
  ruleFormRef.value.resetFields()
  suspendVisible.value = false
}

// 新增
// const addProcessForm = () => {
//   dialogTitle.value = '添加生产报工'
//   dialogVisible.value = true
//   nextTick(() => {
//     setTimeout(() => {
//       taskCodeRef.value.focus()
//     })
//   })
// }

// 任务单号回车功能
const handleTaskCodeEnter = () => {
  quantityRef.value.focus()
}

// 数量回车功能
// const handleQuantityEnter = () => {
//   workHourRef.value.focus()
// }

// 修改
// const handleTableUpDate = (value?) => {
//   dialogTitle.value = '修改生产报工'
//   const dataID = value?.id || selectionID.value[0]
//   queryProcessInfoAPI(dataID)
// }

// 删除
// const handleDeleteTable = () => {
//   const id = selectionID.value[0]
//   ElMessageBox.confirm('是否删除当前数据?').then(() => {
//     deleteProductionListInfo(id).then((res) => {
//       ElMessage.success('删除成功')
//       getTableListInfo()
//     })
//   })
// }

// 查看
const handleReadProcessInfo = (value) => {
  dialogTitle.value = '查看报工数据'
  const dataID = value?.id || selectionID.value[0]
  viewState.value = 'view'
  queryProcessInfoAPI(dataID)
}

// 获取工序详情数据
const queryProcessInfoAPI = (value) => {
  queryProductionListInfo(value).then((res) => {
    processForm.value = {
      taskCode: res.taskCode,
      quantity: Number(res.quantity),
      workHour: Number(res.workHour),
      id: res.id
    }
    produceTableData.value = JSON.parse(res.equipmentCode) || []
    dialogVisible.value = true
  })
}

// 表格分页事件
const handlePagination = (value) => {
  pagination = value?.value
  getTableListInfo()
}

// 生产报工 - 增加表格
const handleAdd = () => {
  const row = { equipmentCode: '' }
  produceTableData.value.push(row)
}

const handleDelete = (value) => {
  if (produceTableData.value.length === 1) return ElMessage.error('已经是最后一个了')
  const { row } = value || {}
  if (row && row.equipmentCode != '') {
    ElMessageBox.confirm('是否删除该参数?').then(() => {
      produceTableData.value.splice(value.$index, 1)
    })
  } else {
    produceTableData.value.splice(value.$index, 1)
  }
}

// 表单提交
const handleSubmit = async () => {
  const params = {
    ...paramSubmit.value,
    quantity: processForm.value.quantity,
    equipmentCode: produceTableData.value
  }
  requestSetWorkOrderStatus(params, '报工成功').then((res) => {
    handleClose()
  })

  // let params = {
  //   taskCode: processForm.value.taskCode,
  //   quantity: processForm.value.quantity,
  //   workHour: processForm.value.workHour,
  //   equipmentCode: produceTableData.value,
  //   userName: JSON.parse(localStorage.userForm).username
  // }
  // if (!ruleFormRef.value) return
  // await ruleFormRef.value.validate((valid) => {
  //   if (valid) {
  //     if (processForm.value.id) {
  //       upDateProductionListInfo(processForm.value.id, params).then((res) => {
  //         ElMessage.success('修改成功')
  //         handleClose()
  //         getTableListInfo()
  //       })
  //     } else {
  //       addProductionInfo(params).then((res) => {
  //         ElMessage.success('新增成功')
  //         handleClose()
  //         getTableListInfo()
  //       })
  //     }
  //   }
  // })
}

// 表单关闭
const handleClose = () => {
  if (!ruleFormRef.value) return
  ruleFormRef.value.resetFields()
  processForm.value = {
    taskCode: '',
    quantity: 0,
    workHour: 0,
    id: ''
  }
  produceTableData.value = [{ equipmentCode: '' }]
  viewState.value = ''
  dialogVisible.value = false
}

// 初始化获取表格数据
const getTableListInfo = () => {
  const params = {
    ...searchModel,
    assignUsername: JSON.parse(localStorage.userForm).username,
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize
  }
  loading.value = true
  getProductionList(params)
    .then((res) => {
      const { total, list = [] } = res || {}
      pagination.total = total
      tableFormList = list
    })
    .finally(() => {
      loading.value = false
    })
}

// 获取设备列表参数
const getQueryDeviceType = () => {
  let params = {}

  queryDeviceType(params).then((res) => {
    facilitySelectList.value = res.list.map((item) => {
      return {
        value: item.machineryCode,
        label: item.machineryCode,
        disabled: false
      }
    })
  })
}

let suspendDataList = ref<any>([])

// 获取暂停下拉参数
const getSuspendDataList = () => {
  getStopReason().then((res) => {
    suspendDataList.value = res
  })
}

const info = () => {
  getTableListInfo()
  getQueryDeviceType()
  getSuspendDataList()
  getOrderQuantity()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss">
.percentage-value {
  display: block;
  font-size: 28px;
}

:deep .el-progress-circle {
  height: 76px !important;
  width: 76px !important;
}
</style>
