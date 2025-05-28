<template>
  <div>
    <span class="check-information"
      >检验人员：{{ qualityInspectionFrom.receiveRecord.inspectUser }} <br />检验日期:{{
        today
      }}</span
    >
    <el-divider content-position="left">检验标准信息</el-divider>
  </div>
  <el-descriptions :column="4">
    <el-descriptions-item label="来料检验单编号">{{
      qualityInspectionFrom.receiveRecord.recordCode
    }}</el-descriptions-item>
    <el-descriptions-item label="到货通知单编号">{{
      qualityInspectionFrom.receiveRecord.recNumber
    }}</el-descriptions-item>
    <el-descriptions-item label="检验方式">{{
      MARRY_INSPECTION_METHOD[qualityInspectionFrom.receiveRecord.inspectWay]
    }}</el-descriptions-item>
    <el-descriptions-item label="检验版本">
      {{ qualityInspectionFrom.receiveRecord.version }}
    </el-descriptions-item>

    <el-descriptions-item label="物料编号" class="bar-code">
      {{ qualityInspectionFrom.receiveRecord.itemCode }}
    </el-descriptions-item>
    <el-descriptions-item label="物料名称" class="bar-code">
      {{ qualityInspectionFrom.receiveRecord.itemName }}
    </el-descriptions-item>
    <el-descriptions-item label="检验总数" class="bar-code">
      {{
        qualityInspectionFrom.receiveRecord.inspectWay != 'fullInspection'
          ? parseInt(qualityInspectionFrom.receiveRecord.checkNumber)
          : parseInt(qualityInspectionFrom.receiveRecord.quantity)
      }}
      <!-- {{ qualityInspectionFrom.receiveRecord.quantity }} -->
    </el-descriptions-item>
    <el-descriptions-item label="当前检测" class="bar-code">
      <span style="font-size: 22px; font-weight: bold">{{ qualityInspectionFrom.number }}</span>
    </el-descriptions-item>
    <el-descriptions-item label="检测进度" class="bar-code">
      <span style="font-size: 22px; font-weight: bold">
        <!-- {{ qualityInspectionFrom.number }}/
        {{
          qualityInspectionFrom.receiveRecord.inspectFlag == '1'
            ? parseInt(qualityInspectionFrom.receiveRecord.checkNumber)
            : parseInt(qualityInspectionFrom.receiveRecord.quantity)
        }} -->
        {{ qualityInspectionFrom.number }}
        /{{
          qualityInspectionFrom.receiveRecord.inspectWay != 'fullInspection'
            ? parseInt(qualityInspectionFrom.receiveRecord.checkNumber)
            : parseInt(qualityInspectionFrom.receiveRecord.quantity)
        }}
      </span>
    </el-descriptions-item>
  </el-descriptions>
  <el-divider content-position="center">对比信息</el-divider>
  <div style="margin-bottom: 20px; text-align: center">
    <el-button
      v-if="qualityInspectionFrom.receiveRecord.inspectFlag == '1'"
      type="success"
      @click="contrastDrawer = true"
      >打开检验表</el-button
    >
    <div v-else>
      <!-- <template v-if="qualityInspectionFrom.receiveRecord.quantity == qualityInspectionFrom.number"
        >1</template
      > -->
      <el-button type="success" @click="handleQualified(1)">合格</el-button>
      <el-button type="danger" @click="handleQualified(0)">不合格</el-button>
    </div>
  </div>
  <!-- 检验表 -->
  <el-drawer
    class="my-drawer"
    v-model="contrastDrawer"
    direction="btt"
    size="100%"
    :close-on-click-modal="false"
    @close="closeDrawer"
  >
    <template #title>
      <div class="custom-title">
        <h4>当前时间:{{ dateYear }} {{ dateDay }} {{ dateWeek }}</h4>
        <div class="fixed-matter">
          <span>物料条码</span>
          <el-input v-model="itemBarcode" placeholder="" style="width: 400px; margin-left: 20px" />
        </div>
        <div class="fixed-matter-text">
          <p>当前检测进度：</p>
          <p class="fixed-matter-text-content">
            <span>当前/总数</span>
            <span style="font-size: 26px; font-weight: bold"
              >{{ qualityInspectionFrom.number }}/
              {{
                qualityInspectionFrom.receiveRecord.inspectWay != 'fullInspection'
                  ? parseInt(qualityInspectionFrom.receiveRecord.checkNumber)
                  : parseInt(qualityInspectionFrom.receiveRecord.quantity)
              }}</span
            >
          </p>
        </div>
        <el-button type="primary" @click="handleNextInfo">{{
          qualityInspectionFrom.receiveRecord.checkNumber == qualityInspectionFrom.number
            ? '完成'
            : '下一条'
        }}</el-button>
      </div>
    </template>

    <!-- tenantId -->
    <el-divider content-position="left" style="margin: 50px 0"
      ><el-tag class="ml-2" style="font-size: 20px; width: 100px; height: 40px" effect="light"
        >检测数据</el-tag
      ></el-divider
    >
    <!-- 实际检测数据 -->
    <common-table
      :isShowPagination="false"
      :tableData="tableData"
      :columns="columnState"
      v-loading="tableLoading"
    >
      <template #itemValue="{ scope }">
        <el-input
          v-model="scope.row.itemValue"
          placeholder="请输入实际信息"
          @blur="handleBlueReality(scope)"
          :disabled="scope.row.isDisable"
        />
      </template>
      <template #status="{ scope }">
        <el-radio-group v-model="scope.row.status" @change="statusChange(scope)">
          <el-radio :label="item.value" v-for="item in QUALITY_STATUS" :key="item.value">{{
            item.label
          }}</el-radio>
        </el-radio-group>
      </template>
    </common-table>

    <el-divider content-position="left" style="margin: 50px 0"
      ><el-tag class="ml-2" style="font-size: 20px; width: 100px; height: 40px" effect="light"
        >图片上传</el-tag
      ></el-divider
    >

    <el-upload
      :headers="uploadHeaders"
      v-model:file-list="imgFileList"
      :action="actionURL"
      :data="{ idValue: 11 }"
      list-type="picture-card"
      :limit="10"
      :on-remove="handleRemove"
      :on-success="handleUploadFileSuccess"
      style="width: 800px"
      :on-exceed="handleExceed"
      :multiple="true"
    >
      <template #file="{ file }">
        <div>
          <img class="el-upload-list__item-thumbnail" :src="file.url" alt="" />
          <span class="el-upload-list__item-actions">
            <span class="el-upload-list__item-delete" @click="handleRemove(file)">
              <el-icon><Delete /></el-icon>
            </span>
          </span>
        </div>
      </template>
      <el-button :icon="'Plus'" link />
      <template #tip>
        <div class="el-upload__tip">最多支持上传10张</div>
      </template>
    </el-upload>
  </el-drawer>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import type { UploadUserFile, UploadFile, UploadFiles } from 'element-plus'
