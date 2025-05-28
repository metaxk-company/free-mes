<template>
  <ContentWrap v-if="false">
    <common-search
      :conditions-list="searchConditions"
      :search-model="searchModel"
      @query-data="handleQueryData"
    />
  </ContentWrap>
  <ContentWrap>
    <div class="flex" style="margin-bottom: 20px; justify-content: space-between">
      <div style="width: 450px">
        <div class="flex">
          <div style="width: 300px; line-height: 30px; font-size: 15px">订单/任务编号</div>
          <el-input v-model="ganttInput" placeholder="输入订单/任务编号" @input="handleSearch" />
          <el-button link @click="handleShowGrid" type="primary">
            <el-icon style="margin-right: 5px" class="el-icon--left"> <DArrowRight /> </el-icon>
            隐藏左侧表格</el-button
          >
        </div>
      </div>
      <div>
        <el-button @click="handleFullscreen" type="primary">
          <el-icon><FullScreen /></el-icon>
          全屏</el-button
        >
      </div>
    </div>

    <div id="myCover" ref="ganttRef" style="min-height: 500px"></div>
  </ContentWrap>
  <ContentWrap v-if="false">
    <div style="margin-bottom: 20px">
      <el-button
        plain
        v-for="(item, index) in btnConditions.slice(3, 4)"
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
      :columns="columnProduction"
      :isSelection="true"
      :tableData="tableFormList"
      :pagination="pagination"
      @pagination-change="handlePagination"
      @selection-change="handleSelectionChange"
    >
      <template #workorderCode="{ scope }">
        <el-button link type="primary" @click="handleWorkCode(scope.row)">{{
          scope.row.workorderCode
        }}</el-button>
      </template>
      <template #produceProgress="{ scope }">
        <el-progress
          :color="customColorMethod"
          :text-inside="true"
          :stroke-width="26"
          :percentage="
            isNaN(parseInt(scope.row.produceProgress)) ? 0 : parseInt(scope.row.produceProgress)
          "
        />
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
      <!-- <template #operation="{ scope }">
        <el-button
          type="success"
          plain
          style="font-size: 17px"
          v-if="scope.row.status != 'PREPARE'"
          @click="handleProduction(scope.row, '新增排产任务信息')"
          >排产</el-button
        >
        <el-button
          style="font-size: 17px"
          type="primary"
          plain
          @click="handleQueryScheduling(scope.row.workorderCode)"
          >查看</el-button
        >
      </template> -->
    </common-table>
  </ContentWrap>

  <XModal
    :key="Math.random()"
    width="70%"
    v-model="dialogVisible"
    :title="dialogTitle"
    @before-close="handleClose"
  >
    <el-form ref="ruleFormRef" :model="pmOrderForm" label-width="70px">
      <el-row>
        <el-col>
          <el-form-item label="排产类型" prop="schedulingType">
            <el-radio-group v-model="schedulingType" class="ml-4">
              <el-radio :label="item.label" :key="index" v-for="(item, index) in SCHEDULING_TYPE">{{
                item.text
              }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>

        <el-col v-if="schedulingType == 'N'">
          <el-descriptions>
            <el-descriptions-item label="数量：">{{ pmOrderForm.quantity }}</el-descriptions-item>
          </el-descriptions>
        </el-col>
      </el-row>
    </el-form>

    <el-tabs
      v-model="activeName"
      class="demo-tabs"
      @tab-change="handleTabClick"
      v-if="!!processOptions.length && schedulingType != 'N'"
    >
      <el-tab-pane
        :label="item.processName"
        :name="item.processId"
        :key="item.processId"
        v-for="item in processOptions"
      >
        <pro-task
          :tabs-id="activeName"
          :orderNum="notArrangedQuantity"
          @update-Scheduling-Table="updateSchedulingTable"
          :is-view="isView"
          :processId="item.processId"
          :work-order-id="pmOrderForm.id"
        />
      </el-tab-pane>
    </el-tabs>
    <template #footer>
      <span class="dialog-footer">
        <el-button v-if="schedulingType == 'N'" type="primary" @click="handleUpdate"
          >确定排产
        </el-button>

        <el-button @click="handleClose">返回</el-button>
      </span>
    </template>
  </XModal>

  <XModal
    v-model="schedulingVisible"
    title="排产任务数据"
    width="70%"
    @before-close="handleCloseProduction"
  >
    <common-table
      :loading="schedulingLoading"
      :columns="tableColumns"
      :tableData="schedulingTableFormList"
      :pagination="schedulingPagination"
      @pagination-change="handleSchedulingPagination"
    >
      <template #colorCode="{ scope }">
        <el-color-picker v-model="scope.row.colorCode" disabled />
      </template>
    </common-table>
  </XModal>

  <!-- 打印弹出框 -->
  <XModal v-model="printVisible" width="70%" title="打印生产任务单">
    <el-button v-print="printObj" type="primary" icon="printer">打印</el-button>
    <el-divider />
    <div id="printMe" style="height: 400px">
      <print-content :print-data-info="printDataInfo" />
    </div>
    <!-- <template #header>
      <span class="dialog-footer">
        <el-button v-print="printObj" type="primary">打印</el-button>
      </span>
    </template> -->
    <template #footer>
      <el-divider />
      <el-button @click="printVisible = false">关闭</el-button>
    </template>
  </XModal>

  <!-- 甘特图的排产详情 -->
  <XModal v-model="ganttVisible" title="任务详情" width="75%">
    <el-row class="title" style="margin-top: 0">
      <div><span></span><span> 当前排产详情任务详情 </span></div>
    </el-row>
    <el-descriptions size="large" :column="4" v-if="typeDistinction == 'project'">
      <el-descriptions-item
        :label="item.label"
        :width="400"
        :key="index"
        v-for="(item, index) in displayParentData"
        >{{ item.value }}</el-descriptions-item
      >
    </el-descriptions>
    <el-descriptions size="large" :column="4" v-else>
      <el-descriptions-item
        :label="item.label"
        :width="400"
        :key="index"
        v-for="(item, index) in displaySubData"
        >{{ item.value }}</el-descriptions-item
      >
    </el-descriptions>

    <template #footer>
      <el-button @click="ganttVisible = false">返回</el-button>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, columnProduction, btnConditions } from './data'
