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
        v-for="(item, index) in [btnConditions[0], btnConditions[2], btnConditions[3]]"
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
        <el-button type="" @click="handlePrinting(scope.row)">打印</el-button>
        <el-button type="primary" @click="handleInspect(scope.row)">查看</el-button>
        <el-button type="success" @click="handleOpenEdit(scope.row)">修改</el-button>
        <el-button type="danger" @click="handleOpenRemove(scope.row)">删除</el-button>
      </template>
    </common-table>
  </ContentWrap>

  <XModal
    v-model="dialogVisible"
    :title="dialogTitle"
    @close="handleCloseDialogVisible"
    width="80%"
  >
    <el-form ref="ruleFormRef" :model="ruleFormData" :rules="rules" label-width="100px" status-icon>
      <el-row>
        <el-col :span="8">
          <el-form-item label="退货编号">
            {{ ruleFormData.number }}
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="采购编号">
            <el-input
              v-if="!isShowView"
              v-model="ruleFormData.poNumber"
              placeholder="请选择采购编号"
            >
              <template #append>
                <el-button icon="DocumentAdd" style="display: flex" @click="handlePurchaseQuery" />
              </template>
            </el-input>
            <div v-else>{{ ruleFormData.poNumber }}</div>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="供应商名称">
            {{ ruleFormData.vendorName }}
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="仓库名称">
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
        <el-col :span="8">
          <el-form-item label="退货日期">
            <el-date-picker
              clearable
              v-model="ruleFormData.returnDate"
              type="date"
              :disabled="isShowView"
              value-format="YYYY-MM-DD"
              placeholder="请选择退货日期"
              :disabled-date="disableTime"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="备注">
            <el-input v-model="ruleFormData.remark" :disabled="isShowView" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="总数">
            {{ quantityReduce }}
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="总价">
            {{ totalPriceReduce }}
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <common-table
      :loading="tableLoading"
      :columns="isShowOperationColumns"
      :tableData="purchaseReturnSelectList"
      :isShowPagination="false"
      :isEmpty="false"
      :height="400"
    >
      <template #empty>
        <!-- :disabled="!ruleFormData.poNumber" -->
        <el-button type="primary" @click="handleAddPurchase">添加</el-button>
      </template>

      <!-- 数量 -->
      <template #amount="{ scope }">
        <!-- {{ scope.row }} -->
        <el-input-number
          v-model="scope.row.amount"
          :min="0"
          :max="scope.row.quantity"
          v-if="!isShowView"
        />
        <div v-else>
          {{ scope.row.amount }}
        </div>
      </template>

      <!-- 总价 -->
      <template #totalPrice="{ scope }">
        {{ totalAmountReduce(scope.row) }}
        <!-- <el-input-number v-model="scope.row.quantity" :min="0" /> -->
      </template>
      <template #operation="{ scope }">
        <el-button type="success" :icon="'Plus'" @click="handleAddPurchase" link />
        <el-button type="danger" :icon="'Minus'" @click="handleRemoveRow(scope)" link />
      </template>
    </common-table>
    <template #footer>
      <el-button type="primary" @click="handleDialogSubmit" v-if="!isShowView">提交</el-button>
      <el-button @click="handleCloseDialogVisible">取消</el-button>
    </template>
  </XModal>

  <!-- 选择采购订单数据 -->
  <XModal v-model="purchaseVisible" title="订单列表" width="80%" @close="handleCloseOrderDialog">
    <common-search
      :conditions-list="searchOrderConditions"
      :search-model="searchOrderModel"
      @query-data="handleQueryOrderData"
    />
    <common-table
      style="margin-top: 20px"
      :loading="tableOrderLoading"
      :isSelection="true"
      :columns="purchaseOrderColumns"
      :tableData="tableOrderDataList"
      :pagination="paginationOrder"
      @pagination-change="handleOrderPagination"
      @selection-change="handleOrderSelectionChange"
    />
    <template #footer>
      <el-button
        type="primary"
        :disabled="!selectionOrderList.length || selectionOrderList.length >= 2"
        @click="handleOrderSelectSubmit"
        >提交</el-button
      >
      <el-button type="" @click="handleCloseOrderDialog">取消</el-button>
    </template>
  </XModal>

  <!-- 选择采购详情下的明细数据 -->
  <XModal v-model="matterVisible" title="物料明细" width="80%" @close="handleCloseMatterDialog">
    <common-search
      :conditions-list="searchMatterConditions"
      :search-model="searchMatterModel"
      @query-data="handleQueryMatterData"
    />
    <common-table
      style="margin-top: 20px"
      :loading="tableMatterLoading"
      :isSelection="true"
      :columns="purchaseReturnColumnsInfo"
      :tableData="tableMatterDataList"
      :pagination="paginationMatter"
      @pagination-change="handleMatterPagination"
      @selection-change="handleMatterSelectionChange"
    />
    <template #footer>
      <el-button type="primary" :disabled="!detailSelectInfo.length" @click="handleSelectSubmit"
        >提交</el-button
      >
      <el-button type="" @click="handleCloseMatterDialog">取消</el-button>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import download from '@/utils/download'