import commonTable from '@/components/CommonTable/index.vue'
import { columnState } from '../data'
import { QUALITY_STATUS, MARRY_INSPECTION_METHOD } from '@/utils/const'
import { getTenantId } from '@/utils/auth'
import { getTableBlurServer } from '@/api/processInspection/inspectionReceipts'
import {
  nextInfoHandle,
  testEndSubmit,
  editQualifiedState,
  submitQualifiedTest
} from '@/api/processInspection/incomingReceipts'
import { ElMessage } from 'element-plus'
import type { UploadProps } from 'element-plus'
import { formatTime } from '@/utils/index'
import dayjs from 'dayjs'
let dateDay = ref()
let dateYear = ref()
let dateWeek = ref()
let weekday = ref(['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'])
let timer = ref<any>(null)
const props = defineProps({
  qualityInspectionFrom: {
    type: Object,
    default: () => {}
  }
})

const emit = defineEmits(['endTestSubmit'])
// 图片超出10张提醒
const handleExceed: UploadProps['onExceed'] = (files, uploadFiles) => {
  ElMessage.warning(`最多只能上传十张图片`)
}
const uploadHeaders = ref({
  'tenant-id': getTenantId()
})
const imgFileList = ref<UploadUserFile[]>([])
const actionURL = ref(
  import.meta.env.VITE_BASE_URL +
    import.meta.env.VITE_API_URL +
    '/mes/qc/process/form/uploadPicture'
)
// 物料条码
const itemBarcode = ref()
const tableData = ref<any>([])
const tableLoading = ref(false)
const contrastDrawer = ref(false)
const today = formatTime(new Date(), 'yyyy-MM-dd HH:mm:ss')
// 处理合格/不合格操作 无检验标准
const handleQualified = (value) => {
  const params = {
    status: value,
    num: props.qualityInspectionFrom.number,
    productBarcode: '',
    itemCode: props.qualityInspectionFrom.receiveRecord.itemCode,
    inspectStartTime: today,
    id: props.qualityInspectionFrom.receiveRecord.id
  }

  const endTime = {
    ...params,
    inspectEndTime: today
  }

  if (props.qualityInspectionFrom.number == props.qualityInspectionFrom.receiveRecord.quantity) {
    submitQualifiedTest(endTime).then((res) => {
      emit('endTestSubmit', res)
      contrastDrawer.value = false
    })
  } else {
    editQualifiedState(params).then((res) => {
      props.qualityInspectionFrom.number = res.number
      ElMessage({
        message: '质检成功',
        type: 'success'
      })
    })
  }
}

