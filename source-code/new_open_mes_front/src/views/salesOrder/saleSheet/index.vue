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
        <el-button link type="primary" @click="handleOpenDetailsData(scope.row)">{{
          scope.row.number
        }}</el-button>
      </template>
      <template #operation="{ scope }">
        <el-button
          type="success"
          v-if="scope.row.status == '未完成'"
          @click="handleOpenEdit(scope.row)"
          size="small"
          >修改</el-button
        >
        <el-button
          type="warning"
          v-if="scope.row.status == '未完成'"
          @click="handleFinish(scope.row)"
          size="small"
          >完成</el-button
        >

        <el-button type="primary" @click="handleQueryProgress(scope.row)" size="small"
          >进度</el-button
        >

        <el-button
          type="danger"
          v-if="scope.row.status == '未完成'"
          @click="handleOpenRemove(scope.row)"
          size="small"
          >删除</el-button
        >
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
          <el-form-item label="订单编号" prop="number">
            {{ ruleFormData.number }}
          </el-form-item>
        </el-col>
        <!-- <el-col :span="6" v-if="!isShowView">
          <el-form-item label="自动生成">
            <el-switch v-model="ruleFormData.createCode" @change="handleCreateCode" />
          </el-form-item>
        </el-col> -->
        <el-col :span="6">
          <el-form-item label="客户名称" prop="customerName">
            <el-select
              v-if="!isShowView"
              placeholder="请选择客户名称"
              filterable
              v-model="ruleFormData.customerName"
              @change="handleSelectClient"
              :disabled="customerDisable"
            >
              <el-option
                v-for="item in clientList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
            <div v-else>{{ ruleFormData.customerName }}</div>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="客户订单号" prop="customerOrderNumber">
            <el-input v-model="ruleFormData.customerOrderNumber" :disabled="isShowView" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="收货地址">
            <el-input v-model="ruleFormData.address" :disabled="isShowView" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="订货日期">
            <el-date-picker
              :disabled="isShowView"
              v-model="ruleFormData.orderDate"
              type="date"
              placeholder="请选择订货日期"
              value-format="YYYY-MM-DD"
              format="YYYY-MM-DD"
              :disabled-date="disableTime"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="交货日期">
            <el-date-picker
              :disabled="isShowView"
              v-model="ruleFormData.deliveryDate"
              type="date"
              placeholder="请选择交货日期"
              value-format="YYYY-MM-DD"
              :default-value="new Date()"
              format="YYYY-MM-DD"
              :default-time="new Date()"
              :disabled-date="disableTime"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="备注">
            <el-input v-model="ruleFormData.remark" :disabled="isShowView" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-tabs v-model="ruleFormData.priceModel">
        <el-tab-pane
          label="重量"
          name="weight"
          :disabled="isShowView || Boolean(ruleFormData.id)"
        />
        <el-tab-pane
          label="件数"
          name="pieces"
          :disabled="isShowView || Boolean(ruleFormData.id)"
        />
        <el-row v-if="ruleFormData.priceModel == 'weight'">
          <el-col :span="6">
            <el-form-item label="价格模式">
              <el-radio-group
                v-model="ruleFormData.priceMode"
                :disabled="isShowView || Boolean(ruleFormData.id)"
              >
                <el-radio label="Process">加工费</el-radio>
                <el-radio label="Product">成品价</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="ruleFormData.priceModel == 'weight'">
          <el-col :span="8" v-if="ruleFormData.priceMode == 'Process'">
            <el-form-item label="铜价" prop="copperPrice">
              <el-select
                v-if="!isShowView"
                placeholder="请选择铜价"
                clearable
                v-model="ruleFormData.copperPrice"
              >
                <el-option
                  v-for="item in copperPriceList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
              <div v-else>{{ ruleFormData.copperPriceTwo }}</div>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="6" v-if="ruleFormData.priceMode == 'Process'">
            <el-form-item label="铜价税率" prop="copperTaxRate">
              <el-select
                placeholder="请选择铜价税率"
                :disabled="isShowView"
                v-model="ruleFormData.copperTaxRate"
              >
                <el-option
                  v-for="item in COPPER_TAXRATE_DATA"
                  :key="item.value"
                  :label="item.label + '%'"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col> -->
          <el-col :span="8" v-if="ruleFormData.priceMode == 'Process'">
            <el-form-item label="铝价" prop="aluminiumPrice">
              <el-select
                v-if="!isShowView"
                placeholder="请选择铝价"
                clearable
                v-model="ruleFormData.aluminiumPrice"
              >
                <el-option
                  v-for="item in aluminiumPriceList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
              <div v-else>{{ ruleFormData.aluminiumPriceTwo }}</div>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="6" v-if="ruleFormData.priceMode == 'Process'">
            <el-form-item label="铝价税率" prop="aluminiumTaxRate">
              <el-select
                placeholder="请选择铝价税率"
                :disabled="isShowView"
                v-model="ruleFormData.aluminiumTaxRate"
              >
                <el-option
                  v-for="item in AI_TAXRATE_DATA"


                  :key="item.value"
                  :label="item.label + '%'"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col> -->
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="是否含税" prop="isTax">
              <el-radio-group v-model="ruleFormData.isTax" v-if="!isShowView">
                <el-radio label="Y">是</el-radio>
                <el-radio label="N">否</el-radio>
              </el-radio-group>
              <div v-else>{{ ruleFormData.isTax == 'Y' ? '是' : '否' }}</div>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="含税">
              {{ (ruleFormData.includeTax = getWithFax) }} 元
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="不含税">
              {{ (ruleFormData.noIncludeTax = getWitHoutFax) }}元
            </el-form-item>
          </el-col>
        </el-row>
        <returnGoodsTable
          :is-show-view="isShowView"
          ref="returnGoodsRef"
          :is-customer-name="ruleFormData.customerName"
          :table-return-list="tableReturnList"
          :form-data-props="ruleFormData"
          @up-date-table-return-list="handleUpDateTableReturnList"
        />
      </el-tabs>
    </el-form>

    <template #footer>
      <el-button type="primary" @click="handleDialogSubmit" v-if="!isShowView">提交</el-button>
      <el-button @click="handleCloseDialogVisible">取消</el-button>
    </template>
  </XModal>
  <XModal v-model="progressDialog" title="查看当前进度" width="90%">
    <common-search
      :conditions-list="progressSearch"
      :search-model="progressSearchModel"
      @query-data="handleQueryProgressData"
      style="margin-bottom: 20px"
    />
    <common-table
      :loading="progressLoading"
      :columns="tableProgressColumns"
      :tableData="tableProgressData"
      :pagination="paginationProgress"
      @pagination-change="handleProgressPagination"
    />
  </XModal>
