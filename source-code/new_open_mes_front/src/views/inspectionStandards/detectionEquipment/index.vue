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
        v-for="(item, index) in [
          btnConditions[0],
          btnConditions[1],
          btnConditions[2],
          btnConditions[4]
        ]"
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
    width="80%"
    :title="dialogTitle"
    @close="handleCloseDialogVisible"
  >
    <el-form ref="ruleFormRef" :model="ruleFormData" :rules="rules" label-width="100px" status-icon>
      <el-row>
        <el-col :span="6">
          <el-form-item label="仪器编号">
            {{ ruleFormData.deviceCode }}
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="仪器名称" :prop="isShowView ? '' : 'deviceName'">
            <el-input v-model="ruleFormData.deviceName" :disabled="isShowView" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="数据协议" :prop="isShowView ? '' : 'agreement'">
            <el-select
              :disabled="isShowView"
              v-model="ruleFormData.agreement"
              placeholder="请选择数据协议"
            >
              <el-option
                v-for="item in DATA_PROTOCOL"
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
          <el-form-item label="所属车间" :prop="isShowView ? '' : 'workshop'">
            <!-- <el-input v-model="ruleFormData.name" :disabled="isShowView" /> -->
            <el-select
              :disabled="isShowView"
              v-model="ruleFormData.workshop"
              placeholder="请选择所属车间"
            >
              <el-option
                v-for="item in workShopSelectList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="所属部门" :prop="isShowView ? '' : 'department'">
            <el-select
              :disabled="isShowView"
              v-model="ruleFormData.department"
              placeholder="请选择所属部门"
            >
              <el-option
                v-for="item in sectorSelectList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="所属工序" :prop="isShowView ? '' : 'processList'">
            <el-select
              :disabled="isShowView"
              v-model="ruleFormData.processList"
              placeholder="请选择所属工序"
              multiple
            >
              <el-option
                v-for="item in workSequenceSelectList"
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
    <template #footer>
      <el-button type="primary" @click="handleDialogSubmit" v-if="!isShowView">提交</el-button>
      <el-button @click="handleCloseDialogVisible">取消</el-button>
    </template>
  </XModal>

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
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, tableColumns } from './data'
import { btnConditions, DATA_PROTOCOL } from '@/utils/const'
import type { FormInstance, FormRules, UploadInstance, UploadRawFile } from 'element-plus'
import { ElMessageBox, ElMessage, genFileId } from 'element-plus'
import {
  getUtensilGenCode,
  getUtensilList,
  addUtensil,
  queryUtensilInfo,
  upDateUtensilInfo,
  deleteUtensilInfo,
  getWorkshopList,
  getDeptList,
  queryCraftListAll,
  downloadTemplate,
  downloadListData
} from '@/api/inspectionStandards/detectionEquipment'
import download from '@/utils/download'

const uploadRef = ref<UploadInstance>()
const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  deviceCode: [{ required: true, message: '请输入仪器编号', trigger: 'blur' }],
  deviceName: [{ required: true, message: '输入仪器名称', trigger: 'blur' }],
  agreement: [{ required: true, message: '请选择数据协议', trigger: 'blur' }],
  workshop: [{ required: true, message: '请选择所属车间', trigger: 'blur' }],
  department: [{ required: true, message: '请选择所属部门', trigger: 'blur' }],
  process: [{ required: true, message: '请选择所属工序', trigger: 'blur' }]
})

// 用户导入的数据
const upload = reactive<object | any>({
  // 是否显示弹出层（用户导入）
  open: false,
  // 弹出层标题（用户导入）
  title: '检测器具导入',
  // 是否禁用上传
  isUploading: false,
  // 是否更新已经存在的用户数据
  updateSupport: 0,
  // 设置上传的请求头部
  headers: { Authorization: 'Bearer ' },
  // 上传的地址
  url: `${import.meta.env.VITE_BASE_URL}/admin-api/mes/md/item/import`
})
// 搜索内容值
const searchModel = ref({
  deviceCode: '',
  deviceName: ''
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
  isCreate: false
}) // 表单数据
const isShowView = ref(false) // 是否是查看状态
const dialogTitle = ref('')
const workShopSelectList = ref<any>([]) // 车间下拉列表
const sectorSelectList = ref<any>([]) // 部门下拉列表
const workSequenceSelectList = ref<any>([]) // 工序列表数据

