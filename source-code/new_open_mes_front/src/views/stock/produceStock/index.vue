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
        v-for="(item, index) in btnConditions.slice(2, 4)"
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
      :columns="returnGoodsColumns"
      :tableData="tableDataList"
      :pagination="pagination"
      @pagination-change="handlePagination"
      @selection-change="handleSelectionChange"
    >
      <template #operation="{ scope }">
        <el-button type="primary" @click="handleAffirm(scope.row)">确认同步</el-button>
        <el-button type="success" v-if="scope.row.status == 1" @click="handleOpenEdit(scope.row)"
          >修改</el-button
        >
        <el-button
          type="warning"
          v-if="scope.row.status == 3"
          @click="handleCancelRepacking(scope.row)"
          >取消重包</el-button
        >
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
      <el-divider content-position="left">产品信息</el-divider>
      <el-row>
        <el-col :span="6">
          <el-form-item label="产品名称" :prop="isShowView ? '' : 'itemCode'">
            <el-select
              placeholder="请选择产品分类"
              :disabled="isShowView"
              @change="handleChangeSelect"
              v-model="ruleFormData.itemCode"
            >
              <el-option
                v-for="item in productNameSelect"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="产品编号" :prop="isShowView ? '' : 'itemCode'">
            <el-input v-model="ruleFormData.itemCode" disabled />
          </el-form-item>
        </el-col>
        <!-- <el-col :span="6">
          <el-form-item label="自动生成">
            <el-switch v-model="ruleFormData.createCode" @change="handleCreateCode" />
          </el-form-item>
        </el-col> -->
        <el-col :span="6">
          <el-form-item label="产品单位">
            <el-input v-model="ruleFormData.unitOfMeasure" disabled />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="产品分类">
            <!-- <el-select
              placeholder="请选择产品分类"
              :disabled="isShowView"
              v-model="ruleFormData.itemType"
            >
              <el-option
                v-for="item in []"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select> -->
            <el-input v-model="ruleFormData.itemTypeName" disabled />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="线别">
            <el-input v-model="ruleFormData.lineType" disabled />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="状态">
            <el-select placeholder="请选择状态" disabled v-model="ruleFormData.status">
              <el-option
                v-for="item in STORE_HOUSE_STATUS_INFO"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="是否退货" :prop="isShowView ? '' : 'returnGood'">
            <el-select
              placeholder="请选择是否退货"
              :disabled="isShowView"
              v-model="ruleFormData.returnGood"
            >
              <el-option
                v-for="item in UN_KNOW_ENABLE"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="退货日期" :prop="isShowView ? '' : 'returnDate'">
            <el-date-picker
              placeholder="请选择退货日期"
              type="date"
              :value-format="'YYYY-MM-DD'"
              v-model="ruleFormData.returnDate"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-divider content-position="left">产品明细</el-divider>
      <el-row>
        <el-col :span="6">
          <el-form-item label="型号" :prop="isShowView ? '' : 'model'">
            <el-select placeholder="请选择型号" :disabled="isShowView" v-model="ruleFormData.model">
              <el-option
                v-for="item in modelSelect"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="规格" :prop="isShowView ? '' : 'spec'">
            <el-input v-model="ruleFormData.spec" :disabled="isShowView" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="批号" :prop="isShowView ? '' : 'batchNumber'">
            <el-input v-model="ruleFormData.batchNumber" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="箱号" :prop="isShowView ? '' : 'boxNumber'">
            <el-input v-model="ruleFormData.boxNumber" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="标准" :prop="isShowView ? '' : 'stand'">
            <el-input v-model="ruleFormData.stand" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="日期" :prop="isShowView ? '' : 'date'">
            <el-date-picker
              placeholder="请选择日期"
              type="date"
              :value-format="'YYYY-MM-DD'"
              v-model="ruleFormData.date"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="客户代码" :prop="isShowView ? '' : 'clientCode'">
            <el-input v-model="ruleFormData.clientCode" />
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="盘号" :prop="isShowView ? '' : 'reelNumber'">
            <el-input v-model="ruleFormData.reelNumber" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="颜色" :prop="isShowView ? '' : 'color'">
            <el-select placeholder="请选择颜色" :disabled="isShowView" v-model="ruleFormData.color">
              <el-option
                v-for="item in colorSelect"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="是否重包" :prop="isShowView ? '' : 'hePackage'">
            <el-select
              placeholder="请选择是否重包"
              :disabled="isShowView"
              v-model="ruleFormData.hePackage"
            >
              <el-option
                v-for="item in UN_KNOW_ENABLE"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="公司名称" :prop="isShowView ? '' : 'corporateName'">
            <el-input v-model="ruleFormData.corporateName" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-divider content-position="left">设备明细</el-divider>
      <el-row>
        <el-col :span="6">
          <el-form-item label="设备编号" :prop="isShowView ? '' : 'palletNumber'">
            <el-input v-model="ruleFormData.palletNumber" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="轴数" :prop="isShowView ? '' : 'axlesNum'">
            <el-select
              placeholder="请选择轴数"
              :disabled="isShowView"
              v-model="ruleFormData.axlesNum"
            >
              <el-option
                v-for="item in AXLE_NUM"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="条码" :prop="isShowView ? '' : 'barCode'">
            <el-input v-model="ruleFormData.barCode" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="托盘编号" :prop="isShowView ? '' : 'palletNumber'">
            <el-input v-model="ruleFormData.palletNumber" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="托盘数量" :prop="isShowView ? '' : 'palletQuantity'">
            <el-input v-model="ruleFormData.palletQuantity" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-divider content-position="left">轴数明细</el-divider>
      <el-row>
        <el-col :span="6">
          <el-form-item label="1轴净重(kg)">
            <el-input v-model="ruleFormData.oneAxleHeight" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="2轴净重(kg)">
            <el-input v-model="ruleFormData.twoAxleHeight" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="3轴净重(kg)">
            <el-input v-model="ruleFormData.threeAxleHeight" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="4轴净重(kg)">
            <el-input v-model="ruleFormData.fourAxleHeight" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="总净重(kg)">
            <el-input v-model="ruleFormData.totalHeight" />
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
import download from '@/utils/download'
import { ref, onMounted, reactive } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, returnGoodsColumns } from './data'
import { btnConditions, UN_KNOW_ENABLE, STORE_HOUSE_STATUS_INFO, AXLE_NUM } from '@/utils/const'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessageBox, ElMessage } from 'element-plus'
import { batchSyncLabel } from '@/api/a_public_port'
import {
  downloadListData,
  queryTableList,
  queryFormInfo,
  updateFormInfo,
  deleteFormInfo,
  cancelRepacking
} from '@/api/stock/produceStock'
import { getColorSelectList } from '@/api/masterData/materialManage'
import { queryMDItemList } from '@/api/masterData/materialManage'
import { getModelSelectList } from '@/api/masterData/materialManage'
import { syncLabel } from '@/api/a_public_port'
import { addTestWay } from '@/api/inspectionStandards/inspectionMethod'
const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  itemCode: [{ required: true, message: '产品名称不能为空', trigger: 'blur' }],
  returnGood: [{ required: true, message: '请选择退货', trigger: 'blur' }],
  returnDate: [{ required: true, message: '请选择退货日期', trigger: 'blur' }],
  model: [{ required: true, message: '请选择型号', trigger: 'blur' }],
  spec: [{ required: true, message: '请输入规格', trigger: 'blur' }],
  batchNumber: [{ required: true, message: '请输入批号', trigger: 'blur' }],
  boxNumber: [{ required: true, message: '请输入箱号', trigger: 'blur' }],
  stand: [{ required: true, message: '请输入标准', trigger: 'blur' }],
  clientCode: [{ required: true, message: '请输入客户代码', trigger: 'blur' }],
  reelNumber: [{ required: true, message: '请输入盘号', trigger: 'blur' }],
  color: [{ required: true, message: '请选择颜色', trigger: 'blur' }],
  hePackage: [{ required: true, message: '请选择是否重包', trigger: 'blur' }],
  corporateName: [{ required: true, message: '请输入公司名称', trigger: 'blur' }],
  equNumber: [{ required: true, message: '请输入设备编号', trigger: 'blur' }],
  axlesNum: [{ required: true, message: '请选择轴数', trigger: 'blur' }],
  barCode: [{ required: true, message: '请输入条码', trigger: 'blur' }],
  palletNumber: [{ required: true, message: '请输入托盘编号', trigger: 'blur' }],
  palletQuantity: [{ required: true, message: '请输入托盘数量', trigger: 'blur' }],
  date: [{ required: true, message: '请选择日期', trigger: 'blur' }]
})