import {
  getProductFormList,
  getAllProduction,
  getWorkSequenceNumAPI
} from '@/api/prodMgmt/production'
import {
  getListWorkOrder,
  getWorkOrderInfo,
  getGanttData,
  getGanttDataInfo
} from '@/api/prodMgmt/pmOrder'
import type { FormInstance } from 'element-plus'
import { SOURCE_TYPE_TEXT, APPROVAL_STATUS } from '@/utils/const'
import proTask from './components/proTask.vue'
import { tableColumns } from '@/views/prodMgmt/task/data'
import printContent from '@/views/prodMgmt/pmOrder/components/printContent.vue'
import { queryPrintInfo, updateDataScheduling } from '@/api/prodMgmt/pmOrder'
import { gantt } from 'dhtmlx-gantt'
import { formatTime } from '@/utils/index'
import { useOrderNumStore } from '@/store/modules/orderData'

const ruleFormRef = ref<FormInstance>()
const userStore = useOrderNumStore()

const SCHEDULING_TYPE = [
  {
    label: 'Y',
    text: '逐个工序'
  },
  {
    label: 'N',
    text: '工单数量'
  }
]

const schedulingType = ref('Y') // 排产类型无需传后端

const ganttVisible = ref(false) // 甘特图的任务详情弹框

const searchModel = reactive({
  workorderCode: '',
  workorderName: '',
  // sourceCode: '',
  productCode: '',
  productName: '',
  // clientCode: '',
  clientName: '',
  requestDate: ''
})
// 表格分页
let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 5, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
// 排产数据分页
let schedulingPagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 5, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
// 表格选中数据
const selectionList = ref([])
// 打印弹出框
const printVisible = ref()
// 获取打印数据
const printDataInfo = ref([])
// 打印组件参数
const printObj = reactive({
  id: 'printMe'
})
const loading = ref<boolean>(false)
const dialogVisible = ref<boolean>(false)
let tableFormList = reactive([])
let dialogTitle = ref<string>('')
let pmOrderForm = ref<any>({
  // 订单编号
  workorderCode: '',
  // 自动生成编码
  autoGenFlag: false,
  // 订单名称
  workorderName: '',
  // 来源类型
  orderSource: 'STORE',
  // 订单编号
  sourceCode: '',
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
  //
  parentId: null
})
// tabs 标签分组数据
let processOptions = ref<any>([])
let isView = ref<boolean>(false)
let activeName = ref('')
const schedulingVisible = ref(false)
const schedulingTableFormList = ref([])
const schedulingLoading = ref()
const notArrangedQuantity = ref()

