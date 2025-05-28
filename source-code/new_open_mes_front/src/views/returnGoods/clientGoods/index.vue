<template>
  <ContentWrap>
    <common-search
      :conditions-list="searchConditions"
      :search-model="searchModel"
      @query-data="handleQueryData"
    />
  </ContentWrap>
  <ContentWrap>
    <div style="margin-bottom: 20px">
      <el-button
        plain
        v-for="(item, index) in btnConditions.slice(0, 4)"
        :type="item.type"
        :key="index"
        :icon="item.icon"
        :disabled="item.disabled"
        @click="handleBtnOperation(item.state)"
      >
        {{ item.label }}</el-button
      >
    </div>
    <common-table
      :loading="tableLoading"
      :isSelection="true"
      :columns="tableColumns"
      :tableData="tableDataList"
      :pagination="pagination"
      @pagination-change="handlePagination"
      @selection-change="handleSelectionChange"
    >
      <template #operation="{ scope }">
        <el-button type="primary" @click="handleOpenDetailsData(scope.row)">详情</el-button>
        <el-button type="" @click="handlePrint(scope.row)">打印</el-button>
        <el-button type="success" @click="handleOpenEdit(scope.row)">修改</el-button>
        <el-button type="danger" @click="handleOpenRemove(scope.row)">删除</el-button>
      </template>
    </common-table>
  </ContentWrap>
  <XModal
    v-model="dialogVisible"
    :title="dialogTitle"
    @close="handleCloseDialogVisible"
    width="90%"
  >
    <el-form ref="ruleFormRef" :model="ruleFormData" :rules="rules" label-width="100px" status-icon>
      <el-row>
        <el-col :span="6">
          <el-form-item label="编号" :prop="isShowView ? '' : 'number'">
            {{ ruleFormData.number }}
            <!-- <el-input v-model="ruleFormData.number" :disabled="isShowView" /> -->
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="类型" prop="type">
            <el-select
              placeholder="请选择类型"
              filterable
              :disabled="isShowView"
              v-model="ruleFormData.type"
            >
              <el-option
                v-for="item in RETURN_STARUS"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="仓库" :prop="isShowView ? '' : 'warehouse'">
            <el-select
              placeholder="请选择仓库"
              :disabled="isShowView"
              v-model="ruleFormData.warehouse"
            >
              <el-option
                v-for="item in wareHousesSelectList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <!-- <el-col :span="6" v-if="!isShowView">
          <el-form-item label="自动生成">
            <el-switch v-model="ruleFormData.createCode" @change="handleCreateCode" />
          </el-form-item>
        </el-col> -->

        <el-col :span="6">
          <el-form-item label="退货日期" :prop="isShowView ? '' : 'inspectCode'">
            <el-date-picker
              clearable
              v-model="ruleFormData.returnDate"
              type="date"
              :disabled="isShowView"
              value-format="YYYY-MM-DD"
              placeholder="请选择退货日期"
              :disabled-date="disableTime"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-form-item label="客户名称" :prop="isShowView ? '' : 'customerName'">
            <el-select
              placeholder="请选择客户名称"
              :disabled="isShowView"
              v-model="ruleFormData.customerName"
            >
              <el-option
                v-for="item in clientList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="备注">
            <el-input v-model="ruleFormData.remark" :disabled="isShowView" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="总净重">
            {{ totalWeight }}
            <!-- <el-input v-model="totalWeight" disabled /> -->
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="总金额">
            {{ totalMoney }}
            <!-- <el-input v-model="totalMoney" disabled /> -->
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <returnGoodsTable
      :is-show-view="isShowView"
      :table-return-list="tableReturnList"
      :rule-form-data="ruleFormData"
      @boxInformation="boxInformation"
    />
    <template #footer>
      <el-button type="primary" @click="handleDialogSubmit" v-if="!isShowView">提交</el-button>
      <el-button @click="handleCloseDialogVisible">取消</el-button>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import download from '@/utils/download'
