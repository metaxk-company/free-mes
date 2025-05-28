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
        v-hasPermi="[item.authority]"
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
      <template #enableFlag="{ scope }">
        <el-tag :type="scope.row.enableFlag === 'Y' ? 'success' : 'warning'">{{
          UN_KNOW_STATE[scope.row.enableFlag]
        }}</el-tag>
      </template>
      <template #primaryFlag="{ scope }">
        <el-tag :type="scope.row.primaryFlag === 'Y' ? 'success' : 'warning'">{{
          UN_KNOW_STATE[scope.row.primaryFlag]
        }}</el-tag>
      </template>

      <template #operation="{ scope }">
        <el-button style="font-size: 17px" link type="primary" @click="handleTableUpDate(scope.row)"
          >修改</el-button
        >
        <el-button
          style="font-size: 17px"
          link
          type="danger"
          @click="handleDeleteTable(scope.row.id)"
          >删除</el-button
        >
      </template>
    </common-table>
  </ContentWrap>

  <XModal width="50%" v-model="dialogVisible" :title="dialogTitle" @before-close="handleClose">
    <el-form ref="ruleFormRef" :model="processForm" :rules="rules" label-width="130px">
      <el-row>
        <!-- <el-col :span="8">
          <el-form-item label="单位编码" prop="id">
            <el-input v-model="processForm.id" />
          </el-form-item>
        </el-col> -->
        <el-col :span="11">
          <el-form-item label="单位名称" prop="measureName">
            <el-input v-model="processForm.measureName" />
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="换算比例" prop="changeRate">
            <el-input-number style="width: 198px" v-model="processForm.changeRate" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="11">
          <el-form-item label="启用主单位" prop="primaryFlag">
            <el-radio-group v-model="processForm.primaryFlag">
              <el-radio :label="item.value" v-for="(item, index) in UN_KNOW_ENABLE" :key="index">{{
                item.label
              }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="11" v-if="processForm.primaryFlag == 'Y'">
          <el-form-item label="主单位">
            <el-select v-model="processForm.primaryId" placeholder="请选择主单位">
              <el-option
                v-for="item in unitLits"
                :key="item.id"
                :label="item.measureName"
                :value="item.id"
                :disabled="item.enableFlag == 'N'"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <!-- <el-col :span="8">
          <el-form-item :span="8" label="是否启用" prop="enableFlag">
            <el-radio-group v-model="processForm.enableFlag">
              <el-radio :label="item.value" v-for="(item, index) in UN_KNOW_ENABLE" :key="index">{{
                item.label
              }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col> -->
      </el-row>

      <el-row>
        <el-col :span="22">
          <el-form-item label="备注" prop="remark">
            <el-input v-model="processForm.remark" type="textarea" placeholder="请输入内容" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="handleSubmit"> 提交 </el-button>
        <el-button @click="handleClose">返回</el-button>
      </span>
    </template>
  </XModal>
</template>

<script setup lang="ts" name="MeasureUnit">
import { ref, reactive, onMounted } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, btnConditions, tableColumns } from './data'
import {
  queryTableList,
  queryUnitList,
  addUnitData,
  getUnitDetail,
  updateUnitData,
  deleteUnitData,
  downloadListData
} from '@/api/masterData/measureUnit'
import type { FormInstance, FormRules } from 'element-plus'
import { UN_KNOW_ENABLE, UN_KNOW_STATE } from '@/utils/const'
import { ElMessage, ElMessageBox } from 'element-plus'
import download from '@/utils/download'

const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  id: [{ required: true, message: '单位编码不能为空', trigger: 'blur' }],
  measureName: [{ required: true, message: '单位名称不能为空', trigger: 'blur' }],
  primaryFlag: [{ required: true, message: '是否是主单位不能为空', trigger: 'blur' }],
  enableFlag: [{ required: true, message: '是否启用不能为空', trigger: 'blur' }]
})
const searchModel = reactive({
  id: '',
  processName: ''
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
  measureName: '',
  changeRate: 0,
  enableFlag: 'Y',
  primaryFlag: 'Y',
  remark: '',
  primaryId: '',
  id: ''
})
// 单位下拉参数
const unitLits = ref<any>([])

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
  download.excel(data, '计量单位.xls')
}

// 表格选择事件
const handleSelectionChange = (value) => {
  selectionID.value = value.map((item) => item?.id)
  btnConditions[1].disabled = !selectionID.value.length || selectionID.value.length >= 2
  btnConditions[2].disabled = !selectionID.value.length
}
// 新增
const addProcessForm = () => {
  getUnitLits()
  dialogTitle.value = '添加单位'
  dialogVisible.value = true
}

// 修改
const handleTableUpDate = (value?) => {
  getUnitLits()
  dialogTitle.value = '修改单位详情'
  const dataID = value?.id || selectionID.value[0]
  queryProcessInfoAPI(dataID)
}

// 删除
const handleDeleteTable = (value?) => {
  const id = value || selectionID.value
  const deleteID = Array.isArray(id) ? id : [id]
  ElMessageBox.confirm('是否删除当前数据?').then(() => {
    deleteUnitData(deleteID).then((res) => {
      ElMessage.success('删除成功')
      getTableListInfo()
    })
  })
}

// 获取工序详情数据
const queryProcessInfoAPI = (value) => {
  getUnitDetail(value).then((res) => {
    processForm.value = res
    dialogVisible.value = true
  })
}

// 表格分页事件
const handlePagination = (value) => {
  pagination = value?.value
  getTableListInfo()
}

// 表单提交
const handleSubmit = async () => {
  if (!ruleFormRef.value) return
  await ruleFormRef.value.validate((valid) => {
    if (valid) {
      if (processForm.value.id) {
        updateUnitData(processForm.value).then((res) => {
          ElMessage.success('修改成功')
          handleClose()
          getTableListInfo()
        })
      } else {
        addUnitData(processForm.value).then((res) => {
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
    measureName: '',
    changeRate: 0,
    enableFlag: 'Y',
    primaryFlag: 'Y',
    remark: '',
    primaryId: '',
    id: ''
  }
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

// 初始化主单位下拉参数
const getUnitLits = () => {
  queryUnitList().then((res) => {
    unitLits.value = res?.list
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
