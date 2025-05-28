<!-- eslint-disable vue/no-mutating-props -->
<template>
  <el-row :gutter="10" style="margin-bottom: 20px">
    <div v-for="(item, index) in btnClickState" :key="index">
      <el-col :span="2">
        <el-button
          @click="handleBtnCollections(item)"
          :disabled="item.disabled"
          :type="item.type"
          :plain="item.plain"
        >
          {{ item.text }}</el-button
        >
      </el-col>
    </div>
  </el-row>

  <!-- handleSelectionChange -->
  <common-table
    :loading="tableLoading"
    :tableData="tableFormList"
    :columns="columnState"
    :pagination="paginationData"
    @pagination-change="handlePagination"
    @selection-change="handleSelectionChange"
    :is-selection="true"
    :is-border="false"
  >
    <template #vendorCode="{ scope }">
      <el-button link type="primary" @click="handleClientCode(scope)">
        {{ scope.row.vendorCode }}</el-button
      >
    </template>
    <template #vendorScore="{ scope }">
      <el-rate v-model="scope.row.vendorScore" disabled />
    </template>
    <template #enableFlag="{ scope }">
      <el-tag :type="scope.row.enableFlag === 'Y' ? 'success' : 'danger'">{{
        scope.row.enableFlag === 'Y' ? '是' : '否'
      }}</el-tag>
    </template>
    <template #operation="{ scope }">
      <el-button style="font-size: 17px" link type="primary" @click="handleClientCode(scope)"
        >查看</el-button
      >
      <el-button style="font-size: 17px" link type="primary" @click="handleModifyInfo(scope)"
        >修改</el-button
      >
      <el-button style="font-size: 17px" link type="danger" @click="handleDeleteInfo(scope)"
        >删除</el-button
      >
    </template>
  </common-table>

  <XModal
    :title="dialogParams.dialogTitle"
    v-model="dialogParams.dialogVisible"
    width="100%"
    @close="handleCancel"
  >
    <dialog-form
      ref="dialogFormDefine"
      :dialog-form-params="dialogFormParams"
      :customer-form-data="dialogParams"
    />

    <template #footer>
      <el-button type="primary" @click="handleSubmit">{{ dialogBtnTitle }}</el-button>
      <el-button @click="handleCancel">取消</el-button>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { computed, reactive, ref, PropType } from 'vue'
import dialogForm from './dialogForm.vue'
import { IBtnClickFun, columnState } from '../../supplierManage/data'
import { ElMessageBox, ElMessage } from 'element-plus'
import commonTable from '@/components/CommonTable/index.vue'
import {
  addVendor,
  queryVendorInfo,
  upDateVendorInfo,
  deleteVendor,
  downloadListData
} from '@/api/masterData/supplierManage'
import download from '@/utils/download'

const btnClickState = reactive<IBtnClickFun[]>([
  {
    text: '新增',
    type: 'primary',
    plain: true,
    disabled: false,
    sign: 'save'
  },
  {
    text: '修改',
    type: 'success',
    plain: true,
    disabled: true,
    sign: 'uqData'
  },
  {
    text: '批量删除',
    type: 'danger',
    plain: true,
    disabled: true,
    sign: 'delete'
  },
  {
    text: '导出',
    type: 'warning',
    plain: true,
    disabled: false,
    sign: 'export'
  }
])

defineProps({
  tableFormList: {
    type: Array<object>,
    default: () => Array<object>
  },
  paginationData: {
    type: Object as PropType<Table.Pagination>,
    default: () => {}
  },
  tableLoading: {
    type: Boolean
  }
})

const emit = defineEmits(['handlePagination', 'renewalData'])

type ResponseParamsCtx = InstanceType<typeof dialogForm>
const dialogFormDefine = ref<ResponseParamsCtx | null>(null)

const dialogParams = reactive({
  dialogTitle: 'www',
  dialogVisible: false,
  unKnowState: 'edit'
})

let dialogFormParams = ref({
  // 供应商编码
  vendorCode: '',
  // 自动生成
  autoGenFlag: false,
  // 供应商名称
  vendorName: '',
  // 供应商简称
  vendorNick: '',
  // 供应商英文名称
  vendorEn: '',
  // 供应商简介
  vendorDes: '',
  //  供应商地址
  address: '',
  // 供应商等级
  vendorLevel: '',
  // 供应商评分
  vendorScore: 0,
  // 供应商官网地址
  website: '',
  // 供应商邮箱地址
  email: '',
  // 供应商电话
  tel: '',
  contact1: '',
  contact1Tel: '',
  contact1Email: '',
  contact2: '',
  contact2Tel: '',
  contact2Email: '',
  creditCode: '',
  enableFlag: 'Y',
  vendorLogo: '',
  remark: '',
  id: ''
})

