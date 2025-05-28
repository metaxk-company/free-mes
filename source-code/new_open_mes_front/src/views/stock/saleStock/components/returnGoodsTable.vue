<template>
  <common-table
    :height="400"
    :pagination="pagination"
    :isShowPagination="false"
    :columns="trendsColumns"
    :tableData="tableReturnList"
    :isEmpty="false"
  >
    <template #saleNumber="{ scope }">
      <el-button type="primary" link @click="handleAddInfo(scope)">{{
        scope.row.saleNumber
      }}</el-button>
    </template>
    <template #tare="{ scope }">
      <el-input-number :min="0" v-model="scope.row.tare" />
    </template>
    <template #totalPrice="{ scope }">
      <!-- {{ scope.row.totalPrice }} -->
      {{ totalPriceValue(scope) }}
    </template>
    <!-- outHighestLimit  不能高于剩余出库数量百分比     outLowestLimit     不能低于剩余出库数量百分比   -->
    <!-- <template #outHighestLimit="{ scope }">
      <el-input-number :min="0" v-model="scope.row.outHighestLimit" />
    </template>
    <template #outLowestLimit="{ scope }">
      <el-input-number :min="0" v-model="scope.row.outLowestLimit" />
    </template> -->
    <template #empty>
      <!-- :disabled="!ruleFormDataProps.saleNumber" -->
      <el-button type="primary" @click="handleAddPurchase">添加</el-button>
    </template>
    <template #quantity="{ scope }">
      <el-input-number
        :min="1"
        :disabled="isShowView"
        v-model="scope.row.remark"
        placeholder="请输入检验描述"
      />
    </template>
    <template #operation="{ scope }">
      <el-button
        v-if="ruleFormDataProps.id"
        type="primary"
        :icon="'Edit'"
        @click="handleAddInfo(scope)"
        link
      />
      <el-button type="success" :icon="'Plus'" @click="handleAddRow" link />
      <el-button type="danger" :icon="'Minus'" @click="handleRemoveRow(scope)" link />
    </template>
  </common-table>
  <!-- 采购数据 -->
  <XModal v-model="dialogVisible" title="销售明细" width="90%" @close="handleClosePurchase">
    <common-search
      style="margin-bottom: 20px"
      :conditions-list="searchPurchaseConditions"
      :search-model="searchModel"
      @query-data="handleQueryData"
    />
    <common-table
      isSelection
      @selection-change="handleSelectionChange"
      :pagination="purchasePagination"
      :columns="tableOrderColumns"
      @pagination-change="handlePurchasePagination"
      :tableData="procurementDataList"
      :loading="purchaseLoading"
    />
    <template #footer>
      <el-button type="primary" @click="handleSubmitPurchase" :disabled="!selectionOrderList.length"
        >提交</el-button
      >
    </template>
  </XModal>
  <!-- 添加明细详情 -->
  <XModal v-model="dialogDetail" title="当前明细" width="90%" height="70%">
    <el-descriptions title="当前明细" :column="4">
      <el-descriptions-item label="客户名称">{{
        detailsFormInfo.customerName
      }}</el-descriptions-item>
      <el-descriptions-item label="销售单号">{{ detailsFormInfo.saleNumber }}</el-descriptions-item>
      <el-descriptions-item label="编号">{{ detailsFormInfo.id }}</el-descriptions-item>
      <el-descriptions-item label="型号">{{ detailsFormInfo.model }}</el-descriptions-item>
      <el-descriptions-item label="单价">{{ detailsFormInfo.price }}</el-descriptions-item>
      <el-descriptions-item label="规格">{{ detailsFormInfo.spec }}</el-descriptions-item>
      <el-descriptions-item label="销售数量">{{ detailsFormInfo.quantity }}</el-descriptions-item>
      <el-descriptions-item label="出库价格">{{ detailsFormInfo.totalPrice }}</el-descriptions-item>
      <el-descriptions-item label="已发">
        {{ getNetWeightData }}
      </el-descriptions-item>
      <!-- <el-descriptions-item label="未发">
        {{ upDataNoSendOutData }}
      </el-descriptions-item> -->
      <!-- <el-descriptions-item label="规格价格">{{ detailsFormInfo.specPrice }}</el-descriptions-item> -->
      <el-descriptions-item label="件数">{{ tableDetailList.length }}</el-descriptions-item>
      <el-descriptions-item label="总净重">{{ getNetWeightData }}</el-descriptions-item>
      <el-descriptions-item label="总皮重">{{ getTotalTareWeight }}</el-descriptions-item>
    </el-descriptions>
    <common-table
      :pagination="detailPagination"
      :columns="detailsTableColumns"
      :tableData="tableDetailList"
      :isEmpty="false"
      @pagination-change="handleDetailsPagination"
    >
      <template #empty>
        <!-- <el-button type="primary" @click="handleAddDetail">添加详情</el-button> -->
      </template>
      <template #operation="{ scope }">
        <el-button type="success" :icon="'Plus'" @click="handleAddDetail" link />
        <el-button type="danger" :icon="'Minus'" @click="handleRemoveDetailRow(scope)" link />
      </template>
    </common-table>

    <template #footer>
      <!-- <el-button type="primary" @click="handleSubmitDetailInfo">提交</el-button> -->
      <el-button type="" @click="dialogDetail = false">关闭</el-button>
    </template>
  </XModal>

  <!-- 选择明细详情数据 -->
  <XModal
    v-model="detailedDetailsDialog"
    title="明细详情数据"
    width="90%"
    @close="handleCloseDialog"
  >
    <common-search
      style="margin-bottom: 20px"
      :conditions-list="searchStock"
      :search-model="searchStockModel"
      @query-data="handleDetailsQueryData"
    />
    <common-table
      isSelection
      @selection-change="handleDetailsSelectionChange"
      :pagination="detailsPagination"
      :columns="detailsTableColumns"
      :tableData="detailsTableDataList"
      :loading="detailsLoading"
      @pagination-change="handleDetailedDetailsPagination"
    />
    <template #footer>
      <el-button
        type="primary"
        @click="handleSubmitDetailedDetails"
        :disabled="!detailsSelectDataInfo.length"
        >提交</el-button
      >
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import {
  returnGoodsColumns,
  searchPurchaseConditions,
  tableOrderColumns,
  searchStock,
  detailsTableColumns
} from '../data'
import {
  getPurchaseOrderDetailInfo,
  saveSingleDetailData,
  deleteRowDelete,
  deleteRowWarehouseDelete,
  addPurchase
} from '@/api/stock/saleStock'
import { queryTableList } from '@/api/stock/produceStock'
import { ElMessage } from 'element-plus'
import { getClientSelect } from '@/api/salesOrder/quotationList'

