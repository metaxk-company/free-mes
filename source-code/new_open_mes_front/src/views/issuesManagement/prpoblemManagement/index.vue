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
      :loading="tableLoading"
      :isSelection="true"
      :columns="tablePurchaseColumns"
      :tableData="tableDataList"
      :pagination="pagination"
      @pagination-change="handlePagination"
      @selection-change="handleSelectionChange"
    >
      <template #operation="{ scope }">
        <el-button type="primary" @click="handleOpenDetailsData(scope.row)">详情</el-button>
        <el-button type="success" @click="handleOpenEdit(scope.row)">修改</el-button>
        <el-button type="warning" @click="handleOpenCheck(scope.row)">审核</el-button>
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
    <el-form ref="ruleFormRef" :model="ruleFormData" :rules="rules" label-width="130px" status-icon>
      <el-row>
        <el-col :span="6">
          <el-form-item label="订单编号">
            <el-input
              v-model="ruleFormData.orderNumber"
              :disabled="isShowView"
              placeholder="请输入订单编号"
            />
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="产品编号">
            <el-input
              v-model="ruleFormData.productNumber"
              :disabled="isShowView"
              placeholder="请输入产品编号"
            />
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="所属车间" :prop="isShowView ? '' : 'workshopAffiliation'">
            <el-input v-model="ruleFormData.workshopAffiliation" :disabled="isShowView" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="来源" :prop="isShowView ? '' : 'source'">
            <el-select
              placeholder="请选择来源"
              v-if="!isShowView"
              v-model="ruleFormData.source"
              filterable
            >
              <el-option
                v-for="item in sourceSelectList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
            <div v-else>{{ ruleFormData.source }}</div>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="零部件" :prop="isShowView ? '' : 'component'">
            <el-select
              placeholder="请选择零部件"
              v-if="!isShowView"
              v-model="ruleFormData.component"
              filterable
            >
              <el-option
                v-for="item in componentSelectList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
            <div v-else>{{ ruleFormData.component }}</div>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="类型" :prop="isShowView ? '' : 'type'">
            <el-select
              placeholder="请选择类型"
              v-if="!isShowView"
              v-model="ruleFormData.type"
              filterable
              @change="changeType"
            >
              <el-option
                v-for="item in typeSelectList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
            <div v-else>{{ ruleFormData.type }}</div>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="模式" :prop="isShowView ? '' : 'mode'">
            <el-select
              placeholder="请选择模式"
              v-if="!isShowView"
              v-model="ruleFormData.mode"
              filterable
              :disabled="!ruleFormData.type"
            >
              <el-option
                v-for="item in modeSelectList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
            <div v-else>{{ ruleFormData.mode }}</div>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="备注">
            <el-input v-model="ruleFormData.remark" :disabled="isShowView" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

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
import { btnConditions } from '@/utils/const'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessageBox, ElMessage } from 'element-plus'
import {
  queryTableList,
  getVendorSelectData,
  getCreateCode,
  productClassList,
  addFormData,
  updateFormInfo,
  queryFormInfo,
  deleteFormInfo,
  getType,
  getComponent,
  getSource,
  getMode
} from '@/api/issuesManagement/problemManagement'

import { searchConditions, tablePurchaseColumns } from './data'

const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  workshopAffiliation: [{ required: true, message: '所属车间不能为空', trigger: 'change' }],
  source: [{ required: true, message: '来源不能为空', trigger: 'change' }],
  component: [{ required: true, message: '零部件不能为空', trigger: 'change' }],
  mode: [{ required: true, message: '模式不能为空', trigger: 'change' }],
  type: [{ required: true, message: '类型不能为空', trigger: 'change' }]
})

// 搜索内容值
const searchModel = ref({
  username: '',
  workshopAffiliation: '',
  source: '',
  component: '',
  type: '',
  mode: '',
  status: ''
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
const ruleFormData = ref<any>({}) // 表单数据
const isShowView = ref(false) // 是否是查看状态
const dialogTitle = ref('')
const tableReturnList = ref<any>([])
const inboundRecBillItemVoList = ref<any>([])
const vendorNameSelectList = ref<any>([])
const productClassSelectList = ref<any>([])
const typeSelectList = ref<any>([])
const componentSelectList = ref<any>([])
const sourceSelectList = ref<any>([])
const modeSelectList = ref<any>([])
// 类型
const getTypeListAPI = () => {
  getType().then((res) => {
    typeSelectList.value = res.list.map((item) => {
      return {
        value: item.name,
        label: item.name
      }
    })
  })
}
//零部件
const getComponentListAPI = () => {
  getComponent().then((res) => {
    componentSelectList.value = res.list.map((item) => {
      return {
        value: item.name,
        label: item.name
      }
    })
  })
}

// 来源
const getSourceListAPI = () => {
  getSource().then((res) => {
    sourceSelectList.value = res.list.map((item) => {
      return {
        value: item.name,
        label: item.name
      }
    })
  })
}
// 类型选择
const changeType = (value) => {
  getModeListAPI(value)
}
//模式
const getModeListAPI = (value) => {
  getMode(value).then((res) => {
    modeSelectList.value = res.map((item) => {
      return {
        value: item.name,
        label: item.name
      }
    })
  })
}

// 搜索功能
const handleQueryData = () => {
  getTableList()
}

// 获取详情接口
const getTestWayInfo = async (value) => {
  await queryFormInfo(value).then((res) => {
    ruleFormData.value = res
    tableReturnList.value = res.receiptItemList
    inboundRecBillItemVoList.value = res.receiptItemList
  })
}

const disabledTime = ref()
//时间限制
const disabledDate = (time: Date) => {
  const today = new Date()
  const yesterday = new Date(today)
  yesterday.setDate(today.getDate() - 1)
  const timestamp = yesterday.getTime() // 获取前一天的时间戳

  return time.getTime() < timestamp
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
  }
}

// 按钮新增功能
const handleOpenSave = () => {
  dialogTitle.value = '新增问题管理'
  disabledTime.value = disabledDate
  handleCreateCode()
  dialogVisible.value = true
}

// 按钮/表格 - 修改功能
const handleOpenEdit = async (value?) => {
  dialogTitle.value = '修改问题管理'
  const getId = value ? value?.id : getSelectionData.value[0].id
  await getTestWayInfo(getId)
  disabledTime.value = null
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
  dialogTitle.value = '问题管理详情'
  isShowView.value = true
  dialogVisible.value = true
}
// 审核功能
const handleOpenCheck = async (value) => {}
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

// 生成检验编号
const handleCreateCode = () => {
  getCreateCode('RECEIPT_CODE').then((res) => {
    ruleFormData.value.number = res
  })
}

// 表格弹框提交
const handleDialogSubmit = async () => {
  const params = {
    ...ruleFormData.value,
    username: JSON.parse(localStorage.userForm).username
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

const getVendorSelectListAPI = () => {
  getVendorSelectData().then((res) => {
    const vendorNameList = res.map((item) => {
      return {
        label: item.vendorName,
        value: item.vendorName
      }
    })
    vendorNameSelectList.value = vendorNameList
  })
}

const productClassListAPI = () => {
  productClassList().then((res) => {
    const productClassSelect = res.map((item) => {
      return {
        value: item.label,
        label: item.label
      }
    })
    productClassSelectList.value = productClassSelect
  })
}

// 初始化数据
const info = () => {
  getTableList()
  getVendorSelectListAPI()
  productClassListAPI()
  getTypeListAPI()
  getComponentListAPI()
  getSourceListAPI()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
