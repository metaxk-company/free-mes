<template>
  <ContentWrap>
    <common-search
      :conditions-list="searchConditions"
      :search-model="searchModel"
      @query-data="handleQueryData"
    />
  </ContentWrap>
  <ContentWrap>
    <el-tabs stretch v-model="tabPaneName" class="demo-tabs" type="border-card" lazy>
      <el-tab-pane
        v-for="(item, index) in tabsList"
        :label="item.label"
        :name="item.name"
        :key="index"
      />
      <common-table
        :loading="tableLoading"
        :columns="tableColumns"
        :tableData="tableDataList"
        :pagination="pagination"
        @pagination-change="handlePagination"
      />
    </el-tabs>
  </ContentWrap>
</template>

<script setup lang="ts">
import { ref, onMounted, reactive } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, tableColumns } from './data'
import { queryTableList } from '@/api/stockCount/stockCountAll'

// 搜索内容值
const searchModel = ref({
  lineType: '',
  model: '',
  spec: '',
  reelNumber: ''
})
const tabPaneName = ref('day')
const tableLoading = ref(false) // 表格加载
const tableDataList = ref([]) // 表格数据
let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const tabsList = ref([
  {
    label: '出库统计（天）',
    name: 'day'
  },
  {
    label: '出库统计（月）',
    name: 'month'
  },
  {
    label: '出库统计（年）',
    name: 'year'
  }
])

watch(
  () => tabPaneName.value,
  () => {
    getTableList()
  }
)

// 搜索功能
const handleQueryData = () => {
  getTableList()
}

// 分页功能
const handlePagination = (value) => {
  pagination = value?.value
  getTableList()
}

const getTableList = () => {
  tableLoading.value = true
  const params = {
    ...searchModel.value,
    mode: tabPaneName.value,
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

// 初始化数据
const info = () => {
  getTableList()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
