<template>
  <ContentWrap>
    <div style="margin-bottom: 20px" v-if="!isView">
      <el-button
        plain
        v-for="(item, index) in btnConditions.slice(0, 3)"
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
      <template #taskCode="{ scope }">
        <el-button type="primary" link @click="handleViewFormInfo(scope.row)">{{
          scope.row.taskCode
        }}</el-button>
      </template>

      <template #operation="{ scope }" v-if="!isView">
        <el-button link type="primary" @click="handleTableUpDate(scope.row)">修改</el-button>
        <el-button link type="danger" @click="handleDeleteTable(scope.row.id)">删除</el-button>
      </template>
    </common-table>
  </ContentWrap>

  <XModal width="65%" v-model="dialogVisible" :title="dialogTitle" @before-close="handleClose">
    <el-form ref="ruleFormRef" :model="processForm" :rules="rules" label-width="120px">
      <el-row>
        <el-form-item label="订单未排产数量"> {{ userStore.orderCount }} </el-form-item>
      </el-row>
      <el-row>
        <el-col :span="7">
          <el-form-item label="工作站类型">
            <el-radio-group v-model="processForm.workstationType" :disabled="isEdit">
              <el-radio
                :disabled="item.disabled"
                :label="item.label"
                v-for="(item, index) in WORK_STATION_STYLE"
                :key="index"
                >{{ item.value }}</el-radio
              >
            </el-radio-group>
          </el-form-item></el-col
        >
        <el-col :span="8">
          <el-form-item
            label="工作站"
            prop="workstationName"
            v-if="processForm.workstationType === WORK_STATION"
          >
            <el-input
              :disabled="isEdit"
              v-model="processForm.workstationName"
              placeholder="请选择工作站"
            >
              <template #append>
                <el-button :icon="'ZoomIn'" @click="handleWorkstationSelect" />
              </template>
            </el-input>
          </el-form-item>
          <el-form-item label="设备" prop="machineryName" v-else>
            <el-select
              @change="handleMachineryName"
              v-model="processForm.machineryCode"
              placeholder="请选择设备"
              clearable
              filterable
            >
              <el-option
                :label="item.label"
                :value="item.value"
                v-for="(item, index) in equipmentList"
                :key="index"
              />
            </el-select>
          </el-form-item>
          <workStationSelect
            ref="workSelectRef"
            :processId="processId"
            @handle-current-submit="onWorkstationSelected"
          />
        </el-col>

        <el-col :span="7">
          <el-form-item label="排产数量" prop="quantity">
            <el-input-number
              :disabled="isEdit"
              :min="1"
              :max="orderNum"
              v-model="processForm.quantity"
              placeholder="请输入排产数量"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="7">
          <el-form-item label="开始时间" prop="startTime">
            <el-date-picker
              clearable
              v-model="processForm.startTime"
              type="datetime"
              value-format="YYYY-MM-DD HH"
              format="YYYY-MM-DD hh:mm:ss"
              placeholder="请选择开始生产时间"
              @change="calculateEndTime"
              :default-time="dateTime"
              :disabled="isEdit"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="生产时长" prop="duration">
            <el-input-number
              :min="1"
              :precision="0"
              :step="1"
              @change="calculateEndTime"
              v-model="processForm.duration"
              placeholder="请输入生产时长"
              :disabled="isEdit"
            />
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item label="完成时间" prop="endTime">
            <el-input v-model="processForm.endTime" :disabled="isEdit" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="7">
          <el-form-item label="班组编号" prop="teamCode">
            <el-select
              v-model="processForm.teamCode"
              placeholder="请选择班组编号"
              @change="handleTeamNum(processForm.teamCode)"
            >
              <el-option
                v-for="item in shiftTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select> </el-form-item
        ></el-col>
        <el-col :span="8">
          <el-form-item label="班组类型" prop="teamType">
            <el-input v-model="processForm.teamType" disabled />
          </el-form-item>
        </el-col>

        <el-col :span="7">
          <el-form-item label="班组名称" prop="teamName">
            <el-input v-model="processForm.teamName" disabled />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="handleSubmit" v-if="!isEdit && !isView"> 提交 </el-button>
        <el-button @click="handleClose">返回</el-button>
      </span>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, watch } from 'vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { btnConditions, tableColumns } from '../data'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage, ElMessageBox } from 'element-plus'
import { WORK_STATION_STYLE, WORK_STATION } from '@/utils/const'
import {
  addProTask,
  queryProTaskList,
  getProTaskInfo,
  updateProTaskInfo,
  deleteProTaskInfo
} from '@/api/prodMgmt/production'
import workStationSelect from './workStationSelect.vue'
import { getShiftSetDataList } from '@/api/scheduling/shiftSet'
import { getNameOfType } from '@/api/scheduling/schedulingPlan'
import { useOrderNumStore } from '@/store/modules/orderData'

