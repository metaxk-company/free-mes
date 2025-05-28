<template>
  <el-form :model="queryParams" :inline="true" ref="ruleFormRef">
    <el-form-item label="设备编码" prop="machineryCode">
      <el-input v-model="queryParams.machineryCode" placeholder="请输入设备编码" />
    </el-form-item>
    <el-form-item label="设备名称" prop="machineryName">
      <el-input v-model="queryParams.machineryName" placeholder="请输入设备名称" />
    </el-form-item>
    <el-form-item label="设备状态" prop="status">
      <el-input v-model="queryParams.status" placeholder="请输入设备状态" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="handleSearch" plain>搜索</el-button>
      <el-button @click="handleResetSearch" plain>重置</el-button>
    </el-form-item>
  </el-form>

  <div style="margin-bottom: 16px">
    <el-button
      plain
      @click="handleBtnEvent(item.state)"
      v-for="(item, index) in BTN_OPERATION"
      :key="index"
      :type="item.type"
      :disabled="item.disabled"
      >{{ item.label }}</el-button
    >
  </div>
  <common-table
    :pagination="paginationData"
    :is-selection="true"
    :tableData="tableData"
    :columns="columnState"
    v-loading="loadingTable"
    @pagination-change="handlePagination"
    @selection-change="handleSelectionChange"
  >
    <template #machineryCode="{ scope }">
      <el-button link type="primary" @click="handleMaterialInfo(scope.row)">{{
        scope.row.machineryCode
      }}</el-button>
    </template>
    <template #status="{ scope }">
      {{ MAINTAIN_STATE[scope.row.status] }}
    </template>

    <template #operation="{ scope }">
      <el-button style="font-size: 17px" link type="primary" @click="handleModifyInfo(scope.row)"
        >修改</el-button
      >
      <el-button style="font-size: 17px" link type="danger" @click="handleDeleteInfo(scope.row)"
        >删除</el-button
      >
    </template>
  </common-table>
  <!-- 新增/修改 -->
  <XModal v-model="dialogVisible" :title="productTitle" width="50%" @close="handleCancel">
    <product-form
      :type-state-list="typeStateList"
      :measure-options="measureOptions"
      :treeMaterialForm="treeMaterialForm"
      :productFormData="productFormData"
      :dialog-state="dialogState"
      ref="productFormRef"
    />

    <template #footer>
      <el-button type="primary" @click="handleSubmitForm">{{
        dialogState === 'View' ? '返回' : '提交'
      }}</el-button>
      <el-button @click="handleCancel">关闭</el-button>
    </template>
  </XModal>

  <!-- 导入设备 -->
  <XModal title="设备类型导入" v-model="upload.open" @before-close="handleUploadClose">
    <el-upload
      ref="uploadRef"
      class="upload-demo"
      drag
      :action="upload.url"
      :limit="1"
      accept=".xlsx, .xls"
      :headers="upload.headers"
      :disabled="upload.isUploading"
      :auto-upload="false"
      :on-progress="handleFileUploadProgress"
      :on-exceed="handleExceed"
      :on-success="handleFileSuccess"
    >
      <el-icon class="el-icon--upload"><upload-filled /></el-icon>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
    </el-upload>
    <div class="el-upload__tip text-center">
      <el-link
        type="primary"
        :underline="false"
        style="vertical-align: baseline"
        @click="handleDownloadTemplate"
        >下载模板</el-link
      >
    </div>
    <template #footer>
      <el-button type="primary" @click="handleSubmitUpload">确 定</el-button>
      <el-button @click="handleUploadClose">取 消</el-button>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import type { FormInstance, UploadInstance, UploadRawFile } from 'element-plus'