const props = defineProps({
  isShowView: {
    type: Boolean
  },
  tableReturnList: {
    type: Array as any,
    default: () => []
  },
  ruleFormDataProps: {
    type: Object as any,
    default: () => {}
  },
  isSaveOrEdit: {
    type: Boolean
  }
})
const emit = defineEmits(['upDataPurchaseData', 'upDataReturnTableList'])

const operation = {
  label: '操作',
  slot: 'operation',
  fixed: 'right',
  width: '150'
}
const rowLineData = ref<any>('')
const dialogVisible = ref(false)
const searchModel = ref<any>({
  lineType: '',
  model: '',
  spec: '',
  saleNumber: '',
  customerNumber: ''
})
const selectionOrderList = ref([])
const procurementDataList = ref([]) // 采购单列表数据
const tableDetailList = ref<any>([])
let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
let detailsPagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
let detailPagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
let purchasePagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const dialogDetail = ref<boolean>(false)
const detailedDetailsDialog = ref<boolean>(false)
const searchStockModel = ref({
  lineType: '',
  model: '',
  spec: '',
  customerCode: '',
  panhao: '',
  color: ''
})
const detailsTableDataList = ref([])
// 明细详情选择的数据
const detailsSelectDataInfo = ref<any>([])
const detailsFormInfo = ref<any>({})
const detailsLoading = ref<boolean>(false)
const purchaseLoading = ref(false)