interface IProps {
  processId: number | string
  workOrderId: number | string
  isView: boolean
  orderNum: number
  tabsId: number | string
}

const emit = defineEmits(['updateSchedulingTable'])
const userStore = useOrderNumStore()

const props = defineProps<IProps>()

const workSelectRef = ref<any>(null)
const ruleFormRef = ref<FormInstance>()

const rules = reactive<FormRules>({
  machineryName: [{ required: true, message: '设备名称不能为空', trigger: 'blur' }],
  quantity: [{ required: true, message: '排产数量不能为空', trigger: 'blur' }],
  startTime: [{ required: true, message: '请选择开始生产日期', trigger: 'blur' }],
  duration: [{ required: true, message: '清输入估算的生产用时', trigger: 'blur' }],
  workstationName: [{ required: true, message: '工作站不能为空', trigger: 'blur' }],
  teamCode: [{ required: true, message: '班组编号不能为空', trigger: 'blur' }]
})

// 表格分页
let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 2, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const loading = ref<boolean>(false)
let isEdit = ref<boolean>(false)
const dialogVisible = ref<boolean>(false)
let tableFormList = reactive([])
let dialogTitle = ref<string>('')
let selectionID = ref([])
let processForm = ref<any>({
  workstationType: WORK_STATION,
  workstationName: '',
  machineryCode: '',
  colorCode: '',
  quantity: 0,
  startTime: '',
  duration: 1,
  endTime: '',
  workstationId: '',
  workstationCode: '',
  workorderId: props.workOrderId,
  processId: props.processId,
  remainQuantity: ''
})
// 班组编号下拉选择数据
const shiftTypeOptions = ref<any>([])

const dateTime = new Date()

watch(
  () => processForm.value.quantity,
  (newValue, oldValue) => {
    if (newValue > oldValue) {
      const diff = newValue - oldValue
      userStore.orderCount -= diff
    } else if (newValue < oldValue) {
      const diff = oldValue - newValue
      userStore.orderCount += diff
    }
  }
)

const handleTeamNum = (value) => {
  getNameOfType(value).then((res) => {
    processForm.value.teamType = res.teamType
    processForm.value.teamName = res.teamName
  })
}

const handleViewFormInfo = (value) => {
  dialogTitle.value = '查看生产任务'
  processForm.value = value
  dialogVisible.value = true
  isEdit.value = true
}

// 设备列表
const equipmentList = ref<any>([])

// 开始时间点击自动计算出完成时间（待完善）
const calculateEndTime = () => {
  if (!!processForm.value.startTime && !!processForm.value.duration) {
    // let parts = processForm.value.startTime.split(' ')
    // let startDate: any = new Date(parts[0])
    // startDate =
    //   startDate.getFullYear() +
    //   '-' +
    //   (startDate.getMonth() + 1) +
    //   '-' +
    //   startDate.getDate() +
    //   ' ' +
    //   parts[1].split(':')[0] +
    //   ':' +
    //   '00' +
    //   ':' +
    //   '00'

    // startDate = Date.parse(new Date(startDate)) / 1000
    // startDate += 3600 * 1 * processForm.value.duration //这里以1(可以自定义)小时为一个单位
    // let endDate = new Date(parseInt(startDate) * 1000)
    // let endTime =
    //   endDate.getFullYear() +
    //   '-' +
    //   (endDate.getMonth() + 1) +
    //   '-' +
    //   endDate.getDate() +
    //   ' ' +
    //   endDate.getHours() +
    //   ':' +
    //   '00' +
    //   ':' +
    //   '00'
    // console.log(endTime)
    // processForm.value.endTime = endTime
    const parts = processForm.value.startTime.split(' ')
    const startDate = new Date(parts[0])
    startDate.setHours(parts[1].split(':')[0], 0, 0)
    const startTimestamp = startDate.getTime() / 1000

    const durationHours = processForm.value.duration // 自定义的小时单位

    const endTimestamp = startTimestamp + 3600 * durationHours
    const endDate = new Date(endTimestamp * 1000)

    function formatDate(date) {
      const year = date.getFullYear()
      const month = String(date.getMonth() + 1).padStart(2, '0')
      const day = String(date.getDate()).padStart(2, '0')
      const hours = String(date.getHours()).padStart(2, '0')

      return `${year}-${month}-${day} ${hours}:00:00`
    }

    // const startTimeFormatted = formatDate(startDate)
    const endTimeFormatted = formatDate(endDate)

    processForm.value.endTime = endTimeFormatted
  }
}

