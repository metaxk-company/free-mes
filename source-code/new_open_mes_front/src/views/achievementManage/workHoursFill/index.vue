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
      <!-- <template #processCode="{ scope }">
        <el-button link type="primary" @click="handleReadProcessInfo(scope.row.id)">{{
          scope.row.processCode
        }}</el-button>
      </template> -->

      <template #operation="{ scope }">
        <el-button link type="primary" @click="handleTableUpDate(scope.row)">修改</el-button>
        <el-button link type="danger" @click="handleDeleteTable(scope.row)">删除</el-button>
      </template>
    </common-table>
  </ContentWrap>

  <XModal width="70%" v-model="dialogVisible" :title="dialogTitle" @before-close="handleClose">
    <el-form ref="ruleFormRef" :model="formData" :rules="rules" label-width="100px">
      <el-row>
        <el-col :span="8">
          <el-form-item label="任务单号" prop="data">
            <el-input v-model="formData.data" placeholder="请输入任务单号" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="填报日期" prop="data">
            <el-input v-model="formData.data" placeholder="请输入人员姓名" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="人员姓名" prop="data">
            <el-input v-model="formData.data" placeholder="请输入人员姓名" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="人员编号" prop="data">
            <el-input v-model="formData.data" placeholder="请输入人员编号" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="工时类别" prop="data">
            <el-select v-model="formData.data" placeholder="请选择工时类别">
              <el-option
                v-for="item in workHoursSelect"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="工时时长" prop="data">
            <el-input-number min="0" v-model="formData.data" placeholder="工时时长" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="生产数量" prop="data">
            <el-input-number min="0" v-model="formData.data" placeholder="生产数量" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="备注" prop="data">
        <el-input v-model="formData.data" type="textarea" placeholder="请输入备注" />
      </el-form-item>
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
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getWorkHoursTypeList } from '@/api/prodMgmt/workHoursType'

const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  // processCode: [{ required: true, message: '工序编码不能为空', trigger: 'blur' }]
})
const searchModel = reactive({
  input: '',
  select: ''
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
let formData = ref({
  // 任务单号
  data: ''
  // 填报日期
  // 人员姓名
  // 人员编号
  // 工时类别
  // 工时时长
  // 生产数量
  // 备注
})
const workHoursSelect = ref<any>([])

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
  // const data = await downloadListData()
  // download.excel(data, '工序设置.xls')
}

// 表格选择事件
const handleSelectionChange = (value) => {
  // selectionID.value = value.map((item) => item?.id)
  // btnConditions[1].disabled = !selectionID.value.length || selectionID.value.length >= 2
  // btnConditions[2].disabled = !selectionID.value.length
}
// 新增
const addProcessForm = () => {
  dialogTitle.value = '添加工时填报'
  dialogVisible.value = true
}

// 修改
const handleTableUpDate = (value?) => {
  dialogTitle.value = '修改'
  const dataID = value?.id || selectionID.value[0]
  queryProcessInfoAPI(dataID)
}

// 删除
const handleDeleteTable = (value?) => {
  const id = value?.id || selectionID.value
  const deleteID = Array.isArray(id) ? id : [id]
  ElMessageBox.confirm('是否删除当前数据?').then(() => {
    // deleteProcessInfo(deleteID).then((res) => {
    //   ElMessage.success('删除成功')
    //   getTableListInfo()
    // })
  })
}

// 查看
const handleReadProcessInfo = (value) => {
  dialogTitle.value = '查看生产工序'
  queryProcessInfoAPI(value)
}

// 获取详情数据
const queryProcessInfoAPI = (value) => {
  // queryProcessInfo(value).then((res) => {
  //   processForm.value = res
  //   dialogVisible.value = true
  // })
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
      // if (processForm.value.id) {
      // } else {
      // }
    }
  })
}

// 表单关闭
const handleClose = () => {
  formData.value = {}
  dialogVisible.value = false
}

// 初始化获取表格数据
const getTableListInfo = () => {
  const params = {
    ...searchModel,
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize
  }
  // loading.value = true
  // queryTableList(params)
  //   .then((res) => {

  //   })
  //   .finally(() => {
  //     loading.value = false
  //   })
}

// 初始化获取下拉表格数据
const getSelectList = () => {
  const params = {}
  getWorkHoursTypeList(params)
    .then((res) => {
      const { list = [] } = res || {}
      workHoursSelect.value = list.map((item) => {
        return {
          label: item.workhoursType,
          value: item.workhoursType
        }
      })
      searchConditions[1].options = workHoursSelect.value
    })
    .finally(() => {
      loading.value = false
    })
}

const info = () => {
  getTableListInfo()
  getSelectList()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
