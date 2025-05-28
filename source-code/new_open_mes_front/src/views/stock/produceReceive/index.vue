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
        <el-button type="primary" @click="handleAffirm(scope.row)">确认同步</el-button>
        <el-button type="" @click="handleOpenDetailsData(scope.row)">详情</el-button>
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
    <el-form ref="ruleFormRef" :model="ruleFormData" :rules="rules" label-width="130px" status-icon>
      <el-row>
        <el-col :span="6">
          <el-form-item label="领料编号" :prop="isShowView ? '' : 'number'">
            {{ ruleFormData.number }}
            <!-- <el-input v-model="ruleFormData.number" :disabled="isShowView" /> -->
          </el-form-item>
        </el-col>
        <el-col :span="6">
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
        </el-col>
        <el-col :span="6">
          <el-form-item label="类型" :prop="isShowView ? '' : 'status'">
            {{ ruleFormData.status }}
            <!-- <el-select placeholder="请选择类型" disabled v-model="ruleFormData.status">
              <el-option
                v-for="item in PURCHASE_STATE"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select> -->
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
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
        <el-col :span="6">
          <el-form-item label="领料日期" :prop="isShowView ? '' : 'pickDate'">
            <el-date-picker
              clearable
              v-model="ruleFormData.pickDate"
              type="date"
              :disabled="isShowView"
              value-format="YYYY-MM-DD"
              placeholder="请选择领料日期"
              :disabled-date="disableTime"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="备注">
            <el-input v-model="ruleFormData.remark" :disabled="isShowView" />
          </el-form-item>
        </el-col>
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
    </el-form>
    <!-- 含税价 、不含税价..... -->
    <!-- <el-form ref="ruleFormRef" :model="ruleFormData" :rules="rules" label-width="130px" status-icon>
      <el-row>
        <el-col :span="6">
          <el-form-item label="领料编号" :prop="isShowView ? '' : 'number'">
            {{ ruleFormData.number }}
            <el-input v-model="ruleFormData.number" :disabled="isShowView" />
          </el-form-item>
        </el-col>

        <el-col :span="6" v-if="!isShowView">
          <el-form-item label="自动生成">
            <el-switch v-model="ruleFormData.createCode" @change="handleCreateCode" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
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
        </el-col>
        <el-col :span="6">
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
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-form-item label="领料日期" :prop="isShowView ? '' : 'pickDate'">
            <el-date-picker
              clearable
              :disabled="isShowView"
              v-model="ruleFormData.pickDate"
              type="date"
              value-format="YYYY-MM-DD"
              placeholder="请选择领料日期"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
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
        </el-col>
        <el-col :span="6">
          <el-form-item label="类型" :prop="isShowView ? '' : 'status'">
            {{ ruleFormData.status }}
            <el-select placeholder="请选择类型" disabled v-model="ruleFormData.status">
              <el-option
                v-for="item in PURCHASE_STATE"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-form-item label="备注">
            <el-input v-model="ruleFormData.remark" :disabled="isShowView" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="不含税总价（元）">
            <el-input v-model="ruleFormData.noTaxPrice" disabled />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="含税总价(元)">
            <el-input v-model="ruleFormData.taxPrice" disabled />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form> -->

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
</template>

