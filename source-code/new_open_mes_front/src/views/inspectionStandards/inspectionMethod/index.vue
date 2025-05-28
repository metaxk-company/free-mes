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
  <XModal v-model="dialogVisible" :title="dialogTitle" @close="handleCloseDialogVisible">
    <el-form ref="ruleFormRef" :model="ruleFormData" :rules="rules" label-width="100px" status-icon>
      <el-row>
        <el-col :span="11">
          <el-form-item label="检验编号" :prop="isShowView ? '' : 'inspectCode'">
            {{ ruleFormData.inspectCode }}
            <!-- <el-input v-model="ruleFormData.inspectCode" :disabled="isShowView" /> -->
          </el-form-item>
        </el-col>
        <!-- <el-col :span="11">
          <el-form-item label="自动生成">
            <el-switch v-model="ruleFormData.createCode" @change="handleCreateCode" />
          </el-form-item>
        </el-col> -->
        <el-col :span="11">
          <el-form-item label="检验名称" :prop="isShowView ? '' : 'inspectName'">
            <el-input v-model="ruleFormData.inspectName" :disabled="isShowView" />
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
import { ref, onMounted } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, tableColumns } from './data'
import { btnConditions } from '@/utils/const'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessageBox, ElMessage } from 'element-plus'
import {
  getTestWayList,
  queryTestWayInfo,
  upDateTestWayInfo,
  deleteTestWayInfo,
  addTestWay,
  getTestWayGenCode
} from '@/api/inspectionStandards/inspectionMethod'

const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  inspectCode: [{ required: true, message: '请输入检验编号', trigger: 'blur' }],
  inspectName: [{ required: true, message: '请输入检验名称', trigger: 'blur' }]
})

// 搜索内容值
const searchModel = ref({
  inspectCode: '',
  inspectName: ''
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

// 获取详情接口
const getTestWayInfo = async (value) => {
  await queryTestWayInfo(value).then((res) => {
    ruleFormData.value = res
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
  }
}

// 按钮新增功能
const handleOpenSave = () => {
  dialogTitle.value = '新增检验方式'
  handleCreateCode()
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
    deleteTestWayInfo(getId).then((res) => {
      ElMessage({ message: '删除成功', type: 'success' })
      getTableList()
    })
  })
}

// 表格详情功能
const handleOpenDetailsData = async (value) => {
  await getTestWayInfo(value.id)
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

// 表格弹框关闭
const handleCloseDialogVisible = () => {
  dialogVisible.value = false
  isShowView.value = false
  ruleFormData.value = { createCode: false }
}

// 生成检验编号
const handleCreateCode = () => {
  getTestWayGenCode('QC_METHOD_CODE').then((res) => {
    ruleFormData.value.inspectCode = res
  })
  // if (value) {
  //   getTestWayGenCode('QC_METHOD_CODE').then((res) => {
  //     ruleFormData.value.inspectCode = res
  //   })
  // }
}

// 表格弹框提交
const handleDialogSubmit = async () => {
  if (!ruleFormRef.value) return
  await ruleFormRef.value.validate((valid) => {
    if (!valid) return
    if (!ruleFormData.value?.id) {
      addTestWay(ruleFormData.value).then((res) => {
        ElMessage({ message: '新增成功', type: 'success' })
        handleCloseDialogVisible()
        getTableList()
      })
    } else {
      upDateTestWayInfo(ruleFormData.value).then((res) => {
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
  getTestWayList(params)
    .then((res) => {
      const { list, total } = res || {}
      pagination.total = total
      tableDataList.value = list
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
