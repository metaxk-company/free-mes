<template>
  <ContentWrap>
    <CommonSearch
      @query-data="handleQueryData"
      :conditions-list="searchConditions"
      :search-model="searchModel"
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
    <Common-table
      :loading="loading"
      :isSelection="true"
      :columns="tableColumns"
      :tableData="tableFormList"
      :pagination="pagination"
      @selection-change="handleSelectionChange"
      @handle-pagination="handlePagination"
    >
      <template #workstationCode="{ scope }">
        <el-button link type="primary" @click="handleReadData(scope.row)">{{
          scope.row.workstationCode
        }}</el-button>
      </template>
      <template #enableFlag="{ scope }">
        <el-tag :type="scope.row.enableFlag == 'Y' ? '' : 'danger'">{{
          UN_KNOW_STATE[scope.row.enableFlag]
        }}</el-tag>
      </template>
      <template #operation="{ scope }">
        <el-button
          style="font-size: 17px"
          link
          type="primary"
          size="small"
          @click="handleUqDataRow(scope.row)"
        >
          修改
        </el-button>
        <el-button
          style="font-size: 17px"
          link
          type="danger"
          size="small"
          @click="handleDeleteData(scope.row)"
        >
          删除
        </el-button>
      </template>
    </Common-table>
  </ContentWrap>

  <XModal v-model="workStationVisible" :title="workStationTips" width="57%" @close="closeReset">
    <el-form
      ref="ruleFormRef"
      :model="workStationForm"
      :rules="rules"
      status-icon
      label-width="120px"
    >
      <el-row>
        <el-col :span="8">
          <el-form-item label="工作站编码" prop="workstationCode">
            {{ workStationForm.workstationCode }}
            <!-- <el-input
              v-model="workStationForm.workstationCode"
              placeholder="请输入工作站编码"
              clearable
              :disabled="isEdit"
            />  -->
          </el-form-item></el-col
        >
        <!-- <el-col :span="8" v-if="!isEdit"
          ><el-form-item prop="autoGenFlag" label="自动生成">
            <el-switch
              v-model="workStationForm.autoGenFlag"
              active-color="#13ce66"
              @change="handleAutoGenChange(workStationForm.autoGenFlag)"
            /> </el-form-item
        ></el-col> -->
        <el-col :span="8"
          ><el-form-item label="工作站名称" prop="workstationName">
            <el-input
              :disabled="isEdit"
              v-model="workStationForm.workstationName"
              placeholder="请输入工作站名称"
              clearable
            /> </el-form-item
        ></el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="工作站地点" prop="workstationAddress">
            <el-input
              :disabled="isEdit"
              v-model="workStationForm.workstationAddress"
              placeholder="请输入工作站地点"
            />
          </el-form-item>
        </el-col>

        <el-col :span="8">
          <el-form-item label="所在车间" prop="workshopId">
            <el-select
              :disabled="isEdit"
              v-model="workStationForm.workshopId"
              placeholder="请选择车间"
            >
              <el-option
                v-for="(item, index) in workshopList"
                :key="index"
                :label="item.workshopName"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="所属工序" prop="processId">
            <el-select
              :disabled="isEdit"
              v-model="workStationForm.processId"
              placeholder="请选择工序"
            >
              <el-option
                v-for="(item, index) in processList"
                :key="index"
                :label="item.processName"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <!-- <el-col :span="8">
          <el-form-item label="所属工序" prop="processId">
            <el-select
              :disabled="isEdit"
              v-model="workStationForm.processId"
              placeholder="请选择工序"
            >
              <el-option
                v-for="(item, index) in processList"
                :key="index"
                :label="item.processName"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col> -->
        <el-col :span="8">
          <el-form-item label="是否启用" prop="enableFlag">
            <el-radio-group :disabled="isEdit" v-model="workStationForm.enableFlag">
              <el-radio :label="item.value" v-for="(item, index) in UN_KNOW_ENABLE" :key="index">
                {{ item.label }}</el-radio
              >
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="备注" prop="remark">
            <el-input
              :disabled="isEdit"
              v-model="workStationForm.remark"
              type="textarea"
              autosize
              placeholder="备注"
              clearable
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <work-station-resources
      :is-edit="isEdit"
      v-if="!!workStationForm.id"
      :work-id="workStationForm.id"
    />
    <template #footer>
      <span class="dialog-footer">
        <el-button v-if="!isEdit" type="primary" @click="handleSubmitForm(ruleFormRef)"
          >提交</el-button
        >
        <el-button @click="closeReset">关闭</el-button>
      </span>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { reactive, onMounted, nextTick } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, tableColumns, btnConditions } from './data'
import { UN_KNOW_STATE, UN_KNOW_ENABLE } from '@/utils/const'
import { ElMessageBox, ElMessage } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import workStationResources from './components/workStationResources.vue'
import {
  queryWorkstationList,
  queryWorkshopList,
  queryProcessList,
  deleteWorkstationInfo,
  downloadListData
} from '@/api/masterData/workstation'
import { createWordOrder } from '@/api/prodMgmt/pmOrder'
import {
  addWorkstationInfo,
  queryWorkstationInfo,
  upDateWorkstation
} from '@/api/masterData/workstation'
import download from '@/utils/download'

