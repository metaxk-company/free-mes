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
      :tableData="tableDataList"
      :pagination="pagination"
      @selection-change="handleSelectionChange"
      @pagination-change="handlePagination"
    >
      <template #teamPeople="{ scope }">
        <el-button link type="primary" @click="handleOpenTeamPeopleDisable(scope.row)"
          >任务分配</el-button
        >
      </template>
      <template #operate="{ scope }">
        <el-button type="primary" @click="handleDetails(scope.row)">详情</el-button>
        <el-button type="success" @click="handleDialogEdit(scope.row)">修改</el-button>
        <el-button type="danger" @click="handleDialogRemove(scope.row)">删除</el-button>
      </template>
    </common-table>
  </ContentWrap>
  <XModal v-model="dialogVisible" :title="dialogTitle" width="90%" @close="handleCancel">
    <el-form ref="ruleFormRef" :model="schedulingPlanForm" :rules="rules" label-width="80px">
      <el-row>
        <el-col :span="6">
          <el-form-item label="排班编号">
            {{ schedulingPlanForm.planCode }}
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="排班名称" prop="planName">
            <el-input v-model="schedulingPlanForm.planName" :disabled="showView" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-divider />
      <el-row>
        <el-col :span="6">
          <el-form-item label="班组编号" prop="teamCode">
            <el-select
              :disabled="showView"
              v-model="schedulingPlanForm.teamCode"
              placeholder="请选择班组编号"
              @change="handleTeamNum(schedulingPlanForm.teamCode)"
            >
              <el-option
                v-for="item in shiftTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="班组类型" prop="teamType">
            <el-input v-model="schedulingPlanForm.teamType" disabled />
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="班组名称" prop="teamName">
            <el-input v-model="schedulingPlanForm.teamName" disabled />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="轮班时长" prop="shiftDuration">
            <el-input-number
              style="width: 197px"
              :min="0"
              v-model="schedulingPlanForm.shiftDuration"
              :disabled="showView"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="轮班方式" prop="shiftWay">
            <el-select
              :disabled="showView"
              v-model="schedulingPlanForm.shiftWay"
              placeholder="请选择轮班方式"
            >
              <el-option
                v-for="item in shiftWay"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="倒班方式" prop="changeShiftWay">
            <el-select
              :disabled="showView"
              v-model="schedulingPlanForm.changeShiftWay"
              placeholder="请选择倒班方式"
            >
              <el-option
                v-for="item in shiftPatternsList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="开始日期" prop="startDate">
            <el-date-picker
              :disabled="showView"
              v-model="schedulingPlanForm.startDate"
              type="datetime"
              placeholder="请选择开始日期"
              value-format="YYYY-MM-DD hh:mm:ss"
              format="YYYY-MM-DD hh:mm:ss"
              :default-time="new Date()"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="结束日期" prop="endDate">
            <el-date-picker
              :disabled="showView"
              v-model="schedulingPlanForm.endDate"
              type="datetime"
              placeholder="请选择结束日期"
              value-format="YYYY-MM-DD hh:mm:ss"
              format="YYYY-MM-DD hh:mm:ss"
              :default-time="new Date()"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-divider />
      <!-- 穿梭框人员选择 -->
      <div style="display: flex; justify-content: center" v-if="!showView">
        <el-transfer
          :props="{
            key: 'id',
            label: 'userName'
          }"
          :titles="['未选人员', '已选人员']"
          v-model="selectedPersonnel"
          :data="personsNotSelected"
        />
      </div>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="handleSubmit" v-if="!showView">确定</el-button>
      <el-button @click="handleCancel">取消</el-button>
    </template>
  </XModal>
  <!-- 排班人员计划弹框 -->
  <XModal
    v-model="teamPeopleDisable"
    width="75%"
    title="排班人员"
    @close="teamPeopleDisable = false"
  >
    <div style="margin-bottom: 15px">
      <!-- <el-descriptions title="当前班组任务总数/个">
        <el-descriptions-item>
          <el-tag size="large" type="success" style="font-weight: bold"
            >{{ countQuantityNum }}个</el-tag
          ></el-descriptions-item
        >
      </el-descriptions>
      <el-descriptions title="当前班组任务/个" />
      <el-row>
        <el-col :span="6" v-for="(item, index) in findTaskListData" :key="index">
          {{ item.taskName }}:
          <el-tag size="large" style="font-weight: bold">{{ item.quantity }}个</el-tag>
        </el-col>
      </el-row> -->
      <el-space wrap fill style="width: 100%">
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <span style="font-weight: bold; font-size: 16px">当前班组任务总数：</span>
              <el-tag type="success" size="large" style="font-weight: bold"
                >{{ countQuantityNum }}个</el-tag
              >
            </div>
          </template>
          <span style="font-weight: bold; font-size: 16px">当前拥有任务</span>
          <el-row>
            <el-col
              :span="6"
              v-for="(item, index) in findTaskListData"
              :key="index"
              style="margin-bottom: 10px"
            >
              {{ item.taskName }}
              <el-tag size="large" style="font-weight: bold">{{ item.quantity }}个</el-tag>
            </el-col>
          </el-row>
        </el-card>
      </el-space>
    </div>

    <common-table
      :columns="personnelColumns"
      :tableData="tablePeopleDataList"
      :loading="peopleLoading"
      :pagination="peoplePagination"
      @pagination-change="handlePeoplePagination"
    >
      <template #peopleQuantity="{ scope }">
        <el-input-number
          :min="0"
          v-model="scope.row.peopleQuantity"
          :max="countQuantityNum"
          placeholder="请输入数量"
        />
      </template>
      <template #taskCode="{ scope }">
        <el-select v-model="scope.row.taskCode" placeholder="请选择任务名称" clearable>
          <el-option
            v-for="item in taskCodeSelectList"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </template>
      <template #operate="{ scope }">
        <el-button type="danger" link @click="handleDeletePeople(scope.row)">移除人员</el-button>
      </template>
    </common-table>
    <template #footer>
      <el-button type="primary" @click="handlePeopleNumSubmit">提交</el-button>
      <el-button @click="teamPeopleDisable = false">取消</el-button>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, btnConditions, tableColumns, personnelColumns } from './data'

