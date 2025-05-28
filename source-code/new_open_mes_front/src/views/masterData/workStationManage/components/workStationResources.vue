<template>
  <el-divider content-position="center">工作站-设备资源</el-divider>
  <!-- <el-carousel type="card" :autoplay="false">
    <el-carousel-item v-for="(item, index) in workStationCard" :key="index">
      <carousel-card
        :card-info="item"
        @handle-resource-save="handleResourceSave"
        @up-date-list="handleUpDateList"
      />
    </el-carousel-item>
  </el-carousel> -->

  <div v-for="(item, index) in workStationCard" :key="index">
    <carousel-card
      :card-info="item"
      @handle-resource-save="handleResourceSave"
      @up-date-list="handleUpDateList"
    />
  </div>

  <!-- <XModal v-model="workStationVisible" :title="workStationTips">
    <el-form
      ref="ruleFormRef"
      :model="manpowerForm"
      :rules="manpowerRules"
      label-width="120px"
      class="demo-ruleForm"
      status-icon
    >
      <el-form-item label="岗位" prop="name">
        <el-input v-model="manpowerForm.postId" />
      </el-form-item>
    </el-form>
  </XModal> -->
  <facilityTreeChoose ref="itemSelectRef" @handle-current-submit="currentData" />
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import carouselCard from './carouselCard.vue'
import { queryFacilityList, addFacilityList } from '@/api/masterData/workstation'
import facilityTreeChoose from '@/components/facilityTreeChoose/index.vue'

interface IProps {
  workId: Number | String
  isEdit: Boolean
}

const props = defineProps<IProps>()

const ruleFormRef = ref<FormInstance>()
// 人力资源校验
const manpowerRules = reactive<FormRules>({
  name: [
    { required: true, message: 'Please input Activity name', trigger: 'blur' },
    { min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' }
  ]
})
// 工装夹具校验
const toolRules = reactive<FormRules>({
  name: [
    { required: true, message: 'Please input Activity name', trigger: 'blur' },
    { min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' }
  ]
})
const itemSelectRef = ref<any>(null)
// 工作资源弹框
const workStationVisible = ref<boolean>(false)
// 工作资源标题
const workStationTips = ref<string>('')
// 工作资源卡片数据
const workStationCard = reactive([
  {
    title: '设备资源',
    cardDescribe: 'facility',
    tableFormList: [],
    isEdit: props.isEdit,
    tableColumns: [
      { prop: 'machineryCode', label: '设备编号' },
      { prop: 'machineryName', label: '设备名称' },
      { prop: 'createTime', label: '时间', width: '200' },
      { slot: 'operate', label: '操作', align: 'center', fixed: 'right' }
    ],
    pagination: {
      total: 0, // 总条目数
      pageSize: 10, // 每页显示条目个数
      pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
      currentPage: 1 // 当前页数
    }
  }
])
const workStationInfo = ref('')

// 人力资源表单
let manpowerForm = ref({ postId: '' })
// 岗位列表
let postList = reactive([])

// 工装夹具表单
let toolForm = ref({})
// 工作站卡片新增事件
const handleResourceSave = (value: string) => {
  workStationInfo.value = value
  switch (value) {
    case 'facility':
      facilitySave()
      break
    case 'manpower':
      manpowerSave()
      break
    case 'tool':
      toolSave()
      break
  }
}

// 表格删除后调用 - 更新卡片列表
const handleUpDateList = (value) => {
  console.log(value, 'handleUpDateList')
  getWorkStationList()
}

// 设备资源新增
const facilitySave = () => {
  itemSelectRef.value.visibleProduct = true
}

// 人力资源新增
const manpowerSave = () => {
  workStationTips.value = '添加人力资源'
  workStationVisible.value = !workStationVisible.value
}

// 工装夹具新增
const toolSave = () => {
  workStationTips.value = '添加工装夹具资源'
  workStationVisible.value = !workStationVisible.value
}

// 物料产品选择弹框-子表格的选中事件
const currentData = (value) => {
  if (value == null) return
  const data = {
    ...value,
    machineryId: value.id,
    workstationId: props.workId
  }
  addFacilityList(data).then((res) => {
    ElMessage.success('新增成功')
    getWorkStationList()
  })
}

// 获取工作站资源列表数据
const getWorkStationList = () => {
  const prams = {
    workstationId: props.workId,
    pageNo: workStationCard[0].pagination.currentPage,
    pageSize: workStationCard[0].pagination.pageSize
  }
  queryFacilityList(prams).then((res) => {
    const { list, total } = res || {}
    workStationCard[0].pagination.total = total
    workStationCard[0].tableFormList = list
  })
}

const info = () => {
  getWorkStationList()
}

onMounted(() => {
  info()
})
</script>

<style scoped lang="scss">
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.box-card {
  width: 400px;
}
</style>
