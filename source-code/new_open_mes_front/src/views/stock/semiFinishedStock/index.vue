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
      <el-button :disabled="batchSyncState" type="primary" @click="handleBatchSyncSemiLabel"
        >批量同步</el-button
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
        <el-button type="primary" @click="handleAffirm(scope.row)">确认同步</el-button>
        <el-button type="" @click="handleOpenDetailsData(scope.row)">详情</el-button>
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
          <el-form-item label="编号">
            {{ ruleFormData.number }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="仓库名称" prop="warehouse">
            <el-select
              placeholder="请选择仓库来源"
              :disabled="isShowView"
              v-model="ruleFormData.warehouse"
            >
              <el-option
                v-for="item in wareHousesSelectList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="备注">
            <el-input v-model="ruleFormData.remark" :disabled="isShowView" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <returnGoodsTable
      :is-show-view="isShowView"
      :table-return-list="tableReturnList"
      :rule-form-data="ruleFormData"
    />
    <template #footer>
      <el-button type="primary" @click="handleDialogSubmit" v-if="!isShowView">提交</el-button>
      <el-button @click="handleCloseDialogVisible">取消</el-button>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import download from '@/utils/download'
import { ref, onMounted, reactive } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, tableColumns } from './data'
import { btnConditions } from '@/utils/const'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessageBox, ElMessage } from 'element-plus'
import {
  queryTableList,
  addFormData,
  updateFormInfo,
  downloadListData,
  queryFormInfo,
  deleteFormInfo
} from '@/api/stock/semiFinishedStock'
import { warehouseList } from '@/api/storeHouse/warehouseModel'
import returnGoodsTable from './components/returnGoodsTable.vue'
import { getCreateCode, syncSemiLabel, batchSyncSemiLabel } from '@/api/a_public_port'
const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  number: [{ required: true, message: '请输入编号', trigger: 'blur' }],
  warehouse: [{ required: true, message: '请选择仓库', trigger: 'blur' }]
})
// 搜索内容值
const searchModel = ref({
  number: ''
})
const wareHousesSelectList = ref<any>([])
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
const ruleFormData = ref<any>({})
const isShowView = ref(false) // 是否是查看状态
const dialogTitle = ref('')
const tableReturnList = ref<any>([])

// 搜索功能
const handleQueryData = () => {
  getTableList()
}

// 获取详情接口
const getTestWayInfo = async (value) => {
  await queryFormInfo(value).then((res) => {
    ruleFormData.value = res
    tableReturnList.value = res.semiLabelItemList
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
  download.excel(data, '半成品入库.xls')
}

// 按钮新增功能
const handleOpenSave = () => {
  dialogTitle.value = '新增半成品入库'
  dialogVisible.value = true
  handleCreateCode()
}

// 按钮/表格 - 修改功能
const handleOpenEdit = async (value?) => {
  dialogTitle.value = '修改半成品入库'
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

const handleBatchSyncSemiLabel = () => {
  const getId = getSelectionData.value.map((item) => item.id)
  batchSyncSemiLabel(getId).then((res) => {
    ElMessage({ message: res, type: 'success' })
    getTableList()
  })
}
// 确认同步
const handleAffirm = (value) => {
  const params = {
    id: value.id
  }
  syncSemiLabel(params).then((res) => {
    ElMessage({ message: res, type: 'success' })
  })
}
// 表格详情功能
const handleOpenDetailsData = async (value) => {
  await getTestWayInfo(value.id)
  dialogTitle.value = '半成品入库详情'
  isShowView.value = true
  dialogVisible.value = true
}

// 分页功能
const handlePagination = (value) => {
  pagination = value?.value
  getTableList()
}

const batchSyncState = ref(true)

// 表格选择事件
const handleSelectionChange = (value) => {
  getSelectionData.value = value
  batchSyncState.value = !getSelectionData.value.length
  btnConditions[1].disabled = !getSelectionData.value.length || getSelectionData.value.length >= 2
  btnConditions[2].disabled = !getSelectionData.value.length
}

// 表格弹框关闭
const handleCloseDialogVisible = () => {
  isShowView.value = false
  ruleFormData.value = {}
  tableReturnList.value = []
  dialogVisible.value = false
}

// 生成检验编号
const handleCreateCode = () => {
  getCreateCode('SEMI_LEBEL_CODE').then((res) => {
    ruleFormData.value.number = res
  })
}

// 表格弹框提交
const handleDialogSubmit = async () => {
  const params = {
    ...ruleFormData.value,
    semiLabelItemList: tableReturnList.value
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

// 获取仓库列表下拉接口
const getWareHousesQueryTableListAPI = () => {
  warehouseList().then((res) => {
    wareHousesSelectList.value = res.map((item) => {
      return {
        value: item.warehouseName,
        label: item.warehouseName
      }
    })
  })
}

// 初始化数据
const info = () => {
  getTableList()
  getWareHousesQueryTableListAPI()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