const handleClosePurchase = () => {
  searchModel.value = {}
}

const totalPriceValue = (value) => {
  return value.row?.totalPrice
    ? (Number(value.row.sendOut) * Number(value.row.price)).toFixed(3)
    : 0
}

// 根据修改还是查看来是否显表格头示操作
const trendsColumns = computed(() => {
  return props.isShowView ? returnGoodsColumns : [...returnGoodsColumns, operation]
})

// 获取已选择列表总净重相加
const getNetWeightData = computed(() => {
  return tableDetailList.value.reduce((accumulator, currentValue) => {
    const height = Number(currentValue.totalHeight)
    const sum = accumulator + height
    return Number(sum.toFixed(3)) || 0
  }, 0)
})

// 计算已发的数据：获取已有已发数据 + 列表所有总净重
const upDataSendOutData = computed(() => {
  const height = Number(detailsFormInfo.value.sendOut) || 0
  return height + getNetWeightData.value
})

// 计算未发的数据
// const upDataNoSendOutData = computed(() => {
//   const height = Number(detailsFormInfo.value.noSend) || 0
//   return height - getNetWeightData.value
//   return (detailsFormInfo.value.noSend = height - getNetWeightData.value)
// })

// 计算总皮重数据
const getTotalTareWeight = computed(() => {
  return tableDetailList.value.reduce((accumulator, currentValue) => {
    const height = Number(currentValue.totalTare) || 0
    // return accumulator + height
    return (detailsFormInfo.value.totalTare = accumulator + height)
  }, 0)
})

// 保存单个数据下明细内容
const handleSubmitDetailInfo = () => {
  const params = {
    ...detailsFormInfo.value,
    labelList: tableDetailList.value,
    number: props.ruleFormDataProps.number,
    saleItemNumber: detailsFormInfo.value.saleItemNumber
  }

  detailsFormInfo.value.sendOut = upDataSendOutData.value
  detailsFormInfo.value.piece = tableDetailList.value.length
  detailsFormInfo.value.totalTare = getTotalTareWeight.value
  detailsFormInfo.value.totalWeight = getNetWeightData.value
  saveSingleDetailData(params).then((res) => {
    ElMessage({
      message: '新增成功.',
      type: 'success'
    })
    // props.tableReturnList[detailsFormInfo.value.index].sendOut = detailsFormInfo.value.sendOut

    // props.tableReturnList[detailsFormInfo.value.index].noSend = detailsFormInfo.value.noSend
    // console.log(props.tableReturnList[detailsFormInfo.value.index], '_props.tableReturnList')

    // props.tableReturnList[detailsFormInfo.value.index].weight = detailsFormInfo.value.weight
    // props.tableReturnList[detailsFormInfo.value.index].totalTareWeight =
    //   detailsFormInfo.value.totalTareWeight
    // ---
    // const tableItem = props.tableReturnList[detailsFormInfo.value.index]
    // const { sendOut, noSend, weight, totalTareWeight } = detailsFormInfo.value
    // tableItem.sendOut = sendOut
    // tableItem.noSend = noSend
    // tableItem.weight = weight
    // tableItem.totalTareWeight = totalTareWeight

    // emit('upDataReturnTableList', detailsFormInfo)
    dialogDetail.value = false
  })
}

const handleCloseDialog = () => {
  detailsSelectDataInfo.value = []
  detailedDetailsDialog.value = false
}

const handleSubmitDetailedDetails = () => {
  detailsSelectDataInfo.value.forEach((itemInfo) => {
    const itemCodeExists = tableDetailList.value.some((item) => item.id === itemInfo.id)
    if (!itemCodeExists) {
      tableDetailList.value.push(itemInfo)
      detailedDetailsDialog.value = false
    } else {
      ElMessage({
        message: '当前数据已选择.',
        type: 'warning'
      })
    }
  })
}