import { ref, onMounted, reactive } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, tableColumns } from './data'
import { btnConditions } from '@/utils/const'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessageBox, ElMessage } from 'element-plus'
import { RETURN_STARUS } from '@/utils/const'
import {
  queryTableList,
  getCreateCode,
  downloadListData,
  addFormData,
  updateFormInfo,
  queryFormInfo,
  deleteFormInfo,
  getPrintingData
} from '@/api/returnGoods/clientGoods'
import { warehouseList } from '@/api/storeHouse/warehouseModel'
import returnGoodsTable from '../components/returnGoodsTable.vue'
import dayjs from 'dayjs'
import { getClientSelect } from '@/api/salesOrder/quotationList'
const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  number: [{ required: true, message: '请输入编号', trigger: 'blur' }],
  warehouse: [{ required: true, message: '请选择仓库', trigger: 'blur' }],
  type: [{ required: true, message: '请选择类型', trigger: 'blur' }]
})
// 搜索内容值
const searchModel = ref({
  number: '',
  customerName: '',
  returnDate: ''
})
const tableLoading = ref(false) // 表格加载
const tableDataList = ref([]) // 表格数据
const getSelectionData = ref()
let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const dialogVisible = ref(false) // 弹框显示
const ruleFormData = ref<any>({
  createCode: false,
  returnDate: dayjs().format().split('T')[0]
}) // 表单数据
watch(
  () => ruleFormData.value.returnDate,
  (newdata) => {
    if (newdata) {
      ruleFormData.value.returnDate = newdata
    } else {
      ruleFormData.value.returnDate = dayjs().format().split('T')[0] //当天日期
    }
  }
)
const isShowView = ref(false) // 是否是查看状态
const dialogTitle = ref('')
const tableReturnList = ref<any>([])
const disableTime = ref()
const clientList = ref()

//时间限制
const disabledDate = (time?: Date | any) => {
  const today = new Date()
  const yesterday = new Date(today)
  yesterday.setDate(today.getDate() - 1)
  const timestamp = yesterday.getTime() // 获取前一天的时间戳

  return time.getTime() < timestamp
}
// 所属仓库下拉列表
const wareHousesSelectList = ref<any>([])
// 获取仓库列表下拉接口
const getWareHousesQueryTableListAPI = () => {
  warehouseList().then((res) => {
    wareHousesSelectList.value = res.map((item) => {
      return {
        value: item.warehouseName,
        label: item.warehouseName
      }
    })
  })
}
const handlePrint = async (value) => {
  let data
  let params = {
    number: value.number,
    type: 'pdf'
  }
  data = await getPrintingData(params).then((res) => res)
  let pnf = new Blob([data], { type: 'application/pdf;charset=utf-8' })

  window.open(window.URL.createObjectURL(pnf))
}
// 获取子组件
const boxInformation = (value, index) => {
  tableReturnList.value[index.$index].lineType = value.lineType
  tableReturnList.value[index.$index].model = value.model
  tableReturnList.value[index.$index].spec = value.spec
  tableReturnList.value[index.$index].color = value.color
  // 轴数
  tableReturnList.value[index.$index].axlesNumber = value.axlesNum
  // 批次
  tableReturnList.value[index.$index].batch = value.batchNumber
  tableReturnList.value[index.$index].barCode = value.barCode
}
// 总净重
const totalWeight = computed(
  () =>
    (ruleFormData.value.weight = tableReturnList.value.reduce((accumulator, currentValue) => {
      return accumulator + currentValue.weight
    }, 0))
)

// 总金额
const totalMoney = computed(
  () =>
    (ruleFormData.value.weight = tableReturnList.value.reduce((accumulator, currentValue) => {
      return accumulator + currentValue.totalPrice
    }, 0))
)

