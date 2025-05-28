<template>
  <common-table
    :isShowPagination="false"
    :columns="!isShowView ? tableTestingItemsColumns : tableTestingItemsColumns.slice(0, 4)"
    :tableData="tableTestingItemsDataList"
    :isEmpty="false"
    :height="300"
  >
    <template #empty>
      <el-button type="primary" @click="handleAddRow" v-if="!isShowView">添加检测项目</el-button>
    </template>
    <template #itemName="{ scope }">
      <el-input :disabled="isShowView" v-model="scope.row.itemName" placeholder="请输入检测名称" />
    </template>
    <template #itemStandard="{ scope }">
      <el-input
        :disabled="isShowView"
        v-model="scope.row.itemStandard"
        placeholder="请输入检测标准"
      />
    </template>
    <template #itemDevice="{ scope }">
      <el-select
        placeholder="请选择检测器具"
        :disabled="isShowView"
        v-model="scope.row.itemDevice"
        :loading="selectLoading"
      >
        <el-option
          v-for="item in utensilOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
    </template>
    <template #remark="{ scope }">
      <el-input :disabled="isShowView" v-model="scope.row.remark" placeholder="请输入检验描述" />
    </template>
    <template #operation="{ scope }">
      <el-button type="success" :icon="'Plus'" @click="handleAddRow(scope)" link />
      <el-button type="danger" :icon="'Minus'" @click="handleRemoveRow(scope)" link />
    </template>
  </common-table>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { tableTestingItemsColumns } from '../data'
import { getUtensilList } from '@/api/inspectionStandards/detectionEquipment'
import { deleteTestItemInfo } from '@/api/inspectionStandards/processInspection'

const selectLoading = ref(false)
const props = defineProps({
  tableTestingItemsDataList: {
    type: Array<any>,
    default: () => []
  },
  isShowView: {
    type: Boolean,
    default: false
  },
  inspectCode: {
    type: String,
    default: ''
  }
})

const utensilOptions = ref<any>([])

// 表格添加行
const handleAddRow = (value) => {
  props.tableTestingItemsDataList.push({
    itemName: '',
    itemStandard: '',
    itemDevice: '',
    remark: ''
  })
}

// 表格删除行
const handleRemoveRow = (value) => {
  if (value.row.inspectDevice) {
    deleteTestItemInfo(value.row.inspectDevice, props.inspectCode).then((res) => {
      props.tableTestingItemsDataList.splice(value.$index, 1)
    })
  } else {
    props.tableTestingItemsDataList.splice(value.$index, 1)
  }
}

// 获取表格数据
const getTableList = () => {
  const params = {}
  selectLoading.value = true
  getUtensilList(params)
    .then((res) => {
      const { list } = res || {}

      utensilOptions.value = list.map((item) => {
        return {
          value: item.deviceCode,
          label: item.deviceName,
          disable: false
        }
      })
    })
    .finally(() => {
      selectLoading.value = false
    })
}

onMounted(() => {
  getTableList()
})
</script>

<style scoped lang="scss"></style>