const ruleFormRef = ref<FormInstance>()
// 搜索参数
const searchModel = reactive({
  // 工作站编码
  workstationCode: '',
  // 工作站名称
  workstationName: '',
  // 车间名称
  workshopId: '',
  // 所属工序
  processId: ''
})
// 表格参数
let tableFormList = ref([])
// 表格分页
const pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
// 表格当前选择数据
let selectionList = reactive<Array<any>>([])
// 弹框的状态
let workStationVisible = ref<boolean>(false)
// 弹框的标题
let workStationTips = ref<string>('')
// 工作站表单数据
let workStationForm = ref({
  // 工作站编号
  workstationCode: '',
  // 自动生成
  autoGenFlag: false,
  // 工作站名称
  workstationName: '',
  // 工作站地点
  workstationAddress: '',
  // 所在车间
  workshopId: '',
  // 所属工序
  processId: '',
  // 是否启用
  enableFlag: 'Y',
  // 备注
  remark: '',
  id: ''
})
// 车间列表下拉选项
let workshopList = reactive<any>([])
// 所属工序下拉选项
let processList = reactive<any>([])
// 校验信息
const rules = reactive<FormRules>({
  workstationCode: [{ required: true, message: '工作站编号不能为空', trigger: 'blur' }],
  workstationName: [{ required: true, message: '工作站名称不能为空', trigger: 'blur' }],
  workshopId: [{ required: true, message: '请选择所属车间', trigger: 'blur' }],
  processId: [{ required: true, message: '请选择所属工序', trigger: 'blur' }]
})
const loading = ref<boolean>(false)
const isEdit = ref<boolean>(false)

// 搜索功能
const handleQueryData = () => {
  getTableList()
}

// 自动生成
const handleAutoGenChange = () => {
  createWordOrder('WORKSTATION_CODE').then((res) => {
    nextTick(() => {
      workStationForm.value.workstationCode = res
    })
  })
}
// 分页事件
const handlePagination = () => {}

// 按键操作事件
const handleBtnOperation = (value) => {
  switch (value) {
    case 'save':
      addWorkstation()
      break
    case 'edit':
      handleUqDataRow()
      break
    case 'delete':
      handleDeleteData()
      break
    case 'download':
      handleDownload()
      break
  }
}

// 导出功能
const handleDownload = async () => {
  const data = await downloadListData()
  download.excel(data, '工作站管理.xls')
}

// 新增
const addWorkstation = () => {
  handleAutoGenChange()
  workStationTips.value = '新增工作站'
  workStationVisible.value = true
}

// 表格数据修改
const handleUqDataRow = (value?) => {
  let workId = value?.id ? value.id : selectionList[0]
  workStationTips.value = '修改工作站'
  getWorkstationInfo(workId)
}

// 表格数据删除
const handleDeleteData = (value?) => {
  const params = value?.id || selectionList
  const delID = Array.isArray(params) ? params : [params]
  ElMessageBox.confirm(`是否确认删除工作站？`).then(() => {
    deleteWorkstationInfo(delID).then((res) => {
      ElMessage.success('删除成功')
      getTableList()
    })
  })
}

// 查看工作站
const handleReadData = (value) => {
  workStationTips.value = '查看工作站详情'
  isEdit.value = true
  getWorkstationInfo(value?.id)
}

// 获取表格数据详情
const getWorkstationInfo = (value) => {
  queryWorkstationInfo(value).then((res) => {
    toRef((workStationForm.value = res), res)
    workStationVisible.value = true
  })
}

// 表格数据的选择
const handleSelectionChange = async (selectionData) => {
  selectionList = selectionData.map((item) => item.id)
  btnConditions[1].disabled = !selectionList.length || selectionList.length >= 2
  btnConditions[2].disabled = !selectionList.length
}

// 表单提交
const handleSubmitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid) => {
    if (valid) {
      if (workStationForm.value.id) {
        upDateWorkstation(workStationForm.value).then((res) => {
          if (!res) return
          ElMessage.success('修改成功')
          closeReset()
          getTableList()
        })
      } else {
        addWorkstationInfo(workStationForm.value).then((res) => {
          if (!res) return
          ElMessage.success('新增成功')
          closeReset()
          getTableList()
        })
      }
    }
  })
}

// 表单取消
const closeReset = () => {
  if (!ruleFormRef.value) return
  ruleFormRef.value.resetFields()
  workStationForm.value = {
    // 工作站编号
    workstationCode: '',
    // 自动生成
    autoGenFlag: false,
    // 工作站名称
    workstationName: '',
    // 工作站地点
    workstationAddress: '',
    // 所在车间
    workshopId: '',
    // 所属工序
    processId: '',
    // 是否启用
    enableFlag: 'Y',
    // 备注
    remark: '',
    id: ''
  }
  workStationVisible.value = false
  isEdit.value = false
}

// 初始化获取表格数据
const getTableList = () => {
  const params = {
    ...searchModel,
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize
  }
  loading.value = true
  queryWorkstationList(params)
    .then((res) => {
      const { list = [], total } = res || {}
      tableFormList.value = list
      pagination.total = total
    })
    .finally(() => {
      loading.value = false
    })
}

// 获取车间列表
const getWorkshopAllList = () => {
  queryWorkshopList().then((res) => {
    searchConditions[2].options = res.map((item) => {
      return {
        value: item.id,
        label: item.workshopName
      }
    })
    workshopList = res
  })
}

// 获取所属工序列表
const getProcessList = () => {
  queryProcessList().then((res) => {
    searchConditions[3].options = res.map((item) => {
      return {
        value: item.id,
        label: item.processName
      }
    })
    processList = res
  })
}

// 初始化
const init = () => {
  getTableList()
  getWorkshopAllList()
  getProcessList()
}

onMounted(() => {
  init()
})
</script>

<style scoped lang="scss"></style>
