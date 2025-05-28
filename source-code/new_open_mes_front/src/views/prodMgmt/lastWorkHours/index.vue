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
        style="margin-bottom: 20px"
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
      <el-descriptions class="margin-top" :column="3" border>
        <el-descriptions-item :width="120">
          <template #label>
            <div class="cell-item">总工时(小时)</div>
          </template>
          {{ totalWorkHours }}
        </el-descriptions-item>
      </el-descriptions>
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
      <template #operation="{ scope }">
        <el-button style="font-size: 17px" type="primary" link @click="handleTableUpDate(scope.row)"
          >修改</el-button
        >
        <el-button style="font-size: 17px" type="primary" link @click="handleDeleteTable(scope.row)"
          >删除</el-button
        >
      </template>
    </common-table>
  </ContentWrap>

  <XModal width="40%" v-model="dialogVisible" :title="dialogTitle" @before-close="handleClose">
    <el-form ref="ruleFormRef" :model="processForm" :rules="rules" label-width="100px">
      <el-row>
        <el-col :span="11">
          <el-form-item label="工时类型" prop="workhoursType">
            <el-select v-model="processForm.workhoursType" placeholder="工时类型选择">
              <el-option
                v-for="item in optionsList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="工时" prop="workhours">
            <el-input-number v-model="processForm.workhours" :min="0" :max="9999" />
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="员工姓名" prop="workerName">
            <el-input v-model="processForm.workerName" placeholder="请输入员工姓名" />
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="所属车间" prop="workshopName">
            <el-input v-model="processForm.workshopName" placeholder="请输入所属车间" />
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

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, btnConditions, tableColumns } from './data'
import {
  getLastWorkHoursList,
  addLastWorkHours,
  queryLastWorkHoursInfo,
  upDataLastWorkHours,
  deleteLastWorkHours,
  downloadListData
} from '@/api/prodMgmt/lastWorkHours'
import type { FormInstance, FormRules } from 'element-plus'
import { getWorkHoursTypeList } from '@/api/prodMgmt/workHoursType'
import { ElMessage, ElMessageBox } from 'element-plus'
import download from '@/utils/download'

const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  workhoursType: [{ required: true, message: '工时类型不能为空', trigger: 'blur' }],
  workerName: [{ required: true, message: '员工姓名不能为空', trigger: 'blur' }],
  workshopName: [{ required: true, message: '所属车间不能为空', trigger: 'blur' }],
  workhours: [{ required: true, message: '工时不能为空', trigger: 'blur' }]
})
const searchModel = reactive({
  workhoursType: '',
  workhours: '',
  workerName: '',
  workshopName: '',
  createTime: '',
  endTime: ''
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
  workhoursType: '',
  workhours: 0,
  workerName: '',
  workshopName: '',
  id: ''
})
let optionsList = ref<any>([])
let totalWorkHours = ref('')

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
  const data = await downloadListData(selectionID.value)
  download.excel(data, '临时工时.xls')
}

// 表格选择事件
const handleSelectionChange = (value) => {
  selectionID.value = value.map((item) => item?.id)
  btnConditions[1].disabled = !selectionID.value.length || selectionID.value.length >= 2
  btnConditions[2].disabled = !selectionID.value.length
}
// 新增
const addProcessForm = () => {
  dialogTitle.value = '添加临时工时'
  dialogVisible.value = true
}

// 修改
const handleTableUpDate = (value?) => {
  dialogTitle.value = '修改临时工时'
  const dataID = value?.id || selectionID.value[0]
  queryProcessInfoAPI(dataID)
}

// 删除
const handleDeleteTable = (value?) => {
  const id = value?.id || selectionID.value
  const deleteID = Array.isArray(id) ? id : [id]
  ElMessageBox.confirm('是否删除当前数据?').then(() => {
    deleteLastWorkHours(deleteID).then((res) => {
      ElMessage.success('删除成功')
      getTableListInfo()
    })
  })
}

// 获取工序详情数据
const queryProcessInfoAPI = (value) => {
  queryLastWorkHoursInfo(value).then((res) => {
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
        upDataLastWorkHours(processForm.value).then((res) => {
          ElMessage.success('修改成功')
          handleClose()
          getTableListInfo()
        })
      } else {
        addLastWorkHours(processForm.value).then((res) => {
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
    workhoursType: '',
    workhours: 0,
    workerName: '',
    workshopName: '',
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
  getLastWorkHoursList(params)
    .then((res) => {
      const { list = [] } = res || {}
      console.log(res.total)

      totalWorkHours.value = list[0].totalWorkhours
      tableFormList = list[0].list
      pagination.total = res.total
    })
    .finally(() => {
      loading.value = false
    })
}

// 获取工时类型的下拉参数
const selectionList = () => {
  const params = {
    pageNo: pagination.currentPage,
    pageSize: 20
  }

  getWorkHoursTypeList(params).then((res) => {
    const selectionWorkHoursTypeList = res.list.map((res) => {
      return {
        label: res.workhoursType,
        value: res.workhoursType
      }
    })
    optionsList.value = selectionWorkHoursTypeList
    searchConditions[0].options = selectionWorkHoursTypeList
  })
}

const info = () => {
  getTableListInfo()
  selectionList()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
