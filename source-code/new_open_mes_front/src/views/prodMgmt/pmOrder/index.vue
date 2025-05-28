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
        :loading="item.loading"
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
      <template #workorderCode="{ scope }">
        <el-button link type="primary" @click="handleWordOrderInfo(scope.row)">{{
          scope.row.workorderCode
        }}</el-button>
      </template>
      <template #productionSchedule="{ scope }">
        <el-progress
          :color="customColorMethod"
          :text-inside="true"
          :stroke-width="26"
          :percentage="
            isNaN(parseInt(scope.row.productionSchedule))
              ? 0
              : parseInt(scope.row.productionSchedule)
          "
        />
      </template>
      <template #orderSource="{ scope }">
        {{ SOURCE_TYPE_TEXT[scope.row.orderSource] }}
      </template>
      <template #status="{ scope }">
        {{ APPROVAL_STATUS[scope.row.status] }}
      </template>

      <template #operation="{ scope }">
        <el-button
          link
          type="primary"
          v-if="scope.row.status == 'CONFIRMED'"
          @click="handleWordOrderInfo(scope.row)"
          >详情</el-button
        >
        <el-button link type="primary" @click="handleQueryTask(scope.row)">查看任务</el-button>
        <el-button link type="primary" @click="handleTableUpData(scope.row)">修改</el-button>
        <el-button link type="danger" @click="handleWorkOrderAPI(scope.row)">删除</el-button>
      </template>
    </common-table>
  </ContentWrap>
  <!-- 添加或修改生产订单对话框 -->

  <orderDialog :pm-order-form="pmOrderForm" @up-date-table-list="handleUpDataData" />

  <XModal v-model="printVisible" width="60%" title="打印任务单">
    <el-button v-print="printObj" type="primary" icon="printer">打印</el-button>
    <el-divider />
    <div id="printMe" style="height: 400px">
      <print-content :print-data-info="printDataInfo" />
    </div>
    <template #footer>
      <el-divider />

      <el-button @click="printVisible = false">关闭</el-button>
    </template>
  </XModal>

  <!-- 任务订单弹框 -->
  <XModal v-model="taskVisible" width="80%" title="生产任务详情" :key="Math.random()">
    <common-table
      :loading="taskLoading"
      :columns="taskTableColumns"
      :tableData="taskTableFormList"
      :pagination="taskPagination"
      @pagination-change="handleTaskPagination"
    >
      <template #status="{ scope }">
        <el-tag :type="produceState(scope.row.status)">
          {{ PRODUCE_STATE[scope.row.status] }}</el-tag
        >
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
    </common-table>
  </XModal>
</template>

<script setup lang="ts">
import { reactive, onMounted, ref } from 'vue'
import CommonTable from '@/components/CommonTable/index.vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import orderDialog from '@/views/prodMgmt/pmOrder/components/orderDialog.vue'
import { searchConditions, btnConditions, tableColumns, taskTableColumns } from './data'
import {
  getListWorkOrder,
  getWorkOrderInfo,
  deleteWorkOrderId,
  queryPrintInfo,
  downloadListData,
  getQueryTask
} from '@/api/prodMgmt/pmOrder'
import { ElMessage, ElMessageBox } from 'element-plus'
import printContent from './components/printContent.vue'
import download from '@/utils/download'
import { SOURCE_TYPE_TEXT, APPROVAL_STATUS } from '@/utils/const'
import {
  PRODUCE_STATE,
  PRODUCE_STARTED,
  PRODUCE_PAUSED,
  PRODUCE_RESUMED,
  PRODUCE_FINISHED
} from '@/utils/const'

// 获取打印数据
const printDataInfo = ref([])
const printObj = reactive({
  id: 'printMe'
})
let printVisible = ref<boolean>(false)
// 搜索参数
const searchModel = reactive({
  // 订单编码
  workorderCode: '',
  // 订单名称
  workorderName: '',
  // 来源单据
  // sourceCode: '',
  // 产品编号
  productCode: '',
  // 产品名称
  productName: '',
  // 客户编码
  // clientCode: '',
  // 客户名称
  clientName: '',
  // 需求日期
  requestDate: ''
})
// 表格数据
let tableFormList = ref([])
// 表格加载状态
let loading = ref<boolean>(false)
// 表格分页
const pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
// 任务表格分页
let taskPagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const taskVisible = ref(false)
// 表单数据
const pmOrderForm = reactive<any>({
  visible: false,
  tips: '新增数据',
  state: 'view',
  ruleForm: {
    // 订单编号
    workorderCode: '',
    // 自动生成编码
    autoGenFlag: false,
    // 订单名称
    workorderName: '',
    // 来源类型
    orderSource: 'STORE',
    // // 订单编号
    // sourceCode: '',
    // 产品编号
    productCode: '',
    // 产品名称
    productName: '',
    // 规格型号
    productSpc: '',
    // 单位
    unitOfMeasure: '',
    // 订单数量
    quantity: 1,
    // 需求日期
    requestDate: '',
    // 批次号
    batchCode: '',
    // 客户编码
    clientCode: '',
    // 客户名称
    clientName: '',
    // 备注
    remark: '',
    // 订单ID
    id: null,
    // 订购日期
    orderDate: '',
    // 生产日期
    produceDate: '',
    //
    //
    parentId: null
  }
})
const taskTableFormList = ref([])
const taskTab = ref()
const taskLoading = ref()

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

