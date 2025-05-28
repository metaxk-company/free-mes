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
      <template #shiftPersonnel="{ scope }">
        <el-button type="primary" link @click="handleQueryPeopleList(scope.row)">详情</el-button>
      </template>

      <template #operate="{ scope }">
        <el-button type="primary" @click="handleAddShiftPersonnel(scope.row)">添加人员</el-button>
        <el-button type="primary" @click="handleDetails(scope.row)">详情</el-button>
        <el-button type="success" @click="handleDialogEdit(scope.row)">修改</el-button>
        <el-button type="danger" @click="handleDialogRemove(scope.row)">删除</el-button>
      </template>
    </common-table>
  </ContentWrap>
  <XModal v-model="dialogVisible" :title="shiftPersonnelTitle" width="60%" @close="handleCancel">
    <el-form label-width="100px" ref="ruleFormRef" :model="ruleForm" :rules="rules" status-icon>
      <el-row>
        <el-col :span="8">
          <el-form-item label="班组编号" prop="teamCode">
            <el-input v-model="ruleForm.teamCode" :disabled="isShowView || ruleForm.id" />
          </el-form-item>
        </el-col>
        <!-- <el-col :span="8" v-if="!isShowView && !ruleForm.id">
          <el-form-item label="自动生成" prop="generate">
            <el-switch v-model="ruleForm.generate" @change="handleGenerate" />
          </el-form-item>
        </el-col> -->
        <el-col :span="8">
          <el-form-item label="班组类型" prop="teamType">
            <el-select
              v-model="ruleForm.teamType"
              placeholder="请选择班组类型"
              :disabled="isShowView"
            >
              <el-option
                v-for="item in teamTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
            <!-- <el-input v-model="ruleForm.teamType" :disabled="isShowView" /> -->
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="班组名称" prop="teamName">
            <el-input v-model="ruleForm.teamName" :disabled="isShowView" />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="班组组长" prop="teamLeader">
            <el-input v-model="ruleForm.teamLeader" :disabled="isShowView" />
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

  <XModal
    v-model="shiftPersonnelDialogVisible"
    :title="shiftPersonnelTitle"
    width="60%"
    @close="handleCloseShiftPersonnel"
  >
    <common-table
      :pagination="peoplePagination"
      :loading="loading"
      :isSelection="!isShowPeople"
      :columns="peopleColumns"
      :tableData="personnelTableDataList"
      @pagination-change="handlePeoplePagination"
      @selection-change="handlePeopleSelectionChange"
    >
      <template #operate="{ scope }">
        <el-button type="danger" @click="handleRemovedPeople(scope.row)" link>移除人员</el-button>
      </template>
    </common-table>
    <template #footer>
      <el-button type="primary" @click="handleSubmitSaveTeamsPeople" v-if="!isShowPeople"
        >确定</el-button
      >
      <el-button @click="handleCloseShiftPersonnel">取消</el-button>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed } from 'vue'
import CommonSearch from '@/components/CommonSearch/index.vue'
import CommonTable from '@/components/CommonTable/index.vue'
import { searchConditions, btnConditions, tableColumns, personnelColumns } from './data'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { FormInstance } from 'element-plus'
import download from '@/utils/download'
import {
  saveShiftSetData,
  deleteBatchShiftSetData,
  updateShiftSetData,
  getShiftSetDataList,
  getShiftSetDetail,
  exportShiftSet,
  generateTypeCode,
  getUserList,
  saveTeamsPeople,
  getTeamsPeopleList,
  deletePeopleData
} from '@/api/scheduling/shiftSet'
import { getClassTypeDataList } from '@/api/scheduling/classType'

const ruleFormRef = ref<FormInstance>()

const rules = ref({
  teamCode: { required: true, message: '班组编号不能为空格', trigger: 'blur' },
  teamType: { required: true, message: '班组类型不能为空格', trigger: 'blur' },
  teamName: { required: true, message: '班组名称不能为空格', trigger: 'blur' },
  teamLeader: { required: true, message: '班组组长不能为空格', trigger: 'blur' }
})

const ruleForm = ref<any>({
  generate: false,
  teamCode: '',
  teamLeader: '',
  teamName: '',
  teamType: '',
  // 备注
  remark: ''
})
const searchModel = reactive({
  // 班组类型
  teamType: '',
  // 班组编号
  teamCode: '',
  // 班组名称
  teamName: ''
})
// 表格参数
const tableDataList = ref([])
// 人员表格参数
const personnelTableDataList = ref([])
// 表格分页
let pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
// 人员表格分页
let peoplePagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})

const loading = ref<boolean>(false)
const dialogVisible = ref<boolean>(false)
const dialogTitle = ref<string>('')
const isShowPeople = ref(false)
const getRowPeopleData = ref()
const shiftPersonnelTitle = ref('')
const shiftPersonnelDialogVisible = ref(false)
const saveTeamData = ref()
const peopleColumns = computed(() => {
  return isShowPeople.value
    ? [...personnelColumns, { label: '操作', slot: 'operate' }]
    : [...personnelColumns]
})

