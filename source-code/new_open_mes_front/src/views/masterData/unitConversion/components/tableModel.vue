<template>
  <div style="margin-bottom: 16px">
    <el-button type="primary" plain @click="handleAddData">新增</el-button>
    <el-button type="success" plain @click="handleUqDataRow" :disabled="disabledUpData"
      >修改</el-button
    >
    <el-button type="danger" plain @click="handleDeleteData" :disabled="disableDelete"
      >删除</el-button
    >
  </div>
  <div>
    <el-table
      :data="props.tableDataList"
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        v-for="(item, index) in columnTable"
        :key="index"
        :prop="item.prop"
        :label="item.label"
        :width="item.width"
        :align="item.align"
        :type="item.type"
      />
      <el-table-column fixed="right" label="操作" width="120">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="handleUqDataRow(scope.row)">
            修改
          </el-button>
          <el-button link type="primary" size="small" @click="handleDeleteData(scope.row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="paginationData.total > 0"
      :total="paginationData.total"
      v-model:page="paginationData.pageNo"
      v-model:limit="paginationData.pageSize"
      @pagination="handlePagination"
    />
  </div>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="ruleFormRef"
      :rules="rules"
      label-position="right"
      label-width="100px"
      :model="formLabelAlign"
      style="max-width: 460px"
    >
      <el-form-item label="数值">
        <el-input v-model="formLabelAlign.quantityFrom" />
      </el-form-item>
      <el-form-item label="原单位名称">
        <el-select v-model="formLabelAlign.unitCode" placeholder="原单位名称">
          <el-option
            v-for="item in measureOptions"
            :key="item.measureCode"
            :label="item.measureName"
            :value="item.measureCode"
            :disabled="item.enableFlag == 'N'"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="转换后数值" prop="quantityTo">
        <el-input v-model="formLabelAlign.quantityTo" />
      </el-form-item>
      <el-form-item label="现单位名称">
        <el-select v-model="formLabelAlign.unitTo" placeholder="转换后单位名称">
          <el-option
            v-for="item in measureOptions"
            :key="item.measureCode"
            :label="item.measureName"
            :value="item.measureCode"
            :disabled="item.enableFlag == 'N'"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="handleDialogSubmit(ruleFormRef)" type="primary" plain>确定</el-button>
      <el-button @click="handleDialogCancel">取消</el-button>
    </template>
  </Dialog>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { ElMessageBox } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import { columnTable, IPagination } from '../data'
import * as unitAPI from '@/api/masterData/unitConversion'

const ruleFormRef = ref<FormInstance>()

const rules = reactive<FormRules>({
  quantityTo: [
    {
      required: true,
      message: '请输入转换后数值',
      trigger: 'change'
    }
  ]
})

const emit = defineEmits(['handlePagination', 'handleRenewData'])

const props = defineProps({
  tableDataList: {
    type: Array,
    default: () => []
  },
  paginationData: {
    type: Object,
    default: () => {}
  }
})

// 对话框标题
const dialogTitle = ref('')

// 对话框内部参数
let formLabelAlign = reactive({
  // 数值
  quantityFrom: '',
  // 原单位名称
  unitCode: '',
  // 转换后数值
  quantityTo: '',
  // 现单位名称
  unitTo: '',
  conversionId: ''
})

// 对话框的显示与否
const dialogVisible = ref<boolean>(false)

// 修改的按钮禁用状态
const disabledUpData = ref(true)

// 删除的按钮禁用状态
const disableDelete = ref(true)

// 表格选中的数据
const selectionData = ref([])

// 原单位名称的数据
const measureOptions = ref([{ measureCode: '', measureName: '', enableFlag: '' }])

// 弹框的确定功能
const handleDialogSubmit = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  // if (formLabelAlign.unitName == formLabelAlign.unitTo) {
  //   return ElMessage({
  //     message: '现单位名称不能与原单位名称一致',
  //     type: 'warning'
  //   })
  // }
  await formEl.validate((valid) => {
    if (valid) {
      handleSaveUpData()
    }
  })
}

// 调用父组件表格的更新操作
const handleRenewData = () => {
  emit('handleRenewData')
}

// 弹框确定事件
const handleSaveUpData = () => {
  const found = measureOptions.value.find((element) => {
    element.measureCode === formLabelAlign.unitCode
  })?.measureName

  let params = {
    unitName: found,
    ...formLabelAlign
  }

  if (params.unitName == params.unitTo) {
    return ElMessage({
      message: '现单位名称不能与原单位名称一致',
      type: 'warning'
    })
  }

  if (params.conversionId != null) {
    unitAPI.updateConversion(params).then((res) => {
      const { code } = res
      if (code != 200) return
      handleRenewData()
    })
  } else {
    unitAPI.saveUnitConversion(params).then((res) => {
      const { code } = res
      if (code != 200) return
      handleRenewData()
    })
  }
}

const resetForm = () => {
  formLabelAlign.conversionId = ''
  formLabelAlign.quantityFrom = ''
  formLabelAlign.quantityTo = ''
  formLabelAlign.unitCode = ''
  formLabelAlign.unitTo = ''
}

// 弹框取消功能
const handleDialogCancel = async () => {
  resetForm()
  dialogVisible.value = !dialogVisible.value
}

// 表格选择事件
const handleSelectionChange = (value) => {
  selectionData.value = value.map((item) => item.conversionId)
  disableDelete.value = !selectionData.value.length
  disabledUpData.value = !selectionData.value.length || selectionData.value.length >= 2
}

// 新增
const handleAddData = () => {
  dialogTitle.value = '添加单位换算'
  dialogVisible.value = !dialogVisible.value
}

// 删除
const handleDeleteData = (value) => {
  let rowDataID = value.conversionId || selectionData.value
  ElMessageBox.confirm(`是否删除"${rowDataID}"数据`)
    .then(() => {
      // 执行删除接口
      console.log(rowDataID)
    })
    .catch(() => {
      // ElMessage({
      //   message: 'Congrats, this is a success message.',
      //   type: 'success'
      // })
    })
}

// 修改
const handleUqDataRow = (value) => {
  dialogVisible.value = !dialogVisible.value
  dialogTitle.value = '修改单位换算'
  let rowDataID = value.conversionId || selectionData.value
  unitAPI.queryUnitDetailed(rowDataID).then((res) => {
    const { code, data } = res || {}
    if (code !== 200) return
    formLabelAlign = data
  })
}

// 分页操作
const handlePagination = (value: IPagination) => {
  emit('handlePagination', value)
}
</script>

<style scoped></style>
