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
        v-for="(item, index) in btnConditions.slice(0, 1)"
        :type="item.type"
        :key="index"
        :icon="item.icon"
        :disabled="item.disabled"
        @click="handleBtnOperation(item.state)"
      >
        {{ item.label }}</el-button
      >
      <el-button
        plain
        v-for="(item, index) in btnConditions.slice(3, 4)"
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
      <template #status="{ scope }">
        {{ scope.row.status == 1 ? '合格' : '不合格' }}
      </template>
      <template #operation="{ scope }">
        <el-button type="primary" @click="handleOpenDetailsData(scope.row)">详情</el-button>
        <el-button type="warning" @click="handleOpenEdit(scope.row)">修改</el-button>
      </template>
    </common-table>
  </ContentWrap>
  <!-- 检验标准对比 -->
  <XModal v-model="QualityInspectionDialog" title="检验标准对比" width="80%" :showFooter="false">
    <inspection-standard-model
      :quality-inspection-from="qualityInspectionFrom"
      @end-test-submit="handleEndTestSubmit"
    />
  </XModal>

  <!-- 来料检验结果记录表-->
  <XModal :showFooter="false" v-model="testResultsDialog" title="来料检验结果表" width="80%">
    <test-results-model :test-results-list="testResultsList" />
  </XModal>

  <!-- 统计检验数量结果表 -->
  <XModal :showFooter="false" v-model="bearFruitDialog" title="统计检验数量结果表" width="80%">
    <div style="display: flex; justify-content: space-around">
      <div
        style="text-align: center; line-height: 50px; font-size: 22px; font-weight: bold"
        v-for="(item, index) in getCountList"
        :key="index"
      >
        <span>{{ item.label }}</span>
        <div :class="'color-' + (index + 1)">
          {{ item.value }}
        </div>
      </div>
    </div>
  </XModal>
  <!-- 新增检验弹框 -->
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
          <el-form-item label="生产单号" :prop="isShowView ? '' : 'workOrderCode'">
            <el-input
              :disabled="isShowView"
              v-model="ruleFormData.workOrderCode"
              placeholder="请选择生产单号"
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
        <el-col :span="6">
          <el-form-item label="客户编码" :prop="isShowView ? '' : 'clientCode'">
            <el-input
              v-model="ruleFormData.clientCode"
              :disabled="isShowView"
              placeholder="请输入客户编码"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="客户名称" :prop="isShowView ? '' : 'clientName'">
            <el-input
              v-model="ruleFormData.clientName"
              :disabled="isShowView"
              placeholder="请输入客户编码"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-form-item label="检验方式" :prop="isShowView ? '' : 'inspectWay'">
            <el-select
              :disabled="isShowView"
              v-model="ruleFormData.inspectWay"
              placeholder="请选择检验方式"
            >
              <el-option
                v-for="item in INSPECTION_METHOD"
                :key="item.value"
                :label="item.label"
                :value="item.label"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="检验标准号" :prop="isShowView ? '' : 'otherStandardNumber'">
            <el-input
              v-model="ruleFormData.otherStandardNumber"
              :disabled="true"
              placeholder="请输入检验标准号"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="盘号" :prop="isShowView ? '' : 'reelNumber'">
            <el-input v-model="ruleFormData.reelNumber" :disabled="true" placeholder="请输入盘号" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="颜色" :prop="isShowView ? '' : 'color'">
            <el-input v-model="ruleFormData.color" :disabled="true" placeholder="请输入颜色" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-form-item label="型号" :prop="isShowView ? '' : 'model'">
            <el-input v-model="ruleFormData.model" :disabled="true" placeholder="请输入型号" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="规格" :prop="isShowView ? '' : 'spec'">
            <el-input v-model="ruleFormData.spec" :disabled="true" placeholder="请输入规格" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="线别" :prop="isShowView ? '' : 'lineType'">
            <el-input v-model="ruleFormData.lineType" :disabled="true" placeholder="请输入线别" />
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
      :disabled="isShowView"
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
    <template #footer>
      <el-button type="success" @click="handleQualified(1)" v-if="!isShowView">合格</el-button>
      <el-button type="danger" @click="handleQualified(0)" v-if="!isShowView">不合格</el-button>
      <el-button @click="handleCloseDialogVisible">取消</el-button>
    </template>
  </XModal>
  <!-- 查看工序信息弹框 -->
  <XModal v-model="processDialog" width="60%" title="查看生产单号">
    <processInfoTable ref="processInfoTableRef" />
    <template #footer>
      <el-button type="primary" @click="handleProcessSubmit">提交</el-button>
      <el-button @click="handleCloseProcessDialog">取消</el-button>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, tableColumns } from './data'
