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
      <template #inspectWay="{ scope }">
        {{ MARRY_INSPECTION_METHOD[scope.row.inspectWay] }}
      </template>
      <template #status="{ scope }">
        {{ scope.row.status == 1 ? '质检完成' : '待质检' }}
      </template>
      <template #operation="{ scope }">
        <el-button
          :type="scope.row.status == 0 ? 'primary' : 'success'"
          @click="handleOpenQualityInspection(scope.row)"
        >
          {{ scope.row.status == 0 ? '质检' : '质检结果' }}
        </el-button>
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
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, tableColumns } from './data'
import { btnConditions, MARRY_INSPECTION_METHOD } from '@/utils/const'
import testResultsModel from './components/test-results-model.vue'
import inspectionStandardModel from './components/inspection-standard-model.vue'
import {
  getTestReceiptList,
  downloadListData,
  getInspectionFormInfo,
  getQualityTestingInfo
} from '@/api/processInspection/incomingReceipts'
import download from '@/utils/download'

const testResultsDialog = ref(false)

// 搜索内容值
const searchModel = ref({
  recNumber: '',
  itemCode: '',
  model: '',
  spec: '',
  recordCode: ''
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

const handleEndTestSubmit = (value) => {
  getTableList()
  getCountList.value = value
  QualityInspectionDialog.value = false
  bearFruitDialog.value = true
  // 显示检验完成后的数据展示
}

// 搜索功能
const handleQueryData = () => {
  getTableList()
}

// 按钮集合功能
const handleBtnOperation = (state) => {
  switch (state) {
    case 'download':
      handleOpenDownload()
      break
  }
}

const handleOpenDownload = async () => {
  const data = await downloadListData()
  download.excel(data, '检验单据.xls')
}

// 按钮检验功能
const handleOpenQualityInspection = async (value) => {
  const params = {
    id: value.id,
    itemCode: value.itemCode,
    inspectUser: JSON.parse(localStorage.userForm).username,
    recNumber: value.recNumber
  }
  if (value.status == 0) {
    // if (value.inspectFlag == 0) {
    //   // 全检
    //   console.log(1)
    // }
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

  getTestReceiptList(params)
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