// 工作站选择弹框打开
const handleWorkstationSelect = () => {
  workSelectRef.value.visibleClient = true
}

// 工作站选择的数据
const onWorkstationSelected = (value) => {
  processForm.value.workstationId = value.id
  processForm.value.workstationCode = value.workstationCode
  processForm.value.workstationName = value.workstationName
}

// 设备选择
const handleMachineryName = (value) => {}

// 按钮点击功能
const handleBtnOperation = (value) => {
  switch (value) {
    case 'save':
      addProcessForm()
      break
    case 'edit':
      handleTableUpDate()
      break
    case 'remove':
      handleDeleteTable()
      break
    case 'download':
      break
  }
}

// 表格选择事件
const handleSelectionChange = (value) => {
  selectionID.value = value.map((item) => item?.id)
  btnConditions[1].disabled = !selectionID.value.length || selectionID.value.length >= 2
  btnConditions[2].disabled = !selectionID.value.length
}
// 新增
const addProcessForm = () => {
  dialogTitle.value = '添加生产任务'
  dialogVisible.value = true
}

// 修改
const handleTableUpDate = (value?) => {
  dialogTitle.value = '修改生产任务'
  const dataID = value?.id || selectionID.value[0]
  queryProcessInfoAPI(dataID)
}

// 删除
const handleDeleteTable = (value?) => {
  const id = value || selectionID.value
  const deleteID = Array.isArray(id) ? id : [id]
  ElMessageBox.confirm('是否删除当前数据?').then(() => {
    deleteProTaskInfo(deleteID).then((res) => {
      ElMessage.success('删除成功')
      getTableListInfo()
      emit('updateSchedulingTable')
    })
  })
}

// 表单提交
const handleSubmit = async () => {
  if (!ruleFormRef.value) return
  if (processForm.value.quantiy == 0) {
    ElMessage.error('排产数量必须大于0')
    return
  }
  processForm.value.remainQuantity = userStore.orderCount
  await ruleFormRef.value.validate((valid) => {
    if (valid) {
      if (processForm.value.id) {
        updateProTaskInfo(processForm.value).then((res) => {
          ElMessage.success('修改成功')
          handleClose()
        })
      } else {
        addProTask(processForm.value).then((res) => {
          ElMessage.success('新增成功')
          handleClose()
        })
      }
    }
  })
}

// 获取工序详情数据
const queryProcessInfoAPI = (value) => {
  getProTaskInfo(value).then((res) => {
    processForm.value = res
    dialogVisible.value = true
  })
}

// 表格分页事件
const handlePagination = (value) => {
  pagination = value?.value
  getTableListInfo()
}

// 表单关闭
const handleClose = () => {
  if (!ruleFormRef.value) return
  ruleFormRef.value.resetFields()
  processForm.value = {
    workstationType: WORK_STATION,
    workstationName: '',
    machineryCode: '',
    colorCode: '',
    quantity: 0,
    startTime: '',
    duration: 1,
    endTime: '',
    workstationId: '',
    workstationCode: '',
    workorderId: props.workOrderId,
    processId: props.processId,
    remainQuantity: ''
  }
  isEdit.value = false
  dialogVisible.value = false
  getTableListInfo()
  emit('updateSchedulingTable', props.tabsId)
}

// 初始化获取表格数据
const getTableListInfo = () => {
  const params = {
    workorderId: props.workOrderId,
    processId: props.processId,
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize
  }
  loading.value = true
  queryProTaskList(params)
    .then((res) => {
      const { taskPage = {} } = res || {}
      console.log(res, '返回排产总数')
      // userStore.orderCount = countQuantity
      pagination.total = taskPage.total
      tableFormList = taskPage.list
    })
    .finally(() => {
      loading.value = false
    })
}

// 查询设备编号列表接口
// const queryEquipmentListAPI = () => {
//   const params = {
//     // 为了获取参数新系统传入的租户ID默认写死为1
//     tenantId: 1
//   }
//   queryEquipmentList(params).then((res) => {
//     console.log(res)
//   })
// }

watch(
  () => processForm.value.startTime,
  (newValue) => {
    if (!newValue) {
      processForm.value.duration = 1
      processForm.value.endTime = ''
    }
  }
)

// 初始化获取班组编号下拉选择数据
const getShiftSetDataListAPI = () => {
  getShiftSetDataList({}).then((res) => {
    const { list = [] } = res || {}
    shiftTypeOptions.value = list.map((item) => {
      return {
        value: item.teamCode,
        label: item.teamCode
      }
    })
  })
}

const info = () => {
  // queryEquipmentListAPI()
  getTableListInfo()
  getShiftSetDataListAPI()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
