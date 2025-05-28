<template>
  <!-- returnGoodsColumns -->
  <common-table
    :isShowPagination="false"
    :columns="isReturnGoodsColumns"
    :tableData="tableReturnList"
    :isEmpty="false"
  >
    <template #empty>
      <el-button class="moving-button" type="success" :icon="'Plus'" @click="handleAddRow"
        >添加</el-button
      >
    </template>

    <template #spec="{ scope }">
      <el-input disabled v-model="scope.row.spec" />
    </template>
    <template #price="{ scope }">
      <el-input-number :min="0" v-model="scope.row.price" :disabled="isShowView" />
    </template>
    <template #startSpec="{ scope }">
      <el-input-number
        :disabled="isShowView"
        :min="0"
        v-model="scope.row.startSpec"
        @change="handleBlurStartSpec(scope)"
      />
    </template>
    <template #endSpec="{ scope }">
      <el-input-number
        :disabled="isShowView || !scope.row.startSpec"
        :min="scope.row.startSpec"
        v-model="scope.row.endSpec"
        @change="handleBlurEndSpec(scope)"
      />
    </template>

    <template #operation="{ scope }">
      <el-button type="success" :icon="'Plus'" @click="handleAddRow()" link />
      <el-button type="danger" :icon="'Minus'" @click="handleRemoveRow(scope)" link />
    </template>
  </common-table>
</template>

<script setup lang="ts">
import { returnGoodsColumns } from '../data'
import { formatNumber } from '@/utils'

const props = defineProps({
  isShowView: {
    type: Boolean
  },
  tableReturnList: {
    type: Array as any,
    default: () => []
  }
})

const operation = {
  label: '操作',
  slot: 'operation',
  fixed: 'right'
}

const isReturnGoodsColumns = computed(() => {
  return props.isShowView ? returnGoodsColumns : [...returnGoodsColumns, operation]
})

const handleBlurStartSpec = (value) => {
  props.tableReturnList[value.$index].startSpec = formatNumber(value.row?.startSpec)
  props.tableReturnList[value.$index].endSpec = formatNumber(value.row?.startSpec)
  props.tableReturnList[value.$index].spec = value.row?.startSpec + '-' + value.row?.endSpec
}

const handleBlurEndSpec = (value) => {
  props.tableReturnList[value.$index].endSpec = formatNumber(value.row?.endSpec)
  props.tableReturnList[value.$index].spec = value.row?.startSpec + '-' + value.row?.endSpec
}

// 表格添加行
const handleAddRow = () => {
  props.tableReturnList.push({ spec: '', price: '', startSpec: '', endSpec: '' })
}

// 表格删除行
const handleRemoveRow = (value) => {
  props.tableReturnList.splice(value.$index, 1)
}
</script>

<style scoped lang="scss"></style>
