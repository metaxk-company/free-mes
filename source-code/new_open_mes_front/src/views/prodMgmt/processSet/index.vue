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
        v-for="(item, index) in btnConditions"
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
      :loading="loading"
      :isSelection="true"
      :columns="tableColumns"
      :tableData="tableFormList"
      :pagination="pagination"
      @selection-change="handleSelectionChange"
      @pagination-change="handlePagination"
    >
      <template #processCode="{ scope }">
        <el-button link type="primary" @click="handleReadProcessInfo(scope.row.id)">{{
          scope.row.processCode
        }}</el-button>
      </template>
      <!--   <el-tag :type="scope.row.enableFlag === STATE_YES ? 'success' : 'warning'">{{
          STATE_TYPE[scope.row.enableFlag]
        }}</el-tag> -->
      <template #enableFlag="{ scope }">
        <el-tag :type="scope.row.enableFlag === 'Y' ? 'success' : 'warning'">{{
          UN_KNOW_STATE[scope.row.enableFlag]
        }}</el-tag>
      </template>

      <template #operation="{ scope }">
        <el-button link type="primary" @click="handleTableUpDate(scope.row)">修改</el-button>
        <el-button link type="danger" @click="handleDeleteTable(scope.row.id)">删除</el-button>
      </template>
    </common-table>
  </ContentWrap>

  <XModal width="65%" v-model="dialogVisible" :title="dialogTitle" @before-close="handleClose">
    <el-form ref="ruleFormRef" :model="processForm" :rules="rules" label-width="100px">
      <el-row>
        <el-col :span="8">
          <el-form-item label="工序编码" prop="processCode">
            {{ processForm.processCode }}
            <!-- <el-input
              v-model="processForm.processCode"
              :disabled="isView"
              placeholder="请输入工序编码"
            /> -->
          </el-form-item>
        </el-col>
        <!-- <el-col :span="7" v-if="!isView">
          <el-form-item label="自动生成" prop="autoGenFlag">
            <el-switch
              v-model="processForm.autoGenFlag"
              active-color="#13ce66"
              @change="handleAutoGenChange(processForm.autoGenFlag)"
            />
          </el-form-item>
        </el-col> -->
        <el-col :span="8">
          <el-form-item label="工序名称" prop="processName">
            <el-input
              v-model="processForm.processName"
              :disabled="isView"
              placeholder="请输入工序名称"
            />
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item label="是否启用" prop="enableFlag">
            <el-radio-group v-model="processForm.enableFlag" :disabled="isView">
              <el-radio :label="item.value" v-for="(item, index) in UN_KNOW_ENABLE" :key="index">{{
                item.label
              }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <!-- <el-col :span="8">
          <el-form-item label="工时" prop="manHour">
            <el-input-number v-model="processForm.manHour" :min="0" :max="10" />
          </el-form-item>
        </el-col> -->
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="准备时间" prop="preparationTime">
            <el-input v-model="processForm.preparationTime" placeholder="准备时间(分)" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="生产时间" prop="productiveTime">
            <el-input v-model="processForm.productiveTime" placeholder="生产时间(分)" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="工序说明" prop="attention">
            <el-input :disabled="isView" v-model="processForm.attention" placeholder="请输入内容" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="是否质检" prop="qualityInspection">
            <el-radio-group v-model="processForm.qualityInspection" :disabled="isView">
              <el-radio :label="item.value" v-for="(item, index) in UN_KNOW_ENABLE" :key="index">{{
                item.label
              }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row />
      <el-row>
        <el-col :span="8">
          <el-form-item label="备注" prop="remark">
            <el-input
              :disabled="isView"
              autosize
              v-model="processForm.remark"
              type="textarea"
              placeholder="请输入内容"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button v-if="!isView" type="primary" @click="handleSubmit"> 提交 </el-button>
        <el-button @click="handleClose">返回</el-button>
      </span>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, nextTick } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, btnConditions, tableColumns } from './data'
import {
  queryTableList,
  addProcessData,
  queryProcessInfo,
  updateProcessInfo,
  deleteProcessInfo,
  downloadListData
} from '@/api/masterData/processSet'
import type { FormInstance, FormRules } from 'element-plus'
import { UN_KNOW_ENABLE, UN_KNOW_STATE } from '@/utils/const'
import { createWordOrder } from '@/api/prodMgmt/pmOrder'
import { ElMessage, ElMessageBox } from 'element-plus'
import download from '@/utils/download'

