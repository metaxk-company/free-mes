<template>
  <el-form :model="queryParams" :inline="true" ref="ruleFormRef">
    <el-form-item label="物料编码" prop="itemCode">
      <el-input v-model="queryParams.itemCode" placeholder="请输入物料编码" />
    </el-form-item>
    <el-form-item label="物料名称" prop="itemName">
      <el-input v-model="queryParams.itemName" placeholder="请输入物料名称" />
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
    <template #itemOrProduct="{ scope }">
      {{ PRODUCT_CLASS[scope.row.itemOrProduct] }}
    </template>
    <template #itemCode="{ scope }">
      <el-button link type="primary" @click="handleMaterialInfo(scope.row)">{{
        scope.row.itemCode
      }}</el-button>
    </template>
    <template #enableFlag="{ scope }">
      <el-tag class="ml-2" :type="scope.row.enableFlag === 'Y' ? 'success' : 'danger'">{{
        UN_KNOW_STATE[scope.row.enableFlag]
      }}</el-tag>
    </template>
    <template #safeStockFlag="{ scope }">
      <el-tag class="ml-2" :type="scope.row.safeStockFlag === 'Y' ? 'success' : 'danger'">
        {{ UN_KNOW_STATE[scope.row.safeStockFlag] }}</el-tag
      >
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
  <XModal
    :key="Math.random()"
    v-model="dialogVisible"
    :title="productTitle"
    width="1000px"
    height="700px"
    @close="handleCancel"
  >
    <product-form
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
  <!-- 物料导入对话框 -->
  <XModal :title="upload.title" v-model="upload.open" @before-close="handleUploadClose">
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
import { ref, onMounted, watch } from 'vue'
import type { FormInstance, UploadInstance, UploadRawFile } from 'element-plus'
import { ElMessageBox, ElMessage, genFileId } from 'element-plus'
import commonTable from '@/components/CommonTable/index.vue'
import { columnState, BTN_OPERATION } from '../data'
import productForm from './productForm.vue'
import { UploadFilled } from '@element-plus/icons-vue'
import {
  getUnitList,
  addMDItem,
  upDateMDItem,
  queryMDItemInfo,
  deleteMDItem,
  downloadListData,
  downloadTemplate
} from '@/api/masterData/materialManage'
import { UN_KNOW_STATE, PRODUCT_CLASS } from '@/utils/const'
import download from '@/utils/download'
import { getAccessToken } from '@/utils/auth'

const ruleFormRef = ref<FormInstance | any>(null)
const productFormRef = ref<any>(null)
const uploadRef = ref<UploadInstance>()

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

// watch(()=>)

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
let productFormData = ref<any>({
  // 物料编码
  itemCode: '',
  // 是否自动生成
  autoGenFlag: false,
  //物料名称
  itemName: '',
  // 规格型号
  specification: '',
  // 单位
  unitOfMeasure: 'kg',
  // 换算后单位
  unitTo: '',
  // 产品物料分类
  itemTypeId: '',
  // 是否启用
  enableFlag: 'Y',
  // 安全库存
  safeStockFlag: 'N',
  // 最小库存
  minStock: '',
  // 最大库存量
  maxStock: '',
  // 备注
  remark: '',
  id: '',
  purchasePrice: 1,
  salePrice: 1,
  lineType: '',
  model: '',
  spec: ''
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
  headers: { Authorization: 'Bearer ', Tenantid: 1, accessToken: getAccessToken() },
  //
  // 上传的地址
  url: `${import.meta.env.VITE_BASE_URL}/mes/md/item/import`
})

// watch(
//   () => productFormData.value,
//   (newValue) => {
//     if (newValue.lineType !== '' || newValue.model !== '' || newValue.spec !== '') {
//       newValue.itemName = newValue.lineType + '|' + newValue.model + '|' + newValue.spec
//     } else {
//     }
//   },
//   { deep: true }
// )

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
  productTitle.value = '新增物料/产品'
  dialogState.value = 'addView'
}

// 删除
const handleRemoveEvent = () => {
  deleteRowListInfo()
}

// 替换文件
const handleExceed = (files) => {
  uploadRef.value!.clearFiles()
  const file = files[0] as UploadRawFile
  file.uid = genFileId()
  uploadRef.value!.handleStart(file)
}

// 下载模版
const handleDownloadTemplate = async () => {
  const data = await downloadTemplate()
  download.excel(data, '物料产品模版.xls')
}

// 导入
const handleImportEvent = () => {
  upload.open = !upload.open
}

// 导出
const handleExportEvent = async () => {
  const data = await downloadListData()
  download.excel(data, '物料产品管理.xls')
}

//  查看详情
const handleMaterialInfo = (value) => {
  let state = queryInfo(value)
  state.then((res) => {
    dialogVisible.value = !dialogVisible.value
    productTitle.value = '查看物料/产品'
    dialogState.value = 'View'
  })
}

// 修改
const handleModifyInfo = (value?: any) => {
  let state = queryInfo(value)
  state.then((res) => {
    dialogVisible.value = !dialogVisible.value
    productTitle.value = '修改物料/产品'
    dialogState.value = 'editView'
  })
}

// 调用详情接口
const queryInfo = (value) => {
  const params = value?.id || setSelectionList.value
  return new Promise<any>((resolve, reject) => {
    queryMDItemInfo(params).then((res) => {
      productFormData.value = res
      resolve(res)
    })
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
    deleteMDItem(batchID).then((res) => {
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

// 弹框关闭
const handleCancel = () => {
  productFormRef.value.handleCancel()
  productFormData.value = {
    // 物料编码
    itemCode: '',
    // 是否自动生成
    autoGenFlag: false,
    //物料名称
    itemName: '',
    // 规格型号
    specification: '',
    // 单位
    unitOfMeasure: '',
    // 换算后单位
    unitTo: '',
    // 产品物料分类
    itemTypeId: '',
    // 是否启用
    enableFlag: 'Y',
    // 安全库存
    safeStockFlag: 'N',
    // 最小库存
    minStock: '',
    // 最大库存量
    maxStock: '',
    // 备注
    remark: '',
    id: ''
  }
  dialogVisible.value = false
  handleResetSearch()
}

// 弹框确定
const handleSubmitForm = async () => {
  if (dialogState.value === 'View') return (dialogVisible.value = !dialogVisible.value)
  let state = await productFormRef.value.handleSubmitForm()
  if (!state) return

  const params = {
    ...productFormData.value,
    itemTypeId:
      typeof productFormData.value.itemTypeId === 'number'
        ? productFormData.value.itemTypeId
        : productFormData.value.itemTypeId?.slice(-1).toString()
  }

  if (productFormData.value.id) {
    upDateMDItem(params).then((res) => {
      if (!res) return
      ElMessage.success('修改成功')
      handleCancel()
    })
  } else {
    addMDItem(params).then((res) => {
      ElMessage.success('新增成功')
      handleCancel()
    })
  }
}

// 文件上传中处理
const handleFileUploadProgress = (event, file, fileList) => {
  upload.isUploading = true
}

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

// 上传文件弹框 - 取消
const handleUploadClose = () => {
  upload.open = !upload.open
  uploadRef.value!.clearFiles()
}

// 获取单位
const getUnitListAPI = () => {
  getUnitList().then((res) => {
    measureOptions.value = res
  })
}

const info = () => {
  getUnitListAPI()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