const customColorMethod = (percentage: number) => {
  if (percentage < 30) {
    return '#909399'
  }
  if (percentage < 70) {
    return '#e6a23c'
  }
  return '#67c23a'
}

const getTaskInfo = () => {
  const params = {
    workorderCode: taskTab.value,
    pageNo: taskPagination.currentPage
  }
  taskLoading.value = true
  return new Promise<void>((resolve, reject) => {
    getQueryTask(params)
      .then((res) => {
        taskTableFormList.value = res.list
        taskPagination.total = res.total
        resolve(res)
      })
      .finally(() => {
        taskLoading.value = false
      })
  })
}

const handleTaskPagination = (value) => {
  taskPagination = value.value
  getTaskInfo()
}

const handleQueryTask = (value) => {
  taskTab.value = value.workorderCode
  getTaskInfo().then((res) => {
    taskVisible.value = true
  })
  // getQueryTask(params).then((res) => {
  //   taskTableFormList.value = res.list
  //   taskVisible.value = true
  // })
}

// 打印数据
const handlePrinter = () => {
  btnConditions[2].loading = true
  queryPrintInfo(selectionList[0])
    .then((res) => {
      printDataInfo.value = res.map((item) => {
        const { processCode, processName, remark, processUrl } = item
        return {
          taskUrl: item.taskUrl,
          printDescribe: {
            taskCode: item.taskCode,
            itemCode: item.itemCode,
            measureName: item.measureName,
            workorderCode: item.workorderCode,
            itemName: item.itemName,
            quantity: parseInt(item.quantity)
          },
          printTable: [{ processCode, processName, remark, processUrl }]
        }
      })
      printVisible.value = true
    })
    .finally(() => {
      btnConditions[2].loading = false
    })
}

const handleWordOrderInfo = (value) => {
  pmOrderForm.tips = '查看生产订单'
  pmOrderForm.state = 'view'
  queryWorkOrderAPI(value?.id)
}

const handleWorkOrderAPI = (value) => {
  ElMessageBox.confirm('是否删除当前订单?').then(() => {
    deleteWorkOrderId([value?.id]).then((res) => {
      if (!res) return
      ElMessage.success('删除成功')
      getWorkOrderTableList()
    })
  })
}
const handlePagination = (value) => {
  pagination.currentPage = value.value.currentPage
  getWorkOrderTableList()
}

const handleUpDataData = () => {
  getWorkOrderTableList()
}
// 表格当前选择数据
let selectionList = reactive<Array<any>>([])

// 搜索功能
const handleQueryData = () => {
  getWorkOrderTableList()
}

// 按钮事件
const handleBtnOperation = (value) => {
  switch (value) {
    case 'save':
      handleTableAdd()
      break
    case 'download':
      handleDownload()
      break
    case 'printer':
      handlePrinter()
      break
  }
}

// 导出功能
const handleDownload = async () => {
  const data = await downloadListData()
  download.excel(data, '生产订单.xls')
}

// 表格数据的选择
const handleSelectionChange = async (selectionData) => {
  selectionList = selectionData.map((item) => item.workorderCode)
  btnConditions[2].disabled = !selectionList.length || selectionList.length >= 2
}

// 表格修改
const handleTableUpData = async (value) => {
  pmOrderForm.tips = '修改生产订单'
  pmOrderForm.state = 'edit'
  await queryWorkOrderAPI(value?.id)
}

// 新增
const handleTableAdd = () => {
  pmOrderForm.tips = '添加生产订单'
  pmOrderForm.state = 'edit'
  pmOrderForm.visible = true
}

// 查询订单明细
const queryWorkOrderAPI = (value) => {
  getWorkOrderInfo(value).then((res) => {
    pmOrderForm.ruleForm = res
    pmOrderForm.visible = true
  })
}

const getWorkOrderTableList = () => {
  loading.value = true
  const params = {
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize,
    ...searchModel
  }
  getListWorkOrder(params)
    .then((res) => {
      const { list = [], total } = res || {}
      tableFormList.value = list
      pagination.total = total
    })
    .finally(() => {
      loading.value = false
    })
}

const info = () => {
  getWorkOrderTableList()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
