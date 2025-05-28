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
    width="75%"
    :title="dialogTitle"
    @close="handleCloseDialogVisible"
  >
    <el-form ref="ruleFormRef" :model="ruleFormData" :rules="rules" label-width="90px" status-icon>
      <el-row>
        <el-col :span="6">
          <el-form-item label="检验编号" :prop="isShowView ? '' : 'projectCode'">
            {{ ruleFormData.projectCode }}
            <!-- <el-input v-model="ruleFormData.projectCode" :disabled="isShowView" /> -->
          </el-form-item>
        </el-col>
        <!-- <el-col :span="6">
          <el-form-item label="自动生成">
            <el-switch v-model="ruleFormData.isCreate" @change="handleOpenCreate" />
          </el-form-item>
        </el-col> -->

        <el-col :span="6">
          <el-form-item label="检验名称" :prop="isShowView ? '' : 'projectName'">
            <el-input v-model="ruleFormData.projectName" :disabled="isShowView" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <!-- Inspection classification -->
          <el-form-item label="检验分类" :prop="isShowView ? '' : 'classify'">
            <el-select
              :disabled="isShowView"
              v-model="ruleFormData.classify"
              placeholder="请选择检验分类"
            >
              <el-option
                v-for="item in TEST_CLASSIFY"
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
          <el-form-item label="量化标准" :prop="isShowView ? '' : 'standValue'">
            <el-input v-model="ruleFormData.standValue" :disabled="isShowView" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="量化单位" :prop="isShowView ? '' : 'standUnit'">
            <el-input v-model="ruleFormData.standUnit" :disabled="isShowView" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="描述">
            <el-input v-model="ruleFormData.remark" :disabled="isShowView" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-divider content-position="left">检测器具</el-divider>
    <common-table
      :loading="utensilTableLoading"
      :isSelection="!isShowView ? true : false"
      :columns="
        dialogTitle == '添加检测项' || isShowView
          ? tableColumnsUtensil.slice(0, 3)
          : tableColumnsUtensil
      "
      :tableData="tableDataUtensilList"
      :pagination="utensilPagination"
      @pagination-change="handleUtensilPagination"
      @selection-change="handleSelectionUtensilChange"
    >
      <template #operate="{ scope }">
        <el-button :icon="'Minus'" type="danger" link @click="handleDeleteRow(scope)" />
      </template>
    </common-table>
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
import { searchConditions, tableColumns, tableColumnsUtensil } from './data'
import { btnConditions, TEST_CLASSIFY } from '@/utils/const'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessageBox, ElMessage } from 'element-plus'
import {
  getTestSettingCode,
  getTestSettingList,
  addTestSetting,
  queryTestSettingInfo,
  upDateTestSettingInfo,
  deleteTestSettingInfo,
  deleteTestEquipment
} from '@/api/inspectionStandards/detectionSettings'
import { getUtensilList } from '@/api/inspectionStandards/detectionEquipment'

const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  projectCode: [{ required: true, message: '请输入检验编号', trigger: 'blur' }],
  projectName: [{ required: true, message: '请输入检验名称', trigger: 'blur' }],
  classify: [{ required: true, message: '请选择检验分类', trigger: 'blur' }],
  standValue: [{ required: true, message: '请输入量化标准', trigger: 'blur' }],
  standUnit: [{ required: true, message: '请输入量化单位', trigger: 'blur' }]
})

// 搜索内容值
const searchModel = ref({
  projectCode: '',
  projectName: ''
})
const tableLoading = ref(false) // 表格加载
const utensilTableLoading = ref(false) // 检验器具加载
const tableDataList = ref([]) // 表格数据
const tableDataUtensilList = ref([]) // 检验器具表格数据
const getSelectionData = ref()
let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
let utensilPagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
// 保存检测器具选择的数据
const testUtensilSelectionData = ref()
const dialogVisible = ref(false) // 弹框显示
const ruleFormData = ref<any>({}) // 表单数据
const isShowView = ref(false) // 是否是查看状态
const dialogTitle = ref('')