// 移除人员
const handleRemovedPeople = (value) => {
  deletePeopleData([value.id]).then((res) => {
    if (!res) return
    ElMessage({
      message: '移除成功',
      type: 'success'
    })
    getShiftPersonnelPeopleData(getRowPeopleData.value)
  })
}

// 班组人员分页功能
const handlePeoplePagination = (value) => {
  peoplePagination = value?.value
  if (isShowPeople.value) {
    getShiftPersonnelPeopleData(getRowPeopleData.value)
  } else {
    getUserListAPI()
  }
}

const getShiftPersonnelPeopleData = async (value) => {
  const params = {
    pageNo: peoplePagination.currentPage,
    pageSize: peoplePagination.pageSize,
    teamCode: value.teamCode
  }
  await getTeamsPeopleList(params).then((res) => {
    const { list, total } = res || {}
    personnelTableDataList.value = list
    peoplePagination.total = total
  })
}

// 查看班组人员详情数据
const handleQueryPeopleList = async (value) => {
  shiftPersonnelTitle.value = '查看班组人员'
  getRowPeopleData.value = value
  isShowPeople.value = true
  await getShiftPersonnelPeopleData(value)
  shiftPersonnelDialogVisible.value = true
}

// 添加人员
const handleAddShiftPersonnel = (value) => {
  shiftPersonnelTitle.value = '添加人员'
  saveTeamData.value = value
  getUserListAPI().then((res) => {
    shiftPersonnelDialogVisible.value = true
  })
}

// 获取添加人员信息
const getUserListAPI = () => {
  return new Promise<void>((resolve, reject) => {
    getUserList({
      pageNo: peoplePagination.currentPage,
      pageSize: peoplePagination.pageSize
    }).then((result) => {
      const { list = [], total } = result
      personnelTableDataList.value = list
      peoplePagination.total = total
      resolve(result)
    })
  })
}

// 提交保存班组人员列表数据
const handleSubmitSaveTeamsPeople = () => {
  if (!peopleList.value.length)
    return ElMessage({
      message: '未选择人员',
      type: 'error'
    })

  saveTeamsPeople(peopleList.value).then((res) => {
    ElMessage({
      message: '新增成功',
      type: 'success'
    })
    handleCloseShiftPersonnel()
    getTableListInfo()
  })
}

// 关闭人员弹框
const handleCloseShiftPersonnel = () => {
  shiftPersonnelDialogVisible.value = false
  isShowPeople.value = false
}

const peopleList = ref([])
// 人员选择的数据
const handlePeopleSelectionChange = (value) => {
  peopleList.value = value.map((item) => {
    return {
      id: item.id,
      personCode: item.id,
      userName: item.userName,
      teamCode: saveTeamData.value.teamCode,
      teamType: saveTeamData.value.teamType,
      teamName: saveTeamData.value.teamName
    }
  })
}

const isShowView = ref(false)
// 查看详情
const handleDetails = (value) => {
  shiftPersonnelTitle.value = '查看班组详情'
  ruleForm.value = value
  isShowView.value = true
  dialogVisible.value = true
}

// 弹框中自动生成类型编号
const handleGenerate = () => {
  // 调用生成类型编号接口
  generateTypeCode().then((res) => {
    ruleForm.value.teamCode = res
  })
}

// 弹框提交功能
const handleSubmit = () => {
  if (!ruleFormRef.value) return
  ruleFormRef.value.validate((valid) => {
    if (valid) {
      if (ruleForm.value?.id) {
        updateShiftSetData(ruleForm.value).then((res) => {
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
        saveShiftSetData(ruleForm.value).then((res) => {
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
  const data = await exportShiftSet()
  download.excel(data, '班组管理.xls')
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
    getShiftSetDetail(value).then((res) => {
      resolve(res)
    })
  })
}

// 打开新增弹框
const handleDialogSave = () => {
  shiftPersonnelTitle.value = '添加班组'
  dialogVisible.value = true
}

// 打开修改弹框
const handleDialogEdit = (value?) => {
  shiftPersonnelTitle.value = '修改班组'
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
  ElMessageBox.confirm('是否删除当前班组？当前班组可能包含班组人员?').then(() => {
    deleteBatchShiftSetData(deleteID).then((res) => {
      ElMessage.success('删除成功')
      getTableListInfo()
    })
  })
}

// 弹框关闭功能
const handleCancel = () => {
  ruleForm.value = {}
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
  getShiftSetDataList(params)
    .then((res) => {
      const { list, total } = res || {}
      searchConditions[0].options = list.map((item) => {
        return {
          value: item.teamType,
          label: item.teamType
        }
      })
      tableDataList.value = list
      pagination.total = total
    })
    .finally(() => {
      loading.value = false
    })
}

interface ITeamTypeOptions {
  value: String
  label: String
}
const teamTypeOptions = ref<ITeamTypeOptions[]>([])

const getTeamTypeOptions = () => {
  getClassTypeDataList({}).then((res) => {
    const { list } = res || {}
    teamTypeOptions.value = list.map((item) => {
      return {
        value: item.typeName,
        label: item.typeName
      }
    })
  })
}

const info = () => {
  getTableListInfo()
}

onMounted(() => {
  info()
  getTeamTypeOptions()
})
</script>

<style scoped lang="scss"></style>
