<template>
  <XModal v-model="visibleClient" title="客户选择" width="80%">
    <common-search
      :conditions-list="searchConditionsClient"
      :search-model="searchModel"
      @query-data="handleQueryData"
      ref="CommonSearchRef"
    />
    <CommonTable
      style="margin-top: 20px"
      ref="commonTableRef"
      currentRow
      :pagination="pagination"
      :columns="columnClient"
      :tableData="tableFormList"
      :loading="loading"
      @current-change="handleCurrentChange"
    >
      <template #clientType="{ scope }">
        <div>
          {{ CUSTOMER_TYPE[scope.row.clientType] }}
        </div>
      </template>
    </CommonTable>
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
import { ref, reactive, onMounted } from 'vue'
import CommonTable from '@/components/CommonTable/index.vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import { searchConditionsClient, columnClient } from '../../data'
import { queryClientList } from '@/api/prodMgmt/pmOrder'
import { CUSTOMER_TYPE } from '@/utils/const'

const emit = defineEmits(['handleCurrentSubmit'])

const CommonSearchRef = ref<any>(null)
const commonTableRef = ref<any>(null)

// 弹框显示
const visibleClient = ref(false)
const loading = ref<boolean>(false)
// 搜索参数
const searchModel = reactive({
  // 客户编码
  clientCode: '',
  // 客户名称
  clientName: '',
  // 客户简称
  clientNick: '',
  // 客户英文名称
  clientEn: '',
  // 是否启用
  enableFlag: ''
})
// 表格参数
let tableFormList = reactive([])

// 保存当前行的选择
let currentRowList = ref('')

// 表格分页
const pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})

// 表格当前行数据
const handleCurrentChange = (selection) => {
  currentRowList.value = selection
}

// 搜索
const handleQueryData = () => {
  getTableListAPI()
}

// 提交弹框确定
const handleCurrentSubmit = () => {
  emit('handleCurrentSubmit', currentRowList.value)
  resetData()
}

// 弹框取消
const handleCancel = () => {
  resetData()
}

// 重置操作
const resetData = () => {
  currentRowList.value = ''
  CommonSearchRef.value.handleResetData()
  visibleClient.value = false
  commonTableRef.value!.clearCurrentRow()
}

// 获取表格数据
const getTableListAPI = () => {
  const params = {
    ...searchModel,
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize
  }
  loading.value = true
  queryClientList(params)
    .then((res) => {
      const { list = [], total } = res || {}
      tableFormList = list
      pagination.total = total
    })
    .finally(() => {
      loading.value = false
    })
}

const info = () => {
  getTableListAPI()
}

onMounted(() => {
  info()
})

defineExpose({
  visibleClient
})
</script>

<style scoped lang="scss"></style>