<script setup lang="ts">
import download from '@/utils/download'
import { ref, onMounted, reactive } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
// import CurrentTimer from '@/components/CurrentTime/index.vue'
import { searchConditions, tableColumns } from './data'
import { btnConditions } from '@/utils/const'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessageBox, ElMessage } from 'element-plus'
import { syncProductPick } from '@/api/a_public_port'
import dayjs from 'dayjs'
import {
  queryTableList,
  getVendorSelectList,
  getCreateCode,
  downloadListData,
  productClassList,
  addFormData,
  updateFormInfo,
  queryFormInfo,
  deleteFormInfo
} from '@/api/stock/produceReceive'
import returnGoodsTable from './components/returnGoodsTable.vue'
import { warehouseList } from '@/api/storeHouse/warehouseModel'
const disableTime = ref()
//时间限制
const disabledDate = (time: Date) => {
  const today = new Date()
  const yesterday = new Date(today)
  yesterday.setDate(today.getDate() - 1)
  const timestamp = yesterday.getTime() // 获取前一天的时间戳

  return time.getTime() < timestamp
}
// 所属仓库下拉列表
const wareHousesSelectList = ref<any>([])
// 查询仓库接口
const getWareHouseList = () => {
  warehouseList().then((res) => {
    wareHousesSelectList.value = res.map((item) => {
      return {
        value: item.warehouseName,
        label: item.warehouseName
      }
    })
  })
}
const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  number: [{ required: true, message: '采购单号不能为空', trigger: 'blur' }],
  currency: [{ required: true, message: '币种类别不能为空', trigger: 'blur' }],
  address: [{ required: true, message: '收货地址不能为空', trigger: 'blur' }],
  vendorName: [{ required: true, message: '供应商名称不能为空', trigger: 'blur' }],
  taxRate: [{ required: true, message: '税率不能为空', trigger: 'blur' }],
  // pickDate: [{ required: true, message: '领料日期不能为空', trigger: 'blur' }],
  productType: [{ required: true, message: '产品类别不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '采购状态不能为空', trigger: 'blur' }],
  warehouse: [{ required: true, message: '仓库类型不能为空', trigger: 'blur' }]
})
// 搜索内容值
const searchModel = ref({
  // status: '',
  pickDate: '',
  number: ''
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
  taxRate: 0,
  status: '领料申请',
  pickDate: dayjs().format('YYYY-MM-DD HH-mm-ss').split(' ')[0]
}) // 表单数据
const isShowView = ref(false) // 是否是查看状态
const dialogTitle = ref('')
const tableReturnList = ref<any>([])
const vendorNameSelectList = ref<any>([])
const productClassSelectList = ref<any>([])

const handleGetSelectData = (value) => {
  tableReturnList.value.push(...value)
}
// 时间选择  默认显示当日
watch(
  () => ruleFormData.value.pickDate,
  (newdata) => {
    if (newdata) {
      ruleFormData.value.pickDate = newdata
    } else {
      ruleFormData.value.pickDate = dayjs().format('YYYY-MM-DD HH-mm-ss').split(' ')[0] //当天日期
    }
  }
)
// 搜索功能
const handleQueryData = () => {
  getTableList()
}

// 获取详情接口
const getTestWayInfo = async (value) => {
  await queryFormInfo(value).then((res) => {
    ruleFormData.value = res
    tableReturnList.value = res.productPickItemList
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
  download.excel(data, '生产领料.xls')
}

// 按钮新增功能
const handleOpenSave = () => {
  dialogTitle.value = '新增生产领料'
  handleCreateCode()
  dialogVisible.value = true
  // 时间限制
  disableTime.value = disabledDate
}

// 按钮/表格 - 修改功能
const handleOpenEdit = async (value?) => {
  dialogTitle.value = '修改生产领料'
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
// 确认同步
const handleAffirm = (value) => {
  const params = {
    id: value.id
  }
  syncProductPick(params).then((res) => {
    ElMessage({ message: res, type: 'success' })
  })
}
// 表格详情功能
const handleOpenDetailsData = async (value) => {
  await getTestWayInfo(value.id)
  dialogTitle.value = '生产领料详情'
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
  ruleFormData.value = { status: '领料申请' }
  tableReturnList.value = []
}

// 生成检验编号
const handleCreateCode = () => {
  getCreateCode('PRODUCT_PICK_CODE').then((res) => {
    ruleFormData.value.number = res
  })
}

// 表格弹框提交
const handleDialogSubmit = async () => {
  const params = {
    ...ruleFormData.value,
    productPickItemList: tableReturnList.value
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
  getVendorSelectList().then((res) => {
    const vendorNameList = res.list.map((item) => {
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
  getWareHouseList()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
