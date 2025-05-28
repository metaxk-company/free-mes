<template>
  <div style="margin-bottom: 20px" v-if="!isEdit">
    <el-button
      plain
      v-for="(item, index) in btnGroup"
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
    :columns="processComposeColumn"
    :tableData="tableFormList"
    :pagination="pagination"
    @selection-change="handleSelectionChange"
    @pagination-change="handlePagination"
  >
    <template #keyFlag="{ scope }">
      <el-tag :type="scope.row.keyFlag === 'Y' ? 'success' : 'warning'">{{
        UN_KNOW_STATE[scope.row.keyFlag]
      }}</el-tag>
    </template>
    <template #colorCode="{ scope }">
      <el-color-picker v-model="scope.row.colorCode" disabled />
    </template>
    <template #linkType="{ scope }">
      {{ PROCESS_RELATION_STATE[scope.row.linkType] }}
    </template>

    <template #operation="{ scope }" v-if="!isEdit">
      <el-button link type="primary" @click="handleTableUpDate(scope.row)">修改</el-button>
      <el-button link type="danger" @click="handleDeleteTable(scope.row.id)">删除</el-button>
    </template>
  </common-table>
  <XModal width="70%" v-model="dialogVisible" :title="dialogTitle" @before-close="handleClose">
    <el-form ref="ruleFormRef" :model="craftForm" :rules="rules" label-width="120px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="序号" prop="orderNum">
            <el-input-number :min="1" v-model="craftForm.orderNum" placeholder="" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="工序" prop="processId">
            <el-select v-model="craftForm.processId" placeholder="请选择工序">
              <el-option
                v-for="(item, index) in processOptions"
                :key="index"
                :label="item.processName"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="工序关系" prop="linkType">
            <el-select v-model="craftForm.linkType" placeholder="请选择与下一道工序关系">
              <el-option
                v-for="dict in PROCESS_RELATION"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="甘特图颜色" prop="colorCode">
            <el-color-picker v-model="craftForm.colorCode" />
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-input
            v-model="craftForm.colorCode"
            maxlength="7"
            placeholder="请输入颜色编码在左侧选择颜色"
          />
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="是否关键工序" prop="keyFlag">
            <el-select v-model="craftForm.keyFlag">
              <el-option
                v-for="dict in UN_KNOW_ENABLE"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="准备时间(分钟)" prop="defaultPreTime">
            <el-input-number
              :min="0"
              :step="1"
              v-model="craftForm.defaultPreTime"
              placeholder="请输入准备时间"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="等待时间(分钟)" prop="defaultSufTime">
            <el-input-number
              :min="0"
              :step="1"
              v-model="craftForm.defaultSufTime"
              placeholder="请输入准备时间"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="craftForm.remark" type="textarea" placeholder="请输入内容" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="handleSubmit">提交</el-button>
        <el-button @click="handleClose">返回</el-button>
      </span>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed } from 'vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { btnConditions, processComposeColumn } from '../../data'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  queryProcessTableList,
  queryCraftListAll,
  addCraft,
  updateCraft,
  queryCraftInfo,
  deleteCraft
} from '@/api/masterData/craftCourse'
import type { FormInstance, FormRules } from 'element-plus'
import {
  PROCESS_RELATION,
  UN_KNOW_ENABLE,
  UN_KNOW_STATE,
  PROCESS_RELATION_STATE
} from '@/utils/const'

interface IProps {
  processId: Number | String
  isEdit: Boolean
}
const props = defineProps<IProps>()
const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  id: [{ required: true, message: '工艺路线ID不能为空', trigger: 'blur' }],
  processId: [{ required: true, message: '工序ID不能为空', trigger: 'blur' }],
  nextProcessId: [{ required: true, message: '工序ID不能为空', trigger: 'blur' }],
  keyFlag: [{ required: true, message: '请指定当前工序是否关键工序', trigger: 'blur' }],
  colorCode: [{ required: true, message: '请选择或输入颜色打码', trigger: 'blur' }]
})
const loading = ref<boolean>(false)
let tableFormList = reactive([])
// 表格分页
let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const selectionID = ref()
const dialogTitle = ref('')
const craftForm = ref({
  orderNum: 1,
  processId: '',
  linkType: '',
  colorCode: '#CD1D1D',
  keyFlag: '',
  defaultPreTime: 0,
  defaultSufTime: 0,
  remark: '',
  routeId: props.processId,
  id: ''
})
const dialogVisible = ref<boolean>(false)
const processOptions = ref<any>([])
const btnGroup = computed(() => {
  return btnConditions.slice(0, -1)
})

// 顶部按钮事件
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
  }
}

// 新增
const addProcessForm = () => {
  dialogTitle.value = '添加工艺组成'
  dialogVisible.value = true
}

// 修改
const handleTableUpDate = (value?) => {
  dialogTitle.value = '修改工艺组成'
  const dataID = value?.id || selectionID.value[0]
  queryProcessInfoAPI(dataID)
}

// 删除
const handleDeleteTable = (value?) => {
  const id = value || selectionID.value
  const deleteID = Array.isArray(id) ? id : [id]
  ElMessageBox.confirm('是否删除当前数据?').then(() => {
    deleteCraft(deleteID).then((res) => {
      ElMessage.success('删除成功')
      getTableListInfo()
    })
  })
}

// 查看组成工序详情
const queryProcessInfoAPI = (value) => {
  queryCraftInfo(value).then((res) => {
    craftForm.value = res
    dialogVisible.value = true
  })
}

// 表格选择事件
const handleSelectionChange = (value) => {
  selectionID.value = value.map((item) => item?.id)
  btnConditions[1].disabled = !selectionID.value.length || selectionID.value.length >= 2
  btnConditions[2].disabled = !selectionID.value.length
}

// 表单提交
const handleSubmit = async () => {
  if (!ruleFormRef.value) return
  await ruleFormRef.value.validate((valid) => {
    if (valid) {
      if (craftForm.value.id) {
        updateCraft(craftForm.value).then((res) => {
          ElMessage.success('修改成功')
          handleClose()
          getTableListInfo()
        })
      } else {
        addCraft(craftForm.value).then((res) => {
          ElMessage.success('新增成功')
          handleClose()
          getTableListInfo()
        })
      }
    }
  })
}

const handleClose = () => {
  craftForm.value = {
    orderNum: 1,
    processId: '',
    linkType: '',
    colorCode: '',
    keyFlag: '',
    defaultPreTime: 0,
    defaultSufTime: 0,
    remark: '',
    routeId: props.processId,
    id: ''
  }
  dialogVisible.value = false
}

// 表格分页事件
const handlePagination = (value) => {
  pagination = value?.value
  getTableListInfo()
}

// 初始化获取工艺表格数据
const getTableListInfo = () => {
  const params = {
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize,
    routeId: props.processId
  }
  loading.value = true
  queryProcessTableList(params)
    .then((res) => {
      const { list, total } = res || {}
      pagination.total = total
      tableFormList = list
    })
    .finally(() => {
      loading.value = false
    })
}

// 查询工序列表
const getCraftList = () => {
  const params = {
    pageNo: pagination.currentPage,
    pageSize: 1000
  }
  queryCraftListAll(params).then((res) => {
    processOptions.value = res.list
  })
}

const info = () => {
  getTableListInfo()
  getCraftList()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
