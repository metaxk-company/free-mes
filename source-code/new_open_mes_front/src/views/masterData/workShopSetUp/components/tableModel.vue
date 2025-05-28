<template>
  <div style="margin-bottom: 16px">
    <el-button
      :type="item.type"
      plain
      :disabled="item.disabled"
      :icon="item.icon"
      @click="handleBtnEvent(item.state)"
      v-for="(item, index) in btnOperation"
      :key="index"
      >{{ item.label }}</el-button
    >
  </div>
  <CommonTable
    @selection-change="handleSelectionChange"
    @pagination-change="handlePaginationChange"
    :isSelection="true"
    :columns="columnState"
    :pagination="paginationData"
    :tableData="tableDataList"
    :loading="tableLoading"
  >
    <template #workshopCode="{ scope }">
      <el-button type="primary" link @click="handleWorkShopInfo(scope.row)">{{
        scope.row.workshopCode
      }}</el-button>
    </template>
    <template #enableFlag="{ scope }">
      <el-tag :type="scope.row.enableFlag === 'Y' ? 'success' : 'danger'">{{
        scope.row.enableFlag === 'Y' ? '是' : '否'
      }}</el-tag>
    </template>
    <template #operate="{ scope }">
      <el-button
        style="font-size: 17px"
        type="primary"
        link
        @click="handleEditWorkshop(scope.row)"
        size="small"
        >修改</el-button
      >
      <el-button
        style="font-size: 17px"
        link
        @click="handleRemoveWorkshop(scope.row)"
        size="small"
        type="danger"
        >删除</el-button
      >
    </template>
  </CommonTable>

  <XModal v-model="dialogVisible" :title="dialogTitle" @before-close="handleClose" width="35%">
    <el-form ref="ruleFormRef" :model="ruleForm" label-width="120px" :rules="rules" status-icon>
      <el-row>
        <el-col :span="22">
          <el-form-item label="车间编码" prop="workshopCode">
            {{ ruleForm.workshopCode }}
          </el-form-item>
        </el-col>
        <el-col :span="22">
          <el-form-item label="车间名称" prop="workshopName">
            <el-input v-model="ruleForm.workshopName" :disabled="dialogState == 'view'" />
          </el-form-item>
        </el-col>
        <el-col :span="22">
          <el-form-item label="负责人" prop="charge">
            <el-input v-model="ruleForm.charge" :disabled="dialogState == 'view'" />
          </el-form-item>
        </el-col>
        <el-col :span="22">
          <el-form-item label="面积" prop="area">
            <el-input-number
              v-model="ruleForm.area"
              :min="1"
              :max="9999"
              :disabled="dialogState == 'view'"
            />
          </el-form-item>
        </el-col>
        <el-col :span="22">
          <el-form-item label="是否启用" prop="enableFlag">
            <el-radio-group v-model="ruleForm.enableFlag" :disabled="dialogState == 'view'">
              <el-radio :label="item.value" v-for="(item, index) in UN_KNOW_ENABLE" :key="index">
                {{ item.label }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="22">
          <el-form-item label="备注" prop="remark">
            <el-input
              :disabled="dialogState == 'view'"
              v-model="ruleForm.remark"
              :rows="2"
              type="textarea"
              placeholder="请输入备注"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button
          v-if="dialogState !== 'view'"
          type="primary"
          @click="handleSubmitForm(ruleFormRef)"
          >确定</el-button
        >
        <el-button @click="handleClose">返回</el-button>
      </span>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { reactive, ref, nextTick } from 'vue'
import { btnOperation, columnState } from '../data'
import { UN_KNOW_ENABLE } from '@/utils/const'
import { ElMessageBox, ElMessage } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import CommonTable from '@/components/CommonTable/index.vue'
import { getCreateCode } from '@/api/masterData/materialManage'
import {
  addWorkshop,
  queryWorkshopInfo,
  upDateWorkshopInfo,
  deleteWorkshop
} from '@/api/masterData/workShopSetUp'

defineProps({
  paginationData: {
    type: Object,
    default: () => {}
  },
  tableDataList: {
    type: Array as any,
    default: () => []
  },
  tableLoading: {
    type: Boolean,
    default: false
  }
})
const emit = defineEmits(['handlePagination', 'renovateInfo'])

const rules = reactive<FormRules>({
  workshopCode: [{ required: true, message: '请输入车间编码', trigger: 'blur' }],
  workshopName: [{ required: true, message: '请输入车间名称', trigger: 'blur' }]
})
const ruleFormRef = ref<FormInstance>()

// 保存当前选择的数据
let selectionList = reactive<Array<Number>>([])
// 弹框显示
let dialogVisible = ref<boolean>(false)
// 弹框标题
let dialogTitle = ref<string>('')
// 弹框数据
let ruleForm = ref({
  // 车间编码
  workshopCode: '',
  //  自动生成
  autoGenFlag: false,
  // 车间名称
  workshopName: '',
  // 面积
  area: 1,
  // 负责人
  charge: '',
  // 是否启用
  enableFlag: 'Y',
  // 备注
  remark: '',
  id: ''
})
// 弹框状态
const dialogState = ref<string>('')
// 开关加载状态
const switchLoading = ref<boolean>(false)

// 按键操作点击事件
const handleBtnEvent = (value: string) => {
  switch (value) {
    case 'add':
      handleAddWorkshop()
      break
    case 'edit':
      handleEditWorkshop()
      break
    case 'remove':
      handleRemoveWorkshop()
      break
  }
}

// 增添
const handleAddWorkshop = () => {
  dialogState.value = 'edit'
  dialogVisible.value = !dialogVisible.value
  handleSwitch()
  dialogTitle.value = '添加车间'
}

// 查看
const handleWorkShopInfo = async (value) => {
  dialogState.value = 'view'
  dialogTitle.value = '查看车间'
  getWorkshopInfo(value)
}

// 修改
const handleEditWorkshop = async (value?) => {
  dialogState.value = 'edit'
  dialogTitle.value = '修改车间'
  getWorkshopInfo(value)
}

// 获取车间数据详情
const getWorkshopInfo = (value) => {
  const params = value?.id || selectionList
  queryWorkshopInfo(params).then((res) => {
    ruleForm.value = res
    dialogVisible.value = true
  })
}

// 删除
const handleRemoveWorkshop = (value?) => {
  const params = value?.id || selectionList
  const _id = Array.isArray(params) ? params : [params]

  ElMessageBox.confirm('是否删除所选数据?').then(() => {
    deleteWorkshop(_id).then((res) => {
      ElMessage.success('删除成功')
      emit('renovateInfo')
    })
  })
}

// 表格选择事件
const handleSelectionChange = (selection) => {
  selectionList = selection.map((item) => item.id)
  btnOperation[1].disabled = !selectionList.length || selectionList.length >= 2
  btnOperation[2].disabled = !selectionList.length
}

// 分页点击事件
const handlePaginationChange = ({ value }) => {
  emit('handlePagination', value)
}

// 生成车间编码
const handleSwitch = () => {
  ruleForm.value.workshopCode = ''
  getCreateCode('WORKSHOP_CODE').then((res) => {
    nextTick(() => {
      ruleForm.value.workshopCode = res
    })
  })
}

// 模态框 - 提交
const handleSubmitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      if (ruleForm.value.id) {
        upDateWorkshopInfo(ruleForm.value).then((res) => {
          handleClose()
        })
      } else {
        addWorkshop(ruleForm.value).then((res) => {
          handleClose()
        })
      }
    }
  })
}

// 模态框 - 关闭
const handleClose = () => {
  if (!ruleFormRef.value) return
  ruleFormRef.value.resetFields()
  ruleForm.value = {
    // 车间编码
    workshopCode: '',
    //  自动生成
    autoGenFlag: false,
    // 车间名称
    workshopName: '',
    // 面积
    area: 1,
    // 负责人
    charge: '',
    // 是否启用
    enableFlag: 'Y',
    // 备注
    remark: '',
    id: ''
  }
  dialogVisible.value = !dialogVisible.value
  emit('renovateInfo')
}
</script>

<style scoped lang="scss"></style>
