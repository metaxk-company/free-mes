<!-- eslint-disable vue/no-mutating-props -->
<template>
  <el-form :model="dialogFormParams" :rules="rules" label-width="120px" ref="ruleFormRef">
    <el-row :gutter="customerGutter.gutter">
      <el-col :span="8">
        <el-form-item label="供应商编码" prop="vendorCode">
          <el-input placeholder="请输入供应商编码" v-model="dialogFormParams.vendorCode" />
        </el-form-item>
      </el-col>
      <!-- <el-col :span="8" v-if="customerFormData.unKnowState == 'edit'">
        <el-form-item label="自动生成" prop="autoGenFlag">
          <el-switch
            v-model="dialogFormParams.autoGenFlag"
            @change="handleAutoGenChange(dialogFormParams.autoGenFlag)"
          />
        </el-form-item>
      </el-col> -->
      <el-col :span="8">
        <el-form-item label="供应商名称" prop="vendorName">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            placeholder="请输入供应商名称"
            v-model="dialogFormParams.vendorName"
          />
        </el-form-item>
      </el-col>
      <el-col :span="customerGutter.span">
        <el-form-item label="供应商简称" prop="vendorNick">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.vendorNick"
            placeholder="请输入供应商简称"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="客户英文名称" prop="vendorEn">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.vendorEn"
            placeholder="请输入客户英文名称"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="供应商简介" prop="vendorDes">
          <el-input
            autosize
            :disabled="customerFormData.unKnowState != 'edit'"
            type="textarea"
            v-model="dialogFormParams.vendorDes"
            placeholder="请输入客户简介"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="供应商地址" prop="address">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            autosize
            type="textarea"
            v-model="dialogFormParams.address"
            placeholder="请输入客户地址"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="供应商等级" prop="vendorLevel">
          <el-select
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.vendorLevel"
            placeholder="请选择客户类型"
            clearable
          >
            <el-option
              :label="item.label"
              :value="item.value"
              v-for="(item, index) in SUPPLIER_TYPE"
              :key="index"
            />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="供应商评分" prop="vendorScore">
          <el-tooltip
            effect="dark"
            raw-content
            content="<p>1星等级：E</p><p>2星等级：D</p><p>3星等级：C</p><p>4星等级：B</p><p>5星等级：A</p>"
            placement="top-start"
          >
            <el-rate
              :disabled="customerFormData.unKnowState != 'edit'"
              v-model="dialogFormParams.vendorScore"
            />
          </el-tooltip>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="供应商官网" prop="website">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.website"
            placeholder="请输入客户官网"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="供应商邮箱地址" prop="email">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.email"
            placeholder="请输入客户邮箱地址"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="供应商电话" prop="tel">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.tel"
            placeholder="请输入客户电话"
          />
        </el-form-item>
      </el-col>

      <el-col :span="customerGutter.span">
        <el-form-item label="联系人1-姓名" prop="contact1">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.contact1"
            placeholder="请输入联系人1-姓名"
          />
        </el-form-item>
      </el-col>

      <el-col :span="8">
        <el-form-item label="联系人1-电话" prop="contact1Tel">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.contact1Tel"
            placeholder="请输入联系人1-电话"
          />
        </el-form-item>
      </el-col>
      <el-col :span="customerGutter.span">
        <el-form-item label="联系人1-邮箱" prop="contact1Email">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.contact1Email"
            placeholder="请输入联系人1-邮箱"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="联系人2-姓名" prop="contact2">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.contact2"
            placeholder="请输入联系人2-姓名"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="联系人2-电话" prop="contact2Tel">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            v-model="dialogFormParams.contact2Tel"
            placeholder="请输入联系人2-电话"
          />
        </el-form-item>
      </el-col>
      <el-col :span="customerGutter.span">
        <el-form-item label="联系人2-邮箱" prop="contact2Email">
          <el-input
            v-model="dialogFormParams.contact2Email"
            placeholder="请输入联系人2-邮箱"
            :disabled="customerFormData.unKnowState != 'edit'"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="社会信用代码" prop="creditCode">
          <el-input
            v-model="dialogFormParams.creditCode"
            placeholder="请输入社会信用代码"
            :disabled="customerFormData.unKnowState != 'edit'"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="备注" prop="remark">
          <el-input
            :disabled="customerFormData.unKnowState != 'edit'"
            autosize
            type="textarea"
            v-model="dialogFormParams.remark"
            placeholder="请输入备注"
          />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="是否启用" prop="enableFlag">
          <el-radio-group
            v-model="dialogFormParams.enableFlag"
            :disabled="customerFormData.unKnowState != 'edit'"
          >
            <el-radio :label="value" v-for="({ label, value }, index) in unknownValid" :key="index">
              {{ label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
      </el-col>
      <!-- <el-col :span="customerGutter.spanTwo">
        <el-form-item label="客户LOGO" prop="vendorLogo">
          <el-input v-model="dialogFormParams.vendorLogo" placeholder="请输入客户LOGO" />
        </el-form-item>
      </el-col> -->
    </el-row>
  </el-form>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import type { FormRules, FormInstance } from 'element-plus'
import { SUPPLIER_TYPE, unknownValid } from '../data'
import { getCreateCode } from '@/api/masterData/materialManage'

defineProps({
  customerFormData: {
    type: Object,
    default: () => {}
  },
  dialogFormParams: {
    type: Object,
    default: () => {}
  }
})

const emit = defineEmits(['generateClientCode'])

const rules = reactive<FormRules>({
  vendorCode: [{ required: true, message: '供应商编码不能为空', trigger: 'blur' }],
  vendorName: [{ required: true, message: '供应商名称不能为空', trigger: 'blur' }],
  enableFlag: [{ required: true, message: '是否启用不能为空', trigger: 'blur' }]
})

const ruleFormRef = ref<FormInstance>()

const customerGutter = reactive({
  gutter: 20,
  span: 8,
  spanTwo: 12
})

// 校验事件
const submitForm = () => {
  return new Promise<void>((resolve, reject) => {
    ruleFormRef?.value?.validate((valid) => {
      if (valid) {
        resolve()
      }
    })
  })
}

const clearFormData = () => {
  ruleFormRef?.value?.resetFields()
}

// 自动生成
const handleAutoGenChange = () => {
  getCreateCode('VENDOR_CODE').then((res) => {
    emit('generateClientCode', res)
  })
}
handleAutoGenChange()
defineExpose({
  submitForm,
  clearFormData
})
</script>

<style scoped lang="scss"></style>
