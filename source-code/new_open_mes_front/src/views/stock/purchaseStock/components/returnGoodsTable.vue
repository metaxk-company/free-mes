<template>
  <common-table
    :isShowPagination="false"
    :columns="trendsTableColumns"
    :tableData="tableReturnList"
    :isEmpty="false"
    :height="400"
  >
    <template #empty>
      <el-button type="primary" :disabled="isShowView" @click="handleAddPurchase">添加</el-button>
    </template>
    <template #batchNumber="{ scope }">
      <el-input :disabled="isShowView" v-model="scope.row.batchNumber" placeholder="请输入批次号" />
    </template>
    <template #boxNumber="{ scope }">
      <el-input :disabled="isShowView" v-model="scope.row.boxNumber" placeholder="请输入箱号" />
    </template>
    <template #barcode="{ scope }">
      <el-input :disabled="isShowView" v-model="scope.row.barcode" placeholder="请输入条码" />
    </template>
    <template #productionDate="{ scope }">
      <el-date-picker
        clearable
        style="width: 80%"
        v-model="scope.row.productionDate"
        type="date"
        value-format="YYYY-MM-DD"
        placeholder="请选择生产日期"
        :disabled="isShowView"
      />
    </template>
    <template #effectiveDate="{ scope }">
      <el-date-picker
        style="width: 80%"
        clearable
        v-model="scope.row.effectiveDate"
        type="date"
        value-format="YYYY-MM-DD"
        placeholder="请输入有效日期"
        :disabled="isShowView"
      />
    </template>
    <template #remark="{ scope }">
      <el-input :disabled="isShowView" v-model="scope.row.remark" placeholder="请输入备注" />
    </template>
    <template #operation="{ scope }">
      <el-button type="success" :icon="'Plus'" @click="handleAddRow()" link />
      <el-button type="danger" :icon="'Minus'" @click="handleRemoveRow(scope)" link />
    </template>
  </common-table>
  <!-- 采购数据 -->
  <XModal v-model="dialogVisible" title="采购单" width="90%">
    <common-search
      style="margin-bottom: 20px"
      :conditions-list="searchPurchaseConditions"
      :search-model="searchModel"
      @query-data="handleQueryData"
    />
    <common-table
      isSelection
      @pagination-change="handlePagination"
      @selection-change="handleSelectionChange"
      :pagination="pagination"
      :columns="tableOrderColumns"
      :tableData="procurementDataList"
      :loading="tableLoading"
    />
    <template #footer>
      <el-button type="primary" @click="handleSubmitPurchase" :disabled="!selectionOrderList.length"
        >提交</el-button
      >
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { ref, reactive, computed } from 'vue'
import { returnGoodsColumns, searchPurchaseConditions, tableOrderColumns } from '../data'
import { getPurchaseOrderList } from '@/api/stock/purchaseStock'
import { getSupplierSelectList } from '@/api/masterData/materialManage'

const operationTable = {
  label: '操作',
  slot: 'operation',
  fixed: 'right',
  width: '90'
}
const trendsTableColumns = computed(() => {
  return props.isShowView ? returnGoodsColumns : [...returnGoodsColumns, operationTable]
})

const emit = defineEmits(['updateProcurementData'])

const dialogVisible = ref(false)
const searchModel = ref({
  number: '',
  name: '',
  model: '',
  spec: '',
  vendor: '',
  receiptNumber: ''
})

const props = defineProps({
  isShowView: {
    type: Boolean
  },
  tableReturnList: {
    type: Array as any,
    default: () => []
  },
  purchaseOrderNum: {
    type: String,
    default: ''
  }
})

const selectionOrderList = ref([])
const procurementDataList = ref([]) // 采购单列表数据
let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const tableLoading = ref(false)

const handlePagination = (value) => {
  pagination = value.value
  getPurchaseOrderListAPI()
}
// 选择采购单当前行的数据
const handleSelectionChange = (value) => {
  selectionOrderList.value = value
}

// 搜索订单数据
const handleQueryData = () => {
  getPurchaseOrderListAPI()
}

// 添加采购
const handleAddPurchase = async () => {
  await getPurchaseOrderListAPI()
  dialogVisible.value = true
  getPurchaseOrderListAPI
}

// 提交采购
const handleSubmitPurchase = () => {
  emit('updateProcurementData', selectionOrderList.value)
  dialogVisible.value = false
}

// 表格添加行
const handleAddRow = () => {
  handleAddPurchase()
}

// 表格删除行
const handleRemoveRow = (value) => {
  props.tableReturnList.splice(value.$index, 1)
}

// 获取采购订单列表数据
const getPurchaseOrderListAPI = async () => {
  tableLoading.value = true
  const params = {
    // receiptNumber: props.purchaseOrderNum,
    ...searchModel.value,
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize
  }
  await getPurchaseOrderList(params)
    .then((res) => {
      const { list, total } = res
      procurementDataList.value = list
      pagination.total = total
    })
    .finally(() => {
      tableLoading.value = false
    })
}

const getSupplierSelectListAPI = () => {
  getSupplierSelectList().then((res) => {
    searchPurchaseConditions[4].options = res.list.map((item) => {
      return {
        value: item.vendorName,
        label: item.vendorName
      }
    })
  })
}

getSupplierSelectListAPI()
</script>

<style scoped lang="scss"></style>