import { ElMessage, ElMessageBox } from 'element-plus'
import download from '@/utils/download'
import type { FormInstance } from 'element-plus'
import {
  saveSchedulingPlanData,
  deleteBatchSchedulingPlanData,
  updateSchedulingPlanData,
  getSchedulingPlanDataList,
  getSchedulingPlanDetail,
  exportSchedulingPlan,
  getNameOfType,
  generateTypeCode,
  getPeopleListData,
  deletePeopleAPI,
  editPeopleInfoAPI,
  getFindTaskQuantityAPI
} from '@/api/scheduling/schedulingPlan'
import { getShiftSetDataList, getShiftPersonnelData } from '@/api/scheduling/shiftSet'

const ruleFormRef = ref<FormInstance>()

interface ISchedulingPlanForm {
  [any: string]: any
}

const schedulingPlanForm = ref<ISchedulingPlanForm>({
  shiftDuration: 0
})
const rules = reactive({
  planCode: [{ required: true, message: '请输入排班编号', trigger: 'blur' }],
  planName: [{ required: true, message: '请输入排班编号', trigger: 'blur' }],
  teamCode: [{ required: true, message: '请选择班组编号', trigger: 'blur' }],
  shiftWay: [{ required: true, message: '请选择轮班方式', trigger: 'blur' }],
  changeShiftWay: [{ required: true, message: '请选择倒班方式', trigger: 'blur' }],
  startDate: [{ required: true, message: '请选择开始时间', trigger: 'blur' }],
  endDate: [{ required: true, message: '请选择结束时间', trigger: 'blur' }]
})
// 获取人员任务的下拉参数
const taskCodeSelectList = ref<any>([])

// 轮班方式
const shiftWay = ref([
  {
    label: '白班',
    value: '白班'
  },
  {
    label: '中班',
    value: '中班'
  },
  {
    label: '晚班',
    value: '晚班'
  }
])