</template>

<script setup lang="ts">
import download from '@/utils/download'
import { ref, onMounted, reactive, watch, computed } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, tableColumns, progressSearch, tableProgressColumns } from './data'
import { btnConditions } from '@/utils/const'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessageBox, ElMessage } from 'element-plus'
import {
  downloadListData,
  queryTableList,
  addFormData,
  queryFormInfo,
  updateFormInfo,
  deleteFormInfo,
  upDateOrderState,
  getMaterialDataList,
  getOrderSchedule,
  getPrintingData,
  queryClientAddress
} from '@/api/salesOrder/saleSheet'
import returnGoodsTable from './components/returnGoodsTable.vue'
import { getCreateCode } from '@/api/a_public_port'
import dayjs from 'dayjs'
import { getClientSelect } from '@/api/salesOrder/quotationList'

const returnGoodsRef = ref<any>(null)
const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  number: [{ required: true, message: '请输入订单编号', trigger: 'blur' }],
  customerOrderNumber: [{ required: true, message: '请输入客户订单号', trigger: 'blur' }],
  address: [{ required: true, message: '请输入收货地址', trigger: 'blur' }],
  customerName: [{ required: true, message: '请输入客户名称', trigger: 'blur' }],
  orderDate: [{ required: true, message: '请选择订货日期', trigger: 'blur' }],
  deliveryDate: [{ required: true, message: '请选择交货日期', trigger: 'blur' }],
  copperPrice: [{ required: true, message: '请选择铜价', trigger: 'blur' }],
  copperTaxRate: [{ required: true, message: '请选择铜价税率', trigger: 'blur' }],
  aluminiumPrice: [{ required: true, message: '请选择铝价', trigger: 'blur' }],
  aluminiumTaxRate: [{ required: true, message: '请选择铝价税率', trigger: 'blur' }],
  isTax: [{ required: true, message: '请选择是否含税', trigger: 'blur' }]
})