import { ElMessageBox, ElMessage, genFileId } from 'element-plus'
import commonTable from '@/components/CommonTable/index.vue'
import { columnState, BTN_OPERATION } from '../data'
import productForm from './productForm.vue'
import {
  addMachinery,
  getMachineryInfo,
  upDateMachineryInfo,
  deleteMachineryInfo,
  downloadListData,
  queryTypeStateList,
  downloadTemplate
} from '@/api/facilityManage/ledger'
import { getWorkshopSelectList } from '@/api/masterData/workShopSetUp'
import download from '@/utils/download'
import { MAINTAIN_STATE } from '@/utils/const'

const ruleFormRef = ref<FormInstance | any>(null)
const productFormRef = ref<any>(null)

const props = defineProps({
  queryParams: {
    type: Object,
    default: () => {}
  },
  tableData: {
    type: Array<Object>,
    default: () => Array<Object>
  },
  paginationData: {
    type: Object as PropType<Table.Pagination>,
    default: () => {}
  },
  loadingTable: {
    type: Boolean,
    default: false
  },
  treeMaterialForm: {
    type: Object,
    default: () => {}
  }
})

const emit = defineEmits(['searchParams', 'handlePagination', 'resetSearch'])
const uploadRef = ref<UploadInstance>()

// 存储当前列表选中的值
const setSelectionList = ref([])
// 新增/修改弹框
const dialogVisible = ref<boolean>(false)
// 弹框标题
const productTitle = ref<string>('')
// 弹框状态 - 查看/修改
const dialogState = ref<string>('')
//单位列表
const measureOptions = ref([])
// 弹框表单数据
let productFormData = ref({
  // 设备编码
  machineryCode: '',
  // 是否自动生成
  autoGenFlag: false,
  //设备名称
  machineryName: '',
  // 品牌
  machineryBrand: '',
  // 设备分类
  machineryTypeId: '',
  // 规格型号
  machinerySpec: '',
  // 所属车间
  workshopId: '',
  // 备注
  remark: '',
  id: '',
  workshopCode: '',
  status: '',
  location: '',
  workshopName: '',
  // 产能
  capacity: '',
  // 型号
  pclModel: '',
  // 购买日期
  purchasingTime: null
})
// 用户导入的数据
const upload = reactive<object | any>({
  // 是否显示弹出层（用户导入）
  open: false,
  // 弹出层标题（用户导入）
  title: '物料/产品导入',
  // 是否禁用上传
  isUploading: false,
  // 是否更新已经存在的用户数据
  updateSupport: 0,
  // 设置上传的请求头部
  headers: { Authorization: 'Bearer ' },
  // 上传的地址
  url: ''
})
// 设别状态列表
const typeStateList = ref([])

// 文件长传成功处理
const handleFileSuccess = () => {
  upload.open = false
  upload.isUploading = false
  emit('searchParams')
}

// 上传文件弹框 - 确定
const handleSubmitUpload = () => {
  uploadRef.value!.submit()
}

// 下载模版
const handleDownloadTemplate = async () => {
  const data = await downloadTemplate()
  download.excel(data, '设备台账模板.xls')
}

// 文件上传中处理
const handleFileUploadProgress = (event, file, fileList) => {
  upload.isUploading = true
}

// 替换文件
const handleExceed = (files) => {
  uploadRef.value!.clearFiles()
  const file = files[0] as UploadRawFile
  file.uid = genFileId()
  uploadRef.value!.handleStart(file)
}

// 上传文件弹框 - 取消
const handleUploadClose = () => {
  upload.open = !upload.open
  uploadRef.value!.clearFiles()
}

// 搜索功能
const handleSearch = () => {
  emit('searchParams')
}

// 重置功能
const handleResetSearch = async () => {
  await ruleFormRef.value.resetFields()
  props.queryParams.itemTypeId = ''
  emit('resetSearch')
}

// 按钮操作事件
const handleBtnEvent = (value) => {
  switch (value) {
    case 'add':
      return handleAddEvent()
    case 'edit':
      return handleModifyInfo()
    case 'remove':
      return handleRemoveEvent()
    case 'import':
      return handleImportEvent()
    case 'export':
      return handleExportEvent()
  }
}