// 倒班方式下拉参数
const shiftPatternsList = ref([
  {
    label: '两班倒',
    value: '两班倒'
  },
  {
    label: '三班倒',
    value: '三班倒'
  },
  {
    label: '四班倒',
    value: '四班倒'
  }
])

const searchModel = reactive({
  teamType: '',
  planCode: '',
  planName: '',
  startDate: '',
  endDate: ''
})
// 表格参数
const tableDataList = ref([])
// 表格分页
let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
// 人员分页
let peoplePagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const loading = ref<boolean>(false)
const dialogVisible = ref<boolean>(false)
const dialogTitle = ref<string>('')
const showView = ref<boolean>(false)
const teamPeopleDisable = ref(false) // 排版人员弹框
// 已选人员
const selectedPersonnel = ref([])
// 未选人员
const personsNotSelected = ref<any>([])
// 转换已选人员格式
const switchSelectedPersonnel = ref<any>([])
// 统计数量总数
const countQuantityNum = ref()

watch(
  () => selectedPersonnel.value,
  (newValue) => {
    switchSelectedPersonnel.value = newValue.map((id: any) => {
      const matchingObj = personsNotSelected.value.find((obj) => obj.id === id)
      return {
        peopleId: matchingObj.id,
        teamPeople: matchingObj.userName
      }
    })
  }
)

// 表格人数提交功能
const handlePeopleNumSubmit = () => {
  const totalQuantum = tablePeopleDataList.value.reduce((accumulator, currentValue: any) => {
    return accumulator + currentValue?.peopleQuantity
  }, 0)
  if (totalQuantum > countQuantityNum.value) return ElMessage.error('不允许超过最大班组任务数量')
  editPeopleInfoAPI(tablePeopleDataList.value).then((res) => {
    ElMessage.success('更新成功')
    teamPeopleDisable.value = false
  })
}

// 表格人数移除人员功能
const handleDeletePeople = (value) => {
  if (Number(value.peopleQuantity) != 0) return ElMessage.error(`请先当前任务数量设置为：0`)
  deletePeopleAPI(value.peopleId).then((res) => {
    ElMessage.success('移除成功')
    getPeopleDataListAPI()
  })
}

// 自动生成
const handleAutoGenChange = () => {
  generateTypeCode().then((res) => {
    schedulingPlanForm.value.planCode = res
  })
}

const tablePeopleDataList = ref([]) // 排班人员列表
// 存储当前人员选择数据
const peopleData = ref()
// 打开班组人员列表弹框
const handleOpenTeamPeopleDisable = async (value) => {
  peopleData.value = value?.planCode
  await getPeopleDataListAPI()
  await getFindTaskList(value)
  teamPeopleDisable.value = true
}

const peopleLoading = ref(false)
// 获取人员列表详情数据
const getPeopleDataListAPI = async () => {
  peopleLoading.value = true
  await getPeopleListData({ planCode: peopleData.value })
    .then((res) => {
      const { countQuantity, pageResult = {}, value } = res || {}
      taskCodeSelectList.value = value
      countQuantityNum.value = countQuantity
      peoplePagination.total = pageResult?.total
      tablePeopleDataList.value = pageResult?.list.map((item) => {
        return {
          ...item,
          planPeopleName: item.planPeopleName,
          tablePeopleDataList: item?.peopleQuantity || 0
        }
      })
    })
    .finally(() => {
      peopleLoading.value = false
    })
}

const findTaskListData = ref<any>([])
// 获取当前班组任务数据
const getFindTaskList = async (value) => {
  getFindTaskQuantityAPI(value?.teamCode).then((res) => {
    findTaskListData.value = res
  })
}

// 排班人员表格分页触发事件
const handlePeoplePagination = (value) => {
  peoplePagination = value?.value
  getPeopleDataListAPI()
}

// 班组编号选择并带出人员列表数据
const handleTeamNum = (value) => {
  getNameOfType(value).then((res) => {
    schedulingPlanForm.value.teamType = res.teamType
    schedulingPlanForm.value.teamName = res.teamName
  })

  getShiftPersonnelData(value).then((res) => {
    personsNotSelected.value = res
  })
  selectedPersonnel.value = []
}

