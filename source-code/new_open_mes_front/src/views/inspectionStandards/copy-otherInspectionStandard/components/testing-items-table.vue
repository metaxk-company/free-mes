<template>
  <common-table
    :isShowPagination="false"
    :columns="!isShowView ? tableTestingItemsColumns : tableTestingItemsColumns.slice(0, 4)"
    :tableData="tableTestingItemsDataList"
  >
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
    <template #actual="{ scope }">
      <el-input
        v-model="scope.row.actual"
        placeholder="请输入实际信息"
        @blur="handleBlueReality(scope)"
        :disabled="scope.row.isDisable"
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
    <template #status="{ scope }">
      <!-- /@change="statusChange(scope)" -->
      <el-radio-group v-model="scope.row.status" @change="statusChange(scope)">
        <el-radio :label="item.value" v-for="item in QUALITY_STATUS" :key="item.value">{{
          item.label
        }}</el-radio>
      </el-radio-group>
    </template>
    <template #operation="{ scope }">
      <el-button type="success" :icon="'Plus'" @click="handleAddRow(scope)" link />
      <el-button
        type="danger"
        :icon="'Minus'"
        @click="handleRemoveRow(scope)"
        link
        v-if="scope.$index"
      />
    </template>
  </common-table>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { tableTestingItemsColumns } from '../data'
import { getUtensilList } from '@/api/inspectionStandards/detectionEquipment'
import { deleteTestItemInfo } from '@/api/inspectionStandards/processInspection'
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
  if (props.tableTestingItemsDataList[value.$index].status == 2) {
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
  let actual = props.tableTestingItemsDataList[value.$index].actual
  // 只有检验标准=实际信息时才合格
  if (actual == '') {
    props.tableTestingItemsDataList[value.$index].status = 2
    statusChange(value)
  } else {
    itemStandard == actual
      ? (props.tableTestingItemsDataList[value.$index].status = 1)
      : (props.tableTestingItemsDataList[value.$index].status = 0)
    statusChange(value)
  }
}
// 表格添加行
const handleAddRow = (value) => {
  props.tableTestingItemsDataList.push({
    inspectName: '',
    inspectStand: '',
    inspectDevice: '',
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
