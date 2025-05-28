<template>
  <ContentWrap style="height: 100vh; display: flex; justify-content: center; align-items: center">
    <h3 style="font-weight: bold; font-size: 20px; margin-bottom: 20px; text-align: center"
      >工人报工信息</h3
    >

    <div>
      <el-form
        size="large"
        ref="formRef"
        :model="formData ? formData : dynamicValidateForm"
        label-width="130px"
        :rules="formData ? {} : rules"
      >
        <el-row>
          <el-col :span="12">
            <el-form-item prop="taskOrder" label="任务单号">
              <el-input v-model="dynamicValidateForm.taskOrder" v-if="visible != 'view'" />
              <div v-else class="describe-center">{{ formData.taskOrder }}</div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="startDate" label="填报日期">
              <el-date-picker
                placeholder="请选择填报日期"
                type="date"
                :default-value="new Date()"
                v-model="dynamicValidateForm.startDate"
                v-if="visible != 'view'"
              />
              <div v-else class="describe-center">{{ formData.startDate }}</div>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item prop="nameOfPerson" label="人员姓名">
              <el-input
                placeholder="请填写人员姓名"
                v-if="visible != 'view'"
                v-model="dynamicValidateForm.nameOfPerson"
                clearable
              />
              <div v-else class="describe-center">{{ formData.nameOfPerson }}</div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="personnelNo" label="人员编号">
              <el-input
                placeholder="请填写人员编号"
                v-model="dynamicValidateForm.personnelNo"
                v-if="visible != 'view'"
                clearable
              />
              <div v-else class="describe-center">{{ formData.personnelNo }}</div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item prop="workingType" label="工时类型">
              <el-select
                v-if="visible != 'view'"
                v-model="dynamicValidateForm.workingType"
                placeholder="工时类型选择"
              >
                <el-option
                  v-for="item in optionsList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
              <div v-else class="describe-center">{{ formData.workingType }}</div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="Workbench" label="工作台">
              <el-input
                placeholder="请填写工作台"
                v-if="visible != 'view'"
                v-model="dynamicValidateForm.Workbench"
                clearable
              />
              <div v-else class="describe-center">{{ formData.Workbench }}</div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item prop="productionQuantities" label="生产数量">
              <el-input-number
                v-if="visible != 'view'"
                placeholder="请填写生产数量"
                v-model="dynamicValidateForm.productionQuantities"
                clearable
              />
              <div v-else class="describe-center">{{ formData.productionQuantities }}</div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="hoursWork" label="工时时长">
              <el-input-number
                placeholder="请填写工时时长"
                v-model="dynamicValidateForm.hoursWork"
                v-if="visible != 'view'"
                clearable
              />
              <div v-else class="describe-center">{{ formData.hoursWork }}</div>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="备注">
          <el-input
            v-model="dynamicValidateForm.remark"
            :rows="4"
            v-if="visible != 'view'"
            type="textarea"
            clearable
            placeholder="请输入备注"
          />
          <div v-else class="describe-center">{{ formData.remark }}</div>
        </el-form-item>

        <div style="display: flex; justify-content: center" v-if="visible != 'view'">
          <el-button type="primary" @click="submitForm(formRef)">确定</el-button>
          <el-button @click="resetForm(formRef)">重置</el-button>
        </div>
      </el-form>
    </div>
  </ContentWrap>
</template>

<script lang="ts" setup>
import { reactive, ref, onMounted } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { getWorkHoursTypeList } from '@/api/prodMgmt/workHoursType'

defineProps({
  visible: {
    type: String,
    required: false
  },
  formData: {
    type: Object,
    required: false
  }
})

const optionsList = ref<any>([])
const formRef = ref<FormInstance>()
let dynamicValidateForm = ref<{
  taskOrder: string
  startDate: any
  nameOfPerson: string
  personnelNo: string
  workingType: string
  hoursWork: number
  productionQuantities: number
  Workbench: string
  remark: string
}>({
  taskOrder: '',
  startDate: new Date(),
  nameOfPerson: '',
  personnelNo: '',
  workingType: '',
  hoursWork: 0,
  productionQuantities: 0,
  Workbench: '',
  remark: ''
})
const rules = reactive<FormRules>({
  taskOrder: [{ required: true, message: '任务单号不能为空', trigger: 'blur' }],
  startDate: [{ required: true, message: '填报日期不能为空', trigger: 'blur' }],
  nameOfPerson: [{ required: true, message: '人员姓名不能为空', trigger: 'blur' }],
  personnelNo: [{ required: true, message: '人员编号不能为空', trigger: 'blur' }],
  workingType: [{ required: true, message: '工时类型不能为空', trigger: 'blur' }],
  hoursWork: [{ required: true, message: '工时时长不能为空', trigger: 'blur' }],
  productionQuantities: [{ required: true, message: '生产数量不能为空', trigger: 'blur' }],
  Workbench: [{ required: true, message: '工作台不能为空', trigger: 'blur' }]
})

const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      console.log('submit!')
    }
  })
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  // formEl.resetFields()
  dynamicValidateForm.value = {
    taskOrder: '',
    startDate: '',
    nameOfPerson: dynamicValidateForm.value.nameOfPerson,
    personnelNo: dynamicValidateForm.value.personnelNo,
    workingType: '',
    hoursWork: 0,
    productionQuantities: 0,
    Workbench: '',
    remark: ''
  }
}

// 获取工时类型的下拉参数
const selectionList = () => {
  const params = {}

  getWorkHoursTypeList(params).then((res) => {
    const selectionWorkHoursTypeList = res.list.map((res) => {
      return {
        label: res.workhoursType,
        value: res.workhoursType
      }
    })
    optionsList.value = selectionWorkHoursTypeList
  })
}

const info = () => {
  selectionList()
}

onMounted(() => {
  info()
})
</script>

<style lang="scss">
.describe-center {
  font-size: 17px;
  font-weight: bold;
}
</style>
