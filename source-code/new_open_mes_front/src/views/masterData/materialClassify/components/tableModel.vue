<template>
  <el-row :gutter="10" class="mb8">
    <el-col :span="1.5" style="margin-bottom: 16px">
      <el-button
        icon="Switch"
        type="info"
        plain
        @click="toggleExpandAll"
        :disabled="!tableFormList.length"
        >展开/折叠</el-button
      >
    </el-col>
  </el-row>
  <el-table
    v-if="refreshTable"
    :data="tableFormList"
    v-loading="tableLoading"
    row-key="id"
    :default-expand-all="isExpandAll"
    :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
  >
    <el-table-column prop="itemTypeName" label="分类名称" />
    <el-table-column prop="orderNum" label="排序" />
    <el-table-column prop="itemOrProduct" label="物料/产品">
      <template #default="scope">
        {{ PRODUCT_CLASS[scope.row.itemOrProduct] }}
        <!-- {{
          scope.row.itemOrProduct
          // PRODUCT_CLASS[scope.row.itemOrProduct]
        }} -->
      </template>
    </el-table-column>
    <el-table-column prop="enableFlag" label="是否启用">
      <template #default="scope">
        <el-tag :type="scope.row.enableFlag === STATE_YES ? 'success' : 'warning'">{{
          STATE_TYPE[scope.row.enableFlag]
        }}</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="createTime" label="创建时间" width="160" />
    <el-table-column label="操作" width="210">
      <template #default="scope">
        <el-button
          style="font-size: 17px"
          type="primary"
          link
          key="plain"
          size="small"
          @click="handleAdd(scope.row)"
          >新增</el-button
        >
        <el-button
          style="font-size: 17px"
          type="primary"
          link
          key="plain"
          size="small"
          @click="handleUpdate(scope.row)"
          >修改</el-button
        >
        <el-button
          v-if="scope.row.parentTypeId !== 0"
          type="danger"
          style="font-size: 17px"
          link
          key="plain"
          size="small"
          @click="handleDelete(scope.row)"
          >删除</el-button
        >
      </template>
    </el-table-column>
  </el-table>
  <XModal :title="dialogTitle" v-model="dialogVisible" width="30%">
    <el-form
      style="min-width: 300px"
      ref="ruleFormRef"
      :model="ruleForm"
      :rules="rules"
      label-width="130px"
    >
      <el-form-item label="父级分类" prop="parentTypeId">
        <el-cascader
          disabled
          :options="treeOptions"
          :show-all-levels="false"
          :props="cascadeProps"
          v-model="ruleForm.parentTypeId"
        />
      </el-form-item>
      <el-form-item label="子分类名称" prop="itemTypeName">
        <el-input style="width: 198px" v-model="ruleForm.itemTypeName" />
      </el-form-item>
      <el-form-item label="显示排序" prop="orderNum">
        <el-input-number
          style="width: 198px"
          v-model="ruleForm.orderNum"
          :min="0"
          controls-position="right"
        />
      </el-form-item>

      <el-form-item label="物料/产品">
        <el-radio-group v-model="ruleForm.itemOrProduct">
          <el-radio label="ITEM" size="large">物料</el-radio>
          <el-radio label="PRODUCT" size="large">产品</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="启用状态" prop="enableFlag">
        <el-radio-group v-model="ruleForm.enableFlag">
          <el-radio label="Y" size="large">是</el-radio>
          <el-radio label="N" size="large">否</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="handleDialogSubmit(ruleFormRef)" type="primary" plain>确定</el-button>
      <el-button @click="handleDialogCancel">取消</el-button>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { reactive, ref, nextTick } from 'vue'
import { STATE_TYPE, STATE_YES, ITEM } from '../data'
import { PRODUCT_CLASS } from '@/utils/const'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessageBox, ElMessage } from 'element-plus'
import {
  queryClassList,
  queryClassifyInfoAPI,
  deleteClassInfo,
  addClass,
  updateClassInfo
} from '@/api/masterData/materialClassify'
import { handleTree } from '@/utils/tree'