// 上传文件弹框 - 确定
const handleSubmitUpload = () => {
  uploadRef.value!.submit()
}

// 下载模版
const handleDownloadTemplate = async () => {
  const data = await downloadTemplate()
  download.excel(data, '检测器具模板.xls')
}

// 文件长传成功处理
const handleFileSuccess = () => {
  upload.open = false
  upload.isUploading = false
  getTableList()
}

// 替换文件
const handleExceed = (files) => {
  uploadRef.value!.clearFiles()
  const file = files[0] as UploadRawFile
  file.uid = genFileId()
  uploadRef.value!.handleStart(file)
}

// 文件上传中处理
const handleFileUploadProgress = (event, file, fileList) => {
  upload.isUploading = true
}

// 上传文件弹框 - 取消
const handleUploadClose = () => {
  upload.open = !upload.open
  uploadRef.value!.clearFiles()
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
    case 'upload':
      handleOpenUpload()
      break
  }
}

const handleOpenUpload = () => {
  upload.open = !upload.open
}

// 查询器具详情内容
const getUtensilInfo = async (value) => {
  await queryUtensilInfo(value).then((res) => {
    ruleFormData.value = res
  })
}

// 按钮新增功能
const handleOpenSave = () => {
  dialogTitle.value = '新增'
  handleCreate()
  dialogVisible.value = true
}

// 按钮/表格 - 修改功能
const handleOpenEdit = async (value?) => {
  dialogTitle.value = '修改'
  const getId = value ? value?.id : getSelectionData.value[0].id
  await getUtensilInfo(getId)
  dialogVisible.value = true
}

// 按钮/表格 - 删除功能
const handleOpenRemove = (value?) => {
  const getId = value ? [value?.id] : getSelectionData.value.map((item) => item.id)
  ElMessageBox.confirm('是否删除当前数据?').then(() => {
    deleteUtensilInfo(getId).then((res) => {
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
  await getUtensilInfo(value.id)
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

// 生成编号
const handleCreate = () => {
  getUtensilGenCode('QC_DEVICE_CODE').then((res) => {
    ruleFormData.value.deviceCode = res
  })
}

// 表格弹框提交
const handleDialogSubmit = async () => {
  if (!ruleFormRef.value) return
  await ruleFormRef.value.validate((valid) => {
    if (!valid) return
    console.log(ruleFormRef.value)
    if (!ruleFormData.value?.id) {
      addUtensil(ruleFormData.value).then((res) => {
        ElMessage({
          message: '新增成功.',
          type: 'success'
        })
        handleCloseDialogVisible()
        getTableList()
      })
    } else {
      upDateUtensilInfo(ruleFormData.value).then((res) => {
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

// 表格弹框关闭
const handleCloseDialogVisible = () => {
  dialogVisible.value = false
  isShowView.value = false
  ruleFormData.value = {
    isCreate: false
  }
}

// 获取所属车间下拉参数
const getWorkShopSelectList = () => {
  getWorkshopList().then((res) => {
    workShopSelectList.value = res.map((item) => {
      return {
        label: item.workshopName,
        value: item.workshopCode
      }
    })
  })
}

// 获取所属部门下拉参数
const getSectorSelectList = () => {
  getDeptList().then((res) => {
    sectorSelectList.value = res.map((item) => {
      return {
        label: item.name,
        value: item.id
      }
    })
  })
}

// 获取所属工序下拉参数
const getWorkSequenceSelectList = () => {
  queryCraftListAll().then((res) => {
    workSequenceSelectList.value = res.map((item) => {
      return {
        label: item.processName,
        value: item.processCode
      }
    })
  })
}

// 获取表格数据
const getTableList = () => {
  tableLoading.value = true
  const params = {
    ...searchModel.value,
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize
  }
  getUtensilList(params)
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
  getWorkShopSelectList()
  getSectorSelectList()
  getWorkSequenceSelectList()
  getTableList()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
