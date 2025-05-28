<!-- eslint-disable vue/no-mutating-props -->
<template>
  <el-form :model="dialogFormParams" :rules="rules" ref="ruleFormRef" label-width="130px">
    <el-row :gutter="customerGutter.gutter">
      <el-col :span="8">
        <el-form-item label="客户编码" prop="clientCode">
          <el-input placeholder="请输入客户编码" v-model="dialogFormParams.clientCode" />
        </el-form-item>
      </el-col>
      <!-- <el-col :span="8" v-if="customerFormData.unKnowState == 'edit'">
        <el-form-item label="自动生成">
          <el-switch
            v-model="dialogFormParams.autoGenFlag"
            @change="handleAutoGenChange(dialogFormParams.autoGenFlag)"
          />
        </el-form-item>
      </el-col> -->
      <el-col :span="8">
        <el-form-item label="客户名称" prop="clientName">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            placeholder="请输入客户名称"
            v-model="dialogFormParams.clientName"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="订单号">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            placeholder="请输入订单号"
            v-model="dialogFormParams.orderNumber"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="客户简称">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.clientNick"
            placeholder="请输入客户简称"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="客户英文名称">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.clientEn"
            placeholder="请输入客户英文名称"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="客户类型">
          <el-select
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.clientType"
            placeholder="请选择客户类型"
            clearable
          >
            <el-option
              :label="item.label"
              :value="item.value"
              v-for="(item, index) in enterpriseType"
              :key="index"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="客户简介">
          <el-input
            autosize
            :disabled="customerFormData.unKnowState != 'edit'"
            type="textarea"
            v-model="dialogFormParams.clientDes"
            placeholder="请输入客户简介"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="客户地址">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            type="textarea"
            autosize
            v-model="dialogFormParams.address"
            placeholder="请输入客户地址"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="客户官网">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.website"
            placeholder="请输入客户官网地址"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="客户邮箱">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.email"
            placeholder="请输入客户邮箱地址"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="客户电话">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.tel"
            placeholder="请输入客户电话"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="社会信用代码">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.creditCode"
            placeholder="请输入社会信用代码"
          />
        </el-form-item>
      </el-col>
      <!-- <el-col :span="23"> -->
      <!-- <el-form-item label="客户LOGO">
          <el-input v-model="dialogFormParams.clientLogo" placeholder="请输入客户LOGO" />
        </el-form-item> -->
      <!-- </el-col> -->
      <el-col :span="8">
        <el-form-item label="联系人1-姓名">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.contact1"
            placeholder="联系人1-姓名"
          />
        </el-form-item>
      </el-col>

      <el-col :span="8">
        <el-form-item label="联系人1-电话">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.contact1Tel"
            placeholder="请输入联系人1-电话"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="联系人1-邮箱">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.contact1Email"
            placeholder="联系人1-邮箱"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="联系人2-姓名">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.contact2"
            placeholder="联系人2-姓名"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="联系人2-电话">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.contact2Tel"
            placeholder="联系人2-电话"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="联系人2-邮箱">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.contact2Email"
            placeholder="联系人2-邮箱"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="备注">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            type="textarea"
            v-model="dialogFormParams.remark"
            autosize
            placeholder="请输入备注"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="出库上限">
          <el-input-number
            :min="0"
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.outMax"
            placeholder="请输入出库上限"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="出库下限">
          <el-input-number
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.outMin"
            :min="0"
            placeholder="请输入出库下限"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="是否有效" prop="enableFlag">
          <el-radio-group
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.enableFlag"
          >
            <el-radio :label="value" v-for="({ label, value }, index) in unknownValid" :key="index">
              {{ label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import type { FormRules, FormInstance } from 'element-plus'
import { enterpriseType, unknownValid } from '../data'
import { ElMessage } from 'element-plus'
import { addClient, upDateClientInfo } from '@/api/masterData/clientManage'
import { createWordOrder } from '@/api/prodMgmt/pmOrder'

const props = defineProps({
  customerFormData: {
    type: Object,
    default: () => {}
  },
  dialogFormParams: {
    type: Object,
    default: () => {}
  }
})

const emit = defineEmits(['upDateFormData'])

const rules = reactive<FormRules>({
  clientName: [{ required: true, message: '请输入客户名称', trigger: 'blur' }],
  clientCode: [{ required: true, message: '请输入客户编码', trigger: 'blur' }],
  enableFlag: [
    {
      required: true,
      message: '请选择是否有效',
      trigger: 'change'
    }
  ]
})

const ruleFormRef = ref<FormInstance>()

const customerGutter = reactive({
  gutter: 20,
  span: 7,
  spanTwo: 12
})

// 提交 or 修改
const submitForm = () => {
  ruleFormRef?.value?.validate((valid) => {
    if (valid) {
      if (props.dialogFormParams.id) {
        upDateClientInfo(props.dialogFormParams).then((res) => {
          ElMessage.success('修改成功')
          emit('upDateFormData')
        })
      } else {
        addClient(props.dialogFormParams).then((res) => {
          ElMessage.success('新增成功')
          emit('upDateFormData')
        })
      }
    }
  })
}

const resetForm = () => {
  if (!ruleFormRef.value) return
  ruleFormRef.value.resetFields()
}

// 自动生成
/*
const handleAutoGenChange = () => {
  createWordOrder('CLIENT_CODE').then((res) => {
    nextTick(() => {
      props.dialogFormParams.clientCode = res
    })
  })
}
 */

//去掉点击修改或详情时自动生成客户编号
//handleAutoGenChange()

defineExpose({
  submitForm,
  resetForm
})
</script>

<style scoped lang="scss"></style>
