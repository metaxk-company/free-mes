<template>
  <el-row :gutter="10" v-if="dialogState !== 'View'">
    <el-col :span="1.5">
      <el-button type="primary" plain icon="plus" @click="handleCurrentDialog"
        >新增</el-button
      ></el-col
    >
    <el-col :span="1.5">
      <el-button type="danger" plain icon="delete" :disabled="isDelete" @click="handleDeleteRow"
        >删除</el-button
      >
    </el-col>
  </el-row>
  <common-table
    style="margin-top: 16px"
    :pagination="pagination"
    :tableData="bomTableData"
    :columns="bomColumnState"
    :is-selection="true"
    :loading="bomLoading"
    @selection-change="handleSelectionChange"
  >
    <template #operation="{ scope }" v-if="dialogState !== 'View'">
      <el-button link type="primary" @click="handleEditBom(scope.row)">修改</el-button>
      <el-button link type="danger" @click="handleDeleteRow(scope.row)">删除</el-button>
    </template>
  </common-table>
  <materialMiniModel ref="itemSelectRef" @handle-current-submit="currentData" />
  <!-- 修改产品BOM关系弹框  -->
  <XModal title="修改产品BOM关系" v-model="bomDialogVisible" width="34%">
    <el-form :model="bomRuleForm" prop="bomItemCode" ref="ruleFormRef">
      <el-form-item label="BOM物料编码">
        <el-input v-model="bomRuleForm.bomItemCode" disabled />
      </el-form-item>
      <el-form-item label="BOM物料名称" prop="bomItemName">
        <el-input v-model="bomRuleForm.bomItemName" disabled />
      </el-form-item>
      <el-form-item label="BOM物料规格" prop="bomItemSpec">
        <el-input v-model="bomRuleForm.bomItemSpec" disabled />
      </el-form-item>
      <el-form-item label="BOM物料单位" prop="unitOfMeasure">
        <el-input v-model="bomRuleForm.unitOfMeasure" disabled />
      </el-form-item>
      <el-form-item label="物料使用比例" prop="quantity">
        <el-input-number
          :precision="4"
          :step="0.1"
          :min="0"
          v-model="bomRuleForm.quantity"
          placeholder="请输入物料使用比例"
        />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="bomRuleForm.remark" type="textarea" placeholder="请输入内容" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="handleBOMRelationSubmit">确定</el-button>
        <el-button @click="handleBomBack">返回</el-button>
      </span>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import commonTable from '@/components/CommonTable/index.vue'
import materialMiniModel from '@/views/prodMgmt/pmOrder/components/materialMiniModel/index.vue'
import { bomColumnState } from '../data'
import type { FormInstance } from 'element-plus'
import { ElMessageBox, ElMessage } from 'element-plus'
import {
  getBOMItemTableList,
  saveBOMRelation,
  queryBOMRelationInfo,
  upDateBOMRelation,
  deleteBOMRelation
} from '@/api/masterData/materialManage'

const props = defineProps({
  dialogState: {
    type: String,
    default: () => ''
  },
  bomParams: {
    type: Number
  }
})

const isDelete = computed(() => !getSelectionList.value.length)

const itemSelectRef = ref<any>(null)
const ruleFormRef = ref<FormInstance>()
// 表格数据
let bomTableData = ref([])
// 分页
const pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
// 查询数据
// const BomFormData = reactive({
//   pageNo: 1,
//   pageSize: 10,
//   itemId: 1,
//   bomItemId: null,
//   bomItemCode: null,
//   bomItemName: null,
//   bomItemSpec: null,
//   unitOfMeasure: null,
//   quantity: null,
//   enableFlag: null,
//   attr1: null,
//   attr2: null,
//   attr3: null,
//   attr4: null
// })
// 当前Bom选中的数据项
let getSelectionList = ref([])
// bom表格加载
const bomLoading = ref<boolean>(false)
// 修改Bom 关系弹框
const bomDialogVisible = ref<boolean>(false)
// Bom关系表单关系
let bomRuleForm = ref({
  // BOM物料编码
  bomItemCode: '',
  // BOM物料名称
  bomItemName: '',
  // BOM物料规格
  bomItemSpec: '',
  // BOM物料单位
  unitOfMeasure: '',
  // 物料使用比例
  quantity: 1,
  // 备注
  remark: ''
})

// Bom 选择事件
const handleSelectionChange = (selection) => {
  getSelectionList.value = selection.map((item: Object | any) => item.id)
}

// Bom 修改操作
const handleEditBom = (value) => {
  queryBOMRelationInfoAPI(value?.id)
}

// 查询产品BOM关系详细
const queryBOMRelationInfoAPI = (value) => {
  queryBOMRelationInfo(value).then((res) => {
    bomRuleForm.value = res
    bomDialogVisible.value = !bomDialogVisible.value
  })
}

// Bom 新增打开弹框
const handleCurrentDialog = () => {
  itemSelectRef.value.visibleProduct = true
}

// Bom 删除事件
const handleDeleteRow = (value) => {
  const deleteId = value?.id || getSelectionList.value
  ElMessageBox.confirm('确定删除当前行数据?').then(() => {
    deleteRowInfo(deleteId)
  })
}

// 调用删除接口
const deleteRowInfo = (value: Array<number> | number) => {
  const params = Array.isArray(value) ? value : [value]
  deleteBOMRelation(params).then((res) => {
    if (!res) return
    ElMessage.success('删除成功')
    getBomTableLitAPI()
  })
}

// 修改Bom关系弹框 - 返回操作
const handleBomBack = () => {
  if (!ruleFormRef.value) return
  ruleFormRef.value.resetFields()
  bomDialogVisible.value = !bomDialogVisible.value
}

// Bom 关系弹框 - 提交功能
const handleBOMRelationSubmit = () => {
  upDateBOMRelation(bomRuleForm.value).then((res) => {
    ElMessage.success('修改成功')
    bomDialogVisible.value = !bomDialogVisible.value
    getBomTableLitAPI()
  })
}

// 物料产品选择弹框-子表格的选中事件
const currentData = (value) => {
  if (value == null) return
  console.log(value)
  const params = {
    itemId: props.bomParams,
    bomItemId: value.id,
    bomItemCode: value.itemCode,
    bomItemName: value.itemName,
    bomItemSpec: value.specification,
    unitOfMeasure: value.unitOfMeasure,
    itemOrProduct: value.itemOrProduct,
    quantity: 1,
    enableFlag: value.enableFlag
  }
  saveBOMRelation(params).then((res) => {
    if (!res) return
    getBomTableLitAPI()
  })
  // childSelectionListData.value = value
}

// // 物料产品选择弹框 - 确定操作
// const handleSubmit = () => {
//   materialMiniRef.value?.clearSelectionListData()
// }

// // 物料产品选择弹框 - 返回操作
// const handleBack = () => {
//   dialogVisible.value = !dialogVisible.value
//   materialMiniRef.value?.clearSelectionListData()
// }

const getBomTableLitAPI = () => {
  const params = {
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize,
    itemId: props.bomParams
  }
  bomLoading.value = true
  getBOMItemTableList(params)
    .then((res) => {
      const { list, total } = res || {}
      // debugger
      pagination.total = total
      bomTableData.value = list
    })
    .finally(() => {
      bomLoading.value = false
    })
}

const info = () => {
  getBomTableLitAPI()
}

//
onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