// 明细数据详情数据分页功能
const handleDetailedDetailsPagination = (value) => {
  detailsPagination = value
}

// 添加详情数据分页功能
const handleDetailsPagination = (value) => {
  detailPagination = value.value
  getDetailData()
}

// 采购单分页功能
const handlePurchasePagination = (value) => {
  purchasePagination = value.value
  handleAddPurchase()
}

const handleDetailsSelectionChange = (value) => {
  detailsSelectDataInfo.value = value
}

const handleAddDetail = () => {
  getStorageDetailsListAPI()
  detailedDetailsDialog.value = true
}

// 行数据明细查询
const handleAddInfo = (value) => {
  rowLineData.value = value.row
  detailsFormInfo.value = value.row
  detailsFormInfo.value.index = value.$index
  getDetailData()
}

// 获取明细列表参数
const getDetailData = () => {
  const params = {
    number: props.ruleFormDataProps.number,
    saleItemNumber: rowLineData.value?.saleItemNumber,
    pageNo: detailPagination.currentPage,
    pageSize: detailPagination.pageSize
  }

  getPurchaseOrderDetailInfo(params).then((res) => {
    tableDetailList.value = res.list
    detailPagination.total = res.total
    dialogDetail.value = true
  })
}

// 选择采购单当前行的数据
const handleSelectionChange = (value) => {
  selectionOrderList.value = value
}

// 搜索订单数据
const handleQueryData = () => {
  getAddPurchaseAPI()
}

// 明细搜索订单数据
const handleDetailsQueryData = () => {
  getStorageDetailsListAPI()
}

// 添加采购
const handleAddPurchase = async () => {
  await getAddPurchaseAPI()
  dialogVisible.value = true
}

// 获取采购详情列表
const getAddPurchaseAPI = async () => {
  const params = {
    ...searchModel.value,
    pageNo: purchasePagination.currentPage,
    pageSize: purchasePagination.pageSize
  }
  purchaseLoading.value = true
  await addPurchase(params)
    .then((res) => {
      procurementDataList.value = res.list
      purchasePagination.total = res.total
    })
    .finally(() => {
      purchaseLoading.value = false
    })
}

// 提交
const handleSubmitPurchase = () => {
  emit('upDataPurchaseData', selectionOrderList.value)
  dialogVisible.value = false
}

// 表格添加行
const handleAddRow = () => {
  handleAddPurchase()
}

// 表格删除行
const handleRemoveRow = (value) => {
  props.tableReturnList.splice(value.$index, 1)

  deleteRowWarehouseDelete(value.row.saleItemNumber).then((res) => {
    ElMessage({
      message: '删除成功.',
      type: 'success'
    })
  })
}

const handleRemoveDetailRow = (value) => {
  deleteRowDelete(value.row.id, props.ruleFormDataProps.number).then((res) => {
    tableDetailList.value.splice(value.$index, 1)
  })
}

// 拿表单中已有的已发数据 - 当前行删除掉的已发数据 / 拿表单中已有的未发 - 当前行删除掉的未发

// 获取选择的入库详情数据
const getStorageDetailsListAPI = () => {
  detailsLoading.value = true
  const ids = tableDetailList.value.map((item) => item.id)
  const params = {
    ids
  }

  queryTableList(params)
    .then((res) => {
      const { list, total } = res
      detailsTableDataList.value = list
      detailsPagination.total = total
    })
    .finally(() => {
      detailsLoading.value = false
    })
}

const getClientSelectListAPI = () => {
  getClientSelect().then((res) => {
    const clientListData = res.map((item: any) => {
      return {
        value: item.customerNumber,
        label: item.customerName
      }
    })
    searchPurchaseConditions[0].options = clientListData
  })
}

const info = () => {
  getStorageDetailsListAPI()
  getClientSelectListAPI()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
