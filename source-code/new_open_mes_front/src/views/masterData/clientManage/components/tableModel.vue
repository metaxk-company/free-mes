<!-- eslint-disable vue/no-mutating-props -->
<template>
  <el-row :gutter="10" style="margin-bottom: 16px">
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

  <commonTable
    :loading="tableLoading"
    :columns="columnState"
    :pagination="paginationData"
    :isSelection="true"
    :tableData="tableFormList"
    @selection-change="handleSelectionChange"
    @pagination-change="handlePagination"
  >
    <template #clientCode="{ scope }">
      <el-button link type="primary" @click="handleClientCode(scope.row)">
        {{ scope.row.clientCode }}
      </el-button>
    </template>
    >
    <template #clientType="{ scope }">
      {{ CUSTOMER_TYPE[scope.row.clientType] }}
    </template>

    <template #enableFlag="{ scope }">
      <el-tag :type="scope.row.enableFlag === 'Y' ? 'success' : 'warning'">{{
        UN_KNOW_STATE[scope.row.enableFlag]
      }}</el-tag>
    </template>
    <template #operation="{ scope }">
      <el-button
        style="font-size: 17px"
        link
        type="primary"
        size="small"
        @click="handleClientCode(scope.row)"
      >
        详情
      </el-button>
      <el-button
        style="font-size: 17px"
        link
        type="primary"
        size="small"
        @click="handleUqDataRow(scope.row)"
      >
        修改
      </el-button>
      <el-button
        style="font-size: 17px"
        link
        type="danger"
        size="small"
        @click="handleDeleteData(scope.row)"
      >
        删除
      </el-button>
    </template>
  </commonTable>

  <XModal
    @close="handleCancel"
    :title="dialogParams.dialogTitle"
    v-model="dialogParams.dialogVisible"
    width="80%"
  >
    <dialog-form
      ref="dialogFormDefine"
      :dialog-form-params="dialogFormParams"
      :customer-form-data="dialogParams"
      @up-date-form-data="handleUpDateFormData"
    />

    <template #footer>
      <el-button type="primary" @click="handleSubmit">{{ dialogBtnTitle }}</el-button>
      <el-button @click="handleCancel">取消</el-button>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { computed, reactive, ref } from 'vue'
import dialogForm from './dialogForm.vue'
import { IBtnClickFun, columnState } from '../data'
import commonTable from '@/components/CommonTable/index.vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import { queryClientInfo, deleteClientInfo, downloadListData } from '@/api/masterData/clientManage'
import { UN_KNOW_STATE, CUSTOMER_TYPE } from '@/utils/const'
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
  tableLoading: {
    type: Boolean
  },
  tableFormList: {
    type: Object,
    default: () => {}
  },
  paginationData: {
    type: Object,
    default: () => {}
  }
})

const emit = defineEmits(['handlePagination', 'upDateTableList'])

type ResponseParamsCtx = InstanceType<typeof dialogForm>
const dialogFormDefine = ref<ResponseParamsCtx | null>(null)

const dialogParams = reactive({
  dialogTitle: 'www',
  dialogVisible: false,
  unKnowState: 'edit'
})

const dialogFormParams = ref({
  // 客户编码
  clientCode: '',
  // 自动生成
  autoGenFlag: false,
  // 客户名称
  clientName: '',
  // 客户简称
  clientNick: '',
  // 客户英文名称
  clientEn: '',
  // 客户类型
  clientType: '',
  // 客户简介
  clientDes: '',
  // 客户地址
  address: '',
  // 客户官网地址
  website: '',
  // 客户邮箱地址
  email: '',
  // 客户电话
  tel: '',
  // 客户LOGO
  clientLogo: '',
  // 联系人1
  contact1: '',
  // 联系人1-电话
  contact1Tel: '',
  // 联系人1-邮箱
  contact1Email: '',
  // 联系人2
  contact2: '',
  // 联系人2-电话
  contact2Tel: '',
  // 联系人2-邮箱
  contact2Email: '',
  // 社会信用代码
  creditCode: '',
  // 是否有效
  enableFlag: 'Y',
  // 备注
  remark: '',
  outMax: 0,
  outMin: 0,
  id: ''
})

