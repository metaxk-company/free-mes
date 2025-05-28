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
        v-for="(item, index) in btnConditions.slice(0, 3)"
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
      isSelection
      :columns="tableColumns"
      :tableData="tableDataList"
      :pagination="pagination"
      @pagination-change="handlePagination"
      @selection-change="handleSelectionChange"
    >
      <template #method="{ scope }">
        {{ MARRY_INSPECTION_METHOD[scope.row.method] }}
      </template>
      <template #enableFlag="{ scope }">
        {{ scope.row.enableFlag == 'true' ? '是' : '否' }}
      </template>
      <template #operation="{ scope }">
        <el-button type="primary" @click="handleOpenDetailsData(scope.row)">详情</el-button>
        <el-button type="success" @click="handleOpenEdit(scope.row)">修改</el-button>
        <el-button type="danger" @click="handleOpenRemove(scope.row)">删除</el-button>
      </template>
    </common-table>
  </ContentWrap>
  <XModal
    v-model="dialogVisible"
    width="60%"
    :title="dialogTitle"
    @close="handleCloseDialogVisible"
  >
    <el-divider content-position="left">基础条件</el-divider>
    <el-form ref="ruleFormRef" :model="ruleFormData" :rules="rules" label-width="90px" status-icon>
      <el-row>
        <el-col :span="8">
          <el-form-item label="检验编码" :prop="isShowView ? '' : 'number'">
            {{ ruleFormData.number }}
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="检验名称" :prop="isShowView ? '' : 'name'">
            <el-input
              v-model="ruleFormData.name"
              :disabled="isShowView"
              placeholder="请输入检验名称"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="检验版本" :prop="isShowView ? '' : 'version'">
            <el-input
              v-model="ruleFormData.version"
              :disabled="isShowView"
              placeholder="请输入检验版本"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="检验方式" :prop="isShowView ? '' : 'method'">
            <el-select
              :disabled="isShowView"
              v-model="ruleFormData.method"
              placeholder="请选择检验方式"
            >
              <el-option
                v-for="item in INSPECTION_METHOD"
                :key="item.value"
                :label="item.label"
                :value="item.label"
              />
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="8">
          <el-form-item label="是否启用" :prop="isShowView ? '' : 'enableFlag'">
            <el-switch v-model="ruleFormData.enableFlag" :disabled="isShowView" />
          </el-form-item>
        </el-col>
        <el-col :span="8" v-if="ruleFormData?.method != '全检'">
          <el-form-item label="抽检数量" :prop="isShowView ? '' : 'quantity'">
            <el-input-number
              :min="1"
              v-model="ruleFormData.quantity"
              :disabled="isShowView"
              placeholder="请输入抽检数量"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-divider content-position="left">其他信息</el-divider>
      <el-row>
        <el-col :span="8">
          <el-form-item label="线别" :prop="isShowView ? '' : 'lineTypeList'">
            <el-select-v2
              :disabled="isShowView"
              v-model="ruleFormData.lineTypeList"
              :options="lintList"
              placeholder="请选择线别"
              style="width: 300px"
              filterable
              clearable
              multiple
              :multiple-limit="3"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="型号" :prop="isShowView ? '' : 'modelList'">
            <el-select-v2
              :disabled="isShowView"
              v-model="ruleFormData.modelList"
              :options="modelList"
              placeholder="请选择型号"
              style="width: 300px"
              filterable
              clearable
              multiple
              :multiple-limit="3"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="颜色" :prop="isShowView ? '' : 'colorList'">
            <el-select-v2
              :disabled="isShowView"
              v-model="ruleFormData.colorList"
              :options="colorList"
              placeholder="请选择颜色"
              style="width: 300px"
              filterable
              clearable
              multiple
              :multiple-limit="3"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="规格" :prop="isShowView ? '' : 'specList'">
            <el-select-v2
              :disabled="isShowView"
              v-model="ruleFormData.specList"
              :options="specList"
              placeholder="请选择规格"
              style="width: 300px"
              filterable
              clearable
              multiple
              :multiple-limit="3"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="盘号" :prop="isShowView ? '' : 'reelNumberList'">
            <el-select-v2
              :disabled="isShowView"
              v-model="ruleFormData.reelNumberList"
              :options="reelList"
              placeholder="请选择盘号"
              style="width: 300px"
              filterable
              clearable
              multiple
              :multiple-limit="3"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-divider content-position="left">检测项目</el-divider>
    <testingItemsTable
      :inspect-code="ruleFormData.number"
      :table-testing-items-data-list="tableTestingItemsDataList"
      :is-show-view="isShowView"
    />
    <template #footer>
      <el-button type="primary" @click="handleDialogSubmit" v-if="!isShowView">提交</el-button>
      <el-button @click="handleCloseDialogVisible">取消</el-button>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { ref, onMounted, reactive } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, tableColumns } from './data'
