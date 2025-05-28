<template>
  <common-table
    :isShowPagination="false"
    :columns="returnGoodsColumnsList"
    :tableData="tableReturnList"
    :isEmpty="false"
  >
    <template #empty v-if="!isShowView">
      <el-button type="primary" @click="handleAddPurchase">添加</el-button>
    </template>
    <template #boxNumber="{ scope }">
      <el-input
        :disabled="isShowView"
        v-model="scope.row.boxNumber"
        placeholder="请输入箱号"
        @change="returnInformationList(scope)"
      />
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

    <template #weight="{ scope }">
      <el-input-number :min="0" :disabled="isShowView" v-model="scope.row.weight" />
    </template>
    <template #price="{ scope }">
      <el-input-number :disabled="isShowView" v-model="scope.row.price" :min="0" />
    </template>
    <template #totalPrice="{ scope }">
      {{ scope.row.totalPrice }}
      {{ totalPrice(scope) }}
    </template>
    <template #color="{ scope }">
      <el-select placeholder="请选择颜色" :disabled="isShowView" v-model="scope.row.color">
        <el-option
          v-for="item in colorSelectList"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
    </template>

    <template #axlesNumber="{ scope }">
      <el-input :disabled="isShowView" v-model="scope.row.axlesNumber" placeholder="请输入轴数" />
    </template>
    <template #batch="{ scope }">
      <el-input :disabled="isShowView" v-model="scope.row.batch" placeholder="请输入批次" />
    </template>
    <template #barCode="{ scope }">
      <el-input :disabled="isShowView" v-model="scope.row.barCode" placeholder="请输入条码" />
    </template>

    <!-- <template #color="{ scope }">{{ scope.row.color }}</template> -->
    <!-- <template #axlesNumber="{ scope }">{{ scope.row.axlesNum }}</template>
    <template #batch="{ scope }">{{ scope.row.batchNumber }}</template> -->
    <template #axlesNum="{ scope }">{{ scope.row.axlesNum }}</template>
    <template #batchNumber="{ scope }">{{ scope.row.batchNumber }}</template>
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
import { ref, onMounted, markRaw } from 'vue'
import { returnGoodsColumns } from '../clientGoods/data'
import { LINES_NAME } from '@/utils/const'
import { ElMessage } from 'element-plus'
import {
  getModelSelectList,
  getSpecSelectList,
  getColorSelectList,
  getProductInformation
} from '@/api/masterData/materialManage'
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
  width: '110'
}
const returnGoodsColumnsList = props.isShowView
  ? returnGoodsColumns
  : [...returnGoodsColumns, operation]

const modelSelectList = ref<any>([])
const specSelectList = ref<any>([])
const colorSelectList = ref<any>([])

const totalPrice = (value) => {
  if (value.$index === -1) return
  props.tableReturnList[value.$index].totalPrice =
    Number(value.row.weight || 0) * Number(value.row.price || 0)
}

const handleAddPurchase = () => {
  handleAddRow()
}

// 表格添加行
const handleAddRow = () => {
  props.tableReturnList.push({
    lineType: '',
    model: '',
    spec: '',
    weight: 0,
    price: 0,
    totalPrice: '',
    color: '',
    boxNumber: '',
    axlesNumber: '',
    batch: '',
    barCode: '',
    remark: ''
  })
}
const emit = defineEmits(['boxInformation'])
// 根据箱号获取信息
const returnInformationList = (value) => {
  getProductInformation(value.row.boxNumber).then((res) => {
    console.log('res :>> ', res)
    if (res) {
      emit('boxInformation', markRaw(res), value)
    } else {
      ElMessage({ message: '箱号输入错误', type: 'error' })
    }
  })
}
//

// 表格删除行
const handleRemoveRow = (value) => {
  props.tableReturnList.splice(value.$index, 1)
}

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

const info = () => {
  getModelSelectListAPI()
  getSpecSelectListAPI()
  getColorSelectListAPI()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
