<template>
  <el-row :gutter="10" v-if="dialogState !== 'View'">
    <el-col :span="1.5">
      <el-button type="primary" plain icon="plus" @click="handleAddSop">新增</el-button></el-col
    >
  </el-row>
  <div class="images">
    <div v-for="(item, index) in sopList" :key="index" class="image-middle">
      <el-card shadow="hover" :body-style="{ padding: '10px' }">
        <div class="image">
          <img :src="sopList[index].sopUrl" alt="" />
        </div>
        <div style="text-align: center">
          <div class="img-text">
            {{ sopList[index].sopTitle }}
          </div>
          <div v-if="dialogState !== 'View'">
            <el-button type="primary" icon="edit" @click="handleUpdateSopInfo(sopList[index])" />
            <el-button type="danger" icon="delete" @click="handleDeleteSopInfo(sopList[index])" />
          </div>
        </div>
      </el-card>
    </div>
  </div>

  <!-- 添加/修改，产品SOP -->
  <XModal v-model="dialogVisible" :title="sopTitle" @before-close="handleResetForm">
    <el-form ref="ruleFormRef" :model="sopForm" label-width="100px">
      <el-form-item label="标题" prop="sopTitle">
        <el-input v-model="sopForm.sopTitle" placeholder="请输入标题" />
      </el-form-item>
      <el-form-item label="展示顺序" prop="orderNum">
        <el-input-number :min="1" v-model="sopForm.orderNum" />
      </el-form-item>
      <el-form-item label="内容说明" prop="sopDescription">
        <el-input type="textarea" v-model="sopForm.sopDescription" placeholder="请输入说明信息" />
      </el-form-item>
      <el-form-item label="所属工序" prop="processId">
        <el-select v-model="sopForm.processId" placeholder="请选择工序">
          <el-option
            v-for="item in processOptions"
            :key="item.processId"
            :label="item.processName"
            :value="item.processId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="图片" prop="sopUrl">
        <UploadImg :modelValue="sopForm.sopUrl" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="handleSubmitForm"> 提交 </el-button>
        <el-button @click="handleResetForm">取消</el-button>
      </span>
    </template>
  </XModal>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { UploadImg } from '@/components/UploadFile'
import type { FormInstance } from 'element-plus'
import { ElMessageBox } from 'element-plus'

defineProps({
  dialogState: {
    type: String,
    default: () => ''
  }
})

const ruleFormRef = ref<FormInstance>()

// 产品SOP表格数据
const sopList = ref<Array<Object | any>>([
  {
    searchValue: null,
    createBy: '',
    createTime: '2023-04-17 01:31:49',
    updateBy: '',
    updateTime: '2023-04-17 01:45:01',
    remark: '',
    params: {},
    sopId: 204,
    itemId: 458,
    orderNum: 1,
    processId: 327,
    processCode: null,
    processName: null,
    sopTitle: 'rgbfbgfbfbgfbfgbfbgfbfgbfgbfgb',
    sopDescription: 'rrrrrgbfbgfbfbgfbfgbfbgfbfgbfgbfgb',
    sopUrl: 'https://minio-test.cloudmes.io/mes/2023/04/17/头像_20230417014458A002.jpeg',
    attr1: null,
    attr2: null,
    attr3: 0,
    attr4: 0
  },
  {
    searchValue: null,
    createBy: '',
    createTime: '2023-04-17 01:44:46',
    updateBy: '',
    updateTime: null,
    remark: '',
    params: {},
    sopId: 205,
    itemId: 458,
    orderNum: 1,
    processId: 328,
    processCode: null,
    processName: null,
    sopTitle: '3232323',
    sopDescription: null,
    sopUrl: 'https://minio-test.cloudmes.io/mes/2023/04/17/头像_20230417014436A001.jpeg',
    attr1: null,
    attr2: null,
    attr3: 0,
    attr4: 0
  }
])
// 添加/修改，产品SOP 弹框
const dialogVisible = ref<boolean>(false)
// 添加/修改，产品SOP 标题
const sopTitle = ref<string>('')
// 添加/修改，产品SOP  表单内容
const sopForm = reactive({
  // 标题
  sopTitle: '',
  // 展示顺序
  orderNum: 1,
  // 内容说明
  sopDescription: '',
  // 所属工序
  processId: '',
  // 图片
  sopUrl: '',
  // 识别ID
  sopId: '21'
})
//工序选项
const processOptions = ref<Array<Object | any>>([])

