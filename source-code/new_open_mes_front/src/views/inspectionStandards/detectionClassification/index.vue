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
    width="40%"
    :title="dialogTitle"
    @close="handleCloseDialogVisible"
  >
    <el-form ref="ruleFormRef" :model="ruleFormData" :rules="rules" label-width="90px" status-icon>
      <el-row>
        <el-col :span="12">
          <el-form-item label="项目编号" :prop="isShowView ? '' : 'projectCode'">
            {{ ruleFormData.projectCode }}
            <!-- <el-input v-model="ruleFormData.projectCode" :disabled="isShowView" /> -->
          </el-form-item>
        </el-col>
        <!-- <el-col :span="8">
          <el-form-item label="自动生成">
            <el-switch v-model="ruleFormData.createCode" @change="handleGetCreateCode" />
          </el-form-item>
        </el-col> -->
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="项目名称" :prop="isShowView ? '' : 'projectName'">
            <el-input v-model="ruleFormData.projectName" :disabled="isShowView" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="当前分类" :prop="isShowView ? '' : 'classify'">
            <el-select
              :disabled="isShowView"
              v-model="ruleFormData.classify"
              placeholder="请选择分类"
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
import { searchConditions, tableColumns } from './data'
import { btnConditions, TEST_CLASSIFY } from '@/utils/const'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessageBox, ElMessage } from 'element-plus'
import {
  getTestClassifyCode,
  getTestClassifyList,
  addTestClassify,
  queryTestClassifyInfo,
  upDateTestClassifyInfo,
  deleteTestClassifyInfo
} from '@/api/inspectionStandards/detectionClassification'

const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  projectCode: [{ required: true, message: '项目编号不能为空', trigger: 'blur' }],
  projectName: [{ required: true, message: '项目编号不能为空', trigger: 'blur' }],
  classify: [{ required: true, message: '请选择分类', trigger: 'blur' }]
})

// 搜索内容值
const searchModel = ref({
  projectCode: '',
  projectName: ''
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

// 调用详情功能
const getTestClassifyInfo = async (value) => {
  await queryTestClassifyInfo(value).then((res) => {
    ruleFormData.value = res
  })
}

// 按钮新增功能
const handleOpenSave = () => {
  dialogTitle.value = '新增检测分类'
  handleGetCreateCode()
  dialogVisible.value = true
}

// 按钮/表格 - 修改功能
const handleOpenEdit = async (value?) => {
  dialogTitle.value = '修改检测分类'
  const getId = value ? value?.id : getSelectionData.value[0].id
  await getTestClassifyInfo(getId)
  dialogVisible.value = true
}

// 按钮/表格 - 删除功能
const handleOpenRemove = (value?) => {
  const getId = value ? [value?.id] : getSelectionData.value.map((item) => item.id)
  ElMessageBox.confirm('是否删除当前数据?').then(() => {
    deleteTestClassifyInfo(getId).then((res) => {
      ElMessage({
        message: '删除成功',
        type: 'success'
      })
      getTableList()
    })
  })
}

// 表格详情功能
const handleOpenDetailsData = async (value) => {
  await getTestClassifyInfo(value.id)
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

// 创建编码
const handleGetCreateCode = () => {
  getTestClassifyCode('QC_CLASSIFY_CODE').then((res) => {
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
  if (!ruleFormRef.value) return
  await ruleFormRef.value.validate((valid) => {
    if (!valid) return
    if (!ruleFormData.value?.id) {
      addTestClassify(ruleFormData.value).then((res) => {
        ElMessage({
          message: '新增成功',
          type: 'success'
        })
        handleCloseDialogVisible()
        getTableList()
      })
    } else {
      upDateTestClassifyInfo(ruleFormData.value).then((res) => {
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

const getTableList = () => {
  tableLoading.value = true
  const prams = {
    ...searchModel.value,
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize
  }
  getTestClassifyList(prams)
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
  getTableList()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