import { btnConditions, INSPECTION_METHOD, MARRY_INSPECTION_METHOD } from '@/utils/const'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessageBox, ElMessage } from 'element-plus'
import testingItemsTable from './components/testing-items-table.vue'
import { queryTreeSelect } from '@/api/masterData/materialManage'
import { getTestStandardCode } from '@/api/inspectionStandards/processInspection'
import {
  getLineTypeList,
  getModelList,
  getSpecList,
  getReelNumberList,
  getColorList,
  inspectionStandard,
  getTestStandardList,
  queryTestStandardInfo,
  upDateTestStandardInfo,
  deleteTestStandardInfo
} from '@/api/inspectionStandards/otherInspection'
const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  number: [{ required: true, message: '检验编码不能为空', trigger: 'blur' }],
  name: [{ required: true, message: '检验名称不能为空', trigger: 'blur' }],
  method: [{ required: true, message: '检验方式不能为空', trigger: 'blur' }],
  productType: [{ required: true, message: '产品分类不能为空', trigger: 'blur' }],
  version: [{ required: true, message: '检验版本不能为空', trigger: 'blur' }],
  processCodeList: [{ required: true, message: '来料编号不能为空', trigger: 'blur' }],
  quantity: [{ required: true, message: '检测数量不能小于1', trigger: 'blur' }],
  lineTypeList: [{ required: true, message: '线别不能为空', trigger: 'change' }],
  modelList: [{ required: true, message: '型号不能为空', trigger: 'change' }],
  specList: [{ required: true, message: '规格不能为空', trigger: 'change' }]
})

// 搜索内容值
const searchModel = ref({
  number: ''
})
const tableLoading = ref(false) // 表格加载
const tableDataList = ref([]) // 表格数据
const lintList = ref([]) //线别
const modelList = ref([]) //型号
const specList = ref([]) //规格
const reelList = ref([]) //盘号
const colorList = ref([]) //颜色
const tableTestingItemsDataList = ref([
  // { itemName: '', itemStandard: '', itemDevice: '', remark: '' }
])
const getSelectionData = ref()
let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const dialogVisible = ref(false) // 弹框显示
const ruleFormData = ref<any>({
  quantity: 1,
  enableFlag: false
}) // 表单数据
const isShowView = ref(false) // 是否是查看状态
const dialogTitle = ref('')
const treeDataSelect = ref() // 产品分类树形下拉参数

// 搜索功能
const handleQueryData = () => {
  getTableList()
  searchModel.value = {
    number: ''
  }
}