// 新增功能
const handleAddSop = () => {
  dialogVisible.value = !dialogVisible.value
  sopTitle.value = '添加产品SOP'
}

// 修改sop信息内容
const handleUpdateSopInfo = (value: Object | any) => {
  sopTitle.value = '修改产品SOP'
  dialogVisible.value = !dialogVisible.value
  const params = value?.sopId
  // 调用接口获取sop信息
}

// 删除sop信息内容
const handleDeleteSopInfo = (value: Object | any) => {
  const params = value?.sopId
  ElMessageBox.confirm(`是否确认删除产品SOP编号为【${params}】的数据项？`).then(() => {
    // 调用删除接口
  })
}

// 获取工序选项列表选项
const getProcess = () => {
  processOptions.value = [
    {
      searchValue: null,
      createBy: '',
      createTime: '2023-04-12 10:50:11',
      updateBy: '',
      updateTime: '2023-04-12 17:01:34',
      remark: '',
      params: {},
      processId: 327,
      processCode: 'PROCESS163',
      processName: '主机生产工序',
      attention: '',
      enableFlag: 'Y',
      attr1: null,
      attr2: null,
      attr3: 0,
      attr4: 0,
      manHour: 1008,
      processUrl: null,
      barcodeFormat: null
    },
    {
      searchValue: null,
      createBy: '',
      createTime: '2023-04-12 11:02:58',
      updateBy: '',
      updateTime: '2023-04-12 17:41:43',
      remark: '',
      params: {},
      processId: 328,
      processCode: 'PROCESS164',
      processName: '耳机生产工序',
      attention: '',
      enableFlag: 'Y',
      attr1: null,
      attr2: null,
      attr3: 0,
      attr4: 0,
      manHour: 20,
      processUrl: null,
      barcodeFormat: null
    },
    {
      searchValue: null,
      createBy: '',
      createTime: '2023-04-12 11:28:24',
      updateBy: '',
      updateTime: '2023-04-12 17:41:39',
      remark: '',
      params: {},
      processId: 329,
      processCode: 'PROCESS165',
      processName: '电瓶工序',
      attention: '',
      enableFlag: 'Y',
      attr1: null,
      attr2: null,
      attr3: 0,
      attr4: 0,
      manHour: 20,
      processUrl: null,
      barcodeFormat: null
    },
    {
      searchValue: null,
      createBy: '',
      createTime: '2023-04-12 20:32:08',
      updateBy: '',
      updateTime: '2023-04-12 20:41:14',
      remark: '254',
      params: {},
      processId: 333,
      processCode: 'PROCESS170',
      processName: '电动车生产工序',
      attention: '21',
      enableFlag: 'Y',
      attr1: null,
      attr2: null,
      attr3: 0,
      attr4: 0,
      manHour: 100,
      processUrl: null,
      barcodeFormat: null
    }
  ]
}

// sop产品详情 - 提交按钮
const handleSubmitForm = () => {
  dialogVisible.value = !dialogVisible.value
  if (sopForm.sopId) {
    // 更新接口
    console.log('更新接口')
  } else {
    // 新增接口
    console.log('新增接口')
  }
}

// sop产品详情 - 退出按钮
const handleResetForm = () => {
  if (!ruleFormRef.value) return
  ruleFormRef.value.resetFields()
  dialogVisible.value = !dialogVisible.value
}

// 总体调用
const info = () => {
  getProcess()
}

// 初始化
onMounted(() => {
  info()
})
</script>

<style scoped lang="scss">
.images {
  display: flex;
  margin-top: 20px;
  flex-wrap: wrap;

  .image-middle {
    margin-right: 13px;
    margin-bottom: 15px;

    .image {
      width: 110px;
      height: 110px;
    }

    .img-text {
      margin-bottom: 5px;
      margin-top: 5px;

      max-width: 110px;
      /* 当文本内容超出容器宽度时，隐藏超出部分并显示省略号 */
      text-overflow: ellipsis;
      /* 保留所有空格和换行符，并将文本截断为容器宽度 */
      white-space: nowrap;
      /* 在移动到元素上时，显示完整文本内容 */
      overflow: hidden;
      word-wrap: break-word;
      transition: all 0.4s;
    }

    /* 在元素上悬停时，取消文本的省略号和隐藏，以显示完整文本 */
    .img-text:hover {
      transform: scale(1.1);
      text-overflow: clip;
      white-space: normal;
    }
  }
}
</style>