import { ref, onMounted, reactive } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import {
  searchConditions,
  tableColumns,
  purchaseReturnColumns,
  searchOrderConditions,
  searchMatterConditions,
  purchaseOrderColumns,
  purchaseReturnColumnsInfo
} from './data'
import { btnConditions } from '@/utils/const'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessageBox, ElMessage } from 'element-plus'
import {
  queryTableList,
  addFormData,
  updateFormInfo,
  downloadListData,
  deleteFormInfo,
  queryPurchaseTableList,
  queryDetailTableList,
  queryFormInfo,
  getPrintingData
} from '@/api/purchaseReturn'
import { warehouseList } from '@/api/storeHouse/warehouseModel'
import { getCreateCode } from '@/api/a_public_port'

const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  poNumber: [{ required: true, message: '请输入采购编号', trigger: 'blur' }]
})
const disableTime = ref()
//时间限制
const disabledDate = (time?: Date | any) => {
  const today = new Date()
  const yesterday = new Date(today)
  yesterday.setDate(today.getDate() - 1)
  const timestamp = yesterday.getTime() // 获取前一天的时间戳

  return time.getTime() < timestamp
}
// 所属仓库下拉列表
const wareHousesSelectList = ref<any>([])
// 搜索内容值
const searchModel = ref({
  number: '',
  poNumber: '',
  vendorName: '',
  returnDate: ''
})
const searchMatterModel = ref({
  name: '',
  number: ''
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
let paginationMatter = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
let paginationOrder = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const dialogVisible = ref(false) // 弹框显示
const ruleFormData = ref<any>({}) // 表单数据
const isShowView = ref(false) // 是否是查看状态
const dialogTitle = ref('')
const purchaseReturnSelectList = ref<any>([])
const purchaseVisible = ref(false)
const searchOrderModel = ref({
  number: '',
  vendorName: '',
  deliveryDate: ''
})
const tableOrderLoading = ref(false)
const selectionOrderList = ref<any>([])
const matterVisible = ref(false)
const tableMatterLoading = ref(false)
const tableMatterDataList = ref([])
const tableOrderDataList = ref([])
const detailSelectInfo = ref<any>([])

const operation = {
  label: '操作',
  slot: 'operation',
  fixed: 'right',
  width: '110'
}

const isShowOperationColumns = computed(() => {
  return isShowView.value ? purchaseReturnColumns : [...purchaseReturnColumns, operation]
})

// 计算总数量
const quantityReduce = computed(() => {
  return (ruleFormData.value.quantity = purchaseReturnSelectList.value.reduce(
    (total, item) => total + item.amount,
    0
  ))
})

// 计算总金额
const totalAmountReduce = (value) => {
  return (value.totalPrice = value.purchasePrice * value.amount)
}

// 计算总价格
const totalPriceReduce = computed(() => {
  return (ruleFormData.value.totalPrice = purchaseReturnSelectList.value.reduce(
    (total, item) => total + item.totalPrice,
    0
  ))
})

const handleSelectSubmit = () => {
  detailSelectInfo.value.forEach((item2) => {
    item2.amount = 0
    const idExists = purchaseReturnSelectList.value.some(
      (item1) => item1.itemCode === item2.itemCode
    )
    // 如果 ID 不存在于 array1 中，则添加到 array1 数组中
    if (!idExists) {
      purchaseReturnSelectList.value.push(item2)
      handleCloseMatterDialog()
    } else {
      ElMessage({ message: '不能添加重复数据！', type: 'warning' })
    }
  })
}

const handlePrinting = async (value) => {
  let data
  let params = {
    number: value.number,
    type: 'pdf'
  }
  data = await getPrintingData(params).then((res) => res)
  let pnf = new Blob([data], { type: 'application/pdf;charset=utf-8' })

  window.open(window.URL.createObjectURL(pnf))
}

const handleOrderPagination = (value) => {
  paginationOrder = value.value
  getPurchaseQueryAPI()
}

const handleMatterSelectionChange = (value) => {
  detailSelectInfo.value = value
}

const handleMatterPagination = (value) => {
  paginationMatter = value.value
  getQueryDetailTableListAPI()
}

const handleQueryMatterData = () => {
  getQueryDetailTableListAPI()
}

const handleCloseMatterDialog = () => {
  matterVisible.value = false
}

const handleOrderSelectSubmit = () => {
  ruleFormData.value.poNumber = selectionOrderList.value[0].number
  ruleFormData.value.vendorName = selectionOrderList.value[0].vendorName
  handleCloseOrderDialog()
}

const handleCloseOrderDialog = () => {
  selectionOrderList.value = []
  purchaseVisible.value = false
}

const handleOrderSelectionChange = (value) => {
  selectionOrderList.value = value
}

// 表格删除行
const handleRemoveRow = (value) => {
  purchaseReturnSelectList.value.splice(value.$index, 1)
}

const handleQueryOrderData = () => {
  getPurchaseQueryAPI()
}

// 查询采购订单数据
const handlePurchaseQuery = async () => {
  console.log(1)

  await getPurchaseQueryAPI()
  purchaseVisible.value = true
}

// 获取采购订单数据
const getPurchaseQueryAPI = async () => {
  const params = {
    ...searchOrderModel.value,
    pageNo: paginationOrder.currentPage,
    pageSize: paginationOrder.pageSize
  }
  tableOrderLoading.value = true
  await queryPurchaseTableList(params)
    .then((res) => {
      const { total, list } = res || {}
      paginationOrder.total = total
      tableOrderDataList.value = list
    })
    .finally(() => {
      tableOrderLoading.value = false
    })
}

// 新增采购退货
const handleAddPurchase = async () => {
  await getQueryDetailTableListAPI()
  matterVisible.value = true
}

const getQueryDetailTableListAPI = async () => {
  const params = {
    ...searchMatterModel.value,
    receiptNumber: ruleFormData.value.poNumber,
    pageNo: paginationMatter.currentPage,
    pageSize: paginationMatter.pageSize
  }
  tableMatterLoading.value = true
  await queryDetailTableList(params)
    .then((res) => {
      const { list, total } = res || {}
      tableMatterDataList.value = list
      paginationMatter.total = total
    })
    .finally(() => {
      tableMatterLoading.value = false
    })
}

// 查看
const handleInspect = async (value) => {
  dialogTitle.value = '查看采购退货'
  await getDetailsInfoAPI(value)
  isShowView.value = true
  dialogVisible.value = true
}
// 按钮/表格 - 修改功能
const handleOpenEdit = async (value?) => {
  dialogTitle.value = '修改采购退货'
  ruleFormData.value = value
  await getDetailsInfoAPI(value)
  disableTime.value = null
  dialogVisible.value = true
}

// 查看详情接口
const getDetailsInfoAPI = async (value) => {
  await queryFormInfo(value.id).then((res) => {
    ruleFormData.value = res
    purchaseReturnSelectList.value = res.purchaseOrderReturnItemList
  })
}

const handleCreateCode = () => {
  getCreateCode('PO_RERUEN_CODE').then((res) => {
    ruleFormData.value.number = res
  })
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
    case 'download':
      handleDownload()
      break
  }
}

const handleDownload = async () => {
  const data = await downloadListData()
  download.excel(data, '采购退货.xls')
}

// 按钮新增功能
const handleOpenSave = () => {
  dialogTitle.value = '新增采购退货'
  handleCreateCode()
  disableTime.value = disabledDate
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
  purchaseReturnSelectList.value = []
  dialogVisible.value = false
  isShowView.value = false
  ruleFormData.value = {}
}

// 表格弹框提交
const handleDialogSubmit = async () => {
  const params = {
    ...ruleFormData.value,
    purchaseOrderReturnItemList: purchaseReturnSelectList.value.map((item) => {
      return {
        ...item,
        id: ''
      }
    })
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
        value: item.warehouseNumber,
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
