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
import { queryClassList } from '@/api/masterData/materialClassify'
import { handleTree } from '@/utils/tree'
import { searchConditions } from './data'

const searchModel = reactive({
  itemTypeName: '',
  enableFlag: '',
  itemOrProduct: ''
})

let tableFormList = reactive<any>([])

const tableLoading = ref<any>(false)

// 获取表格数据
const getTableListInfo = () => {
  tableLoading.value = !tableLoading.value
  queryClassList(searchModel)
    .then((res) => {
      const { total, list = [] } = res || {}
      const treeList = handleTree(list, 'id', 'parentTypeId')
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