// 新增
const handleAddEvent = () => {
  dialogVisible.value = !dialogVisible.value
  productTitle.value = '新增设备'
  dialogState.value = 'addView'
}

// 删除
const handleRemoveEvent = () => {
  deleteRowListInfo()
}

// 导入
const handleImportEvent = () => {
  upload.open = !upload.open
}

// 导出
const handleExportEvent = async () => {
  const data = await downloadListData()
  download.excel(data, '设备台账.xls')
}

//  查看详情
const handleMaterialInfo = (value) => {
  queryInfo(value)
  productTitle.value = '查看物料/产品'
  dialogState.value = 'View'
}

// 修改
const handleModifyInfo = (value?: any) => {
  productTitle.value = '修改物料/产品'
  dialogState.value = 'editView'
  queryInfo(value)
}

// 调用详情接口
const queryInfo = (value) => {
  const params = value?.id || setSelectionList.value
  getMachineryInfo(params).then((res) => {
    productFormData.value = res
    dialogVisible.value = true
  })
}

// 表格操作 - 删除
const handleDeleteInfo = (_value: any) => {
  deleteRowListInfo(_value)
}

// 表格删除接口
const deleteRowListInfo = (value?) => {
  const params = value?.id || setSelectionList.value
  const batchID = Array.isArray(params) ? params : [params]
  ElMessageBox.confirm('是否删除当前行数据?').then(() => {
    deleteMachineryInfo(batchID).then((res) => {
      if (!res) return
      ElMessage.success('删除成功')
      emit('resetSearch')
    })
  })
}

// 表格勾选数据
const handleSelectionChange = (selection) => {
  setSelectionList.value = selection.map((item) => item.id)
  BTN_OPERATION[1].disabled = !setSelectionList.value.length || setSelectionList.value.length >= 2
  BTN_OPERATION[2].disabled = !setSelectionList.value.length
}

// 分页点击事件
const handlePagination = (value) => {
  emit('handlePagination', value)
}

// 弹框确定
const handleSubmitForm = async () => {
  if (dialogState.value === 'View') return (dialogVisible.value = !dialogVisible.value)
  let state = await productFormRef.value.handleSubmitForm()
  if (!state) return
  const params = {
    ...productFormData.value,
    updater: productFormData.value.id ? JSON.parse(localStorage.userForm).username : '',
    machineryTypeId:
      typeof productFormData.value.machineryTypeId === 'number'
        ? productFormData.value.machineryTypeId
        : productFormData.value.machineryTypeId?.slice(-1).toString()
  }

  if (productFormData.value.id) {
    upDateMachineryInfo(params).then((res) => {
      ElMessage.success('修改成功')
      handleCancel()
    })
  } else {
    addMachinery(params).then((res) => {
      ElMessage.success('新增成功')
      handleCancel()
    })
  }
}

// 弹框关闭
const handleCancel = () => {
  productFormRef.value.handleCancel()
  productFormData.value = {
    // 设备编码
    machineryCode: '',
    // 是否自动生成
    autoGenFlag: false,
    //设备名称
    machineryName: '',
    // 品牌
    machineryBrand: '',
    // 设备分类
    machineryTypeId: '',
    // 规格型号
    machinerySpec: '',
    // 所属车间
    workshopId: '',
    // 备注
    remark: '',
    id: '',
    // 设备状态
    status: '',
    workshopCode: '',
    location: '',
    workshopName: '',
    // 产能
    capacity: '',
    // 型号
    pclModel: '',
    // 购买日期
    purchasingTime: null
  }
  dialogVisible.value = false
  handleResetSearch()
}

// 获取车间下拉数据
const getUnitListAPI = () => {
  getWorkshopSelectList().then((res) => {
    measureOptions.value = res
  })
}

// 获取设备状态下拉数据
const getTypeStateListAPI = () => {
  queryTypeStateList().then((res) => {
    typeStateList.value = res.list
  })
}

const info = () => {
  getUnitListAPI(), getTypeStateListAPI()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
