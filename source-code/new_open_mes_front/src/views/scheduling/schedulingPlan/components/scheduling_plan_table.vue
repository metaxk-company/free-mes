<template>
  <CommonTable
    :loading="loading"
    :columns="personnelColumns"
    :tableData="schedulingPlanTableDataList"
    :isShowPagination="false"
    :height="250"
  >
    <template #teamName="{ scope }">
      <el-input v-model="scope.row.teamName" disabled />
    </template>
    <template #teamPeople="{ scope }">
      <el-select
        v-model="scope.row.teamPeople"
        placeholder="请选择人员名称"
        :disabled="schedulingPlanTableDataList[scope.$index].teamNum == ''"
        @focus="handlePersonName(scope.row.teamNum)"
      >
        <el-option
          v-for="item in personNameList"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
    </template>

    <template #remark="{ scope }">
      <el-input v-model="scope.row.remark" autosize type="textarea" placeholder="请输入备注" />
    </template>

    <template #operate="{ scope }">
      <el-button link type="success" @click="handleAddRow">
        <el-icon><Plus /></el-icon>
      </el-button>
      <el-button
        link
        type="danger"
        @click="handleRemoveRow(scope.$index)"
        v-if="scope.$index !== 0"
      >
        <el-icon><Minus /></el-icon>
      </el-button>
    </template>
  </CommonTable>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { personnelColumns } from '../data'
import { getShiftSetDataList, getShiftPersonnelData } from '@/api/scheduling/shiftSet'
import { getNameOfType } from '@/api/scheduling/schedulingPlan'

const props = defineProps({
  schedulingPlanTableDataList: Array as any
})

const loading = ref<boolean>(false)
// 轮班方式
const shiftPattern = ref([
  {
    label: '白班',
    value: '白班'
  },
  {
    label: '中班',
    value: '中班'
  },
  {
    label: '晚班',
    value: '晚班'
  }
])

// 倒班方式下拉参数
const shiftPatternsList = ref([
  {
    label: '两班倒',
    value: '两班倒'
  },
  {
    label: '三班倒',
    value: '三班倒'
  },
  {
    label: '四班倒',
    value: '四班倒'
  }
])

const handlePersonName = async (value) => {
  getShiftPersonnelDataAPI(value)
}

const handleAddRow = () => {
  const row = {
    shiftNum: '',
    shiftName: '',
    shiftType: '',
    teamNum: '',
    teamName: '',
    teamPeople: '',
    shiftLength: '',
    shiftPattern: '',
    shiftPatterns: '',
    startDate: '',
    endDate: '',
    remark: ''
  }
  props.schedulingPlanTableDataList.push(row)
}

const handleRemoveRow = (value) => {
  props.schedulingPlanTableDataList.splice(value, 1)
}

const personNameList = ref<any>([])

const handleTeamNum = (value, index) => {
  props.schedulingPlanTableDataList[index].teamPeople = ''
  getNameOfType(value).then((res) => {
    props.schedulingPlanTableDataList[index].shiftType = res.teamType
    props.schedulingPlanTableDataList[index].teamName = res.teamName
  })
  getShiftPersonnelDataAPI(value)
}

const getShiftPersonnelDataAPI = async (value) => {
  await getShiftPersonnelData(value).then((res) => {
    personNameList.value = res.map((item) => {
      return {
        value: item.userName,
        label: item.userName
      }
    })
  })
}

// 择班组类型下拉参数
const shiftTypeOptions = ref<any>([])

const info = () => {
  getShiftSetDataList({}).then((res) => {
    const { list = [] } = res || {}
    shiftTypeOptions.value = list.map((item) => {
      return {
        value: item.teamCode,
        label: item.teamCode
      }
    })
  })
}

onMounted(() => {
  info()
})
</script>

<style lang="scss" scoped>
// :deep .el-table__header {
//   tr {
//     th:nth-child(1) {
//       .cell::before {
//         content: '*';
//         color: #f56c6c;
//         margin-right: 4px;
//       }
//     }
//   }
// }
</style>
