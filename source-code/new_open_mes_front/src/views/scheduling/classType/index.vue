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
        v-for="(item, index) in btnConditions"
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
      :loading="loading"
      :isSelection="true"
      :columns="tableColumns"
      :tableData="tableDataList"
      :pagination="pagination"
      @selection-change="handleSelectionChange"
      @pagination-change="handlePagination"
    >
      <template #operate="{ scope }">
        <el-button type="primary" @click="handleDetails(scope.row)">详情</el-button>
        <el-button type="success" @click="handleDialogEdit(scope.row)">修改</el-button>
        <el-button type="danger" @click="handleDialogRemove(scope.row)">删除</el-button>
      </template>
    </common-table>
  </ContentWrap>
  <XModal v-model="dialogVisible" :title="dialogTitle" width="60%" @close="handleCancel">
    <el-form label-width="100px" ref="ruleFormRef" :model="ruleForm" :rules="rules" status-icon>
      <el-row>
        <el-col :span="8">
          <el-form-item label="类型编号" prop="typeCode">
            <el-input v-model="ruleForm.typeCode" :disabled="isShowView || ruleForm.id" />
          </el-form-item>
        </el-col>
        {{}}
        <el-col :span="8" v-if="!isShowView && !ruleForm.id">
          <el-form-item label="自动生成" prop="generate">
            <el-switch v-model="ruleForm.generate" @change="handleGenerate" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="类型名称" prop="typeName">
            <el-input v-model="ruleForm.typeName" :disabled="isShowView" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="创建人员" prop="creator">
            <el-input v-model="ruleForm.creator" :disabled="isShowView" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="创建时间" prop="createTime">
            <el-date-picker
              :disabled="isShowView"
              v-model="ruleForm.createTime"
              type="datetime"
              placeholder="请选择创建时间"
              format="YYYY-MM-DD HH:mm:ss"
              value-format="YYYY-MM-DD HH:mm:ss"
              :default-time="new Date()"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="备注" prop="remark">
            <el-input :disabled="isShowView" v-model="ruleForm.remark" type="textarea" autosize />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <template #footer>
      <el-button type="primary" @click="handleSubmit" v-if="!isShowView">确定</el-button>
      <el-button @click="handleCancel">取消</el-button>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, btnConditions, tableColumns } from './data'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { FormInstance } from 'element-plus'
import download from '@/utils/download'
import {
  getClassTypeDataList,
  generateTypeCode,
  saveClassTypeData,
  updateClassTypeData,
  getClassTypeDetail,
  deleteBatchClassTypeData,
  exportClassType
} from '@/api/scheduling/classType'

const ruleFormRef = ref<FormInstance>()

const rules = ref({
  typeCode: { required: true, message: '班组编号不能为空格', trigger: 'blur' },
  typeName: { required: true, message: '班组类型不能为空格', trigger: 'blur' },
  creator: { required: true, message: '创建人员不能为空格', trigger: 'blur' },
  createTime: { required: true, message: '创建时间不能为空格', trigger: 'blur' }
})

const ruleForm = ref<any>({
  // 班组编号
  typeCode: '',
  // 自动生成
  generate: false,
  // 班组类型
  typeName: '',
  // 创建时间
  createTime: '',
  // 创建人员
  creator: '',
  // 备注
  remark: ''
})
const searchModel = reactive({
  // 班组类型
  typeName: '',
  // 创建人员
  creator: '',
  // 创建时间
  createTime: ''
})
// 表格参数
const tableDataList = ref([])
// 表格分页
let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
const loading = ref<boolean>(false)
const dialogVisible = ref<boolean>(false)
const dialogTitle = ref<string>('')

const isShowView = ref(false)
// 查看详情
const handleDetails = (value) => {
  dialogTitle.value = '查看班组类型'
  ruleForm.value = value
  isShowView.value = true
  dialogVisible.value = true
}

// 弹框中自动生成类型编号
const handleGenerate = (value) => {
  if (value) {
    // 调用生成类型编号接口
    generateTypeCode().then((res) => {
      ruleForm.value.typeCode = res
    })
  }
}

// 弹框提交功能
const handleSubmit = () => {
  if (!ruleFormRef.value) return
  ruleFormRef.value.validate((valid) => {
    if (valid) {
      if (ruleForm.value?.id) {
        updateClassTypeData(ruleForm.value).then((res) => {
          if (res) {
            ElMessage({
              message: '修改成功',
              type: 'success'
            })
            handleCancel()
            getTableListInfo()
          }
        })
      } else {
        saveClassTypeData(ruleForm.value).then((res) => {
          if (res) {
            ElMessage({
              message: '新增成功',
              type: 'success'
            })
            handleCancel()
            getTableListInfo()
          }
        })
      }
    }
  })
}

// 搜索功能
const handleQueryData = () => {
  getTableListInfo()
}

// 导出功能
const handleDownload = async () => {
  const data = await exportClassType()
  download.excel(data, '班组类型.xls')
}

const selectionValue = ref()
// 表格选择事件
const handleSelectionChange = (value) => {
  selectionValue.value = value.map((item) => item?.id)
  btnConditions[1].disabled = !selectionValue.value.length || selectionValue.value.length >= 2
  btnConditions[2].disabled = !selectionValue.value.length
}

// 表格分页事件
const handlePagination = (value) => {
  pagination = value?.value
  getTableListInfo()
}

// 按钮点击功能
const handleBtnOperation = (value) => {
  switch (value) {
    case 'save':
      handleDialogSave()
      break
    case 'edit':
      handleDialogEdit()
      break
    case 'remove':
      handleDialogRemove()
      break
    case 'download':
      handleDownload()
      break
  }
}

// 调用详情接口
const getClassTypeDetailAPI = (value) => {
  return new Promise<void>((resolve, reject) => {
    getClassTypeDetail(value).then((res) => {
      resolve(res)
    })
  })
}

// 打开新增弹框
const handleDialogSave = () => {
  dialogTitle.value = '添加班组类型'
  dialogVisible.value = true
}

// 打开修改弹框
const handleDialogEdit = (value?) => {
  dialogTitle.value = '修改班组类型'
  const _id = value ? value.id : selectionValue.value[0]
  getClassTypeDetailAPI(_id).then((res: any) => {
    ruleForm.value = res
    dialogVisible.value = true
  })
}

// 打开删除弹框
const handleDialogRemove = (value?) => {
  const id = value?.id || selectionValue.value
  const deleteID = Array.isArray(id) ? id : [id]
  ElMessageBox.confirm('是否删除当前数据?').then(() => {
    deleteBatchClassTypeData(deleteID).then((res) => {
      ElMessage.success('删除成功')
      getTableListInfo()
    })
  })
}

// 弹框关闭功能
const handleCancel = () => {
  ruleForm.value = {
    // 班组编号
    typeCode: '',
    // 自动生成
    generate: false,
    // 班组类型
    typeName: '',
    // 创建时间
    createTime: '',
    // 创建人员
    creator: '',
    // 备注
    remark: ''
  }
  isShowView.value = false
  dialogVisible.value = false
}

// 初始化获取表格数据
const getTableListInfo = () => {
  loading.value = true
  const params = {
    ...searchModel,
    pageSize: pagination.pageSize,
    pageNo: pagination.currentPage
  }
  getClassTypeDataList(params)
    .then((res) => {
      const { list, total } = res || {}
      searchConditions[0].options = list.map((item) => {
        return {
          value: item.typeName,
          label: item.typeName
        }
      })
      tableDataList.value = list
      pagination.total = total
    })
    .finally(() => {
      loading.value = false
    })
}

const info = () => {
  getTableListInfo()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss"></style>
