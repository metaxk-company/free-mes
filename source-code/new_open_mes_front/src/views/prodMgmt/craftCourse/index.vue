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
      <template #routeCode="{ scope }">
        <el-button link type="primary" @click="handleReadProcessInfo(scope.row.id)">{{
          scope.row.routeCode
        }}</el-button>
      </template>
      <template #enableFlag="{ scope }">
        <el-tag :type="scope.row.enableFlag === 'Y' ? 'success' : 'warning'">{{
          UN_KNOW_STATE[scope.row.enableFlag]
        }}</el-tag>
      </template>

      <template #operation="{ scope }">
        <el-button
          style="font-size: 17px"
          link
          type="primary"
          @click="handleReadProcessInfo(scope.row.id)"
          >详情</el-button
        >
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

  <XModal width="70%" v-model="dialogVisible" :title="dialogTitle" @before-close="handleClose">
    <el-form ref="ruleFormRef" :model="processForm" :rules="rules" label-width="100px">
      <el-row>
        <el-col :span="8">
          <el-form-item label="工艺编号" prop="routeCode">
            <el-input
              :disabled="isEdit"
              v-model="processForm.routeCode"
              placeholder="请输入工艺路线编号"
            />
          </el-form-item>
        </el-col>
        <!-- <el-col :span="8" v-if="!isEdit">
          <el-form-item label="自动生成" prop="autoGenFlag">
            <el-switch
              v-model="processForm.autoGenFlag"
              active-color="#13ce66"
              @change="handleAutoGenChange(processForm.autoGenFlag)"
            />
          </el-form-item>
        </el-col> -->
        <el-col :span="8">
          <el-form-item label="工艺名称">
            <el-input
              :disabled="isEdit"
              v-model="processForm.routeName"
              placeholder="请输入工艺路线名称"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="是否启用" prop="enableFlag">
            <el-radio-group v-model="processForm.enableFlag" :disabled="isEdit">
              <el-radio :label="item.value" v-for="(item, index) in UN_KNOW_ENABLE" :key="index">{{
                item.label
              }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="工艺说明" prop="routeDesc">
            <el-input
              autosize
              :disabled="isEdit"
              v-model="processForm.routeDesc"
              type="textarea"
              placeholder="请输入内容"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="备注" prop="remark">
            <el-input
              autosize
              :disabled="isEdit"
              v-model="processForm.remark"
              type="textarea"
              placeholder="请输入内容"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-tabs type="border-card" v-if="!!processForm.id">
      <el-tab-pane label="组成工序">
        <processCompose :process-id="processForm.id" :is-edit="isEdit" />
      </el-tab-pane>
      <el-tab-pane label="关联产品">
        <productRelevance :process-id="processForm.id" :is-edit="isEdit" />
      </el-tab-pane>
    </el-tabs>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="handleSubmit" v-if="!isEdit">提交</el-button>
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
  addCraftCourseData,
  queryCraftCourseInfo,
  updateCraftCourseInfo,
  deleteCraftCourseInfo,
  downloadListData
} from '@/api/masterData/craftCourse'
import type { FormInstance, FormRules } from 'element-plus'
import { UN_KNOW_ENABLE, UN_KNOW_STATE } from '@/utils/const'
import { ElMessage, ElMessageBox } from 'element-plus'
import processCompose from './components/processCompose/index.vue'
import productRelevance from './components/productRelevance/index.vue'
import download from '@/utils/download'

const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  routeCode: [{ required: true, message: '工艺路线编号不能为空', trigger: 'blur' }],
  routeName: [{ required: true, message: '工艺路线名称不能为空', trigger: 'blur' }],
  enableFlag: [{ required: true, message: '是否启用不能为空', trigger: 'blur' }]
})
const searchModel = reactive({
  routeCode: '',
  routeName: '',
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
  routeCode: '',
  autoGenFlag: false,
  routeName: '',
  enableFlag: 'Y',
  routeDesc: '',
  remark: '',
  id: ''
})
const isEdit = ref(false)

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
  download.excel(data, '工艺流程.xls')
}

// 表格选择事件
const handleSelectionChange = (value) => {
  selectionID.value = value.map((item) => item?.id)
  btnConditions[1].disabled = !selectionID.value.length || selectionID.value.length >= 2
  btnConditions[2].disabled = !selectionID.value.length
}
// 新增
const addProcessForm = () => {
  dialogTitle.value = '添加工艺路线'
  dialogVisible.value = true
  // handleAutoGenChange()
}

// 修改
const handleTableUpDate = (value?) => {
  dialogTitle.value = '修改工艺路线'
  const dataID = value?.id || selectionID.value[0]
  queryProcessInfoAPI(dataID)
}

// 删除
const handleDeleteTable = (value?) => {
  const id = value || selectionID.value
  const deleteID = Array.isArray(id) ? id : [id]
  ElMessageBox.confirm('是否删除当前数据?').then(() => {
    deleteCraftCourseInfo(deleteID).then((res) => {
      ElMessage.success('删除成功')
      getTableListInfo()
    })
  })
}

// 查看
const handleReadProcessInfo = (value) => {
  isEdit.value = true
  dialogTitle.value = '查看工艺路线'
  queryProcessInfoAPI(value)
}

// 获取工序详情数据
const queryProcessInfoAPI = (value) => {
  queryCraftCourseInfo(value).then((res) => {
    processForm.value = res
    dialogVisible.value = true
  })
}

// 表格分页事件
const handlePagination = (value) => {
  pagination = value?.value
  getTableListInfo()
}

/*
// 自动生成
const handleAutoGenChange = () => {
  createWordOrder('ROUTE_CODE').then((res) => {
    nextTick(() => {
      processForm.value.routeCode = res
    })
  })
}

 */

// 表单提交
const handleSubmit = async () => {
  if (!ruleFormRef.value) return
  await ruleFormRef.value.validate((valid) => {
    if (valid) {
      if (processForm.value.id) {
        updateCraftCourseInfo(processForm.value).then((res) => {
          ElMessage.success('修改成功')
          handleClose()
          getTableListInfo()
        })
      } else {
        addCraftCourseData(processForm.value).then((res) => {
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
    routeCode: '',
    autoGenFlag: false,
    routeName: '',
    enableFlag: 'Y',
    routeDesc: '',
    remark: '',
    id: ''
  }
  dialogVisible.value = false
  isEdit.value = false
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