// 存储当前列表选中的值
const setSelectionList = ref([])

const dialogBtnTitle = computed(() => {
  return dialogParams.unKnowState == 'edit' ? '确定' : '返回'
})

const handleUpDateFormData = () => {
  dialogParams.dialogVisible = false

  emit('upDateTableList')
}

// 弹框确定功能
const handleSubmit = () => {
  if (dialogParams.unKnowState == 'edit') {
    dialogFormDefine?.value?.submitForm()
  } else {
    handleCancel()
  }
}

// 弹框取消功能
const handleCancel = () => {
  dialogFormDefine?.value?.resetForm()
  dialogParams.dialogVisible = false
  dialogFormParams.value = {
    // 客户编码
    clientCode: '',
    // 自动生成
    autoGenFlag: false,
    // 客户名称
    clientName: '',
    // 客户简称
    clientNick: '',
    // 客户英文名称
    clientEn: '',
    // 客户类型
    clientType: '',
    // 客户简介
    clientDes: '',
    // 客户地址
    address: '',
    // 客户官网地址
    website: '',
    // 客户邮箱地址
    email: '',
    // 客户电话
    tel: '',
    // 客户LOGO
    clientLogo: '',
    // 联系人1
    contact1: '',
    // 联系人1-电话
    contact1Tel: '',
    // 联系人1-邮箱
    contact1Email: '',
    // 联系人2
    contact2: '',
    // 联系人2-电话
    contact2Tel: '',
    // 联系人2-邮箱
    contact2Email: '',
    // 社会信用代码
    creditCode: '',
    // 是否有效
    enableFlag: 'Y',
    // 备注
    remark: '',
    id: ''
  }
}

// 表格勾选数据
const handleSelectionChange = (selection) => {
  setSelectionList.value = selection.map((item) => item.id)
  btnClickState[1].disabled = !setSelectionList.value.length || setSelectionList.value.length >= 2
  btnClickState[2].disabled = !setSelectionList.value.length
}

// 客户编码点击事件
const handleClientCode = (value) => {
  dialogParams.unKnowState = 'view'
  dialogParams.dialogTitle = '查看客户信息'
  getClientInfo(value.id)
  // 调用查询客户信息详情接口
}

// 修改操作
const handleUqDataRow = (value?) => {
  dialogParams.unKnowState = 'edit'
  dialogParams.dialogTitle = '修改客户信息'
  const clientId = value?.id || setSelectionList.value
  getClientInfo(clientId)
}

// 客户信息详情
const getClientInfo = (value) => {
  queryClientInfo(value).then((res) => {
    dialogFormParams.value = res
    dialogParams.dialogVisible = !dialogParams.dialogVisible
  })
}

// 删除操作
const handleDeleteData = (value?) => {
  const clientId = value?.id || setSelectionList.value
  const id = Array.isArray(clientId) ? clientId : [clientId]
  console.log(id)

  ElMessageBox.confirm(`是否确认删除客户编号为:${clientId}`).then(() => {
    deleteClientInfo(id).then((res) => {
      ElMessage.success('删除成功')
      handleUpDateFormData()
    })
  })
}

// 四种点击的功能区域
const handleBtnCollections = (value: IBtnClickFun) => {
  if (value.sign == '') return
  switch (value.sign) {
    case 'save':
      dialogParams.unKnowState = 'edit'
      dialogParams.dialogTitle = '添加客户信息'
      dialogParams.dialogVisible = !dialogParams.dialogVisible
      break
    case 'uqData':
      handleUqDataRow()
      break
    case 'delete':
      handleDeleteData()
      break
    case 'export':
      handleDownload()
      break
  }
}

// 导出功能
const handleDownload = async () => {
  const data = await downloadListData()
  download.excel(data, '客户管理.xls')
}

// 分页点击事件
const handlePagination = (value) => {
  emit('handlePagination', value)
}
</script>

<style scoped lang="scss"></style>