// 下一个操作更新检查列表数据  有检验标准
const handleNextInfo = () => {
  // id,inspectStartTime检验开始时间，num已经抽检的数量，itemBarcode物料条码
  const params = {
    id: props.qualityInspectionFrom.receiveRecord.id,
    // processCode: props.qualityInspectionFrom.receiveRecord.processCode,
    productBarcode: itemBarcode.value,
    num: props.qualityInspectionFrom.number,
    inspectStartTime: today
    // processName: props.qualityInspectionFrom.receiveRecord.processName
  }

  const endParams = {
    id: props.qualityInspectionFrom.receiveRecord.id,
    inspectEndTime: formatTime(new Date(), 'yyyy-MM-dd HH:mm:ss'),
    num: props.qualityInspectionFrom.receiveRecord.checkNumber,
    itemCode: props.qualityInspectionFrom.receiveRecord.itemCode
  }
  if (props.qualityInspectionFrom.receiveRecord.checkNumber == props.qualityInspectionFrom.number) {
    // 完成接口
    testEndSubmit(endParams).then((res) => {
      emit('endTestSubmit', res)
      contrastDrawer.value = false
    })
  } else {
    const allValuesNotNull = tableData.value.every((obj) => {
      return (obj.itemValue !== '' && obj.itemValue !== null) || obj.isDisable == true
    })

    if (allValuesNotNull) {
      if (props.qualityInspectionFrom.checkNumber == props.qualityInspectionFrom.number) {
        console.log('完成')
      } else {
        nextInfoHandle(params).then((res) => {
          ElMessage({
            message: '检验成功',
            type: 'success'
          })
          itemBarcode.value = ''
          props.qualityInspectionFrom.number = res.number
          tableData.value = res.list.map((item) => {
            console.log(item)
            return {
              ...item,
              status: 2
            }
          })
        })
      }
    } else {
      return ElMessage({
        message: '实际信息不能为空',
        type: 'warning'
      })
    }
  }
}
// 选择合格不合格
const statusChange = (value) => {
  if (tableData.value[value.$index].status == 2) {
    tableData.value[value.$index].isDisable = true
    handleBlueReality(value)
  } else {
    tableData.value[value.$index].isDisable = false
  }
}
// 关闭弹窗
const closeDrawer = () => {
  tableData.value.forEach((item) => {
    item.itemValue = ''
    item.status = null
    item.isDisable = false
  })
}
// 每一行失去焦点后触发更新操作
const handleBlueReality = async (value) => {
  // 当前检验标准
  let itemStandard = tableData.value[value.$index].itemStandard
  // 当前输入的对比参数
  let itemValue = tableData.value[value.$index].itemValue
  // 只有检验标准=实际信息时才合格
  if (itemValue == '') {
    tableData.value[value.$index].status = 2
    tableData.value[value.$index].isDisable = true
  } else {
    itemStandard == itemValue
      ? (tableData.value[value.$index].status = 1)
      : (tableData.value[value.$index].status = 0)
    tableData.value[value.$index].isDisable = false
  }

  const { id, ...rest } = value.row

  const params = {
    ...rest,
    sortNumber: props.qualityInspectionFrom.number,
    // itemCode: props.qualityInspectionFrom.receiveRecord.itemCode,
    recordId: props.qualityInspectionFrom.receiveRecord.id,
    itemCode: props.qualityInspectionFrom.receiveRecord.itemCode //物料编码
    // recordId:props.qualityInspectionFrom.receiveRecord.itemCode
  }
  await getTableBlurServer(params).then((res) => {
    if (res !== 200) return
    ElMessage({
      message: '更新成功',
      type: 'success'
    })
  })
}

// 上传图片成功时候操作
const handleUploadFileSuccess = (
  response: any,
  uploadFile: UploadFile,
  uploadFiles: UploadFiles
) => {
  console.log(response)
  console.log(uploadFile)
  console.log(uploadFiles)
}

// 删除图片
const handleRemove = (file: UploadFile) => {
  imgFileList.value = imgFileList.value.filter((item) => {
    return item.url !== file.url
  })
}

onMounted(() => {
  tableData.value = props.qualityInspectionFrom.list.map((item) => {
    return {
      ...item,
      // status: 1,
      status: null,
      itemValue: '',
      isDisable: false
    }
  })
  timer.value = setInterval(() => {
    const date = dayjs(new Date())
    dateDay.value = date.format('HH:mm:ss')
    dateYear.value = date.format('YYYY-MM-DD')
    dateWeek.value = date.format(weekday.value[date.day()])
  }, 1000)
})
</script>

<style scoped lang="scss">
.my-drawer .el-drawer__container {
  position: relative;
}

:deep .my-drawer {
  height: 10% !important;
}

.my-drawer .fixed-matter {
  display: flex;
  align-items: center;
}
.fixed-matter-text {
  display: flex;
  align-items: center;
  .fixed-matter-text-content {
    display: flex;
    flex-direction: column;
    align-items: center;
    > span:nth-child(1) {
      font-size: 12px;
    }
  }
}
.bar-code::before {
  content: '*';
  display: inline-block;
  color: red;
  margin-right: 5px;
}
.check-information {
  display: flex;
  justify-content: right;
  padding: 0 20px;
}
:deep .el-divider__text {
  font-size: 16px;
}
.drawerTitle {
  display: flex;
  justify-content: space-between;
}
.custom-title {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 16px;
  padding: 6px 12px;
}
</style>
