<template>
  <XModal
    v-model="pmOrderForm.visible"
    :title="pmOrderForm.tips"
    width="80%"
    height="700px"
    @close="handleCloseDialog"
  >
    <el-form ref="ruleFormRef" :model="pmOrderForm.ruleForm" :rules="rules" label-width="120px">
      <el-row>
        <el-col :span="8">
          <el-form-item label="编号(MO开头)" prop="workorderCode">
            <el-input
              v-model="pmOrderForm.ruleForm.workorderCode"
              placeholder="请输入订单编号"
              :disabled="!isEdit"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="订单名称" prop="workorderName">
            <el-input
              v-model="pmOrderForm.ruleForm.workorderName"
              placeholder="请输入订单名称"
              :disabled="!isEdit"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="来源类型" prop="orderSource">
            <el-radio-group v-model="pmOrderForm.ruleForm.orderSource" class="ml-4">
              <el-radio :label="item.label" :key="index" v-for="(item, index) in SOURCE_TYPE">{{
                item.text
              }}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="产品编号" prop="productCode">
            <el-input
              v-model="pmOrderForm.ruleForm.productCode"
              placeholder="请输入产品编号"
              :disabled="!isEdit"
            >
              <template #append>
                <el-button :icon="'DocumentAdd'" @click="handleSelectProduct" :disabled="!isEdit" />
              </template>
            </el-input>
            <materialMiniModel ref="itemSelectRef" @handle-current-submit="currentData" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="产品名称" prop="productName">
            <el-input
              v-model="pmOrderForm.ruleForm.productName"
              placeholder="请输入产品名称"
              :disabled="!isEdit"
            />
          </el-form-item>
        </el-col>
        <!-- <el-col :span="8" v-if="pmOrderForm.ruleForm.orderSource == 'ORDER'">
          <el-form-item label="订单编号" prop="sourceCode">
            <el-input
              v-model="pmOrderForm.ruleForm.sourceCode"
              placeholder="请输入订单编号"
              :disabled="!isEdit"
            />
          </el-form-item>
        </el-col> -->
      </el-row>
      <el-row />
      <el-row>
        <el-col :span="8">
          <el-form-item label="规格型号" prop="productSpc">
            <el-input
              v-model="pmOrderForm.ruleForm.productSpc"
              placeholder="请输入规格型号"
              :disabled="!isEdit"
            />
          </el-form-item>
        </el-col>

        <el-col :span="8">
          <el-form-item label="单位" prop="unitOfMeasure">
            <el-input
              v-model="pmOrderForm.ruleForm.unitOfMeasure"
              placeholder="请输入单位"
              :disabled="!isEdit"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="订单数量" prop="quantity">
            <el-input-number
              :min="1"
              :disabled="!isEdit"
              v-model="pmOrderForm.ruleForm.quantity"
              placeholder="请输入生产数量"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item label="预计生产日期" prop="produceDate">
            <el-date-picker
              clearable
              :disabled="!isEdit"
              v-model="pmOrderForm.ruleForm.produceDate"
              type="date"
              value-format="YYYY-MM-DD"
              placeholder="请选择预计生产日期"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="预计结束日期" prop="requestDate">
            <el-date-picker
              clearable
              :disabled="!isEdit"
              v-model="pmOrderForm.ruleForm.requestDate"
              type="date"
              value-format="YYYY-MM-DD"
              placeholder="请选择预计结束日期"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="订购日期" prop="orderDate">
            <el-date-picker
              clearable
              :disabled="!isEdit"
              v-model="pmOrderForm.ruleForm.orderDate"
              type="date"
              value-format="YYYY-MM-DD"
              placeholder="请选择订购日期"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="批次号" prop="batchCode">
            <el-input
              v-model="pmOrderForm.ruleForm.batchCode"
              placeholder="请输入批次号"
              :disabled="!isEdit"
            />
          </el-form-item>
        </el-col>
        <div> </div>
        <el-col :span="8" v-if="pmOrderForm.ruleForm.orderSource == 'ORDER'">
          <el-form-item label="客户编码" prop="clientCode">
            <el-input
              v-model="pmOrderForm.ruleForm.clientCode"
              placeholder="请输入客户编码"
              :disabled="!isEdit"
            >
              <template #append>
                <el-button :icon="'DocumentAdd'" @click="handleSelectClient" :disabled="!isEdit" />
              </template>
            </el-input>
            <clientModel @handle-current-submit="handleClientCurrentRow" ref="clientModelRef" />
          </el-form-item>
        </el-col>
        <el-col :span="8" v-if="pmOrderForm.ruleForm.orderSource == 'ORDER'">
          <el-form-item label="客户名称" prop="clientName">
            <el-input
              v-model="pmOrderForm.ruleForm.clientName"
              placeholder="请输入客户名称"
              :disabled="!isEdit"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="备注" prop="remark">
            <el-input
              v-model="pmOrderForm.ruleForm.remark"
              type="textarea"
              autosize
              placeholder="请输入内容"
              :disabled="!isEdit"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row />
    </el-form>
    <el-tabs type="border-card" v-if="!!pmOrderForm.ruleForm.id">
      <el-tab-pane label="BOM组成">
        <bom-model :work-order-id="pmOrderForm.ruleForm.id" />
      </el-tab-pane>
      <!-- <el-tab-pane label="物料需求">
        <material-model :work-order-id="pmOrderForm.ruleForm.id" />
      </el-tab-pane> -->
    </el-tabs>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="handleSubmit" :loading="submitLoading" v-if="isEdit"
          >确定</el-button
        >
        <el-button
          type="success"
          :loading="submitLoading"
          @click="handleFulfill"
          v-if="pmOrderForm.ruleForm?.status == 'PREPARE' && !!pmOrderForm.ruleForm?.id"
          >完成</el-button
        >
        <el-button @click="handleCloseDialog">取消</el-button>
      </span>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { nextTick, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import { SOURCE_TYPE } from '../data'
import materialMiniModel from './materialMiniModel/index.vue'
import clientModel from './clientModel/index.vue'
import bomModel from './bomModel/index.vue'
// import materialModel from './materialModel/index.vue'
import { addWorkOrder, createWordOrder, upDataWorkOrder } from '@/api/prodMgmt/pmOrder'

const props = defineProps({
  pmOrderForm: {
    type: Object,
    default: () => {}
  }
})

const emit = defineEmits(['upDateTableList'])

const rules = reactive<FormRules>({
  workorderCode: [{ required: true, message: '订单编号不能为空', trigger: 'blur' }],
  workorderName: [{ required: false, message: '订单名称不能为空', trigger: 'blur' }],
  orderSource: [{ required: true, message: '来源类型不能为空', trigger: 'blur' }],
  productId: [{ required: true, message: '产品不能为空', trigger: 'blur' }],
  productCode: [{ required: true, message: '产品编号不能为空', trigger: 'blur' }],
  productName: [{ required: true, message: '产品名称不能为空', trigger: 'blur' }],
  unitOfMeasure: [{ required: true, message: '单位不能为空', trigger: 'blur' }],
  quantity: [{ required: true, message: '生产数量不能为空', trigger: 'blur' }],
  requestDate: [{ required: true, message: '预计结束日期不能为空', trigger: 'blur' }],
  orderDate: [{ required: true, message: '订购日期不能为空', trigger: 'blur' }],
  produceDate: [{ required: true, message: '预计生产日期不能为空', trigger: 'blur' }]
})
const ruleFormRef = ref<FormInstance>()
const clientModelRef = ref<any>(null)
const itemSelectRef = ref<any>(null)
const submitLoading = ref<boolean>(false)

const isEdit = computed(() => props.pmOrderForm.state == 'edit')

const handleFulfill = () => {
  ElMessageBox.confirm('是否完成订单编制？【完成后将不能更改】').then(() => {
    props.pmOrderForm.ruleForm.status = 'CONFIRMED'
    handleSubmit()
  })
}

// 自动生成功能
/*
const handleAutoGenChange = () => {
  createWordOrder('WORKORDER_CODE').then((res) => {
    nextTick(() => {
      props.pmOrderForm.ruleForm.workorderCode = res
    })
  })
}
handleAutoGenChange()
*/
// 产品编号的选择功能
const handleSelectProduct = () => {
  itemSelectRef.value.visibleProduct = true
}

// 客户选择按钮弹框
const handleSelectClient = () => {
  clientModelRef.value.visibleClient = true
}

// 产品编号选中功能
const currentData = (value) => {
  if (value == null) return
  props.pmOrderForm.ruleForm.productId = value.id
  props.pmOrderForm.ruleForm.productCode = value.itemCode
  props.pmOrderForm.ruleForm.productName = value.itemName
  props.pmOrderForm.ruleForm.productSpc = value.specification
  props.pmOrderForm.ruleForm.unitOfMeasure = value.unitOfMeasure
}

// 客户选择选中功能
const handleClientCurrentRow = (value) => {
  props.pmOrderForm.ruleForm.clientCode = value.clientCode
  props.pmOrderForm.ruleForm.clientName = value.clientName
}

// 数据新增修改提交
const handleSubmit = () => {
  if (!ruleFormRef.value) return
  ruleFormRef.value.validate((valid, fields) => {
    if (valid) {
      const oldOrderCode = props.pmOrderForm.ruleForm.workorderCode
      if (oldOrderCode.substring(0, 2) !== 'MO') {
        const newOrderCode = 'MO' + props.pmOrderForm.ruleForm.workorderCode
        props.pmOrderForm.ruleForm.workorderCode = newOrderCode
        if (props.pmOrderForm.ruleForm.workorderName === '') {
          props.pmOrderForm.ruleForm.workorderName = newOrderCode
        }
      }

      if (!!props.pmOrderForm.ruleForm.id) {
        const params = {
          workorderId: props.pmOrderForm.ruleForm.id,
          ...props.pmOrderForm.ruleForm
        }

        // 修改
        upDataWorkOrder(params).then((res) => {
          ElMessage({
            message: '修改成功',
            type: 'success'
          })
          handleCloseDialog()
        })
      } else {
        // 新增
        submitLoading.value = true
        addWorkOrder(props.pmOrderForm.ruleForm)
          .then((res) => {
            ElMessage({
              message: '新增成功',
              type: 'success'
            })
            props.pmOrderForm.ruleForm.id = res || ''
            upDateTableList()
          })
          .finally(() => {
            submitLoading.value = false
          })
      }
    }
  })
}

// 更新父级的表格数据
const upDateTableList = () => {
  emit('upDateTableList')
}

const handleCloseDialog = () => {
  if (!ruleFormRef.value) return
  ruleFormRef.value.resetFields()
  props.pmOrderForm.ruleForm = {}
  upDateTableList()
  props.pmOrderForm.visible = false
}
</script>

<style scoped lang="scss"></style>