// 查看详情
const handleDetails = (value) => {
  schedulingPlanForm.value = value
  dialogTitle.value = '查看排班计划信息'
  showView.value = true
  dialogVisible.value = true
}

// 弹框提交功能
const handleSubmit = () => {
  if (!ruleFormRef.value) return
  const params = {
    ...schedulingPlanForm.value,
    selectedPersonnel: switchSelectedPersonnel.value
  }
  ruleFormRef.value.validate((valid) => {
    if (!valid) return
    if (schedulingPlanForm.value.id) {
      updateSchedulingPlanData(params).then((res) => {
        ElMessage.success('修改成功')
        handleCancel()
      })
    } else {
      saveSchedulingPlanData(params).then((res) => {
        ElMessage.success('新增成功')
        handleCancel()
      })
    }
  })
}

// 弹框关闭功能
const handleCancel = () => {
  selectedPersonnel.value = []
  schedulingPlanForm.value = {
    shiftDuration: 0
  }
  dialogVisible.value = false
  showView.value = false
  switchSelectedPersonnel.value = []
  personsNotSelected.value = []
  info()
}

// 搜索功能
const handleQueryData = () => {
  getTableListInfo()
}

// 导出功能
const handleDownload = async () => {
  const data = await exportSchedulingPlan()
  download.excel(data, '排班计划.xls')
}

const selectionValue = ref()

// 表格选择事件
const handleSelectionChange = (value) => {
  selectionValue.value = value.map((item) => item?.id)
  btnConditions[1].disabled = !selectionValue.value.length || selectionValue.value.length >= 2
  btnConditions[2].disabled = !selectionValue.value.length
}

// 表格分页事件
const handlePagination = (value) => {
  pagination = value?.value
  getTableListInfo()
}

// 按钮点击功能
const handleBtnOperation = (value) => {
  switch (value) {
    case 'save':
      handleDialogSave()
      break
    case 'edit':
      handleDialogEdit()
      break
    case 'remove':
      handleDialogRemove()
      break
    case 'download':
      handleDownload()
      break
  }
}

// 调用详情接口
const getSchedulingPlanDetailAPI = (value) => {
  return new Promise<void>((resolve, reject) => {
    getSchedulingPlanDetail(value).then((res) => {
      resolve(res)
    })
  })
}

// 打开新增弹框
const handleDialogSave = () => {
  dialogTitle.value = '添加排班计划信息'
  handleAutoGenChange()
  dialogVisible.value = true
}
// 打开修改弹框
const handleDialogEdit = (value?) => {
  dialogTitle.value = '修改排班计划信息'
  const _id = value ? value.id : selectionValue.value[0]
  getSchedulingPlanDetailAPI(_id).then((res: any) => {
    schedulingPlanForm.value = res
    personsNotSelected.value = res.personsNotSelected
    selectedPersonnel.value = res.selectedPersonnel.map((item) => item.id)

    console.log(selectedPersonnel.value, '已选人员')

    dialogVisible.value = true
  })
}

// 打开删除弹框
const handleDialogRemove = (value?) => {
  const id = value?.id || selectionValue.value
  const deleteID = Array.isArray(id) ? id : [id]
  ElMessageBox.confirm('是否删除当前数据?').then(() => {
    deleteBatchSchedulingPlanData(deleteID).then((res) => {
      ElMessage.success('删除成功')
      getTableListInfo()
    })
  })
}

// 初始化获取表格数据
const getTableListInfo = () => {
  loading.value = true
  const params = {
    ...searchModel,
    pageSize: pagination.pageSize,
    pageNo: pagination.currentPage
  }
  getSchedulingPlanDataList(params)
    .then((res) => {
      const { list, total } = res || {}
      tableDataList.value = list
      pagination.total = total
    })
    .finally(() => {
      loading.value = false
    })
}

// 班组编号下拉选择数据
const shiftTypeOptions = ref<any>([])

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
  getTableListInfo()
  getShiftSetDataListAPI()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
