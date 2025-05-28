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
      <template #number="{ scope }">
        <el-button link type="primary" @click="handleOpenDetailsData(scope.row)">
          {{ scope.row.number }}</el-button
        >
      </template>
      <template #includTax="{ scope }">
        {{ UN_KNOW_STATE[scope.row.includTax] }}
      </template>
      <template #operation="{ scope }">
        <el-button type="primary" @click="handleAffirm(scope.row)">确认同步</el-button>
        <el-button
          type="warning"
          @click="handleWareHouse(scope.row)"
          v-if="scope.row.status !== '已出库'"
          >确认出库</el-button
        >
        <el-button
          type="warning"
          v-if="scope.row.status == '已出库'"
          @click="handleSalesReturn(scope.row)"
          >退库</el-button
        >
        <el-button
          type="success"
          v-if="scope.row.status !== '已出库'"
          @click="handleOpenEdit(scope.row)"
          >修改</el-button
        >
        <el-button
          type="danger"
          @click="handleOpenRemove(scope.row)"
          v-if="scope.row.status !== '已出库'"
          >删除</el-button
        >
        <el-dropdown style="margin-left: 12px" :disabled="scope.row.sendOut == '0'">
          <el-button :disabled="scope.row.sendOut == '0'">打印</el-button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="handleIncludeTaxPrint('hanshui', scope.row)"
                >含税</el-dropdown-item
              >
            </el-dropdown-menu>
          </template>
        </el-dropdown>
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
      <!-- <el-row>
        <el-col :span="6">
          <el-form-item label="出库单号">
            {{ ruleFormData.number }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="销售单号" :prop="isShowView ? '' : 'saleNumber'">
            <el-input v-model="ruleFormData.saleNumber" :disabled="isShowView">
              <template #append>
                <el-button @click="handleSelectPurchase">选择</el-button>
              </template>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="订单日期">
            {{ ruleFormData.orderDate }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="交货日期">
            {{ ruleFormData.deliveryDate }}
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="6">
          <el-form-item label="客户编号"> {{ ruleFormData.customerNumber }} </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="客户订单号"> {{ ruleFormData.customerOrderNumber }} </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item label="客户名称"> {{ ruleFormData.customerName }} </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="是否含税">
            {{ ruleFormData?.isTax == 'Y' ? '是' : '否' }}
          </el-form-item>
        </el-col>
      </el-row> -->

      <el-row>
        <el-col :span="6">
          <el-form-item label="销售数量">
            {{ quantityRender }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="销售总价">
            {{ getTotalPrice }}
          </el-form-item>
        </el-col>
        <!-- <el-col :span="6">
          <el-form-item label="总价">
            {{ (ruleFormData.totalPrice = getTotalPrice) }}
          </el-form-item>
        </el-col> -->
        <el-col :span="6">
          <el-form-item label="已发">
            {{ _getSendOutTotal }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="未发">
            {{ getTotalNoSend }}
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="6">
          <el-form-item label="总净重">
            {{ ruleFormData.totalWeight }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="出库来源" :prop="isShowView ? '' : 'source'">
            <el-select placeholder="请选择出库来源" disabled v-model="ruleFormData.source">
              <el-option
                v-for="item in OUTBOUND_SOURCE"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="出库状态" :prop="isShowView ? '' : 'status'">
            <el-select placeholder="请选择出库状态" disabled v-model="ruleFormData.status">
              <el-option
                v-for="item in WARE_HOUSE_STATUS"
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
          <el-form-item label="总件数">
            {{ getPieceTotalPiece }}
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="出库总价">
            {{ getTotalPriceAmount }}
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <returnGoodsTable
      @up-data-purchase-data="handleUpDataPurchaseData"
      :table-return-list="tableReturnList"
      :rule-form-data-props="ruleFormData"
      :is-show-view="isShowView"
      :is-save-or-edit="isSaveOrEdit"
    />
    <template #footer>
      <el-button type="primary" @click="handleDialogSubmit" v-if="!isShowView">提交</el-button>
      <el-button @click="handleCloseDialogVisible">取消</el-button>
    </template>
  </XModal>

  <!-- 采购单号弹框内容 -->
  <XModal v-model="dialogPurchaseVisible" title="选购销售单" width="90%">
    <common-search
      style="margin-bottom: 20px"
      :conditions-list="searchPurchase"
      :search-model="searchPurchaseModel"
      @query-data="handlePurchaseQueryData"
    />
    <common-table
      isSelection
      @selection-change="handleSalesOrderSelectionChange"
      :pagination="pagination"
      :columns="tablePurchaseColumns"
      :tableData="purchaseDataList"
      @up-data-return-table-list="handleUpDataList"
    />
    <template #footer>
      <el-button
        type="primary"
        @click="handleAddPurchase"
        :disabled="!salesOrderSelectList.length || salesOrderSelectList.length > 1"
        >提交</el-button
      >
    </template>
  </XModal>

  <!-- 退货弹框 -->
  <XModal v-model="dialogSalesReturn" title="选择退库数据" width="30%">
    <div class="input-wrapper">
      <div class="label">型号规格</div>
      <div class="input-field">
        <el-select
          style="width: 100%"
          v-model="SalesReturnList"
          multiple
          placeholder="请选择规格型号"
        >
          <el-option
            v-for="item in SalesReturnSelectList"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </div>
    </div>
    <template #footer>
      <el-button type="primary" @click="handleSubmitSalesReturn" :disabled="!SalesReturnList.length"
        >提交</el-button
      >
    </template>
  </XModal>
</template>

<script setup lang="ts">
import download from '@/utils/download'
import { ref, onMounted, reactive, computed } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, tableColumns, searchPurchase, tablePurchaseColumns } from './data'
import { btnConditions, OUTBOUND_SOURCE, WARE_HOUSE_STATUS, UN_KNOW_STATE } from '@/utils/const'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessageBox, ElMessage } from 'element-plus'
import {
  queryNoSaleTableList,
  addFormData,
  downloadListData,
  queryFormInfo,
  updateFormInfo,
  deleteFormInfo,
  confirmWarehouse,
  confirmRetreatWarehouse,
  getPrintingTest,
  confirmSync
} from '@/api/stock/saleStock'
import returnGoodsTable from './components/returnGoodsTable.vue'
import { getCreateCode } from '@/api/a_public_port'

import { querySaleTableList as queryClientSaleTableList } from '@/api/salesOrder/saleSheet'

// 存新增和编辑的状态用来给子组件表示
const isSaveOrEdit = ref<boolean>(false)
const salesOrderSelectList = ref([])
const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  saleNumber: [{ required: true, message: '销售单号不能为空', trigger: 'blur' }],
  source: [{ required: true, message: '出库来源不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '出库状态不能为空', trigger: 'blur' }]
})
const searchPurchaseModel = ref({})
// 搜索内容值
const searchModel = ref({
  number: '',
  customerName: '',
  status: ''
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
const ruleFormData = ref<any>({ status: '待出库', source: '销售', noSend: '', sendOut: '' }) // 表单数据
const isShowView = ref(false) // 是否是查看状态
const dialogTitle = ref('')
const tableReturnList = ref<any>([])
// 单出创建一个参数用来拷贝
// const _copyTableReturnList = ref<any>([])
const dialogPurchaseVisible = ref(false)
const purchaseDataList = ref([])
const dialogSalesReturn = ref<boolean>(false)
const SalesReturnList = ref([])
const SalesReturnSelectList = ref<any>([])

// 子表数据获取出来更新父组件列表中的参数
const handleUpDataList = (value) => {
  console.log(tableReturnList.value[value.index], '件数')
  // // 总净重-total
  // tableReturnList.value[value.index].total = value.total
  // // 总皮重-totalTare
  // tableReturnList.value[value.index].totalTare = value.totalTare
  // // sendOut - 已发
  // tableReturnList.value[value.index].sendOut = value.sendOut
  // // 总件数
  // tableReturnList.value[value.index].piece = value.length
}
// 确认同步
const handleAffirm = (value) => {
  const params = { id: value.id }
  confirmSync(params).then((res) => {
    ElMessage({ message: res, type: 'success' })
  })
}
// 出库功能
const handleWareHouse = (value) => {
  ElMessageBox.confirm('是否出库当前数据?').then(() => {
    confirmWarehouse(value.number).then((res) => {
      ElMessage({ message: '出库成功', type: 'success' })
      getTableList()
    })
  })
}

// 打印含税数据
const handleIncludeTaxPrint = async (label, value) => {
  const prams = {
    label: label,
    number: value.number,
    type: 'pdf'
  }
  let date = await getPrintingTest(prams).then((res) => res)
  let pnf = new Blob([date], { type: 'application/pdf;charset=utf-8' })
  window.open(window.URL.createObjectURL(pnf))
  // const iframe = document.createElement('iframe')
  // iframe.className = 'tmp-pdf'
  // iframe.style.display = 'none'
  // iframe.style.zIndex = '99999'
  // iframe.src = url
  // document.body.appendChild(iframe)

  // download(pnf, '213')
}

// 退库功能
const handleSalesReturn = (value) => {
  ElMessageBox.confirm('是否退库当前数据?').then(() => {
    confirmRetreatWarehouse(value.number).then((res) => {
      ElMessage({ message: '退库成功', type: 'success' })
      getTableList()
    })
  })
}

// 销售数量相加
const quantityRender = computed(() => {
  return tableReturnList.value.reduce((accumulator, currentValue) => {
    ruleFormData.value.quantity = accumulator + Number(currentValue.quantity) || 0
    return accumulator + Number(currentValue.quantity) || 0
  }, 0)
})

// 销售总价相加
const priceReduce = computed(() => {
  return tableReturnList.value.reduce((accumulator, currentValue) => {
    ruleFormData.value.saleTotalPrice = accumulator + Number(currentValue.saleTotalPrice) || 0
    return accumulator + Number(currentValue.saleTotalPrice) || 0
  }, 0)
})

const calculateTotal = (property) => {
  return computed(() => {
    return tableReturnList.value.reduce((accumulator, currentValue) => {
      return (Number((accumulator + Number(currentValue[property])).toFixed(3) || 0) * 1000) / 1000
    }, 0)
  })
}

// 使用通用计算函数
const getTotalNetWeight = calculateTotal('totalWeight') // 总净重计算
const getTotalPrice = calculateTotal('totalPrice') // 总价计算
const getTotalSendOut = calculateTotal('sendOut') // 已发计算
const getTotalNoSend = calculateTotal('noSend') // 未发计算

// 计算tableReturnList的列表下的已发数据相加
const _getSendOutTotal = computed(() => {
  return tableReturnList.value.reduce((accumulator, currentValue) => {
    const height = Number(currentValue?.sendOut) || 0
    let getThreeNum = accumulator + height
    ruleFormData.value.totalWeight = Number(getThreeNum.toFixed(3))
    return (ruleFormData.value.sendOut = Number(getThreeNum.toFixed(3)))
  }, 0)
})

// 列表已发：表单已发 + _getSendOutTotal
const getSendOutTotal = computed(() => {
  // return tableReturnList.value.reduce((accumulator, currentValue) => {
  //   return (ruleFormData.value.sendOut = ruleFormData.value.sendOut + _getSendOutTotal.value)
  // }, 0)
  return ruleFormData.value?.sendOut + _getSendOutTotal.value
})

// 列表未发：表单未发 - _getSendOutTotal
const getNoSendTotal = computed(() => {
  return ruleFormData.value?.noSend - _getSendOutTotal.value
  // console.log(ruleFormData.value.noSend, '-ruleFormData.value.noSend')

  // return tableReturnList.value.reduce((accumulator, currentValue) => {
  //   return (ruleFormData.value.noSend = ruleFormData.value.noSend - _getSendOutTotal.value)
  // }, 0)
})

// 总件数计算 = tableReturnList 列表下所有件数相加
const getPieceTotalPiece = computed(() => {
  return tableReturnList.value.reduce((accumulator, currentValue) => {
    const height = Number(currentValue.pieces) || 0
    return (ruleFormData.value.pieces = accumulator + height)
  }, 0)
})

// 出库总价计算 = tableReturnList 列表下所有出库总价相加
const getTotalPriceAmount = computed(() => {
  return tableReturnList.value.reduce((accumulator, currentValue) => {
    const height = Number(currentValue.totalPrice) || 0
    let getThreeNum = accumulator + height
    return (ruleFormData.value.outboundTotalPrice = Number(getThreeNum.toFixed(3)))
  }, 0)
})

// 添加完采购提交进行数据是否重复的判断
const handleUpDataPurchaseData = (value) => {
  value.forEach((item2) => {
    const idExists = tableReturnList.value.some((item1) => item1.itemCode === item2.itemCode)
    const colorExists = tableReturnList.value.some((item1) => item1.color === item2.color)
    // 如果 ID 不存在于 array1 中，则添加到 array1 数组中
    if (!idExists || !colorExists) {
      tableReturnList.value.push(item2)
    } else {
      ElMessage({ message: '不能添加重复数据！', type: 'warning' })
    }
  })
}

// 选择销售单号带出表单中其他数据
const handleAddPurchase = () => {
  const selectForm: any = salesOrderSelectList.value[0]
  ruleFormData.value = {
    saleNumber: selectForm.saleNumber,
    deliveryDate: selectForm.deliveryDate,
    orderDate: selectForm.orderDate,
    customerNumber: selectForm.customerNumber,
    customerOrderNumber: selectForm.customerOrderNumber,
    quantity: selectForm.quantity,
    totalPrice: selectForm.totalPrice,
    sendOut: selectForm.sendOut,
    noSend: selectForm.noSend,
    customerName: selectForm.customerName,
    isTax: selectForm.isTax,
    saleTotalPrice: selectForm.totalPrice
  }
  // 清空当前已选择的采购数据
  tableReturnList.value = []
  dialogPurchaseVisible.value = false
}

// 退货弹框的提交功能
const handleSubmitSalesReturn = () => {}

const handleSalesOrderSelectionChange = (value) => {
  salesOrderSelectList.value = value
}

const handleSelectPurchase = () => {
  dialogPurchaseVisible.value = true
}

const handlePurchaseQueryData = () => {
  getClientSaleTableList()
}

// 搜索功能
const handleQueryData = () => {
  getTableList()
}

// 获取详情接口
const getTestWayInfo = async (value) => {
  await queryFormInfo(value).then((res) => {
    ruleFormData.value = res
    tableReturnList.value = res.orderOutboundItemList
    // 拷贝一份作位独立数据
    // _copyTableReturnList.value = res.orderOutboundItemList
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
  download.excel(data, '销售出库-无销售单.xls')
}

// 按钮新增功能
const handleOpenSave = () => {
  dialogTitle.value = '新增销售出库'
  getWareHouseCode()
  isSaveOrEdit.value = true
  dialogVisible.value = true
}

// 按钮/表格 - 修改功能
const handleOpenEdit = async (value?) => {
  dialogTitle.value = '修改销售出库'
  const getId = value ? value?.id : getSelectionData.value[0].id
  await getTestWayInfo(getId)
  isSaveOrEdit.value = false
  dialogVisible.value = true
}

// 按钮/表格 - 删除功能
const handleOpenRemove = (value?) => {
  const getId = value ? [value?.id] : getSelectionData.value.map((item) => item.id)
  ElMessageBox.confirm('是否删除当前出库单?').then(() => {
    deleteFormInfo(getId).then((res) => {
      ElMessage({ message: '删除成功', type: 'success' })
      getTableList()
    })
  })
}

// 表格详情功能
const handleOpenDetailsData = async (value) => {
  await getTestWayInfo(value.id)
  dialogTitle.value = '销售出库详情'
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
  ruleFormData.value = { status: '待出库', source: '销售' }
  tableReturnList.value = []
  isSaveOrEdit.value = false
  dialogVisible.value = false
}

// 表格弹框提交
const handleDialogSubmit = async () => {
  const params = {
    ...ruleFormData.value,
    orderOutboundItemList: tableReturnList.value
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
  queryNoSaleTableList(params)
    .then((res) => {
      const { list, total } = res || {}
      pagination.total = total
      tableDataList.value = list
    })
    .finally(() => {
      tableLoading.value = false
    })
}

// 获取销售单号列表数据
const getClientSaleTableList = () => {
  queryClientSaleTableList({}).then((res) => {
    purchaseDataList.value = res
  })
}

// 初始生成出库单号
const getWareHouseCode = () => {
  getCreateCode('OUTBOUND_CODE').then((res) => {
    ruleFormData.value.number = res
  })
}

// 初始化数据
const info = () => {
  getTableList()
  getClientSaleTableList()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss">
.input-wrapper {
  display: flex;
  align-items: center;
  /* 可以根据需求设置整体样式，比如宽度、边距等 */
}

.label {
  flex: 0 0 auto; /* 左侧文字不拉伸，根据内容自适应宽度 */
  margin-right: 10px; /* 可以根据需求设置左侧文字与输入框的间距 */
  /* 其它样式，比如颜色、字体大小等 */
}

.input-field {
  flex: 1; /* 右侧输入框充满剩余空间 */
  /* 其它样式，比如边框、边距等 */
}
</style>
