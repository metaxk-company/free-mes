<template>
  <common-table
    :isShowPagination="false"
    :columns="returnGoodsColumnsList"
    :tableData="tableReturnList"
    :isEmpty="false"
    :height="400"
  >
    <template #empty v-if="!isShowView">
      <el-button type="primary" @click="handleAddPurchase">添加</el-button>
    </template>
    <template #model="{ scope }">
      <el-select
        filterable
        placeholder="请选择型号"
        :disabled="isShowView"
        v-model="scope.row.model"
      >
        <el-option
          v-for="item in modelSelectList"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
    </template>

    <template #spec="{ scope }">
      <el-select
        filterable
        placeholder="请选择规格"
        v-model="scope.row.spec"
        :disabled="isShowView"
      >
        <el-option
          v-for="item in specSelectList"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
    </template>

    <template #color="{ scope }">
      <el-select
        filterable
        placeholder="请选择颜色"
        :disabled="isShowView"
        v-model="scope.row.color"
      >
        <el-option
          v-for="item in colorSelectList"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
    </template>

    <template #lineType="{ scope }">
      <el-select
        filterable
        placeholder="请选择线别"
        :disabled="isShowView"
        v-model="scope.row.lineType"
      >
        <el-option
          v-for="item in LINES_NAME"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
    </template>

    <template #reelNumber="{ scope }">
      <el-select
        filterable
        placeholder="请选择盘号"
        clearable
        :disabled="isShowView"
        v-model="scope.row.reelNumber"
      >
        <el-option
          v-for="item in diskSelectDataList"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
    </template>

    <template #quantity="{ scope }">
      <el-input-number
        :min="0"
        :disabled="isShowView"
        v-model="scope.row.quantity"
        placeholder="请输入数量"
      />
    </template>

    <template #unitOfMeasure="{ scope }">
      <el-select filterable v-model="scope.row.unitOfMeasure" :disabled="isShowView">
        <el-option
          v-for="item in measureOptions"
          :key="item.measureCode"
          :label="item.measureName"
          :value="item.measureCode"
        />
      </el-select>
    </template>

    <template #remark="{ scope }">
      <el-input :disabled="isShowView" v-model="scope.row.remark" placeholder="请输入描述" />
    </template>
    <template #operation="{ scope }">
      <el-button type="success" :icon="'Plus'" @click="handleAddRow()" link />
      <el-button type="danger" :icon="'Minus'" @click="handleRemoveRow(scope)" link />
    </template>
  </common-table>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { returnGoodsColumns } from '../data'
import { LINES_NAME } from '@/utils/const'
import {
  getModelSelectList,
  getSpecSelectList,
  getColorSelectList,
  getUnitList
} from '@/api/masterData/materialManage'
import { queryTableList as diskNumSelectList } from '@/api/masterData/diskNum'

const props = defineProps({
  isShowView: {
    type: Boolean
  },
  tableReturnList: {
    type: Array as any,
    default: () => []
  },
  ruleFormData: {
    type: Object as any,
    default: () => {}
  }
})

const operation = {
  label: '操作',
  slot: 'operation',
  fixed: 'right',
  width: '150'
}
const returnGoodsColumnsList = props.isShowView
  ? returnGoodsColumns
  : [...returnGoodsColumns, operation]

const modelSelectList = ref<any>([])
const specSelectList = ref<any>([])
const colorSelectList = ref<any>([])
const diskSelectDataList = ref<any>([])
//单位列表
const measureOptions = ref<any>([])

const handleAddPurchase = () => {
  handleAddRow()
}

// 表格添加行
const handleAddRow = () => {
  props.tableReturnList.push({
    quantity: 0
  })
}

// 表格删除行
const handleRemoveRow = (value) => {
  props.tableReturnList.splice(value.$index, 1)
}

// 获取型号下拉
const getModelSelectListAPI = () => {
  getModelSelectList().then((res) => {
    modelSelectList.value = res.map((item) => {
      return {
        label: item.name,
        value: item.name
      }
    })
  })
}

// 获取规格下拉
const getSpecSelectListAPI = () => {
  getSpecSelectList().then((res) => {
    specSelectList.value = res.map((item) => {
      return {
        label: item.name,
        value: item.name
      }
    })
  })
}

// 获取颜色下拉
const getColorSelectListAPI = () => {
  getColorSelectList().then((res) => {
    colorSelectList.value = res.list.map((item) => {
      return {
        label: item.name,
        value: item.name
      }
    })
  })
}

// 获取盘号下拉选择
const getDiskTableList = () => {
  diskNumSelectList({}).then((res) => {
    const { list } = res || {}
    diskSelectDataList.value = list.map((item) => {
      return {
        value: item.number,
        label: item.number
      }
    })
  })
}

// 获取单位
const getUnitListAPI = () => {
  getUnitList().then((res) => {
    measureOptions.value = res
  })
}

const info = () => {
  getModelSelectListAPI()
  getSpecSelectListAPI()
  getColorSelectListAPI()
  getDiskTableList()
  getUnitListAPI()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
