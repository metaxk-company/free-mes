<template>
  <common-table
    :isShowPagination="false"
    :columns="returnGoodsColumnsList"
    :tableData="tableReturnList"
    :height="400"
    :isEmpty="false"
  >
    <template #empty v-if="!isShowView">
      <el-button type="primary" @click="handleAddPurchase">添加</el-button>
    </template>
    <template #quantity="{ scope }">
      <el-input-number
        :min="0"
        :disabled="isShowView"
        v-model="scope.row.quantity"
        placeholder="请输入数量"
      />
    </template>
    <template #includTax="{ scope }">
      {{ scope.row.includTax }}
      {{ includTaxTotal(scope) }}
    </template>
    <template #purchasePrice="{ scope }">
      <el-input-number :min="0" :disabled="isShowView" v-model="scope.row.purchasePrice" />
    </template>
    <template #noIncludTax="{ scope }">
      {{ scope.row.noIncludTax }}
      {{ excludingTaxTotal(scope) }}
    </template>
    <template #operation="{ scope }">
      <el-button type="success" :icon="'Plus'" @click="handleAddRow" link />
      <el-button type="danger" :icon="'Minus'" @click="handleRemoveRow(scope)" link />
    </template>
  </common-table>

  <!-- 采购数据 -->
  <XModal v-model="dialogVisible" title="采购单" width="90%" @close="handleCloseData">
    <common-search
      style="margin-bottom: 20px"
      :conditions-list="searchPurchaseConditions"
      :search-model="searchModel"
      @query-data="handleQueryData"
    />
    <common-table
      @pagination-change="handlePaginationPurchase"
      isSelection
      @selection-change="handleSelectionChange"
      :pagination="paginationPurchase"
      :columns="tableOrderColumns"
      :tableData="procurementDataList"
      :loading="purchaseLoading"
    />
    <template #footer>
      <el-button
        type="primary"
        @click="handleSubmitSelectData()"
        :disabled="!selectionOrderList.length"
        >提交</el-button
      >
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { returnGoodsColumns, tableOrderColumns, searchPurchaseConditions } from '../data'
import { purchaseOrderList } from '@/api/purchaseOrder'

const props = defineProps({
  isShowView: {
    type: Boolean
  },
  tableReturnList: {
    type: Array as any,
    default: () => []
  },
  ruleFormData: {
    type: Object as any,
    default: () => {}
  }
})

const emit = defineEmits(['getSelectData'])

const isAddOperation = {
  label: '操作',
  slot: 'operation',
  fixed: 'right',
  width: '100'
}
console.log(props.isShowView, 'props.isShowView')

const returnGoodsColumnsList = computed(() => {
  return props.isShowView ? returnGoodsColumns : [...returnGoodsColumns, isAddOperation]
})

const dialogVisible = ref(false)
const searchModel = ref({
  itemName: '',
  specification: ''
})
const purchaseLoading = ref(false)
const selectionOrderList = ref([])
const procurementDataList = ref([]) // 采购单列表数据

let paginationPurchase = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})

// 含税总价
const includTaxTotal = (value) => {
  if (value.$index === -1) return
  const row = value.row
  const purchasePrice = row?.purchasePrice || 0
  const quantity = row?.quantity || 0
  const taxRate = props.ruleFormData?.taxRate || 0
  const totalPrice = purchasePrice * quantity
  const tax = (totalPrice * taxRate) / 100
  const includTax = isNaN(totalPrice + tax) ? 0 : totalPrice + tax
  props.tableReturnList[value.$index].includTax = includTax
  props.ruleFormData.taxPrice = props.tableReturnList
    .reduce((accumulator, currentValue) => {
      return accumulator + currentValue.includTax
    }, 0)
    .toFixed(1)
}

// 不含税总价
const excludingTaxTotal = (value) => {
  if (value.$index === -1) return
  const row = value.row
  const purchasePrice = row?.purchasePrice || 0
  const quantity = row?.quantity || 0
  const includTax = isNaN(purchasePrice * quantity) ? 0 : purchasePrice * quantity
  props.tableReturnList[value.$index].noIncludTax = includTax
  props.ruleFormData.noTaxPrice = props.tableReturnList.reduce((accumulator, currentValue) => {
    return accumulator + currentValue.noIncludTax
  }, 0)
}

// 选择采购单当前行的数据
const handleSelectionChange = (value) => {
  selectionOrderList.value = value
}

// 搜索订单数据
const handleQueryData = () => {
  getPurchaseOrderList()
}

// 添加采购
const handleAddPurchase = () => {
  dialogVisible.value = true
  getPurchaseOrderList()
}

const handleSubmitSelectData = () => {
  emit('getSelectData', selectionOrderList.value)
  handleCloseData()
}

const handleCloseData = () => {
  selectionOrderList.value = []
  dialogVisible.value = false
}

// 表格添加行
const handleAddRow = () => {
  dialogVisible.value = true
}

// 表格删除行
const handleRemoveRow = (value) => {
  props.tableReturnList.splice(value.$index, 1)
}

const handlePaginationPurchase = (value) => {
  paginationPurchase = value.value
  getPurchaseOrderList()
}

const getPurchaseOrderList = () => {
  purchaseLoading.value = true
  const params = {
    ...searchModel.value,
    vendor: props.ruleFormData.vendorName, //供应商
    pageNo: paginationPurchase.currentPage,
    pageSize: paginationPurchase.pageSize
  }
  purchaseOrderList(params)
    .then((res) => {
      const { list, total } = res
      paginationPurchase.total = total
      procurementDataList.value = list
    })
    .finally(() => {
      purchaseLoading.value = false
    })
}

onMounted(() => {
  getPurchaseOrderList()
})
</script>

<style scoped lang="scss"></style>
