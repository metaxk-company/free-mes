<template>
  <ContentWrap>
    <common-search
      :conditions-list="searchConditions"
      :search-model="searchModel"
      @query-data="handleQueryData"
    />
  </ContentWrap>
  <ContentWrap>
    <div style="margin-bottom: 20px">
      <el-button
        plain
        v-for="(item, index) in [btnConditions[0], btnConditions[2]]"
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
      :loading="tableLoading"
      :isSelection="true"
      :columns="tableColumns"
      :tableData="tableDataList"
      :pagination="pagination"
      @pagination-change="handlePagination"
      @selection-change="handleSelectionChange"
    >
      <template #operation="{ scope }">
        <el-button type="success" @click="handleOpenEdit(scope.row)">修改</el-button>
        <el-button type="danger" @click="handleOpenRemove(scope.row)">删除</el-button>
      </template>
    </common-table>
  </ContentWrap>
  <XModal
    v-model="dialogVisible"
    :title="dialogTitle"
    @close="handleCloseDialogVisible"
    width="80%"
  >
    <el-form ref="ruleFormRef" :model="ruleFormData" :rules="rules" label-width="100px" status-icon>
      <el-row>
        <el-col :span="6">
          <el-form-item label="库位编号" :prop="isShowView ? '' : 'locationNumber'">
            {{ ruleFormData.locationNumber }}
            <!-- <el-input v-model="ruleFormData.locationNumber" :disabled="isShowView" /> -->
          </el-form-item>
        </el-col>
        <!-- <el-col :span="6">
          <el-form-item label="自动生成">
            <el-switch v-model="ruleFormData.createCode" @change="handleCreateCode" />
          </el-form-item>
        </el-col> -->
        <el-col :span="6">
          <el-form-item label="库位名称" :prop="isShowView ? '' : 'locationName'">
            <el-input v-model="ruleFormData.locationName" :disabled="isShowView" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="所属仓库" :prop="isShowView ? '' : 'warehouseName'">
            <el-select v-model="ruleFormData.warehouseName" placeholder="请选择仓库">
              <el-option
                v-for="item in wareHousesSelectList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="所属库区" :prop="isShowView ? '' : 'areaName'">
            <el-select v-model="ruleFormData.areaName" placeholder="请选择库区">
              <el-option
                v-for="item in wareHouseAreaSelectList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="位置" :prop="isShowView ? '' : 'location'">
            <el-input v-model="ruleFormData.location" :disabled="isShowView" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="面积" :prop="isShowView ? '' : 'area'">
            <el-input v-model="ruleFormData.area" :disabled="isShowView" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="负责人" :prop="isShowView ? '' : 'charge'">
            <el-input v-model="ruleFormData.charge" :disabled="isShowView" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-form-item label="备注">
            <el-input v-model="ruleFormData.remark" :disabled="isShowView" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="handleDialogSubmit" v-if="!isShowView">提交</el-button>
      <el-button @click="handleCloseDialogVisible">取消</el-button>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { ref, onMounted, reactive } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, tableColumns } from './data'
import { btnConditions } from '@/utils/const'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessageBox, ElMessage } from 'element-plus'
import {
  queryTableList,
  addFormData,
  updateFormInfo,
  deleteFormInfo
} from '@/api/storeHouse/wareHousePlace'
import { queryTableList as wareHousesQueryTableList } from '@/api/storeHouse/warehouseModel'
import { queryTableList as wareHouseAreaTableList } from '@/api/storeHouse/wareHouseArea'
import { getCreateCode } from '@/api/a_public_port'

// 所属仓库下拉列表
const wareHousesSelectList = ref<any>([])
// 所属库区下拉列表
const wareHouseAreaSelectList = ref<any>([])

const ruleFormRef = ref<FormInstance>()
const rules = reactive<FormRules>({
  locationNumber: [{ required: true, message: '请输入库位编号', trigger: 'blur' }],
  locationName: [{ required: true, message: '请输入库位名称', trigger: 'blur' }],
  warehouseName: [{ required: true, message: '请选择所属仓库', trigger: 'blur' }],
  areaName: [{ required: true, message: '请选择所属库区', trigger: 'blur' }],
  location: [{ required: true, message: '请输入位置', trigger: 'blur' }],
  area: [{ required: true, message: '请输入面积', trigger: 'blur' }],
  charge: [{ required: true, message: '请输入负责人', trigger: 'blur' }]
})