const handleUpdate = () => {
  updateDataScheduling({ workorderId: pmOrderForm.value.id }).then((res) => {
    handleClose()
    getTableListInfo()
    info()
  })
}

// 添加生产任务完成后更新排产列表数据
const updateSchedulingTable = (value) => {
  getWorkSequenceNum(value)
  getTableListInfo()
  info()
}

// 表格选中事件
const handleSelectionChange = (value) => {
  selectionList.value = value.map((item) => item.workorderCode)
  btnConditions[3].disabled = !selectionList.value.length || selectionList.value.length >= 2
}

// 按钮操作
const handleBtnOperation = (value) => {
  switch (value) {
    case 'printer':
      handlePrinter()
      break
  }
}

const handlePrinter = () => {
  btnConditions[0].loading = true
  queryPrintInfo(selectionList.value[0])
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
      btnConditions[0].loading = false
    })
}

// 搜索功能
const handleQueryData = () => {
  getTableListInfo()
}

let getWorkOrderCode = ref('')
// 查看当前所有已排产数据
// const handleQueryScheduling = async (value) => {
//   getWorkOrderCode.value = value
//   getAllProductionAPI().then((res) => {
//     schedulingVisible.value = true
//   })
// }

// 获取排产数据
const getAllProductionAPI = () => {
  return new Promise<void>((resolve, reject) => {
    schedulingLoading.value = true
    const params = {
      pageNo: schedulingPagination.currentPage,
      workOrderCode: getWorkOrderCode.value,
      pageSize: schedulingPagination.pageSize
    }
    getAllProduction(params)
      .then((res) => {
        const { list, total } = res || {}
        schedulingTableFormList.value = list
        schedulingPagination.total = total
        resolve(res)
      })
      .finally(() => {
        schedulingLoading.value = false
      })
  })
}

