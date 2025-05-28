<template>
  <common-table
    :isShowPagination="false"
    :columns="!isShowView ? tableTestingItemsColumns : tableTestingItemsColumns.slice(0, 6)"
    :tableData="tableTestingItemsDataList"
  >
    <template #itemName="{ scope }">
      <el-input :disabled="true" v-model="scope.row.itemName" placeholder="请输入检测名称" />
    </template>
    <template #itemStandard="{ scope }">
      <el-input :disabled="true" v-model="scope.row.itemStandard" placeholder="请输入检测标准" />
    </template>
    <template #actual="{ scope }">
      <el-input
        v-model="scope.row.itemValue"
        placeholder="请输入实际信息"
        @blur="handleBlueReality(scope)"
        :disabled="isShowView"
      />
    </template>
    <template #itemDevice="{ scope }">
      <el-select
        placeholder="请选择检测器具"
        :disabled="true"
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
    <template #status="{ scope }">
      <el-radio-group
        v-model="scope.row.status"
        @change="statusChange(scope)"
        :disabled="isShowView"
      >
        <el-radio v-for="item in QUALITY_STATUS" :key="item.value" :label="item.value.toString()">{{
          item.label
        }}</el-radio>
      </el-radio-group>
    </template>
  </common-table>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { tableTestingItemsColumns } from '../data'
import { getUtensilList } from '@/api/inspectionStandards/detectionEquipment'
import { QUALITY_STATUS } from '@/utils/const'
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
// 选择合格不合格
const statusChange = (value) => {
  if (props.tableTestingItemsDataList[value.$index].status == '2') {
    props.tableTestingItemsDataList[value.$index].isDisable = true
  } else {
    props.tableTestingItemsDataList[value.$index].isDisable = false
  }
}
// 每一行失去焦点后触发更新操作
const handleBlueReality = async (value) => {
  // 当前检验标准
  let itemStandard = props.tableTestingItemsDataList[value.$index].itemStandard
  // 当前输入的对比参数
  let itemValue = props.tableTestingItemsDataList[value.$index].itemValue
  // 只有检验标准=实际信息时才合格
  if (itemValue == '') {
    props.tableTestingItemsDataList[value.$index].status = '2'
    statusChange(value)
  } else {
    itemStandard == itemValue
      ? (props.tableTestingItemsDataList[value.$index].status = '1')
      : (props.tableTestingItemsDataList[value.$index].status = '0')
    statusChange(value)
  }
}

// 获取表格数据
const getTableList = () => {
  const params = {}
  selectLoading.value = true
  getUtensilList(params)
    .then((res) => {
      console.log(res)

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