import { btnConditions, INSPECTION_METHOD } from '@/utils/const'
import testResultsModel from './components/test-results-model.vue'
import inspectionStandardModel from './components/inspection-standard-model.vue'
import type { FormInstance, FormRules } from 'element-plus'
import testingItemsTable from './components/testing-items-table.vue'
import processInfoTable from './components/process-info-table.vue'
import {
  getInspectionFormInfo,
  getQualityTestingInfo
} from '@/api/processInspection/inspectionReceipts'
import {
  getOtherRecordList,
  downloadListData,
  getOtherInspectCode,
  addFormList,
  addFormData,
  upDateOtherInfo,
  queryOtherInfo
} from '@/api/inspect/otherInspect'
import download from '@/utils/download'
import { getTenantId } from '@/utils/auth'
import type { UploadProps, UploadUserFile, UploadFile, UploadFiles } from 'element-plus'
const rules = reactive<FormRules>({
  number: [{ required: true, message: '检验单号不能为空', trigger: 'change' }],
  workOrderCode: [{ required: true, message: '生产单号不能为空', trigger: 'change' }],
  customerName: [{ required: false, message: '客户编码不能为空', trigger: 'blur' }]
})
const ruleFormRef = ref<FormInstance>()
const testResultsDialog = ref(false)
const code = ref()
// 搜索内容值
const searchModel = ref({
  number: '',
  workOrderCode: '',
  model: '',
  spec: '',
  lineType: ''
})
const tableLoading = ref(false) // 表格加载
const tableDataList = ref([{ status: '0' }, { status: '1' }]) // 表格数据
const getSelectionData = ref()
let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const QualityInspectionDialog = ref<boolean>(false)
const qualityInspectionFrom = ref({}) // 用来存储获取检验表的信息
const bearFruitDialog = ref(false)
const getCountList = ref<any>([])
const testResultsList = ref<any>()
const dialogVisible = ref(false) // 弹框显示
const dialogTitle = ref('')
const isShowView = ref(false) // 是否是查看状态
const processDialog = ref<boolean>(false) // 查看工序弹框
const processInfoTableRef = ref<any>(null)
const ruleFormData = ref<any>({
  enableFlag: false,
  quantity: 1,
  unitOfMeasure: ''
}) // 表单数据
const tableTestingItemsDataList = ref([
  { inspectName: '', inspectStand: '', inspectDevice: '', remark: '', status: '', itemValue: '' }
])
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
    '/mes/qc/other/record/uploadPicture'
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
const handleEndTestSubmit = (value) => {
  getTableList()
  getCountList.value = value
  QualityInspectionDialog.value = false
  bearFruitDialog.value = true
}
// 生产单号选择多个任务弹框
const handleOpenProcessDialog = () => {
  processDialog.value = true
}

// 工序弹框信息选择提交
const handleProcessSubmit = () => {
  if (!processInfoTableRef.value?.selectionValue.length)
    return ElMessage({
      message: '至少选择一条信息',
      type: 'warning'
    })
  ruleFormData.value.workOrderCode = processInfoTableRef.value?.selectionValue.map(
    (item) => item.workorderCode
  )

  const param = {
    workOrderCode: ruleFormData.value.workOrderCode[0]
  }
  addFormList(param).then((res) => {
    ruleFormData.value = res
    ruleFormData.value.number = code.value
    tableTestingItemsDataList.value = res.otherRecordItemList
  })

  processDialog.value = false
}

// 工序弹框信息选择取消
const handleCloseProcessDialog = () => {
  processDialog.value = false
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

    case 'download':
      handleOpenDownload()
      break
  }
}
// // 按钮新增功能
const handleOpenSave = () => {
  dialogTitle.value = '新增'
  dialogVisible.value = true
  handleCreateCode()
}
// 生成工序标准编码
const handleCreateCode = () => {
  getOtherInspectCode('OTHER_RECORD_CODE').then((res) => {
    ruleFormData.value.number = res
    code.value = res
  })
}
// 处理合格/不合格操作
const handleQualified = async (value) => {
  const params = {
    ...ruleFormData.value,
    otherRecordItemList: tableTestingItemsDataList.value,
    status: value
  }

  if (!ruleFormRef.value) return

  await ruleFormRef.value.validate((valid) => {
    if (!ruleFormData.value.id) {
      addFormData(params).then((res) => {
        ElMessage({
          message: '新增成功',
          type: 'success'
        })
        handleCloseDialogVisible()
        getTableList()
      })
    } else {
      upDateOtherInfo(params).then((res) => {
        ElMessage({
          message: '修改成功',
          type: 'success'
        })
        handleCloseDialogVisible()
        getTableList()
      })
    }
  })
  // 弹窗关闭
  handleCloseDialogVisible()
}
// 表格详情功能
const handleOpenDetailsData = async (value) => {
  await getOtherInfo(value.id)
  dialogTitle.value = '详情'
  isShowView.value = true
  dialogVisible.value = true
}
// 获取详情接口
const getOtherInfo = async (value) => {
  await queryOtherInfo(value).then((res) => {
    ruleFormData.value = res
    tableTestingItemsDataList.value = res.otherRecordItemList
  })
}
// 按钮/表格 - 修改功能
const handleOpenEdit = async (value?) => {
  dialogTitle.value = '修改'
  const getId = value ? value?.id : getSelectionData.value[0].id
  await getOtherInfo(getId)
  dialogVisible.value = true
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
    { inspectName: '', inspectStand: '', inspectDevice: '', remark: '', status: '', itemValue: '' }
  ]
}

const handleOpenDownload = async () => {
  const data = await downloadListData()
  download.excel(data, '检验单据.xls')
}

// 按钮检验功能
const handleOpenQualityInspection = async (value) => {
  const params = {
    id: value.id,
    processCode: value.processCode,
    inspectUser: JSON.parse(localStorage.userForm).username
  }
  if (value.status == 0) {
    // 质检
    getInspectionFormInfo(params).then((res) => {
      qualityInspectionFrom.value = res
      QualityInspectionDialog.value = true
    })
  } else {
    // 质检结果
    getQualityTestingInfo(params).then((res) => {
      testResultsList.value = res
      testResultsDialog.value = true
    })
  }
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

// 获取表格数据
const getTableList = () => {
  tableLoading.value = true
  const params = {
    ...searchModel.value,
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize
  }

  getOtherRecordList(params)
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

<style scoped lang="scss">
.color-1 {
  color: #409eff;
}

.color-3 {
  color: #f56c6c;
}

.color-2 {
  color: #67c23a;
}
</style>
