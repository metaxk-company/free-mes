<template>
  <common-table
    :isShowPagination="false"
    :columns="isShowReturnGoodsColumns"
    :tableData="tableReturnList"
    :isEmpty="false"
    :height="300"
    class="destination-table"
  >
    <template #empty>
      <el-button type="primary" @click="handleAddPurchase" :disabled="!isCustomerName"
        >添加</el-button
      >
    </template>
    <!-- 数量 -->
    <template #quantity="{ scope }">
      <el-input-number
        :min="0"
        :max="999999"
        :disabled="isShowView"
        v-model="scope.row.quantity"
        :precision="4"
      />
    </template>
    <!-- 原材料价格 -->
    <template #rawPrice="{ scope }">
      <el-input-number
        :min="0"
        :disabled="isShowView"
        v-model="scope.row.rawPrice"
        v-if="formDataProps.priceMode == 'Product' || formDataProps.priceModel == 'pieces'"
      />
      <div v-else>{{ scope.row.rawPrice }}</div>
    </template>
    <!-- 件数 -->
    <template #pieces="{ scope }">
      <el-input-number :min="0" :disabled="isShowView" v-model="scope.row.pieces" />
    </template>
    <!-- 销售价 -->
    <template #price="{ scope }">
      <!-- {{ scope.row.price }} -->
      {{ Number(scope.row.processingFee) + Number(scope.row.rawPrice) }}
      <!-- {{ salePriceTotal(scope.row) }} -->
    </template>
    <!-- 加工费 -->
    <template #processingFee="{ scope }">
      {{ scope.row.processingFee }}
      <!-- <el-input-number
        v-if="formDataProps.priceMode != 'Product'"
        :min="0"
        :disabled="isShowView"
        v-model="scope.row.processingFee"
      />
      <div v-else>
        {{ scope.row.processingFee }}
      </div> -->
    </template>
    <!-- 总价 -->
    <template #totalPrice="{ scope }">
      {{ calculateTotalPrice(scope.row) }}
      <!-- {{ scope.row.totalPrice }} -->
    </template>
    <template #color="{ scope }">
      <el-select
        placeholder="请选择颜色"
        clearable
        :disabled="isShowView"
        v-model="scope.row.color"
      >
        <el-option
          v-for="item in colorSelectDataList"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
    </template>
    <template #panhao="{ scope }">
      <el-select
        placeholder="请选择盘号"
        clearable
        :disabled="isShowView"
        v-model="scope.row.panhao"
      >
        <el-option
          v-for="item in diskSelectDataList"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
    </template>
    <template #customerCode="{ scope }">
      <el-input :disabled="isShowView" v-model="scope.row.customerCode" />
    </template>
    <template #inventoryNumber="{ scope }">
      <el-input :disabled="isShowView" v-model="scope.row.inventoryNumber" />
    </template>
    <template #warrantNumber="{ scope }">
      <el-input :disabled="isShowView" v-model="scope.row.warrantNumber" />
    </template>
    <template #remark="{ scope }">
      <el-input :disabled="isShowView" v-model="scope.row.remark" />
    </template>

    <template #operation="{ scope }">
      <el-button
        type="primary"
        :icon="'DocumentCopy'"
        @click="handleDocumentCopyRow(scope.row)"
        link
      />
      <el-button type="success" :icon="'Plus'" @click="handleAddRow" link />
      <el-button type="danger" :icon="'Minus'" @click="handleRemoveRow(scope)" link />
    </template>
  </common-table>
  <!-- 采购数据 -->
  <XModal
    v-model="dialogVisible"
    title="产品信息"
    width="90%"
    @beforeClose="handleCloseProcurement"
  >
    <common-search
      style="margin-bottom: 20px"
      :conditions-list="searchPurchaseConditions"
      :search-model="searchModel"
      @query-data="handleQueryData"
    />
    <common-table
      :height="400"
      isSelection
      @selection-change="handleSelectionChange"
      @pagination-change="handleProgressPagination"
      :pagination="pagination"
      :columns="tableOrderColumns"
      :tableData="procurementDataList"
      :loading="tableLoading"
    >
      <template #stocks="{ scope }">
        <el-button link type="primary" @click="handleOpenVisible(scope.row)">{{
          scope.row.stocks
        }}</el-button>
      </template>
    </common-table>

    <template #footer>
      <el-button type="primary" @click="handleAddSubmit" :disabled="!selectionOrderList.length"
        >提交</el-button
      >
    </template>
  </XModal>

  <XModal v-model="detailsVisible" title="产品详情" width="80%">
    <!-- <common-search
      :conditions-list="searchProductConditions"
      :search-model="searchDetailsModel"
      @query-data="getDetailsInfo"
      style="margin-bottom: 20px"
    /> -->
    <common-table
      :height="400"
      @pagination-change="handleDetailsPagination"
      :pagination="detailsPagination"
      :columns="tableDetailsColumns"
      :tableData="detailsDataList"
      :loading="detailsLoading"
      :isShowPagination="false"
    />
    <template #footer> </template>
  </XModal>
