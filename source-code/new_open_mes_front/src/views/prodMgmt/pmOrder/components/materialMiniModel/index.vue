<template>
  <XModal v-model="visibleProduct" title="物料产品选择" width="90%">
    <el-row :gutter="20">
      <el-col :span="4" :xs="19">
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
          @handle-current-change="handleCurrentChange"
          ref="materialDataRef"
        />
      </el-col>
    </el-row>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="handleCurrentSubmit" :disabled="!currentRowList"
          >确 定</el-button
        >
        <el-button @click="handleCancel">取 消</el-button>
      </span>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue'
import treeMaterial from './treeMaterial.vue'
import materialData from './materialData.vue'
import { queryTreeSelect, queryMDItemList } from '@/api/prodMgmt/pmOrder'

const emit = defineEmits(['handleCurrentSubmit'])

const materialDataRef = ref<any>(null)

const visibleProduct = ref(false)

// 保存当前行选中的数据
let currentRowList = ref('')

// tree 的搜索参数
const treeMaterialForm = reactive({
  classifyName: '',
  treeData: ref([]),
  treeProps: {
    children: 'children',
    label: 'label'
  }
})
// 表格加载状态
const loadingTable = ref<Boolean | any>(false)
// 右边 - 搜索参数
const queryParams = reactive({ itemCode: '', itemName: '', itemTypeId: '' })

// 表格数据
let tableData = reactive([])

// 分页数据
const paginationData = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})

// 搜索
const handleSearchParams = () => {
  getQueryMDItemList()
}

// 搜索重置
const handleResetSearch = () => {
  getQueryMDItemList()
}

// 树形列的点击事件
const handleNodeClick = (value) => {
  queryParams.itemTypeId = value
  getQueryMDItemList()
}

// 调用表格当前行选择事件
const handleCurrentChange = (value) => {
  currentRowList.value = value
}

// 调用表格的清除事件
const clearSelectionListData = () => {
  if (!materialDataRef.value) return
  materialDataRef?.value.handleClearSelectionData()
}

// 分页操作
const handlePagination = (_value: Table.Pagination) => {}

// 弹框确认
const handleCurrentSubmit = () => {
  emit('handleCurrentSubmit', currentRowList.value)
  resetData()
}

// 弹框取消
const handleCancel = () => {
  resetData()
}

// 统一处理弹框结束数据的重置功能
const resetData = () => {
  currentRowList.value = ''
  treeMaterialForm.classifyName = ''
  queryParams.itemCode = ''
  queryParams.itemName = ''
  clearSelectionListData()
  visibleProduct.value = !visibleProduct.value
}

const getQueryTreeSelect = () => {
  queryTreeSelect().then((res) => {
    treeMaterialForm.treeData = res
  })
}

const getQueryMDItemList = () => {
  const params = {
    ...queryParams,
    pageNo: paginationData.currentPage,
    pageSize: paginationData.pageSize
  }
  loadingTable.value = true
  queryMDItemList(params)
    .then((res) => {
      const { list = [], total } = res || {}
      tableData = list
      paginationData.total = total
    })
    .finally(() => {
      loadingTable.value = false
    })
}

const info = () => {
  getQueryTreeSelect()
  getQueryMDItemList()
}

onMounted(() => {
  info()
})

defineExpose({
  clearSelectionListData,
  visibleProduct
})
</script>

<style scoped lang="ts"></style>