// 查询详情信息
const getTestStandardInfo = async (value) => {
  await queryTestStandardInfo(value).then((res) => {
    res.otherStandardVoList == 'true' ? true : false
    ruleFormData.value = res
    ruleFormData.value.enableFlag = JSON.parse(res.enableFlag)
    ruleFormData.value.productType = JSON.parse(res.productType)
    tableTestingItemsDataList.value = res.otherStandardVoList
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
  }
}

// 按钮新增功能
const handleOpenSave = () => {
  dialogTitle.value = '新增'
  dialogVisible.value = true
  handleCreateCode()
}

// 按钮/表格 - 修改功能
const handleOpenEdit = async (value?) => {
  dialogTitle.value = '修改'
  const getId = value ? value?.id : getSelectionData.value[0].id
  await getTestStandardInfo(getId)
  dialogVisible.value = true
}

// 按钮/表格 - 删除功能
const handleOpenRemove = (value?) => {
  const getId = value ? [value?.id] : getSelectionData.value.map((item) => item.id)
  ElMessageBox.confirm('是否删除当前数据?').then(() => {
    deleteTestStandardInfo(getId).then(() => {
      ElMessage({
        message: '删除成功',
        type: 'success'
      })
      getTableList()
    })
  })
}
// 线别
const getLineType = () => {
  getLineTypeList().then((res) => {
    const lintTypeData = res.map((item) => {
      return {
        value: item.label,
        label: item.label
      }
    })
    lintList.value = lintTypeData
  })
}
//型号
const getModel = () => {
  getModelList().then((res) => {
    const modelTypeData = res.map((item) => {
      return {
        value: item.number,
        label: item.name
      }
    })
    modelList.value = modelTypeData
  })
}
//规格
const getSpec = () => {
  getSpecList().then((res) => {
    const specTypeData = res.map((item) => {
      return {
        value: item.name,
        label: item.name
      }
    })
    specList.value = specTypeData
  })
}
//盘号
const getReel = () => {
  getReelNumberList().then((res) => {
    const ReelData = res.map((item) => {
      return {
        value: item.number,
        label: item.number
      }
    })
    reelList.value = ReelData
  })
}
//颜色
const getColor = () => {
  getColorList().then((res) => {
    const colorData = res.map((item) => {
      return {
        value: item.name,
        label: item.name
      }
    })
    colorList.value = colorData
  })
}
// 生成工序标准编码
const handleCreateCode = () => {
  getTestStandardCode('OTHER_RECORD_CODE').then((res) => {
    ruleFormData.value.number = res
  })
}

// 表格详情功能
const handleOpenDetailsData = async (value) => {
  await getTestStandardInfo(value.id)
  dialogTitle.value = '详情'
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
    enableFlag: false,
    quantity: 1
  }
  tableTestingItemsDataList.value = [
    // { itemName: '', itemStandard: '', itemDevice: '', remark: '' }
  ]
}

// 表格弹框提交
const handleDialogSubmit = async () => {
  const params = {
    ...ruleFormData.value,
    productType:
      typeof ruleFormData.value.productType === 'number'
        ? ruleFormData.value.productType.toString()
        : ruleFormData.value.productType?.slice(-1).toString(),
    otherStandardVoList: tableTestingItemsDataList.value
  }

  if (!ruleFormRef.value) return
  await ruleFormRef.value.validate((valid) => {
    if (!valid) return
    if (!ruleFormData.value.id) {
      inspectionStandard(params).then((res) => {
        ElMessage({
          message: '新增成功',
          type: 'success'
        })
        handleCloseDialogVisible()
        getTableList()
      })
    } else {
      upDateTestStandardInfo(params).then((res) => {
        ElMessage({
          message: '修改成功',
          type: 'success'
        })
        handleCloseDialogVisible()
        getTableList()
      })
    }
  })
}

// 查询分类下拉树结构
const queryTreeSelectAPI = () => {
  queryTreeSelect().then((res) => {
    treeDataSelect.value = res
  })
}

const getTableList = () => {
  const params = {
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize,
    ...searchModel.value
  }
  tableLoading.value = true
  getTestStandardList(params)
    .then((res) => {
      const { list, total } = res || {}
      tableDataList.value = list
      pagination.total = total
    })
    .finally(() => {
      tableLoading.value = false
    })
}

// 初始化数据
const info = () => {
  queryTreeSelectAPI()
  getTableList()
  getModel() //型号
  getLineType() //线别
  getSpec() //规格
  getReel() //盘号
  getColor() //颜色
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
