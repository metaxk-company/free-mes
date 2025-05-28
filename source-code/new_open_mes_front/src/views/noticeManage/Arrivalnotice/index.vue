<template>
  <ContentWrap>
    <common-search
      :conditions-list="searchConditions"
      :search-model="searchModel"
      @query-data="handleQueryData"
    />
  </ContentWrap>
  <ContentWrap>
    <common-table
      :loading="tableLoading"
      :isSelection="true"
      :columns="tablePurchaseColumns"
      :tableData="tableDataList"
      :pagination="pagination"
      @pagination-change="handlePagination"
      @selection-change="handleSelectionChange"
    >
      <template #operation="{ scope }">
        <el-button type="primary" @click="handleOpenDetailsData(scope.row)">详情</el-button>
      </template>
    </common-table>
  </ContentWrap>
  <XModal
    v-model="dialogVisible"
    :title="dialogTitle"
    @close="handleCloseDialogVisible"
    width="90%"
  >
    <ContentWrap>
      <common-search
        :conditions-list="searchPurchaseConditions"
        :search-model="returnSearch"
        @query-data="handleQueryData"
      />
    </ContentWrap>
    <ContentWrap>
      <common-table
        :loading="tableLoading"
        :isSelection="true"
        :columns="returnGoodsColumns"
        :tableData="returnDataList"
        :pagination="returnPagination"
        @pagination-change="handleReturnPagination"
        @selection-change="handleSelectionChange"
      />
    </ContentWrap>

    <template #footer>
      <el-button @click="handleCloseDialogVisible">取消</el-button>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { ref, onMounted, reactive } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { btnConditions } from '@/utils/const'
import { warehouseList } from '@/api/storeHouse/warehouseModel'
import {
  queryTableList,
  getVendorSelectData,
  productClassList,
  queryFormInfo
} from '@/api/noticeManage/Arrivalnotice'

import {
  searchConditions,
  tablePurchaseColumns,
  searchPurchaseConditions,
  returnGoodsColumns
} from './data'
import dayjs from 'dayjs'

// 所属仓库下拉列表
const wareHousesSelectList = ref<any>([])
// 搜索内容值
const searchModel = ref({
  number: '',
  vendorName: '',
  createTime: '',
  deliveryDate: ''
})
const returnSearch = ref({
  itemCode: '',
  itemName: '',
  model: '',
  spec: ''
})
const tableLoading = ref(false) // 表格加载
const tableDataList = ref([]) // 表格数据
const returnDataList = ref([])
const getSelectionData = ref()

let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
let returnPagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const dialogVisible = ref(false) // 弹框显示
const isShowView = ref(false) // 是否是查看状态
const dialogTitle = ref('')
const tableReturnList = ref<any>([])
const vendorNameSelectList = ref<any>([])
const productClassSelectList = ref<any>([])
const number = ref()
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

// 搜索功能
const handleQueryData = () => {
  getTableList()
  getTestWayInfo()
}

// 获取详情接口
const getTestWayInfo = async (value?) => {
  const params = {
    recNumber: number.value,
    id: value,
    ...returnSearch.value,
    pageNo: returnPagination.currentPage,
    pageSize: returnPagination.pageSize
  }
  await queryFormInfo(params).then((res) => {
    const { list, total } = res || {}
    returnPagination.total = total
    returnDataList.value = list
  })
}

// 表格详情功能
const handleOpenDetailsData = async (value) => {
  number.value = value.number
  await getTestWayInfo(value.id)

  dialogTitle.value = '到货详情'
  isShowView.value = true
  dialogVisible.value = true
}

// 分页功能
const handlePagination = (value) => {
  pagination = value?.value
  getTableList()
}
const handleReturnPagination = (value) => {
  returnPagination = value?.value

  getTestWayInfo()
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
  number.value = ''
  tableReturnList.value = []
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

const getVendorSelectListAPI = () => {
  getVendorSelectData().then((res) => {
    const vendorNameList = res.map((item) => {
      return {
        label: item.vendorName,
        value: item.vendorName
      }
    })
    vendorNameSelectList.value = vendorNameList
  })
}

const productClassListAPI = () => {
  productClassList().then((res) => {
    const productClassSelect = res.map((item) => {
      return {
        value: item.label,
        label: item.label
      }
    })
    productClassSelectList.value = productClassSelect
  })
}

// 初始化数据
const info = () => {
  getTableList()
  getVendorSelectListAPI()
  productClassListAPI()
  getWareHousesQueryTableListAPI()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
