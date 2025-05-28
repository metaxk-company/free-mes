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
      isSelection
      :columns="tableColumns"
      :tableData="tableDataList"
      :pagination="pagination"
      @pagination-change="handlePagination"
      @selection-change="handleSelectionChange"
    >
      <template #method="{ scope }">
        {{ MARRY_INSPECTION_METHOD[scope.row.method] }}
      </template>
      <template #enableFlag="{ scope }">
        {{ scope.row.enableFlag == 'true' ? '是' : '否' }}
      </template>
      <template #operation="{ scope }">
        <el-button type="primary" @click="handleOpenDetailsData(scope.row)">详情</el-button>
        <el-button type="success" @click="handleOpenEdit(scope.row)">修改</el-button>
        <el-button type="danger" @click="handleOpenRemove(scope.row)">删除</el-button>
      </template>
    </common-table>
  </ContentWrap>
  <XModal
    v-model="dialogVisible"
    width="60%"
    :title="dialogTitle"
    @close="handleCloseDialogVisible"
  >
    <el-divider content-position="left">基础条件</el-divider>
    <el-form ref="ruleFormRef" :model="ruleFormData" :rules="rules" label-width="90px" status-icon>
      <el-row>
        <el-col :span="8">
          <el-form-item label="检验编码" :prop="isShowView ? '' : 'number'">
            {{ ruleFormData.number }}
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="检验名称" :prop="isShowView ? '' : 'name'">
            <el-input
              v-model="ruleFormData.name"
              :disabled="isShowView"
              placeholder="请输入检验名称"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="检验方式" :prop="isShowView ? '' : 'method'">
            <el-select
              :disabled="isShowView"
              v-model="ruleFormData.method"
              placeholder="请选择检验方式"
            >
              <el-option
                v-for="item in INSPECTION_METHOD"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="产品分类" :prop="isShowView ? '' : 'productType'">
            <el-cascader
              :disabled="isShowView"
              :options="treeDataSelect"
              :show-all-levels="false"
              placeholder="请选择产品分类"
              :props="cascadeProps"
              v-model="ruleFormData.productType"
              clearable
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="检验版本" :prop="isShowView ? '' : 'version'">
            <el-input
              v-model="ruleFormData.version"
              :disabled="isShowView"
              placeholder="请输入检验版本"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-divider content-position="left">来料信息</el-divider>
      <el-row>
        <el-col>
          <el-form-item label="物料编码" :prop="isShowView ? '' : 'itemCodeList'">
            <el-input
              :disabled="isShowView"
              v-model="ruleFormData.itemCodeList"
              placeholder="请选择物料编码"
              class="overflow-hidden"
            >
              <template #append>
                <el-button
                  :disabled="isShowView"
                  :icon="'document'"
                  @click="handleOpenProcessDialog"
                  link
                />
              </template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8" v-if="!isShowView">
          <el-form-item label="是否启用" :prop="isShowView ? '' : 'enableFlag'">
            <el-switch v-model="ruleFormData.enableFlag" :disabled="isShowView" />
          </el-form-item>
        </el-col>
        <el-col :span="16" v-if="ruleFormData?.method != 'fullInspection'">
          <el-form-item label="抽检数量" :prop="isShowView ? '' : 'quantity'">
            <el-input-number
              :min="1"
              v-model="ruleFormData.quantity"
              :disabled="isShowView"
              placeholder="请输入抽检数量"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-divider content-position="left">检测项目</el-divider>
    <testingItemsTable
      :inspect-code="ruleFormData.number"
      :table-testing-items-data-list="tableTestingItemsDataList"
      :is-show-view="isShowView"
    />
    <template #footer>
      <el-button type="primary" @click="handleDialogSubmit" v-if="!isShowView">提交</el-button>
      <el-button @click="handleCloseDialogVisible">取消</el-button>
    </template>
  </XModal>
  <!-- 查看来料信息弹框 -->
  <XModal v-model="processDialog" width="60%" title="查看到货信息">
    <processInfoTable ref="processInfoTableRef" />
    <template #footer>
      <el-button type="primary" @click="handleProcessSubmit">提交</el-button>
      <el-button @click="handleCloseProcessDialog">取消</el-button>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { ref, onMounted, reactive } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, tableColumns } from './data'
import { btnConditions, INSPECTION_METHOD, MARRY_INSPECTION_METHOD } from '@/utils/const'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessageBox, ElMessage } from 'element-plus'
import testingItemsTable from './components/testing-items-table.vue'
import processInfoTable from './components/process-info-table.vue'
import { queryTreeSelect } from '@/api/masterData/materialManage'
import { getTestStandardCode } from '@/api/inspectionStandards/processInspection'
import {
  addTestStandard,
  getTestStandardList,
  deleteTestStandardInfo,
  queryTestStandardInfo,
  upDateTestStandardInfo
} from '@/api/inspectionStandards/incomingInspection'

