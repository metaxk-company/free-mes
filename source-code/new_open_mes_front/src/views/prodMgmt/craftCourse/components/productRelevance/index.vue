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
    :columns="productRelevanceColumn"
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
        <el-col :span="8">
          <el-form-item label="产品物料编码" prop="itemCode">
            <el-input v-model="craftForm.itemCode" placeholder="请输入产品编号">
              <template #append>
                <el-button :icon="'DocumentAdd'" @click="handleSelectProduct" />
              </template>
            </el-input>
          </el-form-item>
          <materialMiniModel ref="itemSelectRef" @handle-current-submit="currentData" />
        </el-col>
        <el-col :span="8">
          <el-form-item label="产品物料名称" prop="itemName">
            <el-input v-model="craftForm.itemName" placeholder="请输入产品物料名称" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="单位" prop="unitOfMeasure">
            <el-input v-model="craftForm.unitOfMeasure" placeholder="请输入单位" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col>
          <el-form-item label="规格型号" prop="specification">
            <el-input v-model="craftForm.specification" type="textarea" placeholder="请输入内容" />
          </el-form-item>
        </el-col>
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
import { btnConditions, productRelevanceColumn } from '../../data'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  queryProductTableList,
  queryCraftListAll,
  addProductData,
  updateProductInfo,
  queryProductInfo,
  deleteProduct
} from '@/api/masterData/craftCourse'
import type { FormInstance, FormRules } from 'element-plus'
import { UN_KNOW_STATE, PROCESS_RELATION_STATE } from '@/utils/const'
import materialMiniModel from '@/views/prodMgmt/pmOrder/components/materialMiniModel/index.vue'

interface IProps {
  processId: Number | String
  isEdit: Boolean
}
const props = defineProps<IProps>()
const ruleFormRef = ref<FormInstance>()
const itemSelectRef = ref<any>(null)
const rules = reactive<FormRules>({
  itemCode: [{ required: true, message: '产品物料编码不能为空', trigger: 'blur' }]
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
const craftForm = ref<any>({
  remark: '',
  specification: '',
  unitOfMeasure: '',
  itemName: '',
  itemCode: '',
  id: '',
  itemId: '',
  routeId: props.processId
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
  dialogTitle.value = '添加产品制程'
  dialogVisible.value = true
}

// 修改
const handleTableUpDate = (value?) => {
  dialogTitle.value = '修改产品制程'
  const dataID = value?.id || selectionID.value[0]
  queryProcessInfoAPI(dataID)
}

// 删除
const handleDeleteTable = (value?) => {
  const id = value || selectionID.value
  const deleteID = Array.isArray(id) ? id : [id]
  ElMessageBox.confirm('是否删除当前数据?').then(() => {
    deleteProduct(deleteID).then((res) => {
      ElMessage.success('删除成功')
      getTableListInfo()
    })
  })
}

// 查看组成工序详情
const queryProcessInfoAPI = (value) => {
  queryProductInfo(value).then((res) => {
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

// 产品编码选择
const handleSelectProduct = () => {
  itemSelectRef.value.visibleProduct = true
}

// 物料产品选择弹框-子表格的选中事件
const currentData = (value) => {
  if (value == null) return
  craftForm.value.itemCode = value.itemCode
  craftForm.value.itemName = value.itemName
  craftForm.value.itemId = value.id
  craftForm.value.unitOfMeasure = value.unitOfMeasure
  craftForm.value.specification = value.specification
}

// 表单提交
const handleSubmit = async () => {
  if (!ruleFormRef.value) return
  await ruleFormRef.value.validate((valid) => {
    if (valid) {
      if (craftForm.value.id) {
        updateProductInfo(craftForm.value).then((res) => {
          ElMessage.success('修改成功')
          handleClose()
          getTableListInfo()
        })
      } else {
        addProductData(craftForm.value).then((res) => {
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
    remark: '',
    specification: '',
    unitOfMeasure: '',
    itemName: '',
    itemCode: '',
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
  queryProductTableList(params)
    .then((res) => {
      const { list, total } = res || {}
      pagination.total = total
      tableFormList = list
    })
    .finally(() => {
      loading.value = false
    })
}

// 查询工艺组成列表
const getCraftList = () => {
  queryCraftListAll().then((res) => {
    processOptions.value = res
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
