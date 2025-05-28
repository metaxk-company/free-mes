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
    </div>
    <common-table
      :loading="tableLoading"
      :isSelection="true"
      :columns="tableColumns"
      :tableData="tableDataList"
      :pagination="pagination"
      @pagination-change="handlePagination"
      @selection-change="handlePageSelectionChange"
    >
      <template #operation="{ scope }">
        <!-- <el-button type="warning" :loading="syncBtnLoading" @click="handleAffirm(scope.row)"
          >确认同步</el-button
        > -->
        <el-button type="primary" @click="handleOpenDetailsData(scope.row)">详情</el-button>
        <el-button type="success" @click="handleOpenEdit(scope.row)">修改</el-button>
        <el-button type="danger" @click="handleOpenRemove(scope.row)">删除</el-button>
        <!-- <el-button type="" @click="handlePrinting(scope.row)">打印</el-button> -->
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
          <el-form-item label="仓库" prop="wareHouse">
            <el-select
              placeholder="请选择仓库来源"
              :disabled="isShowView"
              v-model="ruleFormData.wareHouse"
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
          <el-form-item label="交货日期">
            <el-date-picker
              v-model="ruleFormData.deliveryDate"
              type="date"
              :disabled="isShowView"
              placeholder="请选择采购日期"
              value-format="YYYY-MM-DD"
              :disabled-date="disabledDate"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="供应商">
            <el-select
              placeholder="请选择供应商"
              :disabled="isShowView"
              v-model="ruleFormData.vendor"
              filterable
            >
              <el-option
                v-for="item in vendorList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="入库状态">
            <el-select
              placeholder="请选择入库状态"
              :disabled="isShowView"
              v-model="ruleFormData.status"
            >
              <el-option
                v-for="item in STORE_HOUSE_STATE"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row />
      <el-row>
        <el-col :span="6">
          <el-form-item label="备注" :prop="isShowView ? '' : 'remark'">
            <el-input v-model="ruleFormData.remark" :disabled="isShowView" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <returnGoodsTable
      @update-procurement-data="handleUpDateProcurementData"
      :table-return-list="tableReturnList"
      :purchase-order-num="ruleFormData.receiptNumber"
      :is-show-view="isShowView"
    />
    <template #footer>
      <el-button type="primary" @click="handleDialogSubmit" v-if="!isShowView">提交</el-button>
      <el-button @click="handleCloseDialogVisible">取消</el-button>
    </template>
  </XModal>

  <!-- 采购单号弹框内容 -->
  <XModal v-model="dialogPurchaseVisible" title="选购采购单" width="100%">
    <common-search
      style="margin-bottom: 20px"
      :conditions-list="searchPurchase"
      :search-model="searchPurchaseModel"
      @query-data="handleChooseQueryData"
    />
    <common-table
      isSelection
      @selection-change="handleSelectionChange"
      @pagination-change="handleChoosePagination"
      :pagination="purchaseOrderPagination"
      :columns="tablePurchaseListColumns"
      :tableData="purchaseOrderDataList"
      :loading="chooseLoading"
    />
    <template #footer>
      <el-button
        type="primary"
        @click="handleAddPurchase"
        :disabled="purchaseOrderSelectionData.length != 1"
        >提交</el-button
      >
    </template>
  </XModal>
</template>

<script setup lang="ts">
import download from '@/utils/download'
import { ref, onMounted, reactive } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, tableColumns, searchPurchase } from './data'
import { btnConditions, STORE_HOUSE_STATE } from '@/utils/const'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessageBox, ElMessage } from 'element-plus'
import { queryTableList as queryPurchaseOrderTable } from '@/api/purchaseOrder'
import returnGoodsTable from './components/returnGoodsTable.vue'
import { tablePurchaseListColumns } from '@/views/purchaseOrder/data'
import { warehouseList } from '@/api/storeHouse/warehouseModel'
import { queryTableList as wareHousePlaceQueryTableList } from '@/api/storeHouse/wareHousePlace'
import {
  queryTableList,
  addFormData,
  updateFormInfo,
  queryFormInfo,
  downloadListData,
  deleteFormInfo,
  getPrintingData,
  confirmSync,
  getVendorList
} from '@/api/stock/purchaseStock'
import dayjs from 'dayjs'

const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  receiptNumber: [{ required: true, message: '请输入采购单号', trigger: 'blur' }],
  wareHouse: [{ required: true, message: '请选择仓库', trigger: 'blur' }],
  location: [{ required: true, message: '请选择仓储位置', trigger: 'blur' }],
  receiptDate: [{ required: true, message: '请选择采购日期', trigger: 'blur' }],
  source: [{ required: true, message: '请选择采购来源', trigger: 'blur' }],
  deliveryDate: [{ required: true, message: '请选择交货日期', trigger: 'blur' }],
  status: [{ required: true, message: '请选择入库状态', trigger: 'blur' }]
})
const syncBtnLoading = ref(false)