const handleDeleteRow = (value) => {
  deleteTestEquipment(value.row.deviceCode, ruleFormData.value.projectCode).then((res) => {
    tableDataUtensilList.value.splice(value.$index, 1)
  })
}

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

// 获取检验设置详情
const getTestSettingInfo = async (value) => {
  await queryTestSettingInfo(value).then((res) => {
    ruleFormData.value = res
    tableDataUtensilList.value = res.inspectDeviceList
  })
}

// 按钮新增功能
const handleOpenSave = async () => {
  await getTestUtensilList()
  dialogTitle.value = '添加检测项'
  handleOpenCreate()
  dialogVisible.value = true
}

// 按钮/表格 - 修改功能
const handleOpenEdit = async (value?) => {
  dialogTitle.value = '修改检测项'
  const getId = value ? value?.id : getSelectionData.value[0].id
  await getTestSettingInfo(getId)
  dialogVisible.value = true
}

// 按钮/表格 - 删除功能
const handleOpenRemove = (value?) => {
  const getId = value ? [value?.id] : getSelectionData.value.map((item) => item.id)
  ElMessageBox.confirm('是否删除当前数据?').then(() => {
    deleteTestSettingInfo(getId).then(() => {
      ElMessage({
        message: '删除成功.',
        type: 'success'
      })
      getTableList()
    })
  })
}

// 表格详情功能
const handleOpenDetailsData = async (value) => {
  await getTestSettingInfo(value.id)
  dialogTitle.value = '检测项详情'
  isShowView.value = true
  dialogVisible.value = true
}

// 分页功能
const handlePagination = (value) => {
  pagination = value?.value
  getTableList()
}

// 检验器具分页功能
const handleUtensilPagination = (value) => {
  utensilPagination = value?.value
}

// 表格选择事件
const handleSelectionChange = (value) => {
  getSelectionData.value = value
  btnConditions[1].disabled = !getSelectionData.value.length || getSelectionData.value.length >= 2
  btnConditions[2].disabled = !getSelectionData.value.length
}

// 检测器具的表格选中功能
const handleSelectionUtensilChange = (value) => {
  testUtensilSelectionData.value = value
}

// 自动生成
const handleOpenCreate = () => {
  getTestSettingCode('QC_INDEX_CODE').then((res) => {
    ruleFormData.value.projectCode = res
  })
}

// 表格弹框关闭
const handleCloseDialogVisible = () => {
  dialogVisible.value = false
  isShowView.value = false
  ruleFormData.value = {}
}

// 表格弹框提交
const handleDialogSubmit = async () => {
  const params = {
    ...ruleFormData.value,
    inspectDeviceList: testUtensilSelectionData.value
  }
  if (!ruleFormRef.value) return
  await ruleFormRef.value.validate((valid) => {
    if (!valid) return
    if (!ruleFormData.value.id) {
      addTestSetting(params).then((res) => {
        ElMessage({
          message: '新增成功.',
          type: 'success'
        })
        handleCloseDialogVisible()
        getTableList()
      })
    } else {
      upDateTestSettingInfo(params).then((res) => {
        ElMessage({
          message: '修改成功.',
          type: 'success'
        })
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
    pageSize: pagination.pageSize,
    pageNo: pagination.currentPage
  }

  getTestSettingList(params)
    .then((res) => {
      const { list, total } = res || {}
      tableDataList.value = list
      pagination.total = total
    })
    .finally(() => {
      tableLoading.value = false
    })
}

// 获取检验器具参数
const getTestUtensilList = async () => {
  const params = {}
  await getUtensilList(params).then((res) => {
    const { list } = res || {}
    tableDataUtensilList.value = list
  })
}

// 初始化数据
const info = () => {
  getTableList()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
