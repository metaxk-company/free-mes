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
    <el-table-column label="设备类型编码" prop="machineryTypeCode" />
    <el-table-column label="设备类型名称" prop="machineryTypeName" />
    <el-table-column prop="enableFlag" label="是否启用">
      <template #default="scope">
        <el-tag :type="scope.row.enableFlag === STATE_YES ? 'success' : 'warning'">{{
          STATE_TYPE[scope.row.enableFlag]
        }}</el-tag>
      </template>
    </el-table-column>

    <el-table-column label="备注" prop="remark" />
    <el-table-column label="操作">
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
          style="font-size: 17px"
          v-if="scope.row.parentTypeId !== 0"
          type="danger"
          link
          key="plain"
          size="small"
          @click="handleDelete(scope.row)"
          >删除</el-button
        >
      </template>
    </el-table-column>
  </el-table>
  <XModal :title="dialogTitle" v-model="dialogVisible" width="60%">
    <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="110px">
      <el-row>
        <el-col :span="8" v-if="ruleForm.parentTypeId !== 0">
          <el-form-item label="父级分类" prop="parentTypeId">
            <el-cascader
              :options="treeOptions"
              :show-all-levels="false"
              :props="cascadeProps"
              v-model="ruleForm.parentTypeId"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="设备类型编号" prop="machineryTypeCode">
            <el-input v-model="ruleForm.machineryTypeCode" placeholder="请输入设备类型编号" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="设备类型名称" prop="machineryTypeName">
            <el-input v-model="ruleForm.machineryTypeName" placeholder="请输入设备类型名称" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="启用状态" prop="enableFlag">
            <el-radio-group v-model="ruleForm.enableFlag">
              <el-radio label="Y" size="large">是</el-radio>
              <el-radio label="N" size="large">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="备注" prop="remark">
            <el-input
              :rows="3"
              v-model="ruleForm.remark"
              autosize
              type="textarea"
              placeholder="请输入内容"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button @click="handleDialogSubmit(ruleFormRef)" type="primary" plain>确定</el-button>
      <el-button @click="handleDialogCancel">取消</el-button>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { reactive, ref, nextTick } from 'vue'
import { STATE_TYPE, STATE_YES } from '../data'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessageBox, ElMessage } from 'element-plus'
import {
  queryFacilityTypeList,
  addFacilityType,
  queryFacilityType,
  upDateFacilityType,
  deleteFacilityType
} from '@/api/facilityManage/typeSet'
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
  machineryTypeCode: [{ required: true, message: '设备类型编号', trigger: 'blur' }],
  machineryTypeName: [{ required: true, message: '设备类型名称', trigger: 'blur' }],
  enableFlag: [
    {
      required: true,
      message: '请选择是否启用',
      trigger: ['blur']
    }
  ]
})
const cascadeProps = ref({
  value: 'id',
  label: 'machineryTypeName',
  children: 'children',
  checkStrictly: true
})

// 父分类树结构
let treeOptions = reactive<any>([])
// 表单数据
let ruleForm = ref<any>({
  // 父分类
  parentTypeId: null,
  id: '',
  // 设备类型名称
  machineryTypeName: '',
  remark: '',
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
  ruleForm.value = {
    // 父分类
    parentTypeId: null,
    id: '',
    // 设备类型名称
    machineryTypeName: '',
    machineryTypeCode: '',
    remark: '',
    // 启用状态
    enableFlag: STATE_YES
  }
}

// 新增
const handleAdd = (value) => {
  clearFormData()
  if (!value) return
  ruleForm.value.parentTypeId = value.id
  dialogTitle.value = '添加设备分类'
  getQueryFacilityTypeList()
}

// 表格修改
const handleUpdate = async (value) => {
  // clearFormData()
  if (!value) return
  getQueryFacilityTypeList()
  getClassifyInfoAPI(value)
  dialogTitle.value = '修改分类'
}

// 获取分类详情
const getClassifyInfoAPI = (value) => {
  queryFacilityType(value?.id).then((res) => {
    ruleForm.value = res
  })
}

// 表格删除
const handleDelete = (value) => {
  ElMessageBox.confirm(`是否确认删除【${value?.machineryTypeName}】的分类？`).then(() =>
    deleteFacilityType(value.id).then((res) => {
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
      const params = {
        ...ruleForm.value,
        parentTypeId:
          typeof ruleForm.value.parentTypeId === 'number'
            ? ruleForm.value.parentTypeId
            : ruleForm.value.parentTypeId?.slice(-1).toString()
      }
      if (!ruleForm.value.id) {
        addFacilityType(params).then((res) => {
          if (!res) return
          ElMessage.success('新增成功')
          dialogVisible.value = !dialogVisible.value
          emit('updateTableList')
        })
      } else {
        upDateFacilityType(params).then((res) => {
          if (!res) return
          ElMessage.success('修改成功')
          dialogVisible.value = false
          emit('updateTableList')
        })
      }
    }
  })
}

const getQueryFacilityTypeList = () => {
  queryFacilityTypeList().then((res) => {
    const { list = [] } = res || {}
    const treeList = handleTree(list, 'id', 'parentTypeId')
    treeOptions = treeList
    dialogVisible.value = !dialogVisible.value
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
  height: 34px;
}
</style>