// 时间限制
const disabledDate = (time: Date) => {
  const today = new Date()
  const yesterday = new Date(today)
  yesterday.setDate(today.getDate() - 1)
  const timestamp = yesterday.getTime() // 获取前一天的时间戳

  return time.getTime() < timestamp
}
// 所属仓库下拉列表
const wareHousesSelectList = ref<any>([])
// 仓库位置下拉列表
const wareHousePlaceSelectList = ref<any>([])
const searchPurchaseModel = ref({
  number: '',
  vendorName: '',
  deliveryDate: ''
})
// 搜索内容值
const searchModel = ref({
  inNumber: '',
  receiptNumber: '',
  wareHouse: '',
  source: '',
  deliveryDate: ''
})
const tableLoading = ref(false) // 表格加载
const tableDataList = ref([]) // 表格数据
const purchaseOrderSelectionData = ref<any>([])
let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const chooseLoading = ref(false)
let purchaseOrderPagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const dialogVisible = ref(false) // 弹框显示
const ruleFormData = ref<any>({
  createCode: false,
  status: '待入库',
  deliveryDate: ref(dayjs().format().split('T')[0]),
  source: '采购'
}) // 表单数据
const isShowView = ref(false) // 是否是查看状态
const dialogTitle = ref('')
const tableReturnList = ref<any>([])
const dialogPurchaseVisible = ref(false)
const purchaseOrderDataList = ref<any>([])
const getSelectionData = ref<any>([])
const vendorList = ref<any>()
// 获取供应商列表
const getVendor = () => {
  getVendorList().then((res) => {
    vendorList.value = res.map((item) => {
      return {
        value: item.vendorName,
        label: item.vendorName
      }
    })
  })
}

const handlePageSelectionChange = (value) => {
  getSelectionData.value = value
  btnConditions[1].disabled = !getSelectionData.value.length || getSelectionData.value.length >= 2
  btnConditions[2].disabled = !getSelectionData.value.length
}

const handleChoosePagination = (value) => {
  purchaseOrderPagination = value.value
  getQueryPurchaseOrderTable()
}

const handleChooseQueryData = () => {
  getQueryPurchaseOrderTable()
}

const handleUpDateProcurementData = (value) => {
  tableReturnList.value.push(...value)
}

const handleAddPurchase = () => {
  ruleFormData.value.receiptNumber = purchaseOrderSelectionData.value[0]?.number
  dialogPurchaseVisible.value = false
}

// 搜索功能
const handleQueryData = () => {
  getTableList()
}

// 获取详情接口
const getTestWayInfo = async (value) => {
  await queryFormInfo(value).then((res) => {
    ruleFormData.value = res
    tableReturnList.value = res.itemList
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
  download.excel(data, '采购入库.xls')
}

// 按钮新增功能
const handleOpenSave = () => {
  dialogTitle.value = '添加采购入库'
  dialogVisible.value = true
}

// 按钮/表格 - 修改功能
const handleOpenEdit = async (value?) => {
  dialogTitle.value = '修改采购入库'
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
// 表格详情功能
const handleOpenDetailsData = async (value) => {
  await getTestWayInfo(value.id)
  dialogTitle.value = '采购入库明细'
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
  purchaseOrderSelectionData.value = value
}

// 表格弹框关闭
const handleCloseDialogVisible = () => {
  tableReturnList.value = []
  isShowView.value = false
  ruleFormData.value = {
    deliveryDate: ref(dayjs().format().split('T')[0]),
    status: '待入库',
    source: '采购'
  }
  dialogVisible.value = false
}

// 表格弹框提交
const handleDialogSubmit = async () => {
  const params = {
    ...ruleFormData.value,
    itemList: tableReturnList.value
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

const getQueryPurchaseOrderTable = () => {
  const params = {
    ...searchPurchaseModel.value,
    pageNo: purchaseOrderPagination.currentPage,
    pageSize: purchaseOrderPagination.pageSize
  }
  chooseLoading.value = true
  queryPurchaseOrderTable(params)
    .then((res) => {
      const { list, total } = res || {}
      purchaseOrderPagination.total = total
      purchaseOrderDataList.value = list
    })
    .finally(() => {
      chooseLoading.value = false
    })
}

// 获取仓库列表下拉
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

// 获取仓库列表下拉接口
const getWareHousePlaceQueryTableListAPI = () => {
  wareHousePlaceQueryTableList().then((res) => {
    wareHousePlaceSelectList.value = res.list.map((item) => {
      return {
        value: item.locationName,
        label: item.locationName
      }
    })
  })
}

// 初始化数据
const info = () => {
  getTableList()
  getQueryPurchaseOrderTable()
  getWareHousesQueryTableListAPI()
  getWareHousePlaceQueryTableListAPI()
  getVendor()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