</template>

<script setup lang="ts">
import { ref, reactive, computed } from 'vue'
import {
  returnGoodsColumns,
  searchPurchaseConditions,
  tableOrderColumns,
  searchProductConditions,
  tableDetailsColumns
} from '../data'
import { queryClientSaleTableList, queryDetailsTableList } from '@/api/salesOrder/saleSheet'
import { queryTableList as colorSelectList } from '@/api/masterData/colorNum'
import { queryTableList as diskNumSelectList } from '@/api/masterData/diskNum'

const operation = {
  label: '操作',
  slot: 'operation',
  fixed: 'right',
  width: '140'
}

const piecesColumns = {
  label: '件数',
  slot: 'pieces',
  width: '180'
}

const dialogVisible = ref(false)

const props = defineProps({
  isShowView: {
    type: Boolean
  },
  tableReturnList: {
    type: Array as any,
    default: () => []
  },
  formDataProps: {
    type: Object as any
  },
  isCustomerName: {
    type: String as any
  }
})

const emit = defineEmits(['upDateTableReturnList', 'updateProcessData'])

const detailsDataList = ref([])
const detailsLoading = ref(false)
// 详情搜索
const searchDetailsModel = ref({})
// 详情弹框
const detailsVisible = ref(false)
const searchModel = ref({
  productNumber: '',
  productName: '',
  model: '',
  spec: '',
  lineType: ''
})
const selectionOrderList = ref([])
const procurementDataList = ref([]) // 采购单列表数据
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
const updateProcessData = ref<any>([])
// 颜色下拉选择
const colorSelectDataList = ref<any>([])
// 盘号的下拉选择
const diskSelectDataList = ref<any>([])
const tableLoading = ref<any>(false)
const updatedColumns = ref()

const salePrice = computed(() => {
  return props.tableReturnList.reduce((totalQuantity, item) => {
    return totalQuantity + salePriceTotal(item)
  }, 0)
})

let isShowReturnGoodsColumns = computed(() => {
  let updatedColumns = props.isShowView ? returnGoodsColumns : [...returnGoodsColumns, operation]
  if (props.formDataProps.priceModel == 'pieces') {
    updatedColumns = updatedColumns.map((column) => {
      if (column.label === '数量') {
        return piecesColumns
      }
      return column
    })
  }
  return updatedColumns
})

// 监听标签页切换的变化
watch(
  () => props.formDataProps.priceModel,
  (newValue) => {
    if (newValue == 'pieces') {
      updatedColumns.value = isShowReturnGoodsColumns.value.map((column) => {
        if (column.label === '数量') {
          return piecesColumns
        }
        return column
      })
    }
  }
)

const handleDetailsPagination = (value) => {
  detailsPagination = value.value
  getDetailsInfo()
}

const detailsData = ref<any>({})
const handleOpenVisible = async (value) => {
  detailsData.value = value
  detailsVisible.value = true
  await getDetailsInfo()
}

// 获取详情参数
const getDetailsInfo = async () => {
  // 获取详情参数列表
  const params = {
    lineType: detailsData.value.lineType,
    model: detailsData.value.model,
    spec: detailsData.value.spec
    // pageNo: detailsPagination.currentPage,
    // pageSize: detailsPagination.pageSize
  }
  detailsLoading.value = true
  await queryDetailsTableList(params)
    .then((res) => {
      console.log(res, '123123')

      // const { list, total } = res || {}
      detailsDataList.value = res
    })
    .finally(() => {
      detailsLoading.value = false
    })
}

const handleCloseProcurement = () => {
  pagination.currentPage = 1
  dialogVisible.value = false
}

const handleAddSubmit = () => {
  emit('upDateTableReturnList', selectionOrderList.value)
  dialogVisible.value = false
}

// 获取销售价（原材料价 + 加工费）
const salePriceTotal = (value): any => {
  return value.purchasePrice + value.processingFee
}