defineProps({
  tableFormList: {
    type: Array<Object>,
    default: () => Array<Object>
  },
  tableLoading: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['updateTableList'])

const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  parentTypeId: [{ required: true, message: '父分类不能为空', trigger: 'blur' }],
  itemTypeName: [{ required: true, message: '分类名称不能为空', trigger: 'blur' }],
  orderNum: [{ required: true, message: '显示排序不能为空', trigger: 'blur' }],
  itemOrProduct: [
    {
      required: true,
      message: '请选择是产品分类还是物料分类',
      trigger: ['blur']
    }
  ]
})
const cascadeProps = ref({
  value: 'id',
  label: 'itemTypeName',
  children: 'children',
  checkStrictly: true
})

// 父分类树结构
let treeOptions = reactive<any>([])
// 表单数据
let ruleForm = ref({
  // 父分类
  parentTypeId: 0,
  id: '',
  // 分类名称
  itemTypeName: '',
  // 显示排序
  orderNum: 0,
  // 物料/产品
  itemOrProduct: ITEM,
  // 启用状态
  enableFlag: STATE_YES
})

// 弹框标题
const dialogTitle = ref<String>('')
// 弹框状态
const dialogVisible = ref<Boolean>(false)

// 是否展开所有
const isExpandAll = ref<Boolean>(true)
// 重新渲染表格状态
const refreshTable = ref<Boolean>(true)

// 是否展开所有
const toggleExpandAll = () => {
  refreshTable.value = !refreshTable.value
  isExpandAll.value = !isExpandAll.value
  nextTick(() => {
    refreshTable.value = !refreshTable.value
  })
}

// 清除表格内容
const clearFormData = () => {
  // if (!ruleFormRef.value) return
  // ruleFormRef.value.resetFields()
  ruleForm.value.parentTypeId = 0
  ruleForm.value.id = ''
  ruleForm.value.itemTypeName = ''
  ruleForm.value.orderNum = 0
  ruleForm.value.itemOrProduct = 'ITEM'
  ruleForm.value.enableFlag = 'Y'
}

// 新增
const handleAdd = (value) => {
  clearFormData()
  if (!value) return
  ruleForm.value.parentTypeId = value.id
  queryClassList().then((res) => {
    const { list = [] } = res || {}
    const treeList = handleTree(list, 'id', 'parentTypeId')
    treeOptions = treeList
    dialogVisible.value = !dialogVisible.value
    dialogTitle.value = '添加产品分类'
  })
}

// 表格修改
const handleUpdate = async (value) => {
  // clearFormData()
  if (!value) return
  getClassifyInfoAPI(value)
  dialogTitle.value = '修改产品分类'
}

// 获取分类详情
const getClassifyInfoAPI = (value) => {
  queryClassifyInfoAPI(value?.id).then((res) => {
    ruleForm.value = res
    dialogVisible.value = !dialogVisible.value
  })
}

// 表格删除
const handleDelete = (value) => {
  ElMessageBox.confirm(`是否确认删除【${value?.itemTypeName}】分类？`).then(() =>
    deleteClassInfo(value.id).then((res) => {
      if (!res) return
      ElMessage.success('删除成功')
      emit('updateTableList')
    })
  )
}

// 弹框提交
const handleDialogSubmit = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid) => {
    if (valid) {
      if (!ruleForm.value.id) {
        addClass(ruleForm.value).then((res) => {
          if (!res) return
          ElMessage({
            message: '新增成功',
            type: 'success'
          })
          dialogVisible.value = !dialogVisible.value
          emit('updateTableList')
        })
      } else {
        updateClassInfo(ruleForm.value).then((res) => {
          if (!res) return
          dialogVisible.value = !dialogVisible.value
          emit('updateTableList')
        })
      }
    }
  })
}

// 弹框取消
const handleDialogCancel = () => {
  clearFormData()
  dialogVisible.value = !dialogVisible.value
}
</script>

<style scoped lang="scss">
:deep .el-radio.el-radio--large {
  height: 33px;
}
</style>
