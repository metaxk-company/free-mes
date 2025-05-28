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
      <template #inspectMethod="{ scope }">
        {{ MARRY_INSPECTION_METHOD[scope.row.inspectMethod] }}
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
    width="90%"
    height="90%"
    :title="dialogTitle"
    @close="handleCloseDialogVisible"
  >
    <el-divider content-position="left">基础条件</el-divider>
    <el-form ref="ruleFormRef" :model="ruleFormData" :rules="rules" label-width="90px" status-icon>
      <el-row>
        <el-col :span="6">
          <el-form-item label="检验单号" :prop="isShowView ? '' : 'number'">
            {{ ruleFormData.number }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="生产订单" :prop="isShowView ? '' : 'productionOrder'">
            <el-input
              v-model="ruleFormData.productionOrder"
              :disabled="isShowView"
              placeholder="请输入生产订单"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="客户编码" :prop="isShowView ? '' : 'customerName'">
            <el-input
              v-model="ruleFormData.customerName"
              :disabled="isShowView"
              placeholder="请输入客户编码"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="客户名称" :prop="isShowView ? '' : 'reelNumber'">
            <el-select
              :disabled="isShowView"
              v-model="ruleFormData.reelNumber"
              placeholder="请选择客户名称"
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
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-form-item label="检验标准号" :prop="isShowView ? '' : 'reelNumber'">
            <el-select
              :disabled="isShowView"
              v-model="ruleFormData.reelNumber"
              placeholder="请选择检验标准号"
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
        <el-col :span="6">
          <el-form-item label="版本" :prop="isShowView ? '' : 'reelNumber'">
            <el-select
              :disabled="isShowView"
              v-model="ruleFormData.reelNumber"
              placeholder="请选择版本"
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
        <el-col :span="6">
          <el-form-item label="盘号" :prop="isShowView ? '' : 'reelNumber'">
            <el-select
              :disabled="isShowView"
              v-model="ruleFormData.reelNumber"
              placeholder="请选择盘号"
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
        <el-col :span="6">
          <el-form-item label="颜色" :prop="isShowView ? '' : 'color'">
            <el-select :disabled="isShowView" v-model="ruleFormData.color" placeholder="请选择颜色">
              <el-option
                v-for="item in INSPECTION_METHOD"
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
          <el-form-item label="线别" :prop="isShowView ? '' : 'lineType'">
            <el-select
              :disabled="isShowView"
              v-model="ruleFormData.lineType"
              placeholder="请选择线别"
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
        <el-col :span="6">
          <el-form-item label="型号" :prop="isShowView ? '' : 'model'">
            <el-select :disabled="isShowView" v-model="ruleFormData.model" placeholder="请选择型号">
              <el-option
                v-for="item in INSPECTION_METHOD"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="规格" :prop="isShowView ? '' : 'spec'">
            <el-select :disabled="isShowView" v-model="ruleFormData.spec" placeholder="请选择规格">
              <el-option
                v-for="item in INSPECTION_METHOD"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select> </el-form-item
        ></el-col>
      </el-row>
    </el-form>

    <div v-for="(item, index) in categorizedData" :key="index">
      <el-divider content-position="left">{{ index }}</el-divider>
      <testingItemsTable
        :inspect-code="ruleFormData.number"
        :table-testing-items-data-list="item"
        :is-show-view="isShowView"
      />
    </div>
    <template #footer>
      <el-button type="success" @click="handleQualified(1)" v-if="!isShowView">合格</el-button>
      <el-button type="danger" @click="handleQualified(0)" v-if="!isShowView">不合格</el-button>
      <el-button type="primary" @click="handleDialogSubmit" v-if="!isShowView">提交</el-button>
      <el-button @click="handleCloseDialogVisible">取消</el-button>
    </template>
    <el-divider content-position="left">上传图片</el-divider>
    <el-upload
      :headers="uploadHeaders"
      v-model:file-list="imgFileList"
      :action="actionURL"
      :data="{ idValue: 11 }"
      list-type="picture-card"
      :limit="10"
      :on-remove="handleRemove"
      :on-success="handleUploadFileSuccess"
      style="width: 800px"
      :on-exceed="handleExceed"
      :multiple="true"
    >
      <template #file="{ file }">
        <div>
          <img class="el-upload-list__item-thumbnail" :src="file.url" alt="" />
          <span class="el-upload-list__item-actions">
            <span class="el-upload-list__item-delete" @click="handleRemove(file)">
              <el-icon><Delete /></el-icon>
            </span>
          </span>
        </div>
      </template>
      <el-button :icon="'Plus'" link />
      <template #tip>
        <div class="el-upload__tip">最多支持上传10张</div>
      </template>
    </el-upload>
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
import { queryTreeSelect } from '@/api/masterData/materialManage'
import { getTenantId } from '@/utils/auth'
import type { UploadProps, UploadUserFile, UploadFile, UploadFiles } from 'element-plus'
import {
  getTestStandardCode,
  getTestStandardList,
  addTestStandard,
  queryTestStandardInfo,
  upDateTestStandardInfo,
  deleteTestStandardInfo
} from '@/api/inspectionStandards/processInspection'

const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  number: [{ required: true, message: '检验单号不能为空', trigger: 'blur' }],
  productionOrder: [{ required: true, message: '生产单号不能为空', trigger: 'blur' }],
  customerName: [{ required: true, message: '客户编码不能为空', trigger: 'blur' }],
  itemTypeList: [{ required: true, message: '产品分类不能为空', trigger: 'blur' }],
  version: [{ required: true, message: '检验版本不能为空', trigger: 'blur' }],
  processCodeList: [{ required: true, message: '来料编号不能为空', trigger: 'blur' }],
  quantity: [{ required: true, message: '检测数量不能小于1', trigger: 'blur' }]
})
const data = ref([
  { id: 1, itemName: '这是第一条数据', itemStandard: 'Item 1', color: 'red', status: 0 },
  { id: 1, itemName: '这是第er条数据', itemStandard: 'Item 1', color: 'green', status: 1 },
  { id: 1, itemName: '这是第3条数据', itemStandard: 'Item 1', color: 'yellow' },
  { id: 1, itemName: '这是第4条数据', itemStandard: 'Item 1', color: 'aa' },
  { id: 1, itemName: '这是第6条数据', itemStandard: 'Item 1', color: 'red' },
  { id: 1, itemName: '这是第6条数据', itemStandard: 'Item 1', color: 'aa' },
  { id: 1, itemName: '这是第77条数据', itemStandard: 'Item 1', color: 'aa' },
  { id: 1, itemName: '这是第88条数据', itemStandard: 'Item 1', color: 'aa' },
  { id: 1, itemName: '这是第9条数据', itemStandard: 'Item 1', color: 're' }
])

