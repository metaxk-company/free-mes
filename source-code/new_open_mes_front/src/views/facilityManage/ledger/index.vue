<template>
  <ContentWrap>
    <el-row :gutter="20">
      <el-col :span="4" :xs="24">
        <!-- 分类数据 -->
        <tree-material :tree-material-form="treeMaterialForm" @node-click="handleNodeClick" />
      </el-col>
      <el-col :span="20" :xs="24">
        <!-- 物料数据 -->
        <material-data
          @search-params="handleSearchParams"
          @reset-search="handleResetSearch"
          :query-params="queryParams"
          :table-data="tableData"
          :pagination-data="paginationData"
          :loading-table="loadingTable"
          @handle-pagination="handlePagination"
          :tree-material-form="treeMaterialForm"
        />
      </el-col>
    </el-row>
  </ContentWrap>
</template>

<script setup lang="ts">
import { reactive, onMounted } from 'vue'
import treeMaterial from './components/treeMaterial.vue'
import materialData from './components/materialData.vue'
import { queryDeviceTypeTree, queryDeviceType } from '@/api/masterData/workstation'
import { handleTree } from '@/utils/tree'

// tree 的搜索参数
const treeMaterialForm = reactive({
  machineryTypeName: '',
  treeData: ref<any>([]),
  treeProps: {
    children: 'children',
    label: 'machineryTypeName'
  }
})

// 右边 - 搜索参数
const queryParams = reactive({
  machineryCode: '',
  machineryName: '',
  machineryTypeId: '',
  status: ''
})

// 表格数据
let tableData = ref([])

// 分页数据
const paginationData = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})

// 表格加载状态
const loadingTable = ref<Boolean | any>(false)

// 查询分类下拉树结构
const queryTreeSelectAPI = () => {
  queryDeviceTypeTree().then((res) => {
    const data = handleTree(res.list, 'id', 'parentTypeId')[0]
    treeMaterialForm.treeData.push(data)
  })
}

// 树形列的点击事件
const handleNodeClick = (value) => {
  queryParams.machineryTypeId = value
  getTableListInfo()
}

// 获取表格数据
const getTableListInfo = () => {
  const params = {
    ...queryParams,
    pageNo: paginationData.currentPage,
    pageSize: paginationData.pageSize
  }
  loadingTable.value = true
  queryDeviceType(params)
    .then((res) => {
      const { list = [], total } = res || {}
      tableData.value = list
      paginationData.total = total
    })
    .finally(() => {
      loadingTable.value = false
    })
}

// 搜索
const handleSearchParams = (value) => {
  getTableListInfo()
}

// 搜索重置
const handleResetSearch = () => {
  getTableListInfo()
}
// 分页操作
const handlePagination = (_value: Table.Pagination) => {
  getTableListInfo()
}

onMounted(() => {
  queryTreeSelectAPI(), getTableListInfo()
})
</script>

<style scoped lang="scss"></style>