// 存储当前列表选中的值
const setSelectionList = ref([])

const dialogBtnTitle = computed(() => {
  return dialogParams.unKnowState == 'edit' ? '确定' : '返回'
})

// 弹框确定功能
const handleSubmit = () => {
  if (dialogParams.unKnowState == 'edit') {
    dialogFormDefine?.value?.submitForm().then(() => {
      // 是否有ID
      if (dialogFormParams.value.id) {
        upDateVendorInfo(dialogFormParams.value).then((res) => {
          if (!res) return
          ElMessage.success('修改成功')
          handleCancel()
        })
      } else {
        addVendor(dialogFormParams.value).then((res) => {
          ElMessage.success('新增成功')
          handleCancel()
        })
      }
    })
  } else {
    handleCancel()
  }
}

// 弹框取消功能
const handleCancel = () => {
  dialogFormDefine.value?.clearFormData()
  dialogFormParams.value = {
    // 供应商编码
    vendorCode: '',
    // 自动生成
    autoGenFlag: false,
    // 供应商名称
    vendorName: '',
    // 供应商简称
    vendorNick: '',
    // 供应商英文名称
    vendorEn: '',
    // 供应商简介
    vendorDes: '',
    //  供应商地址
    address: '',
    // 供应商等级
    vendorLevel: '',
    // 供应商评分
    vendorScore: 0,
    // 供应商官网地址
    website: '',
    // 供应商邮箱地址
    email: '',
    // 供应商电话
    tel: '',
    contact1: '',
    contact1Tel: '',
    contact1Email: '',
    contact2: '',
    contact2Tel: '',
    contact2Email: '',
    creditCode: '',
    enableFlag: 'Y',
    vendorLogo: '',
    remark: '',
    id: ''
  }
  emit('renewalData')
  dialogParams.dialogVisible = false
}

// 是否需要自动生成客户端代码
/*
const handleGenerateClientCodeChange = (value) => {
  // 新增 修改 查看生成客户端代码
  if (dialogParams.unKnowState == 'edit') {
    //新增or修改
    if (!dialogFormParams.value.id) {
      // 新增
      dialogFormParams.value.vendorCode = value //自动生成客户端代码
    }
  }
}
 */

// 表格勾选数据
const handleSelectionChange = (selection) => {
  setSelectionList.value = selection.map((item) => item.id)
  btnClickState[1].disabled = !setSelectionList.value.length || setSelectionList.value.length >= 2
  btnClickState[2].disabled = !setSelectionList.value.length
}

// 客户编码点击事件
const handleClientCode = (value) => {
  const { row } = value || {}
  dialogParams.unKnowState = 'view'
  dialogParams.dialogTitle = '查看供应商'
  const clientId = row?.id || setSelectionList.value

  // 调用查询客户信息详情接口
  handleModifyInfoAPI(clientId)
}

// 修改操作
const handleModifyInfo = (value?) => {
  const { row } = value || {}
  dialogParams.unKnowState = 'edit'
  dialogParams.dialogTitle = '修改供应商'
  const clientId = row?.id || setSelectionList.value
  handleModifyInfoAPI(clientId)
}

const handleModifyInfoAPI = (value) => {
  queryVendorInfo(value).then((res) => {
    dialogFormParams.value = res
    dialogParams.dialogVisible = true
  })
}

// 删除操作
const handleDeleteInfo = (value?) => {
  const { row } = value || {}
  const clientId = row?.id || setSelectionList.value
  const id = Array.isArray(clientId) ? clientId : [clientId]
  console.log(id)

  ElMessageBox.confirm(`是否确认删除客户编号为:${clientId}`).then(() => {
    deleteVendor(id)
      .then((_res) => {
        ElMessage.success('删除成功')
        emit('renewalData')
      })
      .finally(() => {})
  })
}

// 四种点击的功能区域
const handleBtnCollections = (value: IBtnClickFun) => {
  if (value.sign == '') return
  switch (value.sign) {
    case 'save':
      dialogParams.unKnowState = 'edit'
      dialogParams.dialogTitle = '添加供应商'
      dialogParams.dialogVisible = !dialogParams.dialogVisible
      break
    case 'uqData':
      // dialogParams.unKnowState = 'updata'
      handleModifyInfo()
      break
    case 'delete':
      handleDeleteInfo()
      break
    case 'export':
      handleDownload()
      break
  }
}

// 导出功能
const handleDownload = async () => {
  const data = await downloadListData()
  download.excel(data, '供应商管理.xls')
}

// 分页点击事件
const handlePagination = ({ value }) => {
  emit('handlePagination', value)
}
</script>

<style scoped lang="scss"></style>