const handleCloseProduction = () => {
  schedulingVisible.value = false
  getWorkOrderCode.value = ''
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

// tabs 点击
const handleTabClick = (value) => {
  getWorkSequenceNum(value)
}

const handleWorkCode = (value) => {
  isView.value = true
  handleProduction(value, '查看订单信息')
}

// 排产 点击
const handleProduction = async (value, label?) => {
  try {
    dialogTitle.value = label
    const res = await getWorkOrderInfo(value)
    pmOrderForm.value = res
    userStore.orderCount = pmOrderForm.value.quantity
    const processList = await getProcess()
    if (processList.length > 0) {
      getWorkSequenceNum(processList[0].processId)
      dialogVisible.value = true
    }
  } catch (error) {
    // 处理异常情况
    console.error(error)
  }
}

// 点击排产之前根据序号ID和产品编号查询对应的工序排产数量
const getWorkSequenceNum = async (value) => {
  const params = {
    workorderCode: pmOrderForm.value.workorderCode,
    processId: value
  }
  try {
    const res = await getWorkSequenceNumAPI(params)
    userStore.orderCount = res.residueQuantity
    notArrangedQuantity.value = res.residueQuantity
  } catch (error) {
    // 处理异常情况
    console.error(error)
  }
}

// 获取排产组成数据
const getProcess = async () => {
  try {
    const res = await getProductFormList(pmOrderForm.value.productId)
    processOptions.value = res
    activeName.value = processOptions.value[0].processId
    return res
  } catch (error) {
    // 处理异常情况
    console.error(error)
    return []
  }
}

interface IDisplaySubData {
  label: String
  value: String
}
// 存储子级夫级的字段类型，用来区分现实详情
const typeDistinction = ref()
// 存储循环需要显示的父数据
const displayParentData = ref<IDisplaySubData[]>([])
// 存储循环需要显示的子数据
const displaySubData = ref<IDisplaySubData[]>([])

// 排产详情
const handleGanttViewInfo = (value) => {
  typeDistinction.value = value.type
  const params = {
    id: value.id,
    type: value.type
  }
  getGanttDataInfo(params).then((res) => {
    if (typeDistinction.value == 'project') {
      displayParentData.value = [
        { label: '订单编号', value: res.workorderCode },
        { label: '订单名称', value: res.workorderName },
        { label: '来源类型', value: SOURCE_TYPE_TEXT[res.orderSource] },
        { label: '产品编号', value: res.productCode },
        { label: '产品名称', value: res.productName },
        { label: '规格型号', value: res.productSpc },
        { label: '产品单位', value: res.unitOfMeasure },
        { label: '订单数量', value: res.quantity },
        { label: '需求日期', value: res.requestDate },
        { label: '客户编码', value: res.clientCode },
        { label: '客户名称', value: res.clientName },
        { label: '备注', value: res.remark }
      ]
    } else {
      displaySubData.value = [
        { label: '任务编号', value: res.taskCode },
        { label: '任务名称', value: res.taskName },
        { label: '数量', value: res.quantity },
        { label: '订单编号', value: res.workorderCode },
        { label: '订单名称', value: res.workorderName },
        { label: '工作站名称', value: res.workstationName },
        { label: '工序编号', value: res.processCode },
        { label: '工序名称', value: res.processName },
        { label: '开始生产时间', value: res.startTime },
        { label: '预计完成时间', value: res.requestDate }
      ]
    }
    ganttVisible.value = true
  })
}

// 表格分页事件
const handlePagination = (value) => {
  pagination = value?.value
  getTableListInfo()
}

// 排产数据分页操作
const handleSchedulingPagination = (value) => {
  schedulingPagination = value?.value
  getAllProductionAPI()
}

// 表单关闭
const handleClose = () => {
  if (!ruleFormRef.value) return
  ruleFormRef.value.resetFields()
  dialogVisible.value = false
  // dialogStatus.value = ''
  isView.value = false
}

// 初始化获取表格数据
const getTableListInfo = () => {
  const params = {
    ...searchModel,
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize
  }
  loading.value = true
  getListWorkOrder(params)
    .then((res) => {
      const { total, list = [] } = res || {}
      pagination.total = total
      tableFormList = list
    })
    .finally(() => {
      loading.value = false
    })
}

const ganttListData = ref()
// 初始化获取gantt图数据
const getGanttDataList = () => {
  return new Promise<void>((resolve, reject) => {
    getGanttData().then((res) => {
      ganttListData.value = res
      resolve(res)
    })
  })
}

// -------------------甘特图数据------
const ganttRef = ref()
// const ganttDataList = ref()

// 甘特图搜索值
const ganttInput = ref()

// 搜索功能
const handleSearch = () => {
  gantt.refreshData()
}

const filterLogic = (task, match?): any => {
  match = match || false
  gantt.eachTask(function (child) {
    if (filterLogic(child)) {
      match = true
    }
  }, task.id)

  // 筛选任务的参数
  if (task.code.toLowerCase().indexOf(ganttInput.value.toLowerCase()) > -1) {
    match = true
  }
  return match
}

gantt.attachEvent('onBeforeTaskDisplay', (id, task) => {
  if (!ganttInput.value) {
    return true
  } else {
    return filterLogic(task)
  }
})

// 隐藏左侧表格
const handleShowGrid = () => {
  if (gantt.config.show_grid) {
    gantt.config.show_grid = false
  } else {
    gantt.config.show_grid = true
  }

  // 初始化甘特图
  gantt.init(ganttRef.value)
}

// 全屏Gantt数据
const handleFullscreen = () => {
  gantt.ext.fullscreen.toggle()
}

// gantt.eachTask(function (task) {
//   // 在任务对象中添加自定义属性
//   task.additionalParam = 'additional value'
// })

// 表格操作功能
gantt.attachEvent('onTaskClick', function (id, e) {
  var task = gantt.getTask(id)
  var button = e.target.closest('[data-action]')
  if (button) {
    var action = button.getAttribute('data-action')
    switch (action) {
      case 'view':
        handleGanttViewInfo(task)
        break
      case 'add':
        const result = task.parent != 0 ? task.parent : task.id
        handleProduction(result, '新增排产任务信息')
        break
    }
    return false
  }
  return true
})

// 初始化Gantt数据
const initConfig = () => {
  // 默认配置
  gantt.config.xml_date = '%Y-%m-%d %H:%i:%s' // 日期格式
  gantt.i18n.setLocale('cn') // 设置中文
  gantt.config.readonly = false // 设置为只读
  gantt.config.show_links = false // 禁用连线
  // 显示列配置
  gantt.config.duration_unit = 'hour'
  gantt.config.duration_step = 8
  gantt.config.grid_width = 800
  gantt.config.columns = [
    { name: 'code', label: '订单/任务编号', tree: true, width: '600' },
    { name: 'itemName', label: '产品名称', tree: true, width: '450' },
    { name: 'workstation', label: '工单执行', align: 'center', width: '400' },
    { name: 'quantity', label: '数量', align: 'center', width: '400' },
    { name: 'start_date', label: '开始时间', align: 'center', width: '400' },
    { name: 'end_date', label: '结束时间', align: 'center', width: '400' },
    {
      name: 'operate',
      label: '操作',
      template: function (task) {
        return (
          '<i style="margin-right: 12px;"  class="fa fa-file-text-o" data-action="view"></i>' +
          '<i class="fa fa-plus" data-action="add"></i>'
        )
      },
      align: 'center',
      min_width: 150
    }
  ]
  // 初始化的时候就展开树结构
  gantt.config.open_tree_initially = true
  // 显示到Gantt图上的任务上的文本
  gantt.templates.task_text = (start, end, task: any): any => {
    if (task.type == 'project') {
      return (
        "<b style='text-align:left;'>生产工单:</b> " +
        task.itemName +
        "    <span style='text-align:left;'>" +
        ' 完成比例：' +
        task.progress * 100 +
        '% </span>'
      )
    } else {
      return (
        "<b style='text-align:left;'>生产任务:</b> " +
        task.process +
        ' ' +
        '数量' +
        task.quantity +
        "    <span style='text-align:left;'>" +
        ' 完成比例：' +
        task.progress * 100 +
        '% </span>'
      )
    }
  }
  // 显示到Gantt图上的任务单元格
  gantt.config.show_task_cells = true

  const weekScaleTemplate = (date) => {
    var dateToStr = gantt.date.date_to_str('%M %d')
    var endDate = gantt.date.add(gantt.date.add(date, 1, 'week'), -1, 'day')
    return dateToStr(date) + ' - ' + dateToStr(endDate)
  }

  const dayTemplate = (date) => {
    var dateToStr = gantt.date.date_to_str('%M %d')
    var weekDay = gantt.date.date_to_str('%D')
    return dateToStr(date) + '(' + weekDay(date) + ')'
  }

  const daysStyle = (date) => {
    // you can use gantt.isWorkTime(date)
    // when gantt.config.work_time config is enabled
    // In this sample it's not so we just check week days

    if (date.getDay() === 0 || date.getDay() === 6) {
      return 'weekend'
    }
    return ''
  }

  gantt.config.scales = [
    { unit: 'week', step: 1, format: weekScaleTemplate },
    { unit: 'day', step: 1, format: dayTemplate, css: daysStyle },
    { unit: 'hour', step: 8, format: '%H:%i' } //这里的step要根据当前的班次设置来。如果是三班倒则是8，如果是两班倒则是12。
  ]

  // 拖动设置
  // 3.1 自动调整类型,当存在子节点时自动升级为project
  gantt.config.auto_types = false
  // 3.2 设置不可以拖动进度
  gantt.config.drag_progress = false
  // 3.3 设置Task不可以拖动
  gantt.config.drag_move = true
  // 3.4 设置不可以拖动关系
  gantt.config.drag_links = false
  // 3.5 设置不可拖动Task 大小
  gantt.config.drag_resize = true
  // 3.6 单击显示添加详情
  gantt.config.details_on_create = true
  // 3.7 双击显示明细
  gantt.config.details_on_dblclick = true
  //时间范围自动适应
  gantt.config.fit_tasks = true
  gantt.config.scrollable = true
  gantt.config.links = true // 启用任务连线

  // 点击表头可排序
  gantt.config.sort = true
  // 设置行高
  gantt.config.row_height = 40
  // 配置gantt图时间高度
  gantt.config.scale_height = 70
  // 取消双击点击显示出编辑弹框
  gantt.config.lightbox = false
  // 显示单元格

  // 鼠标悬浮框
  gantt.plugins({
    // 启用全屏插件
    fullscreen: true,
    // 启用撤销插件
    undo: true,
    // 启用tooltip
    tooltip: true,
    // multiselect: true,
    // 标记
    marker: true,
    // 自动调度
    auto_scheduling: true,
    critical_path: true,
    // 显示是否可以编辑任务名称
    quick_info: false
  })

  gantt.ext.fullscreen.getFullscreenElement = function (): any {
    return document.getElementById('myCover')
  }

  // 移入显示悬浮内容
  gantt.templates.tooltip_text = (start, end, task: any): any => {
    if (task.type == 'project') {
      return ` <div style="display: flex; flex-wrap: wrap;">
    <div style="flex-basis: 50%;text-align: left;width: 50%;">
      <span style="font-weight: bold; font-size: 17px">订单号：</span>
      <span style="font-size: 16px">${task.code}</span>
    </div>
    <div style="flex-basis: 50%;text-align: right">
      <span style="font-weight: bold; font-size: 17px">订单数量：</span>
      <span style="font-size: 16px">${task.quantity}</span>
    </div>
    <div style="flex-basis: 50%;text-align: left">
      <span style="font-weight: bold; font-size: 17px">已排产数量：</span>
      <span style="font-size: 16px">${task.arrangedQuantity}</span>
    </div>
    <div style="flex-basis: 50%;text-align: right">
      <span style="font-weight: bold; font-size: 17px">未排产数量：</span>
      <span style="font-size: 16px">${task.unArrangedQuantity}</span>
    </div>
    <div style="flex-basis: 50%;text-align: left">
      <span style="font-weight: bold; font-size: 17px">产品名称：</span>
      <span style="font-size: 16px">${task.itemName}</span>
    </div>
    <div style="flex-basis: 50%;text-align: right">
      <span style="font-weight: bold; font-size: 17px">产品编码：</span>
      <span style="font-size: 16px">${task.itemCode}</span>
    </div>
    <div style="flex-basis: 50%;text-align: left">
      <span style="font-weight: bold; font-size: 17px">预计开始时间：</span>
      <span style="font-size: 16px">${formatTime(task.start_date, 'yyyy-MM-dd HH:mm:ss')}</span>
    </div>
    <div style="flex-basis: 50%;text-align: right">
      <span style="font-weight: bold; font-size: 17px">预计结束时间：</span>
      <span style="font-size: 16px">${formatTime(task.end_date, 'yyyy-MM-dd HH:mm:ss')}</span>
    </div>
    <div style="flex-basis: 50%;text-align: left">
      <span style="font-weight: bold; font-size: 17px">订单接受日期：</span>
      <span style="font-size: 16px">${formatTime(task.orderDate, 'yyyy-MM-dd HH:mm:ss')}</span>
    </div>

  </div>`
    } else {
      return `<div style="display: flex; flex-wrap: wrap;">
   <div style="flex-basis: 50%;text-align: left">
      <span style="font-weight: bold; font-size: 17px">任务编号：</span>
      <span style="font-size: 16px">${task.code}</span>
    </div>
   <div style="flex-basis: 50%;text-align: left">
      <span style="font-weight: bold; font-size: 17px">产品名称：</span>
      <span style="font-size: 16px">${task.itemName}</span>
    </div>
    <div style="flex-basis: 50%;text-align: left">
      <span style="font-weight: bold; font-size: 17px">数量：</span>
      <span style="font-size: 16px">${task.quantity}</span>
    </div>
   <div style="flex-basis: 50%;text-align: left">
      <span style="font-weight: bold; font-size: 17px">工序名称：</span>
 <span style="font-size: 16px">${task.process}</span>
    </div>
   <div style="flex-basis: 50%;text-align: left">
      <span style="font-weight: bold; font-size: 17px">开始生产时间：</span>
   <span style="font-size: 16px">${formatTime(task.start_date, 'yyyy-MM-dd HH:mm:ss')}</span>
    </div>
   <div style="flex-basis: 50%;text-align: left">
      <span style="font-weight: bold; font-size: 17px">预计完成时间：</span>
      <span style="font-size: 16px">${formatTime(task.end_date, 'yyyy-MM-dd HH:mm:ss')}</span>
    </div>
  </div>`
    }
  }

  // let t: any = props.ganttDataList
  gantt.attachEvent('onAfterTaskUpdate', function (id, obj) {
    // let tt = t.data.filter((item: any) => item.id == id)
    // tt = obj
  })
}

const addTodayLine = () => {
  // 时间线
  var date_to_str = gantt.date.date_to_str(gantt.config.task_date)
  var today = new Date()

  gantt.addMarker({
    start_date: today,
    css: 'today',
    text: '今天',
    title: '今11天: ' + date_to_str(today)
  })
}

// 调用初始化甘特图数据
// const reload = () => {
//   addTodayLine()
//   // 初始化甘特图
//   gantt.init(ganttRef.value)
//   gantt.parse()
// }

const info = () => {
  getTableListInfo()
  getGanttDataList().then((res: any) => {
    const data = res?.data.map((item) => {
      return {
        ...item,
        start_date: formatTime(item.start_date, 'yyyy-MM-dd'),
        end_date: formatTime(item.end_date, 'yyyy-MM-dd'),
        progress: Number(item.progress) / 100
      }
    })

    const links = res?.links.map((item) => {
      return {
        ...item,
        type: '1'
      }
    })

    // 从甘特图中删除所有任务和其他元素（包括标记）
    gantt.clearAll()
    // 初始化甘特图配置
    initConfig()
    addTodayLine()
    // 初始化甘特图
    gantt.init(ganttRef.value)
    // 渲染数据
    gantt.parse({ data, links })
    // 渲染整个甘特图
    gantt.render()
  })
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss">
:deep .fa {
  cursor: pointer;
  font-size: 14px;
  text-align: center;
  opacity: 0.2;
  padding: 5px;
}

:deep .weekend {
  background: rgb(189, 185, 186) !important;
  width: 30s;
}

.el-icon--right {
  transition: All 0.4s ease-in-out;
  -webkit-transition: All 0.4s ease-in-out;
  -moz-transition: All 0.4s ease-in-out;
  -o-transition: All 0.4s ease-in-out;
}

.el-icon--right:hover {
  transform: rotate(180deg);
  -webkit-transform: rotate(180deg);
  -moz-transform: rotate(180deg);
  -o-transform: rotate(180deg);
  -ms-transform: rotate(180deg);
}

// :deep .gantt_task_line {
//   height: 35px !important;
//   line-height: 35px !important;
//   border: none !important;
//   border-radius: 50px;
//   background: linear-gradient(
//     43deg,
//     rgb(65, 88, 208) 0%,
//     rgb(200, 80, 192) 46%,
//     rgb(255, 204, 112) 100%
//   );
// }

:deep .gantt_task_line::after {
  content: '';
  position: absolute;
  top: -2px;
  left: -2px;
  right: -2px;
  bottom: -2px;
  background: #ffffff;
  z-index: -2;
  /*添加模糊滤镜*/
  filter: blur(40px);
}

// 完成比例进度条
:deep .gantt_task_progress {
  background-image: linear-gradient(160deg, #0093e9 100%, #80d0c7 0%);
}

.title {
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: relative;
  padding: 8px 10px;
  box-sizing: border-box;
  margin: 20px 0;
  background-color: #f5f5f5;
  border-radius: 4px;
}
.title div {
  display: flex;
  align-items: center;
}
.title div > span:first-child {
  display: inline-block;
  width: 8px;
  height: 20px;
  background-color: #4c92fe;
  margin-right: 6px;
}
.title div > span:nth-of-type(2) {
  // font-weight: 700;
  font-size: 16px;
  color: #333333;
}

/* 标记线 */
:deep .gantt_marker {
  background-color: #f4ae05;
  opacity: 0.8;
}

:deep .gantt_tooltip {
  background-color: #f4ae05 !important;
}
</style>
