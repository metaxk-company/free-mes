<template>
  <ContentWrap>
    <common-search
      :conditions-list="searchConditions"
      :search-model="searchModel"
      @query-data="handleQueryData"
    />
  </ContentWrap>
  <ContentWrap>
    <table-model
      :table-form-list="tableFormList"
      :table-loading="tableLoading"
      @update-table-list="handleUpdateTableList"
    />
  </ContentWrap>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import tableModel from './components/tableModel.vue'
import {
  queryFacilityTypeList
  // queryClassifyInfoAPI,
  // deleteDepartmentInfo,
  // addDepartment,
  // updateDepartmentInfo
} from '@/api/facilityManage/typeSet'
import { handleTree } from '@/utils/tree'
import { searchConditions } from './data'

const searchModel = reactive({
  machineryTypeName: '',
  enableFlag: ''
})

let tableFormList = reactive<any>([])

const tableLoading = ref<any>(false)

// 获取表格数据
const getTableListInfo = () => {
  tableLoading.value = !tableLoading.value
  queryFacilityTypeList(searchModel)
    .then((res) => {
      const treeList = handleTree(res.list, 'id', 'parentTypeId')
      tableFormList = treeList
    })
    .finally(() => {
      tableLoading.value = !tableLoading.value
    })
}

// 初始化获取表格数据
onMounted(() => {
  getTableListInfo()
})

// 搜索
const handleQueryData = () => {
  getTableListInfo()
}

// 更新表格数据
const handleUpdateTableList = () => {
  getTableListInfo()
}
</script>

<style scoped lang="scss"></style>