const processInfoTableRef = ref<any>(null)
const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  number: [{ required: true, message: '检验编码不能为空', trigger: 'blur' }],
  name: [{ required: true, message: '检验名称不能为空', trigger: 'blur' }],
  method: [{ required: true, message: '检验方式不能为空', trigger: 'change' }],
  productType: [{ required: true, message: '产品分类不能为空', trigger: 'change' }],
  version: [{ required: true, message: '检验版本不能为空', trigger: 'blur' }],
  quantity: [{ required: true, message: '检测数量不能小于1', trigger: 'blur' }],
  itemCodeList: [{ required: true, message: '物料编码不能为空', trigger: 'change' }]
})
const cascadeProps = ref({
  value: 'id',
  label: 'label',
  children: 'children',
  checkStrictly: true
})
// 搜索内容值
const searchModel = ref({
  number: '',
  processName: '',
  inspectScenario: ''
})
const tableLoading = ref(false) // 表格加载
const tableDataList = ref([]) // 表格数据
const tableTestingItemsDataList = ref()
const getSelectionData = ref()
let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const dialogVisible = ref(false) // 弹框显示
const ruleFormData = ref<any>({
  enableFlag: false,
  quantity: 1
}) // 表单数据
const isShowView = ref(false) // 是否是查看状态
const dialogTitle = ref('')
const processDialog = ref<boolean>(false) // 查看工序弹框
const treeDataSelect = ref() // 产品分类树形下拉参数

// 搜索功能
const handleQueryData = () => {
  getTableList()
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
  dialogTitle.value = '新增'
  dialogVisible.value = true
  handleCreateCode()
}
// 查询详情信息
const getTestStandardInfo = (value) => {
  queryTestStandardInfo(value).then((res) => {
    res.inspectDeviceList == 'true' ? true : false
    ruleFormData.value = res
    ruleFormData.value.enableFlag = JSON.parse(res.enableFlag)
    ruleFormData.value.productType = JSON.parse(res.productType)
    tableTestingItemsDataList.value = res.inspectDeviceList
  })
}
// 按钮/表格 - 修改功能
const handleOpenEdit = async (value?) => {
  dialogTitle.value = '修改'
  const getId = value ? value?.id : getSelectionData.value[0].id
  await getTestStandardInfo(getId)
  dialogVisible.value = true
}

// 按钮/表格 - 删除功能
const handleOpenRemove = (value?) => {
  const getId = value ? [value?.id] : getSelectionData.value.map((item) => item.id)
  ElMessageBox.confirm('是否删除当前数据?').then(() => {
    deleteTestStandardInfo(getId).then(() => {
      ElMessage({
        message: '删除成功',
        type: 'success'
      })
      getTableList()
    })
  })
}

// 生成工序标准编码
const handleCreateCode = () => {
  getTestStandardCode('QC_PROINSPECT_CODE').then((res) => {
    ruleFormData.value.number = res
  })
}

// 表格详情功能
const handleOpenDetailsData = async (value) => {
  await getTestStandardInfo(value.id)
  dialogTitle.value = '详情'
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

// 工序编号选择多个任务弹框
const handleOpenProcessDialog = () => {
  processDialog.value = true
}

// 工序弹框信息选择提交
const handleProcessSubmit = () => {
  if (!processInfoTableRef.value?.selectionValue.length)
    return ElMessage({
      message: '至少选择一条到货信息',
      type: 'warning'
    })
  ruleFormData.value.itemCodeList = processInfoTableRef.value?.selectionValue.map(
    (item) => item.itemCode
  )

  processDialog.value = false
}

// 工序弹框信息选择取消
const handleCloseProcessDialog = () => {
  processDialog.value = false
}

// 表格弹框关闭
const handleCloseDialogVisible = () => {
  dialogVisible.value = false
  isShowView.value = false
  ruleFormData.value = {
    enableFlag: false,
    quantity: 1
  }
  tableTestingItemsDataList.value = [
    // { inspectName: '', inspectStand: '', inspectDevice: '', remark: '' }
  ]
}

const handleDialogSubmit = async () => {
  const params = {
    ...ruleFormData.value,
    productType:
      typeof ruleFormData.value.productType === 'number'
        ? ruleFormData.value.productType.toString()
        : ruleFormData.value.productType?.slice(-1).toString(),
    inspectDeviceList: tableTestingItemsDataList.value
  }
  if (!ruleFormRef.value) return
  await ruleFormRef.value.validate((valid) => {
    if (!valid) return
    if (!ruleFormData.value.id) {
      addTestStandard(params).then((res) => {
        ElMessage({
          message: '新增成功',
          type: 'success'
        })
        handleCloseDialogVisible()
        getTableList()
      })
    } else {
      upDateTestStandardInfo(params).then((res) => {
        ElMessage({
          message: '修改成功',
          type: 'success'
        })
        handleCloseDialogVisible()
        getTableList()
      })
    }
  })
}

// 查询分类下拉树结构
const queryTreeSelectAPI = () => {
  queryTreeSelect().then((res) => {
    treeDataSelect.value = res
  })
}

const getTableList = () => {
  const params = {
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize,
    ...searchModel.value
  }
  tableLoading.value = true
  getTestStandardList(params)
    .then((res) => {
      const { list, total } = res || {}
      tableDataList.value = list
      pagination.total = total
    })
    .finally(() => {
      tableLoading.value = false
    })
}

// 初始化数据
const info = () => {
  queryTreeSelectAPI()
  getTableList()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss">
.overflow-hidden {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
