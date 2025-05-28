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
        v-for="(item, index) in btnConditions.slice(0, 4)"
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
      :loading="tableLoading"
      :isSelection="true"
      :columns="tableColumns"
      :tableData="tableDataList"
      :pagination="pagination"
      @pagination-change="handlePagination"
      @selection-change="handleSelectionChange"
    >
      <template #operation="{ scope }">
        <el-button type="primary" @click="handleOpenDetailsData(scope.row)">详情</el-button>
        <el-button type="success" @click="handleOpenEdit(scope.row)">修改</el-button>
        <el-button type="danger" @click="handleOpenRemove(scope.row)">删除</el-button>
      </template>
    </common-table>
  </ContentWrap>
  <XModal
    v-model="dialogVisible"
    :title="dialogTitle"
    @close="handleCloseDialogVisible"
    width="90%"
  >
    <el-form ref="ruleFormRef" :model="ruleFormData" :rules="rules" label-width="100px" status-icon>
      <el-row>
        <el-col :span="6">
          <el-form-item label="客户名称" :prop="isShowView ? '' : 'customerName'">
            <el-select
              v-model="ruleFormData.customerName"
              filterable
              placeholder="请输入客户名称"
              v-if="!isShowView"
            >
              <el-option
                v-for="item in clientList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
            <div v-else>{{ ruleFormData.customerName }}</div>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="型号" :prop="isShowView ? '' : 'model'">
            <el-select
              filterable
              placeholder="请选择型号"
              v-model="ruleFormData.model"
              v-if="!isShowView"
            >
              <el-option
                v-for="item in modelSelectList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
            <div v-else>{{ ruleFormData.model }}</div>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="单位" :prop="isShowView ? '' : 'unit'">
            <el-select
              filterable
              placeholder="请选择单位"
              :disabled="isShowView"
              v-model="ruleFormData.unit"
            >
              <el-option
                v-for="item in unitList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="线别" :prop="isShowView ? '' : 'lineType'">
            <el-select
              placeholder="请选择线别"
              :disabled="isShowView"
              v-model="ruleFormData.lineType"
            >
              <el-option
                v-for="item in LINES_NAME"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-form-item label="备注">
            <el-input v-model="ruleFormData.remark" :disabled="isShowView" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <returnGoodsTable :table-return-list="tableReturnList" :is-show-view="isShowView" />
    <template #footer>
      <el-button type="primary" @click="handleDialogSubmit" v-if="!isShowView">提交</el-button>
      <el-button @click="handleCloseDialogVisible">取消</el-button>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { ref, onMounted, reactive } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, tableColumns } from './data'
import { btnConditions, LINES_NAME } from '@/utils/const'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessageBox, ElMessage } from 'element-plus'
import download from '@/utils/download'
import {
  queryTableList,
  addFormData,
  updateFormInfo,
  deleteFormInfo,
  queryFormInfo,
  downloadListData,
  getClientSelectData,
  getUnitSelectList,
  getModelSelectList
} from '@/api/salesOrder/quotationList'
import returnGoodsTable from './components/returnGoodsTable.vue'

const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  customerName: [{ required: true, message: '请输入客户名称', trigger: 'blur' }],
  customerNumber: [{ required: true, message: '请输入客户编号', trigger: 'blur' }],
  model: [{ required: true, message: '请选择型号', trigger: 'blur' }],
  unit: [{ required: true, message: '请选择单位', trigger: 'blur' }],
  lineType: [{ required: true, message: '请选择线别', trigger: 'blur' }]
})

// 搜索内容值
const searchModel = ref({
  // customerNumber: '',
  customerName: '',
  lineType: '',
  createTimes: '',
  model: ''
})
const tableLoading = ref(false) // 表格加载
const tableDataList = ref([]) // 表格数据
const getSelectionData = ref()
let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const dialogVisible = ref(false) // 弹框显示
const ruleFormData = ref<any>({
  createCode: false
}) // 表单数据
const isShowView = ref(false) // 是否是查看状态
const dialogTitle = ref('')
const tableReturnList = ref([])
const modelSelectList = ref<any>([])
const clientList = ref<any>([])
const unitList = ref<any>([])