// 搜索内容值
const searchModel = ref({
  locationNumber: '',
  locationName: ''
})
const tableLoading = ref(false) // 表格加载
const tableDataList = ref([]) // 表格数据
const getSelectionData = ref()
let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const dialogVisible = ref(false) // 弹框显示
const ruleFormData = ref<any>({}) // 表单数据
const isShowView = ref(false) // 是否是查看状态
const dialogTitle = ref('')

const handleCreateCode = () => {
  getCreateCode('AREA_CODE').then((res) => {
    ruleFormData.value.locationNumber = res
  })
}

// 搜索功能
const handleQueryData = () => {
  getTableList()
}

// 按钮集合功能
const handleBtnOperation = (state) => {
  switch (state) {
    case 'save':
      handleOpenSave()
      break
    case 'edit':
      handleOpenEdit()
      break
    case 'remove':
      handleOpenRemove()
      break
  }
}

// 按钮新增功能
const handleOpenSave = () => {
  handleCreateCode()
  dialogTitle.value = '新增仓库模块'
  dialogVisible.value = true
}

// 按钮/表格 - 修改功能
const handleOpenEdit = async (value?) => {
  dialogTitle.value = '修改仓库模块'
  ruleFormData.value = value
  dialogVisible.value = true
}

// 按钮/表格 - 删除功能
const handleOpenRemove = (value?) => {
  const getId = value ? [value?.id] : getSelectionData.value.map((item) => item.id)
  ElMessageBox.confirm('是否删除当前数据?').then(() => {
    deleteFormInfo(getId).then((res) => {
      ElMessage({ message: '删除成功', type: 'success' })
      getTableList()
    })
  })
}

// 分页功能
const handlePagination = (value) => {
  pagination = value?.value
}

// 表格选择事件
const handleSelectionChange = (value) => {
  getSelectionData.value = value
  btnConditions[1].disabled = !getSelectionData.value.length || getSelectionData.value.length >= 2
  btnConditions[2].disabled = !getSelectionData.value.length
}

// 表格弹框关闭
const handleCloseDialogVisible = () => {
  dialogVisible.value = false
  isShowView.value = false
  ruleFormData.value = { createCode: false }
}

// 表格弹框提交
const handleDialogSubmit = async () => {
  if (!ruleFormRef.value) return
  await ruleFormRef.value.validate((valid) => {
    if (!valid) return
    if (!ruleFormData.value?.id) {
      addFormData(ruleFormData.value).then((res) => {
        ElMessage({ message: '新增成功', type: 'success' })
        handleCloseDialogVisible()
        getTableList()
      })
    } else {
      updateFormInfo(ruleFormData.value).then((res) => {
        ElMessage({ message: '修改成功', type: 'success' })
        handleCloseDialogVisible()
        getTableList()
      })
    }
  })
}

const getTableList = () => {
  tableLoading.value = true
  const params = {
    ...searchModel.value,
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize
  }
  queryTableList(params)
    .then((res) => {
      const { list, total } = res || {}
      pagination.total = total
      tableDataList.value = list
    })
    .finally(() => {
      tableLoading.value = false
    })
}

// 获取仓库列表下拉接口
const getWareHousesQueryTableListAPI = () => {
  wareHousesQueryTableList().then((res) => {
    wareHousesSelectList.value = res.list.map((item) => {
      return {
        value: item.warehouseName,
        label: item.warehouseName
      }
    })
  })
}

// 获取库区列表下拉接口
const getWareHouseAreaTableListAPI = () => {
  wareHouseAreaTableList().then((res) => {
    wareHouseAreaSelectList.value = res.list.map((item) => {
      return {
        value: item.areaName,
        label: item.areaName
      }
    })
  })
}

// 初始化数据
const info = () => {
  getTableList()
  getWareHousesQueryTableListAPI()
  getWareHouseAreaTableListAPI()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