const clientList = ref<any>([])
// 搜索内容值
const searchModel = ref({
  saleNumber: '',
  customerName: '',
  status: ''
})
// 进度搜索内容值
const progressSearchModel = ref({
  model: '',
  spec: ''
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
let paginationProgress = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const dialogVisible = ref(false) // 弹框显示
const ruleFormData = ref<any>({
  priceMode: 'Process',
  isTax: '',
  priceModel: 'weight',
  orderDate: ref(dayjs().format().split('T')[0]),
  isDisable: false
}) // 表单数据
const isShowView = ref(false) // 是否是查看状态
const dialogTitle = ref('')
const tableReturnList = ref<any>([])
// 克隆已选数据
const copy_tableReturnList = ref<any>([])
// 铜价列表
const copperPriceList = ref<any>([])
// 铝价列表
const aluminiumPriceList = ref<any>([])
// 进度弹框
const progressDialog = ref<boolean>(false)
const progressLoading = ref<boolean>(false)
const tableProgressData = ref<any>([])
const progressSaleNumber = ref<any>({})
const disableTime = ref()
const customerDisable = ref(false) //客户名称选择
//时间限制
const disabledDate = (time: Date) => {
  const today = new Date()
  const yesterday = new Date(today)
  yesterday.setDate(today.getDate() - 1)
  const timestamp = yesterday.getTime() // 获取前一天的时间戳

  return time.getTime() < timestamp
}
// 监听价格模式选择的时候监听
watch(
  () => ruleFormData.value.priceMode,
  (value) => {
    if (value == 'Product') {
      // ruleFormData.value.aluminiumPrice = ''
      // ruleFormData.value.copperPrice = ''
      tableReturnList.value = tableReturnList.value.map((item: any) => {
        return {
          ...item,
          processingFee: 0,
          rawPrice: item.rawPrice ? item.rawPrice : 0,
          quantity: item.quantity ? item.quantity : 0
        }
      })
    } else {
      tableReturnList.value = tableReturnList.value.map((item: any) => {
        const copyItem = copy_tableReturnList.value.find(
          (copyValue) => copyValue.productNumber == item.productNumber
        )
        if (copyItem) {
          item.rawPrice = copyItem.processingFee
          item.processingFee = copyItem.processingFee
          item.quantity = copyItem.quantity ? copyItem.quantity : 0
        }

        const hasCopper = /铜/.test(item.lineType)
        const hasAL = /铝/.test(item.lineType)
        if (item.lineType === '铜包铝') {
          item.rawPrice = Number(sumTotal.value).toFixed(4)
        } else if (hasCopper) {
          item.rawPrice = Number(ruleFormData.value?.copperPrice || 0).toFixed(4)
        } else if (hasAL) {
          item.rawPrice = Number(ruleFormData.value?.aluminiumPrice || 0).toFixed(4)
        }
        return item // 需要返回更新后的 item
      })
    }
  }
)

// 监听铜价的计算
watch(
  () => (ruleFormData.value.copperPrice ? ruleFormData.value.copperPrice : ''),
  (newMetal) => {
    tableReturnList.value.forEach((item: { lineType: string; rawPrice: any }) => {
      const hasCopper = /铜/.test(item.lineType)
      if (item.lineType == '铜包铝') {
        item.rawPrice = Number(sumTotal.value).toFixed(4)
      } else if (hasCopper) {
        item.rawPrice = Number(newMetal) || 0.0
      }
    })
    ruleFormData.value.copperLabelName = copperPriceList.value.find(
      (item) => item.value == newMetal
    )?.label
  }
)

// 监听铝价功能
watch(
  () => (ruleFormData.value.aluminiumPrice ? ruleFormData.value.aluminiumPrice : ''),
  (newMetal) => {
    tableReturnList.value.forEach((item: { lineType: string; rawPrice: any }) => {
      const hasAL = /铝/.test(item.lineType)
      if (item.lineType == '铜包铝') {
        item.rawPrice = Number(sumTotal.value).toFixed(4)
      } else if (hasAL) {
        item.rawPrice = Number(newMetal) || 0.0
      }
    })
    ruleFormData.value.aluminiumName = aluminiumPriceList.value.find(
      (item) => item.value == newMetal
    )?.label
  }
)

// 铜税率监听
watch(
  () => ruleFormData.value.copperTaxRate,
  (newValue) => {
    const shiftCopperFax = newValue / 100
    tableReturnList.value.forEach((item: { lineType: string; totalPrice: number }) => {
      const hasCopper = /铜/.test(item.lineType)
      if (item.lineType == '铜包铝') {
        item.totalPrice = item.totalPrice + taxRate.value
      } else if (hasCopper) {
        item.totalPrice = item.totalPrice + shiftCopperFax
      }
    })
  }
)

// 监听Tabs切换
watch(
  () => ruleFormData.value.priceModel,
  (newValue) => {
    if (newValue == 'pieces') {
      tableReturnList.value.forEach((item) => {
        // 原材料价
        item.rawPrice = item.rawPrice ? item.rawPrice : 0
        // 加工费
        item.processingFee = 0
        item.unit = '件'
      })
    } else {
      tableReturnList.value = tableReturnList.value.map((item: any) => {
        const hasCopper = /铜/.test(item.lineType)
        const hasAL = /铝/.test(item.lineType)
        const copyItem = copy_tableReturnList.value.find(
          (copyValue) => copyValue.productNumber == item.productNumber
        )

        // 判断价格模式是否是 加工费还是成品价
        // Process：加工费
        // Product：成品价
        if (ruleFormData.value.priceMode != 'Product') {
          item.unit = copyItem.unit
          item.processingFee = copyItem.processingFee
          item.quantity = copyItem.quantity ? copyItem.quantity : 0
          if (item.lineType === '铜包铝') {
            item.rawPrice = Number(sumTotal.value).toFixed(4)
          } else if (hasCopper) {
            item.rawPrice = Number(ruleFormData.value?.copperPrice || 0).toFixed(4)
          } else if (hasAL) {
            item.rawPrice = Number(ruleFormData.value?.aluminiumPrice || 0).toFixed(4)
          }
        } else {
          item.processingFee = 0
          item.quantity = 0
          item.rawPrice = 0
        }
        return item // 需要返回更新后的 item
      })
    }
  }
)

// 原有功能监视是否含税的变化来加上含税的值
// watch(
//   () => ruleFormData.value.isTax,
//   (value) => {
//     if (value) {
//       if (value == 'Y') {
//         ruleFormData.value.includeTax = tableReturnList.value.reduce(
//           (totalQuantity, item) => totalQuantity + Number(item.totalPrice),
//           0
//         )
//       } else {
//         ruleFormData.value.noIncludeTax = tableReturnList.value.reduce(
//           (totalQuantity, item) => totalQuantity + Number(item.totalPrice),
//           0
//         )
//       }
//     }
//   }
// )

// 选择客户名称带出收货地址
const handleSelectClient = (value) => {
  queryClientAddress({
    customerNumber: value
  }).then((res) => {
    ruleFormData.value.address = res.address
    // 客户订单号
    ruleFormData.value.customerOrderNumber = res.orderNumber
    tableReturnList.value = []
  })
}

const handlePrinting = async (value) => {
  if (value.includeTax !== 0 || value.noIncludeTax !== 0) {
    value.isDisable = false
    let data
    const params = {
      number: value.number,
      type: 'pdf'
    }
    data = await getPrintingData(params).then((res) => res)
    let pnf = new Blob([data], { type: 'application/pdf;charset=utf-8' })
    window.open(window.URL.createObjectURL(pnf))
  } else {
    value.isDisable = true
    ElMessage({ message: '金额为0,不支持打印', type: 'warning' })
  }
}

// 进度分页功能
const handleProgressPagination = (value) => {
  paginationProgress = value.value
  handleQueryProgress(progressSaleNumber.value)
}

// 进度搜索
const handleQueryProgressData = () => {
  handleQueryProgress(progressSaleNumber.value)
}

// 查看进度
const handleQueryProgress = (value?) => {
  progressSaleNumber.value = value
  progressLoading.value = true
  progressDialog.value = true
  const params = {
    ...progressSearchModel.value,
    number: progressSaleNumber.value.number,
    pageNo: paginationProgress.currentPage,
    pageSize: paginationProgress.pageSize
  }

  getOrderSchedule(params)
    .then((res) => {
      const { list, total } = res || {}
      tableProgressData.value = list
      paginationProgress.total = total
    })
    .finally(() => {
      progressLoading.value = false
    })
}

// 完成当前订单
const handleFinish = (value) => {
  upDateOrderState(value.id).then((res) => {
    getTableList()
  })
}

// 含税总价
const getWithFax = computed(() => {
  if (ruleFormData.value.isTax == 'Y') {
    return parseFloat(
      (
        (tableReturnList.value.reduce(
          (totalQuantity, item) => totalQuantity + Number(item.totalPrice),
          0
        ) *
          1000) /
        1000
      ).toFixed(4)
    )
  } else {
    return 0
  }
})

// 不含税总价
const getWitHoutFax = computed(() => {
  if (ruleFormData.value.isTax == 'Y') {
    return 0
  } else {
    return parseFloat(
      (
        (tableReturnList.value.reduce((totalQuantity, item) => {
          return totalQuantity + Number(item.totalPrice)
        }, 0) *
          1000) /
        1000
      ).toFixed(4)
    )
  }
})

const sumTotal = computed((): any => {
  // 获取铜的价格
  const copperPrice = Number(ruleFormData.value.copperPrice) * 0.3 || 0
  // 获取铝的价格
  const aluminiumPrice = Number(ruleFormData.value.aluminiumPrice) * 0.7 || 0
  return (copperPrice * 1000 + aluminiumPrice * 1000) / 1000
  // return copperPrice + aluminiumPrice
})

// 税率总价计算
const taxRate = computed(() => {
  // 铜
  const copperTaxRate = ruleFormData.value.copperTaxRate || 0
  // 铝
  const aluminiumTaxRate = ruleFormData.value.aluminiumTaxRate || 0
  return (copperTaxRate + aluminiumTaxRate) / 100
})

// 产品信息提交时候进行一个对比以及取值操作
const handleUpDateTableReturnList = (value: any[]) => {
  copy_tableReturnList.value.push(...value)
  const idExists = value.some((data) => {
    return tableReturnList.value.some((item2) => data?.productNumber == item2.productNumber)
  })
  // if (!idExists) {
  const tableList = value.map((item: any) => {
    const hasCopper = /铜/.test(item.lineType)
    const hasAL = /铝/.test(item.lineType)
    if (item.lineType === '铜包铝') {
      item.rawPrice = Number(sumTotal.value.toFixed(4)) || 0
    } else if (hasCopper) {
      item.rawPrice = Number(((ruleFormData.value.copperPrice * 1000) / 1000).toFixed(4)) || 0
    } else if (hasAL) {
      item.rawPrice = Number(((ruleFormData.value.aluminiumPrice * 1000) / 1000).toFixed(4)) || 0
    }
    return {
      ...item,
      processingFee:
        ruleFormData.value.priceMode == 'Product' || ruleFormData.value.priceModel == 'pieces'
          ? 0
          : item.processingFee,
      price: Number(item.processingFee) + Number(item.rawPrice),
      quantity:
        ruleFormData.value.priceMode == 'Product' || ruleFormData.value.priceModel == 'pieces'
          ? 0
          : item.quantity
          ? item.quantity
          : 0,
      rawPrice:
        ruleFormData.value.priceModel == 'pieces' || ruleFormData.value.priceMode == 'Product'
          ? 0
          : item.rawPrice,
      totalPrice: 0,
      // 件数
      pieces: 0,
      panhao: '',
      color: ''
    }
  })
  tableReturnList.value.push(...tableList)
  // } else {
  //   ElMessage({ message: '不能添加重复数据！', type: 'warning' })
  // }
}

// 生成检验编号
const handleCreateCode = () => {
  getCreateCode('ORDER_SALE_CODE').then((res) => {
    ruleFormData.value.number = res
  })
}

// 搜索功能
const handleQueryData = () => {
  getTableList()
}

// 获取详情接口
const getTestWayInfo = async (value: any) => {
  await queryFormInfo(value).then((res: any) => {
    ruleFormData.value = res
    tableReturnList.value = res.itemList
    // console.log(tableReturnList.value, '12312')
    // copy_tableReturnList.value = res.itemList
  })
}

// 按钮集合功能
const handleBtnOperation = (state: any) => {
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
  download.excel(data, '销售订单.xls')
}

// 按钮新增功能
const handleOpenSave = () => {
  dialogTitle.value = '新增销售订单'
  handleCreateCode()
  disableTime.value = disabledDate
  dialogVisible.value = true
}

// 按钮/表格 - 修改功能
const handleOpenEdit = async (value?: { id: any } | undefined) => {
  dialogTitle.value = '修改销售订单'
  const getId = value ? value?.id : getSelectionData.value[0].id
  await getTestWayInfo(getId)
  disableTime.value = null
  dialogVisible.value = true
  customerDisable.value = true
}

// 按钮/表格 - 删除功能
const handleOpenRemove = (value?: { id: any } | undefined) => {
  const getId = value ? [value?.id] : getSelectionData.value.map((item: { id: any }) => item.id)
  ElMessageBox.confirm('是否删除当前数据?').then(() => {
    deleteFormInfo(getId).then((res: any) => {
      ElMessage({ message: '删除成功', type: 'success' })
      getTableList()
    })
  })
}

// 表格详情功能
const handleOpenDetailsData = async (value: { id: any }) => {
  await getTestWayInfo(value.id)
  dialogTitle.value = '销售订单详情'
  isShowView.value = true
  dialogVisible.value = true
}

// 分页功能
const handlePagination = (value) => {
  pagination = value?.value
  getTableList()
}

// 表格选择事件
const handleSelectionChange = (value: any) => {
  getSelectionData.value = value
  btnConditions[1].disabled = !getSelectionData.value.length || getSelectionData.value.length >= 2
  btnConditions[2].disabled = !getSelectionData.value.length
}

// 表格弹框关闭
const handleCloseDialogVisible = () => {
  customerDisable.value = false
  dialogVisible.value = false
  isShowView.value = false
  ruleFormData.value = {
    priceMode: 'Process',
    priceModel: 'weight',
    orderDate: ref(dayjs().format().split('T')[0]),
    isDisable: false
  }
  tableReturnList.value = []
  copy_tableReturnList.value = []
}

// 表格弹框提交
const handleDialogSubmit = async () => {
  let calculateList = tableReturnList.value.map((item) => {
    return {
      ...item,
      price: Number(item.rawPrice) + Number(item.processingFee)
    }
  })
  const prams = {
    ...ruleFormData.value,
    itemList: calculateList
  }

  const allPropertiesNotEmpty = calculateList.every((item) => {
    return item?.panhao !== '' && item?.color !== ''
  })

  if (!ruleFormRef.value) return
  await ruleFormRef.value.validate((valid) => {
    if (!valid || !allPropertiesNotEmpty)
      return ElMessage({ message: '颜色,盘号不能为空', type: 'error' })
    if (!ruleFormData.value?.id) {
      addFormData(prams).then((res) => {
        ElMessage({ message: '新增成功', type: 'success' })
        handleCloseDialogVisible()
        getTableList()
      })
    } else {
      updateFormInfo(prams).then((res) => {
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

// 客户名称拉下参数
const getClientSelectListAPI = () => {
  getClientSelect().then((res) => {
    const clientListData = res.map((item: any) => {
      return {
        value: item.customerNumber,
        // value: item.customerName,
        label: item.customerName
      }
    })
    clientList.value = clientListData
    // searchConditions[1].options = clientListData
  })
}

// 获取铜数据
const getCuDataListAPI = () => {
  const params = {
    copper: '铜'
  }
  getMaterialDataList(params).then((res) => {
    copperPriceList.value = res
  })
}

// 获取铝数据
const getAluminiumDataListAPI = () => {
  const params = {
    copper: '铝'
  }
  getMaterialDataList(params).then((res) => {
    aluminiumPriceList.value = res
  })
}

// 初始化数据
const info = () => {
  getTableList()
  getClientSelectListAPI()
  getCuDataListAPI()
  getAluminiumDataListAPI()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss">
:deep .el-select {
  width: 120%;
}
</style>