// 搜索功能
const handleQueryData = () => {
  getTableList()
}

// 获取详情接口
const getTestWayInfo = async (value) => {
  await queryFormInfo(value).then((res) => {
    ruleFormData.value = res
    tableReturnList.value = res.orderQuoteItemList
  })
}

// 按钮集合功能
const handleBtnOperation = (state) => {
  switch (state) {
    case 'save':
      handleOpenSave()
      break
    case 'edit':
      handleOpenEdit()
      break
    case 'remove':
      handleOpenRemove()
      break
    case 'download':
      handleDownload()
      break
  }
}

const handleDownload = async () => {
  const data = await downloadListData()
  download.excel(data, '客户报价单.xls')
}

// 按钮新增功能
const handleOpenSave = () => {
  dialogTitle.value = '新增报价单'
  dialogVisible.value = true
}

// 按钮/表格 - 修改功能
const handleOpenEdit = async (value?) => {
  dialogTitle.value = '修改'
  const getId = value ? value?.id : getSelectionData.value[0].id
  await getTestWayInfo(getId)
  dialogVisible.value = true
}

// 按钮/表格 - 删除功能
const handleOpenRemove = (value?) => {
  const getId = value ? [value?.id] : getSelectionData.value.map((item) => item.id)
  ElMessageBox.confirm('是否删除当前数据?').then(() => {
    deleteFormInfo(getId).then((res) => {
      ElMessage({ message: '删除成功', type: 'success' })
      getTableList()
    })
  })
}

// 表格详情功能
const handleOpenDetailsData = async (value) => {
  await getTestWayInfo(value.id)
  dialogTitle.value = '报价单详情'
  isShowView.value = true
  dialogVisible.value = true
}

// 分页功能
const handlePagination = (value) => {
  pagination = value?.value
  getTableList()
}

// 表格选择事件
const handleSelectionChange = (value) => {
  getSelectionData.value = value
  btnConditions[1].disabled = !getSelectionData.value.length || getSelectionData.value.length >= 2
  btnConditions[2].disabled = !getSelectionData.value.length
}

// 表格弹框关闭
const handleCloseDialogVisible = () => {
  dialogVisible.value = false
  isShowView.value = false
  ruleFormData.value = {}
  tableReturnList.value = []
}

// 表格弹框提交
const handleDialogSubmit = async () => {
  const params = {
    ...ruleFormData.value,
    orderQuoteItemList: tableReturnList.value
  }
  if (!ruleFormRef.value) return
  await ruleFormRef.value.validate((valid) => {
    if (!valid) return
    if (!ruleFormData.value?.id) {
      addFormData(params).then((res) => {
        ElMessage({ message: '新增成功', type: 'success' })
        handleCloseDialogVisible()
        getTableList()
      })
    } else {
      updateFormInfo(params).then((res) => {
        ElMessage({ message: '修改成功', type: 'success' })
        handleCloseDialogVisible()
        getTableList()
      })
    }
  })
}

const getTableList = () => {
  tableLoading.value = true
  const params = {
    ...searchModel.value,
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize
  }
  queryTableList(params)
    .then((res) => {
      const { list, total } = res || {}
      pagination.total = total
      tableDataList.value = list
    })
    .finally(() => {
      tableLoading.value = false
    })
}

const getModelSelectListAPI = () => {
  getModelSelectList().then((res) => {
    const modelLabelList = res.map((item) => {
      return {
        value: item.model,
        label: item.model
      }
    })
    modelSelectList.value = modelLabelList
    // searchConditions[1].options = modelLabelList
  })
}

const getUnitSelectListAPI = () => {
  getUnitSelectList().then((res) => {
    const modelLabelList = res.map((item) => {
      return {
        value: item.measureName,
        label: item.measureName
      }
    })
    unitList.value = modelLabelList
  })
}

const getClientSelectListAPI = () => {
  getClientSelectData().then((res) => {
    const clientListData = res.map((item) => {
      return {
        value: item.clientName,
        label: item.clientName
      }
    })
    clientList.value = clientListData
  })
}

// 初始化数据
const info = () => {
  getTableList()
  getModelSelectListAPI()
  getUnitSelectListAPI()
  getClientSelectListAPI()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