// 搜索内容值
const searchModel = ref({
  model: '',
  spec: '',
  status: '',
  createTimes: '',
  barCode: '',
  boxNumber: '',
  reelNumber: '',
  color: '',
  palletNumber: '',
  lineType: ''
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
const ruleFormData = ref<any>({}) // 表单数据
const isShowView = ref(false) // 是否是查看状态
const dialogTitle = ref('')
const productNameSelect = ref<any>([]) // 产品名称下拉列表数据
const modelSelect = ref<any>([]) // 型号下拉列表数据
const colorSelect = ref<any>([]) // 颜色下拉列表数据
const batchSyncState = ref(true)

const handleChangeSelect = (data) => {
  // 在 productNameSelect 中找到符合条件的项
  const selectedProduct = productNameSelect.value.find((item) => item.value === data)
  // 如果找到匹配的项，则将其属性赋值给对应的字段
  if (selectedProduct) {
    const { itemName, unitOfMeasure, itemTypeName, lineType } = selectedProduct
    ruleFormData.value.itemName = itemName
    ruleFormData.value.unitOfMeasure = unitOfMeasure
    ruleFormData.value.itemTypeName = itemTypeName
    ruleFormData.value.lineType = lineType
  }
}

// 取消重包
const handleCancelRepacking = (value) => {
  cancelRepacking([value.id]).then((res) => {
    getTableList()
    ElMessage.success('取消重包成功')
  })
}

// 搜索功能
const handleQueryData = () => {
  getTableList()
}

// 获取详情接口
const getTestWayInfo = async (value) => {
  await queryFormInfo(value).then((res) => {
    ruleFormData.value = res
  })
}

// 按钮集合功能
const handleBtnOperation = (state) => {
  switch (state) {
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
  download.excel(data, '成品入库.xls')
}

// 确认同步
const handleAffirm = (value) => {
  const params = {
    id: value.id
  }
  syncLabel(params).then((res) => {
    ElMessage({ message: res, type: 'success' })
  })
}
// 按钮/表格 - 修改功能
const handleOpenEdit = async (value?) => {
  dialogTitle.value = '修改成品入库'
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
  batchSyncLabel(getId).then((res) => {
    ElMessage({ message: res, type: 'success' })
    getTableList()
  })
}

// 表格详情功能
const handleOpenDetailsData = async (value) => {
  await getTestWayInfo(value.id)
  dialogTitle.value = '盘号详情'
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
  batchSyncState.value = !getSelectionData.value.length
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
const handleCreateCode = (value) => {
  if (value) {
    getTestWayGenCode('QC_METHOD_CODE').then((res) => {
      ruleFormData.value.inspectCode = res
    })
  }
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
      updateFormInfo(ruleFormData.value).then((res) => {
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

const getQueryMDItemList = () => {
  queryMDItemList().then((res) => {
    productNameSelect.value = res.list.map((item) => {
      return {
        itemTypeName: item.itemTypeName,
        lineType: item.lineType,
        unitOfMeasure: item.unitOfMeasure,
        label: item.itemName,
        value: item.itemCode
      }
    })
  })
}

const getQueryModelSelectList = () => {
  getModelSelectList().then((res) => {
    modelSelect.value = res.map((item) => {
      return {
        label: item.name,
        value: item.number
      }
    })
  })
}

const getColorSelectListAPI = () => {
  getColorSelectList().then((res) => {
    colorSelect.value = res.list.map((item) => {
      return {
        label: item.name,
        value: item.name
      }
    })
  })
}

// 初始化数据
const info = () => {
  getTableList()
  getQueryMDItemList()
  getQueryModelSelectList()
  getColorSelectListAPI()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
