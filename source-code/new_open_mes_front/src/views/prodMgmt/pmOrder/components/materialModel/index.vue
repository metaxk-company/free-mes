<template>
  <!-- <el-button :icon="'download'" type="warning" plain style="margin-bottom: 20px">导出</el-button> -->
  <common-table
    :loading="loading"
    :columns="columnMaterialModel"
    :tableData="tableFormList"
    :pagination="pagination"
  >
    <template #itemOrProduct="{ scope }">
      {{ PRODUCT_CLASS[scope.row.itemOrProduct] }}
    </template>
  </common-table>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { columnMaterialModel } from '../../data'
import { getMaterialDataList } from '@/api/prodMgmt/pmOrder'
import { PRODUCT_CLASS } from '@/utils/const'

interface TableProps {
  workOrderId: number | string
}
const props = defineProps<TableProps>()

const tableFormList = ref([])
const loading = ref<boolean>(false)

// 表格分页
const pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})

const getMaterialDataListAPI = () => {
  loading.value = true
  const params = {
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize,
    workorderId: props.workOrderId
  }
  getMaterialDataList(params)
    .then((res) => {
      // const { list = [], total } = res || {}
      tableFormList.value = res
      // pagination.total = total
    })
    .finally(() => {
      loading.value = false
    })
}

const info = () => {
  getMaterialDataListAPI()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