// 数据处理
const categorizedData = data.value.reduce((result, item) => {
  if (!result[item.color]) {
    result[item.color] = []
  }
  result[item.color].push(item)
  return result
}, {})
// 处理合格/不合格操作
const handleQualified = (value) => {
  console.log(value)
}
// 搜索内容值
const searchModel = ref({
  number: '',
  processName: '',
  inspectScenario: ''
})
const tableLoading = ref(false) // 表格加载
const tableDataList = ref([]) // 表格数据

const tableTestingItemsDataList = ref([
  { inspectName: '', inspectStand: '', inspectDevice: '', remark: '', status: '', actual: '' }
])
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
const treeDataSelect = ref() // 产品分类树形下拉参数
// 图片超出10张提醒
const handleExceed: UploadProps['onExceed'] = (files, uploadFiles) => {
  ElMessage.warning(`最多只能上传十张图片`)
}
const uploadHeaders = ref({
  'tenant-id': getTenantId()
})
const imgFileList = ref<UploadUserFile[]>([])
const actionURL = ref(
  import.meta.env.VITE_BASE_URL +
    import.meta.env.VITE_API_URL +
    '/mes/qc/process/form/uploadPicture'
)
// 上传图片成功时候操作
const handleUploadFileSuccess = (
  response: any,
  uploadFile: UploadFile,
  uploadFiles: UploadFiles
) => {
  console.log(response)
  console.log(uploadFile)
  console.log(uploadFiles)
}

// 删除图片
const handleRemove = (file: UploadFile) => {
  imgFileList.value = imgFileList.value.filter((item) => {
    return item.url !== file.url
  })
}
// 搜索功能
const handleQueryData = () => {
  getTableList()
}

// 查询详情信息
const getTestStandardInfo = async (value) => {
  await queryTestStandardInfo(value).then((res) => {
    res.inspectDeviceList == 'true' ? true : false
    ruleFormData.value = res
    tableTestingItemsDataList.value = res.inspectDeviceList
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
  dialogTitle.value = '新增'
  dialogVisible.value = true
  handleCreateCode()
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
  getTestStandardCode('OTHER_RECORD_CODE').then((res) => {
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
// 表格弹框关闭
const handleCloseDialogVisible = () => {
  dialogVisible.value = false
  isShowView.value = false
  ruleFormData.value = {
    enableFlag: false,
    quantity: 1
  }
  tableTestingItemsDataList.value = [
    { inspectName: '', inspectStand: '', inspectDevice: '', remark: '' }
  ]
}

// 表格弹框提交
const handleDialogSubmit = async () => {
  const params = {
    ...ruleFormData.value,
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
// const getInspectionStandardApi = () => {
//   inspectionStandard().then((resp) => {
//     console.log(resp, '新增')
//   })
// }
// 初始化数据
const info = () => {
  queryTreeSelectAPI()
  getTableList()
  // getInspectionStandardApi()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
