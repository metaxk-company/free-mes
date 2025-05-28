<template>
  <el-form ref="refProduct" :model="productFormData" :rules="rules" label-width="120px">
    <el-form-item label="设备编码">
      {{ productFormData.machineryCode }}
    </el-form-item>
    <el-row>
      <el-col :span="11">
        <el-form-item label="设备名称" prop="machineryName">
          <el-input
            v-model="productFormData.machineryName"
            placeholder="请输入设备名称"
            :disabled="dialogState === 'View'"
          />
        </el-form-item>
      </el-col>
      <el-col :span="11">
        <el-form-item label="品牌名称" prop="machineryBrand">
          <el-input
            v-model="productFormData.machineryBrand"
            :disabled="dialogState === 'View'"
            placeholder="请输入品牌"
          />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="11">
        <el-form-item label="设备分类" prop="machineryTypeId">
          <el-cascader
            :disabled="dialogState === 'View'"
            :options="treeMaterialForm.treeData"
            :show-all-levels="false"
            :props="cascadeProps"
            v-model="productFormData.machineryTypeId"
          />
        </el-form-item>
      </el-col>
      <el-col :span="11">
        <el-form-item label="规格型号" prop="machinerySpec">
          <el-input
            v-model="productFormData.machinerySpec"
            :disabled="dialogState === 'View'"
            placeholder="请输入规格型号"
          />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="11">
        <el-form-item label="所属车间" prop="workshopId">
          <el-select
            @change="handleSelectData"
            v-model="productFormData.workshopId"
            :disabled="dialogState === 'View'"
          >
            <el-option
              v-for="item in measureOptions"
              :key="item.id"
              :label="item.workshopName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="11">
        <el-form-item label="设备状态" prop="status">
          <el-select
            @change="handleSelectData"
            v-model="productFormData.status"
            :disabled="dialogState === 'View'"
          >
            <el-option
              v-for="item in typeStateList"
              :key="item.id"
              :label="item.statusName"
              :value="item.statusName"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="11">
        <el-form-item label="位置" prop="location">
          <el-input
            v-model="productFormData.location"
            :disabled="dialogState === 'View'"
            placeholder="请输入位置"
          />
        </el-form-item>
      </el-col>
      <el-col :span="11">
        <el-form-item label="产能" prop="capacity">
          <el-input
            v-model="productFormData.capacity"
            :disabled="dialogState === 'View'"
            placeholder="请输产能"
          />
        </el-form-item>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="11">
        <el-form-item label="购买日期" prop="purchasingTime">
          <el-date-picker
            placeholder="请输入购买日期"
            type="date"
            :value-format="'YYYY-MM-DD'"
            v-model="productFormData.purchasingTime"
          />
        </el-form-item>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="22">
        <el-form-item label="备注">
          <el-input
            :disabled="dialogState === 'View'"
            v-model="productFormData.remark"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>

<script setup lang="ts">
import { reactive, ref, nextTick } from 'vue'
import type { PropType } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { getCreateCode } from '@/api/masterData/materialManage'

interface UnitOfMeasure {
  workshopCode: string
  workshopName: string
  id: string
}
interface TypeState {
  statusName: string
  id: string
}

const props = defineProps({
  typeStateList: {
    type: Array as PropType<TypeState[]>,
    default: () => []
  },
  measureOptions: {
    type: Array as PropType<UnitOfMeasure[]>,
    default: () => []
  },
  productFormData: {
    type: Object,
    default: () => {}
  },
  treeMaterialForm: {
    type: Object,
    default: () => {}
  },
  dialogState: {
    type: String,
    default: () => ''
  }
})

const cascadeProps = ref({
  value: 'id',
  label: 'machineryTypeName',
  children: 'children',
  checkStrictly: true
})

const refProduct = ref<FormInstance | any>()

const rules = reactive<FormRules>({
  machineryCode: [{ required: true, message: '设备编码不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '设备状态不能为空', trigger: 'blur' }],
  machineryName: [{ required: true, message: '设备名称不能为空', trigger: 'blur' }],
  machineryTypeId: [{ required: true, message: '设备分类不能为空', trigger: 'blur' }],
  workshopId: [{ required: true, message: '所属车间不能为空', trigger: 'blur' }]
})

const handleSelectData = (value) => {
  props.productFormData.workshopCode = props.measureOptions.find(
    (item) => item.id == value
  )?.workshopCode
  props.productFormData.workshopName = props.measureOptions.find(
    (item) => item.id == value
  )?.workshopName
}

// 自动生成物料编码标识
const handleAutoGenChange = () => {
  const params = 'MACHINERY_CODE'
  props.productFormData.machineryCode = ''
  getCreateCode(params).then((res) => {
    nextTick(() => {
      props.productFormData.machineryCode = res
    })
  })
}

if (!props.productFormData.id) {
  handleAutoGenChange()
}

// 弹框 - 确定
const handleSubmitForm = async () => {
  if (!refProduct?.value) return
  return await new Promise((resolve) => {
    refProduct.value.validate((valid) => {
      resolve(valid)
    })
  })
}

// 弹框 - 取消
const handleCancel = () => {
  if (!refProduct.value) return
  refProduct.value.resetFields()
}
defineExpose({
  handleSubmitForm,
  handleCancel,
  handleAutoGenChange
})
</script>

<style scoped lang="scss"></style>