// 计算总价(数量 * 销售价)
const calculateTotalPrice = (value?): any => {
  // 铜税率
  const copperFaxScale =
    (Number(props.formDataProps.copperTaxRate ? props.formDataProps.copperTaxRate : 1) / 100) * 100

  // 铝税率
  const aluminiumFaxScale =
    (Number(props.formDataProps.aluminiumTaxRate ? props.formDataProps.aluminiumTaxRate : 1) /
      100) *
    100
  if (props.formDataProps.priceModel == 'pieces') {
    const num = value.pieces * (Number(value?.rawPrice) + Number(value?.processingFee))
    return (value.totalPrice = (num * 1000) / 1000).toFixed(4)
  } else {
    // 总价 数量 * （原材料价 + 加工费）
    const num = value.quantity * (Number(value?.rawPrice) + Number(value?.processingFee))
    return (value.totalPrice = (num * 1000) / 1000).toFixed(4)
  }

  // 需要判断是否含税进行税率的计算，本期不用
  // if (props.formDataProps.isTax === 'Y') {
  //   const lineType = value.lineType
  //   const hasCopper = /铜/.test(lineType)
  //   const hasAluminium = /铝/.test(lineType)
  //   if (hasCopper && !hasAluminium) {
  //     value.totalPrice = (num * (1 + copperFaxScale)).toFixed(2)
  //   } else if (!hasCopper && hasAluminium) {
  //     value.totalPrice = (num + 1 * aluminiumFaxScale).toFixed(2)
  //   } else if (lineType.includes('铜包铝')) {
  //     value.totalPrice = (num * (1 + copperFaxScale) + (num + 1 * aluminiumFaxScale)).toFixed(2)
  //   }
  // } else {
  //   value.totalPrice = parseFloat(num.toFixed(2))
  // }
}

// 选择采购单当前行的数据
const handleSelectionChange = (value) => {
  selectionOrderList.value = value
}

// 拷贝当前行数据
const handleDocumentCopyRow = (value) => {
  const params = {
    ...value,
    color: '',
    quantity: 0,
    panhao: '',
    warrantNumber: '',
    customerCode: '',
    inventoryNumber: '',
    remark: ''
  }
  props.tableReturnList.push(params)
}

const handleProgressPagination = (value) => {
  pagination = value.value
  handleAddPurchase()
}

// 搜索订单数据
const handleQueryData = () => {
  handleAddPurchase()
}

// 添加采购
const handleAddPurchase = () => {
  const params = {
    customerName: props.formDataProps.customerName,
    ...searchModel.value,
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize
  }

  tableLoading.value = true
  queryClientSaleTableList(params)
    .then((res) => {
      const { list, total } = res || {}
      pagination.total = total
      procurementDataList.value = list
      dialogVisible.value = true
    })
    .finally(() => {
      tableLoading.value = false
    })
}

// 表格添加行
const handleAddRow = () => {
  handleAddPurchase()
}

// 表格删除行
const handleRemoveRow = (value) => {
  props.tableReturnList.splice(value.$index, 1)
}

// 获取颜色下拉选择
const getColorTableList = () => {
  colorSelectList({}).then((res) => {
    const { list } = res || {}
    colorSelectDataList.value = list.map((item) => {
      return {
        value: item.name,
        label: item.name
      }
    })
  })
}
// 获取盘号下拉选择
const getDiskTableList = () => {
  diskNumSelectList({}).then((res) => {
    const { list } = res || {}
    diskSelectDataList.value = list.map((item) => {
      return {
        value: item.number,
        label: item.number
      }
    })
  })
}

onMounted(() => {
  getColorTableList()
  getDiskTableList()
})

defineExpose({
  updateProcessData
})
</script>

<style scoped lang="scss">
:deep .el-select {
  width: 95% !important;
}

.destination-table {
  :deep .el-table__header {
    tr {
      th:nth-child(11) {
        .cell::before {
          content: '*';
          color: #f56c6c;
          margin-right: 4px;
        }
      }
      th:nth-child(12) {
        .cell::before {
          content: '*';
          color: #f56c6c;
          margin-right: 4px;
        }
      }
      // th:nth-child(13) {
      //   .cell::before {
      //     content: '*';
      //     color: #f56c6c;
      //     margin-right: 4px;
      //   }
      // }
      th:nth-child(4) {
        .cell::before {
          content: '*';
          color: #f56c6c;
          margin-right: 4px;
        }
      }
    }
  }
}
</style>
