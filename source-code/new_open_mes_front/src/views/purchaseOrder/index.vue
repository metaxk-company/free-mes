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
      :columns="tablePurchaseColumns"
      :tableData="tableDataList"
      :pagination="pagination"
      @pagination-change="handlePagination"
      @selection-change="handleSelectionChange"
    >
      <template #operation="{ scope }">
        <el-button type="primary" @click="handleOpenDetailsData(scope.row)">详情</el-button>
        <el-button type="success" @click="handleOpenEdit(scope.row)">修改</el-button>
        <el-button type="danger" @click="handleOpenRemove(scope.row)">删除</el-button>
        <!--  v-hasPermi="['wh:rec:bill:receiveAll']" -->
        <el-button
          color="#515aef"
          @click="handleAllArrived(scope.row)"
          v-if="scope.row.state == '0'"
          v-hasPermi="['wh:rec:bill:receiveAll']"
          >全部到货</el-button
        >
        <el-button
          type="info"
          @click="handlePartialArrival(scope.row)"
          v-if="scope.row.state == '1' || scope.row.state == '0'"
          v-hasPermi="['wh:rec:bill:receiveSome']"
          >部分到货</el-button
        >
        <el-button type="warning" :loading="SyncBtnLoading" @click="handleAffirm(scope.row)"
          >确认同步</el-button
        >
        <el-button type="" @click="handlePrinting(scope.row)">打印</el-button>
      </template>
    </common-table>
  </ContentWrap>
  <XModal
    v-model="dialogVisible"
    :title="dialogTitle"
    @close="handleCloseDialogVisible"
    width="90%"
  >
    <el-form ref="ruleFormRef" :model="ruleFormData" :rules="rules" label-width="130px" status-icon>
      <el-row>
        <el-col :span="6">
          <el-form-item label="采购单号">
            {{ ruleFormData.number }}
            <!-- <el-input v-model="ruleFormData.number" :disabled="isShowView" /> -->
          </el-form-item>
        </el-col>

        <!-- <el-col :span="6">
          <el-form-item label="自动生成">
            <el-switch v-model="ruleFormData.createCode" @change="handleCreateCode" />
          </el-form-item>
        </el-col> -->
        <!-- <el-col :span="6">
          <el-form-item label="币种类别" :prop="isShowView ? '' : 'currency'">
            <el-select
              placeholder="请选择币种类别"
              :disabled="isShowView"
              v-model="ruleFormData.currency"
            >
              <el-option
                v-for="item in CURRENCY_TYPE"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col> -->
        <el-col :span="6">
          <el-form-item label="收货地址">
            <el-input v-model="ruleFormData.address" :disabled="isShowView" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="供应商名称" :prop="isShowView ? '' : 'vendorName'">
            <el-select
              placeholder="请选择供应商名称"
              v-if="!isShowView"
              v-model="ruleFormData.vendorName"
              filterable
            >
              <el-option
                v-for="item in vendorNameSelectList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
            <div v-else>{{ ruleFormData.vendorName }}</div>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="交货日期">
            <el-date-picker
              clearable
              :disabled="isShowView"
              v-model="ruleFormData.deliveryDate"
              type="date"
              value-format="YYYY-MM-DD"
              placeholder="请选择交货日期"
              :disabled-date="disabledTime"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="类型">
            {{ ruleFormData.status }}
            <!-- <el-select
              placeholder="请选择类型"
              :disabled="isShowView"
              v-model="ruleFormData.status"
            >
              <el-option
                v-for="item in PURCHASE_STATE"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select> -->
          </el-form-item>
        </el-col>
        <!-- <el-col :span="6">
          <el-form-item label="不含税总价（元）">
            <el-input v-model="ruleFormData.noTaxPrice" disabled />
          </el-form-item>
        </el-col> -->

        <el-col :span="6">
          <el-form-item label="是否含税" :prop="isShowView ? '' : 'isTax'">
            <el-radio-group v-model="ruleFormData.isTax" :disabled="isShowView">
              <el-radio label="Y">是</el-radio>
              <el-radio label="N">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="总价(元)">
            {{ ruleFormData.taxPrice }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="备注">
            <el-input v-model="ruleFormData.remark" :disabled="isShowView" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <!-- <el-col :span="6">
          <el-form-item label="税率" :prop="isShowView ? '' : 'taxRate'">
            <el-select
              placeholder="请选择税率"
              :disabled="isShowView"
              v-model="ruleFormData.taxRate"
            >
              <el-option
                v-for="item in TAXRATE_DATA"
                :key="item.value"
                :label="item.label + '%'"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col> -->
      </el-row>
      <el-row>
        <!-- <el-col :span="6">
          <el-form-item label="产品类别" :prop="isShowView ? '' : 'productType'">
            <el-select
              placeholder="请选择产品类别"
              :disabled="isShowView"
              v-model="ruleFormData.productType"
            >
              <el-option
                v-for="item in productClassSelectList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col> -->
      </el-row>
    </el-form>
    <returnGoodsTable
      :rule-form-data="ruleFormData"
      :table-return-list="tableReturnList"
      @get-select-data="handleGetSelectData"
      :is-show-view="isShowView"
    />
    <template #footer>
      <el-button type="primary" @click="handleDialogSubmit" v-if="!isShowView">提交</el-button>
      <el-button @click="handleCloseDialogVisible">取消</el-button>
    </template>
  </XModal>
  <!-- 部分到货 -->
  <XModal
    v-model="DeliveryVisible"
    :title="dialogTitle"
    @close="handleCloseDialogVisible"
    width="90%"
  >
    <el-form
      ref="ruleFormRef"
      :model="deliveryDataList"
      :rules="rules"
      label-width="130px"
      status-icon
    >
      <el-row>
        <el-col :span="6">
          <el-form-item label="到货通知单">
            {{ deliveryDataList.receiveBillCode }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="采购单号">
            {{ deliveryDataList.receiptNumber }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="仓库名称" :prop="isShowView ? '' : 'wareHouse'">
            <el-select placeholder="请选择仓库来源" v-model="deliveryDataList.wareHouse">
              <el-option
                v-for="item in wareHousesSelectList"
                :key="item.value"
                :label="item.label"
                :value="item.label"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="到货日期" :prop="isShowView ? '' : 'receiveDate'">
            <el-date-picker
              v-model="deliveryDataList.receiveDate"
              type="date"
              placeholder="请选择到货日期"
              format="YYYY/MM/DD"
              value-format="YYYY-MM-DD"
              :disabled="isShowView"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <common-table
      :isShowPagination="false"
      :columns="deliveryData"
      :tableData="inboundRecBillItemVoList"
      :height="400"
      :isEmpty="false"
    >
      <template #quantity="{ scope }">
        {{ scope.row.quantity }}
      </template>
      <template #arrivedQuantity="{ scope }">
        {{ scope.row.arrivedQuantity != '' ? scope.row.arrivedQuantity : 0 }}
      </template>
      <template #amount="{ scope }">
        <template v-if="isShowView">{{ scope.row.quantity }}</template>
        <el-input-number
          :min="0"
          :max="scope.row.quantity - scope.row.arrivedQuantity"
          v-else
          v-model="scope.row.amount"
          placeholder="请输入到货数量"
        />
      </template>
    </common-table>
    <template #footer>
      <el-button type="primary" @click="handleDeliverySubmit">提交</el-button>
      <el-button @click="handleCloseDeliveryVisible">取消</el-button>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import download from '@/utils/download'
import { ref, onMounted, reactive } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { btnConditions } from '@/utils/const'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessageBox, ElMessage } from 'element-plus'
import { warehouseList } from '@/api/storeHouse/warehouseModel'
import {
  queryTableList,
  getVendorSelectData,
  getCreateCode,
  productClassList,
  addFormData,
  updateFormInfo,
  queryFormInfo,
  deleteFormInfo,
  downloadListData,
  getPrintingData,
  addPartialArrival,
  addAllPartialArrival
} from '@/api/purchaseOrder'
import { purchaseOrderSync } from '@/api/a_public_port'
import returnGoodsTable from './components/returnGoodsTable.vue'
import { searchConditions, tablePurchaseColumns, deliveryData } from './data'
import dayjs from 'dayjs'

// 所属仓库下拉列表
const wareHousesSelectList = ref<any>([])
const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  number: [{ required: true, message: '采购单号不能为空', trigger: 'blur' }],
  currency: [{ required: true, message: '币种类别不能为空', trigger: 'blur' }],
  address: [{ required: true, message: '收货地址不能为空', trigger: 'blur' }],
  vendorName: [{ required: true, message: '供应商名称不能为空', trigger: 'blur' }],
  taxRate: [{ required: true, message: '税率不能为空', trigger: 'blur' }],
  deliveryDate: [{ required: true, message: '交货日期不能为空', trigger: 'blur' }],
  productType: [{ required: true, message: '产品类别不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '采购状态不能为空', trigger: 'blur' }],
  isTax: [{ required: true, message: '请选择是否含税', trigger: 'blur' }],
  wareHouse: [{ required: true, message: '仓库类型不能为空', trigger: 'blur' }],
  receiveDate: [{ required: true, message: '到期日期不能为空', trigger: 'blur' }]
})

// 搜索内容值
const searchModel = ref({
  number: '',
  vendorName: '',
  createTime: '',
  deliveryDate: ''
})
const SyncBtnLoading = ref(false)
const tableLoading = ref(false) // 表格加载
const tableDataList = ref([]) // 表格数据
const getSelectionData = ref()
const DeliveryVisible = ref(false) //部分到货弹框
let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const dialogVisible = ref(false) // 弹框显示
const ruleFormData = ref<any>({
  createCode: false,
  status: '采购申请',
  deliveryDate: ref(dayjs().format().split('T')[0])
}) // 表单数据
const isShowView = ref(false) // 是否是查看状态
const dialogTitle = ref('')
const tableReturnList = ref<any>([])
const inboundRecBillItemVoList = ref<any>([])
const vendorNameSelectList = ref<any>([])
const productClassSelectList = ref<any>([])
const deliveryDataList = ref<any>({
  receiveDate: ref(dayjs().format().split('T')[0])
}) //部分到货信息
// 获取仓库列表下拉接口
const getWareHousesQueryTableListAPI = () => {
  warehouseList().then((res) => {
    wareHousesSelectList.value = res.map((item) => {
      return {
        value: item.warehouseNumber,
        label: item.warehouseName
      }
    })
  })
}

const handlePrinting = async (value) => {
  let data
  let params = {
    number: value.number,
    type: 'pdf'
  }
  data = await getPrintingData(params).then((res) => res)
  let pnf = new Blob([data], { type: 'application/pdf;charset=utf-8' })

  window.open(window.URL.createObjectURL(pnf))
}

const handleGetSelectData = (value) => {
  tableReturnList.value.push(...value)
}

// 搜索功能
const handleQueryData = () => {
  getTableList()
}

// 获取详情接口
const getTestWayInfo = async (value) => {
  await queryFormInfo(value).then((res) => {
    ruleFormData.value = res
    tableReturnList.value = res.receiptItemList
    //到货显示内容
    deliveryDataList.value = res
    deliveryDataList.value.receiptNumber = res.number
    deliveryDataList.value.receiveDate = dayjs().format().split('T')[0]
    inboundRecBillItemVoList.value = res.receiptItemList
  })
}
// 到货通知单编号
const getReceiveBillCode = () => {
  getCreateCode('RECEIVE_BILL_CODE').then((res) => {
    deliveryDataList.value.receiveBillCode = res
  })
}
// 全部到货
const handleAllArrived = async (value) => {
  isShowView.value = true
  dialogTitle.value = '全部到货详情'
  DeliveryVisible.value = true
  await getTestWayInfo(value.id)
  getReceiveBillCode()
}
// 部分到货
const handlePartialArrival = async (value) => {
  isShowView.value = false
  dialogTitle.value = '部分到货详情'
  DeliveryVisible.value = true
  await getTestWayInfo(value.id)
  getReceiveBillCode()
}

// 关闭部分到货弹框
const handleCloseDeliveryVisible = () => {
  DeliveryVisible.value = false
  deliveryDataList.value = {
    receiveDate: ref(dayjs().format().split('T')[0])
  }
}
// 到货提交
const handleDeliverySubmit = async () => {
  // 判断输入框是否有值
  if (isShowView.value == true) {
    // 全部
    const params = {
      ...deliveryDataList.value
    }

    if (!ruleFormRef.value) return
    await ruleFormRef.value.validate((valid) => {
      if (!valid) return
      addAllPartialArrival(params).then((res) => {
        ElMessage({ message: '提交成功', type: 'success' })
        handleCloseDeliveryVisible()
        getTableList()
      })
    })
  } else {
    // 部分
    const allValuesNotNull = inboundRecBillItemVoList.value.every((obj) => {
      return obj.amount != undefined
    })

    if (allValuesNotNull) {
      const params = {
        ...deliveryDataList.value,
        inboundRecBillItemVoList: inboundRecBillItemVoList.value
      }
      if (!ruleFormRef.value) return
      await ruleFormRef.value.validate((valid) => {
        if (!valid) return
        addPartialArrival(params).then((res) => {
          ElMessage({ message: '提交成功', type: 'success' })
          handleCloseDeliveryVisible()
          getTableList()
        })
      })
    } else {
      ElMessage({ message: '到货数量不能为空', type: 'warning' })
    }
  }
}
const disabledTime = ref()
//时间限制
const disabledDate = (time: Date) => {
  const today = new Date()
  const yesterday = new Date(today)
  yesterday.setDate(today.getDate() - 1)
  const timestamp = yesterday.getTime() // 获取前一天的时间戳

  return time.getTime() < timestamp
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
    case 'download':
      handleDownload()
      break
  }
}

const handleDownload = async () => {
  const data = await downloadListData()
  download.excel(data, '采购订单.xls')
}

// 按钮新增功能
const handleOpenSave = () => {
  dialogTitle.value = '新增采购订单'
  disabledTime.value = disabledDate
  handleCreateCode()
  dialogVisible.value = true
}

// 按钮/表格 - 修改功能
const handleOpenEdit = async (value?) => {
  dialogTitle.value = '修改采购订单'
  const getId = value ? value?.id : getSelectionData.value[0].id
  await getTestWayInfo(getId)
  disabledTime.value = null
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
// 确认同步
const handleAffirm = (value) => {
  const params = {
    id: value.id
  }
  SyncBtnLoading.value = true
  purchaseOrderSync(params)
    .then((res) => {
      ElMessage({ message: res, type: 'success' })
    })
    .finally(() => {
      SyncBtnLoading.value = false
    })
}
// 表格详情功能
const handleOpenDetailsData = async (value) => {
  await getTestWayInfo(value.id)
  dialogTitle.value = '采购订单详情'
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
  dialogVisible.value = false
  isShowView.value = false
  ruleFormData.value = {
    status: '采购申请',
    deliveryDate: ref(dayjs().format().split('T')[0])
  }
  tableReturnList.value = []
}

// 生成检验编号
const handleCreateCode = () => {
  getCreateCode('RECEIPT_CODE').then((res) => {
    ruleFormData.value.number = res
  })
}

// 表格弹框提交
const handleDialogSubmit = async () => {
  const params = {
    ...ruleFormData.value,
    receiptItemList: tableReturnList.value
  }
  if (!ruleFormRef.value) return
  await ruleFormRef.value.validate((valid) => {
    if (!valid) return
    if (!ruleFormData.value?.id) {
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

const getVendorSelectListAPI = () => {
  getVendorSelectData().then((res) => {
    const vendorNameList = res.map((item) => {
      return {
        label: item.vendorName,
        value: item.vendorName
      }
    })
    vendorNameSelectList.value = vendorNameList
  })
}

const productClassListAPI = () => {
  productClassList().then((res) => {
    const productClassSelect = res.map((item) => {
      return {
        value: item.label,
        label: item.label
      }
    })
    productClassSelectList.value = productClassSelect
  })
}

// 初始化数据
const info = () => {
  getTableList()
  getVendorSelectListAPI()
  productClassListAPI()
  getWareHousesQueryTableListAPI()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