const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  processCode: [{ required: true, message: '工序编码不能为空', trigger: 'blur' }],
  processName: [{ required: true, message: '工序名称不能为空', trigger: 'blur' }],
  enableFlag: [{ required: true, message: '是否启用不能为空', trigger: 'blur' }],
  preparationTime: [{ required: true, message: '准备时间不能为空', trigger: 'blur' }],
  productiveTime: [{ required: true, message: '生产时间不能为空', trigger: 'blur' }]
})
const searchModel = reactive({
  processCode: '',
  processName: '',
  enableFlag: ''
})
// 表格分页
let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const loading = ref<boolean>(false)
const dialogVisible = ref<boolean>(false)
let tableFormList = reactive([])
let dialogTitle = ref<string>('')
let selectionID = ref([])
let processForm = ref({
  processCode: '',
  // manHour: 0,
  processName: '',
  enableFlag: 'Y',
  autoGenFlag: false,
  attention: '',
  remark: '',
  preparationTime: '',
  productiveTime: '',
  qualityInspection: 'N',
  id: ''
})

// 搜索功能
const handleQueryData = () => {
  getTableListInfo()
}

// 按钮点击功能
const handleBtnOperation = (value) => {
  switch (value) {
    case 'save':
      addProcessForm()
      break
    case 'edit':
      handleTableUpDate()
      break
    case 'remove':
      handleDeleteTable()
      break
    case 'download':
      handleDownload()
      break
  }
}

// 导出功能
const handleDownload = async () => {
  const data = await downloadListData()
  download.excel(data, '工序设置.xls')
}

// 表格选择事件
const handleSelectionChange = (value) => {
  selectionID.value = value.map((item) => item?.id)
  btnConditions[1].disabled = !selectionID.value.length || selectionID.value.length >= 2
  btnConditions[2].disabled = !selectionID.value.length
}
// 新增
const addProcessForm = () => {
  dialogTitle.value = '添加生产工序'
  handleAutoGenChange()
  dialogVisible.value = true
}

// 修改
const handleTableUpDate = (value?) => {
  dialogTitle.value = '修改生产工序'
  const dataID = value?.id || selectionID.value[0]
  queryProcessInfoAPI(dataID)
}

// 删除
const handleDeleteTable = (value?) => {
  const id = value || selectionID.value
  const deleteID = Array.isArray(id) ? id : [id]
  ElMessageBox.confirm('是否删除当前数据?').then(() => {
    deleteProcessInfo(deleteID).then((res) => {
      ElMessage.success('删除成功')
      getTableListInfo()
    })
  })
}

const isView = ref(false)

// 查看
const handleReadProcessInfo = (value) => {
  dialogTitle.value = '查看生产工序'
  isView.value = true
  queryProcessInfoAPI(value)
}

// 获取工序详情数据
const queryProcessInfoAPI = (value) => {
  queryProcessInfo(value).then((res) => {
    processForm.value = res
    dialogVisible.value = true
  })
}

// 表格分页事件
const handlePagination = (value) => {
  pagination = value?.value
  getTableListInfo()
}

// 自动生成
const handleAutoGenChange = () => {
  createWordOrder('PROCESS_CODE').then((res) => {
    nextTick(() => {
      processForm.value.processCode = res
    })
  })
}

// 表单提交
const handleSubmit = async () => {
  if (!ruleFormRef.value) return
  await ruleFormRef.value.validate((valid) => {
    if (valid) {
      if (processForm.value.id) {
        updateProcessInfo(processForm.value).then((res) => {
          ElMessage.success('修改成功')
          handleClose()
          getTableListInfo()
        })
      } else {
        addProcessData(processForm.value).then((res) => {
          ElMessage.success('新增成功')
          handleClose()
          getTableListInfo()
        })
      }
    }
  })
}

// 表单关闭
const handleClose = () => {
  processForm.value = {
    processCode: '',
    // manHour: 0,
    processName: '',
    enableFlag: 'Y',
    autoGenFlag: false,
    attention: '',
    remark: '',
    id: ''
  }
  isView.value = false
  dialogVisible.value = false
}

// 初始化获取表格数据
const getTableListInfo = () => {
  const params = {
    ...searchModel,
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize
  }
  loading.value = true
  queryTableList(params)
    .then((res) => {
      const { total, list = [] } = res || {}
      pagination.total = total
      tableFormList = list
    })
    .finally(() => {
      loading.value = false
    })
}

const info = () => {
  getTableListInfo()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