// 搜索功能
const handleQueryData = () => {
  getTableList()
}
// 获取详情接口
const getTestWayInfo = async (value) => {
  await queryFormInfo(value).then((res) => {
    ruleFormData.value = res
    tableReturnList.value = res.returnsItemList
    console.log(res.returnsItemList)
  })
}

// 按钮集合功能
const handleBtnOperation = (state) => {
  switch (state) {
    case 'save':
      handleOpenSave()
      break
    case 'edit':
      handleOpenEdit()
      break
    case 'remove':
      handleOpenRemove()
      break
    case 'download':
      handleDownload()
      break
  }
}

const handleDownload = async () => {
  const data = await downloadListData()
  download.excel(data, '客户退货.xls')
}

// 按钮新增功能
const handleOpenSave = () => {
  dialogTitle.value = '新增客户退货'
  disableTime.value = disabledDate
  dialogVisible.value = true
  handleCreateCode()
}

// 按钮/表格 - 修改功能
const handleOpenEdit = async (value?) => {
  dialogTitle.value = '修改客户退货'
  const getId = value ? value?.id : getSelectionData.value[0].id
  await getTestWayInfo(getId)
  disableTime.value = null

  dialogVisible.value = true
}

// 按钮/表格 - 删除功能
const handleOpenRemove = (value?) => {
  const getId = value ? [value?.id] : getSelectionData.value.map((item) => item.id)
  ElMessageBox.confirm('是否删除当前数据?').then(() => {
    deleteFormInfo(getId).then((res) => {
      ElMessage({ message: '删除成功', type: 'success' })
      getTableList()
    })
  })
}

// 表格详情功能
const handleOpenDetailsData = async (value) => {
  await getTestWayInfo(value.id)
  // 获取订单详情
  dialogTitle.value = '客户退货详情'
  isShowView.value = true
  dialogVisible.value = true
}

// 分页功能
const handlePagination = (value) => {
  pagination = value?.value
  getTableList()
}

// 表格选择事件
const handleSelectionChange = (value) => {
  getSelectionData.value = value
  btnConditions[1].disabled = !getSelectionData.value.length || getSelectionData.value.length >= 2
  btnConditions[2].disabled = !getSelectionData.value.length
}

// 表格弹框关闭
const handleCloseDialogVisible = () => {
  isShowView.value = false
  ruleFormData.value = {}
  tableReturnList.value = []
  dialogVisible.value = false
}

// 生成检验编号
const handleCreateCode = () => {
  getCreateCode('RETURNS_CODE').then((res) => {
    ruleFormData.value.number = res
  })
}

// 表格弹框提交
const handleDialogSubmit = async () => {
  const params = {
    ...ruleFormData.value,
    returnsItemList: tableReturnList.value
  }

  if (!ruleFormRef.value) return
  await ruleFormRef.value.validate((valid) => {
    if (!valid) return
    if (!ruleFormData.value?.id) {
      // 新增
      addFormData(params).then((res) => {
        ElMessage({ message: '新增成功', type: 'success' })
        handleCloseDialogVisible()
        getTableList()
      })
    } else {
      updateFormInfo(params).then((res) => {
        ElMessage({ message: '修改成功', type: 'success' })
        handleCloseDialogVisible()
        getTableList()
      })
    }
  })
}

const getTableList = () => {
  tableLoading.value = true
  const params = {
    ...searchModel.value,
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize
  }
  queryTableList(params)
    .then((res) => {
      const { list, total } = res || {}
      pagination.total = total
      tableDataList.value = list
    })
    .finally(() => {
      tableLoading.value = false
    })
}

const getClientSelectListAPI = () => {
  getClientSelect().then((res) => {
    const clientListData = res.map((item) => {
      return {
        value: item.customerNumber,
        label: item.customerName
      }
    })
    clientList.value = clientListData
    // searchConditions[0].options = clientListData
  })
}

// 初始化数据
const info = () => {
  getTableList()
  getClientSelectListAPI()
  getWareHousesQueryTableListAPI()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
