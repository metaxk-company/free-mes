<template>
  <div>
    <div class="pillar" v-for="(item, index) in printDataInfo" :key="index">
      <div class="print-header">
        <span class="header-text">生产任务单记录</span>
        <img :src="item.taskUrl" alt="" />
        <span class="time">{{ DateInfo }}</span>
      </div>

      <div class="descry-info">
        <el-descriptions border :column="3">
          <el-descriptions-item label="订单编号">{{
            item.printDescribe.workorderCode
          }}</el-descriptions-item>
          <el-descriptions-item label="产品编号">{{
            item.printDescribe.itemCode
          }}</el-descriptions-item>
          <el-descriptions-item label="单位">{{
            item.printDescribe.measureName
          }}</el-descriptions-item>
          <el-descriptions-item label="任务单号">{{
            item.printDescribe.taskCode
          }}</el-descriptions-item>
          <el-descriptions-item label="产品名称">{{
            item.printDescribe.itemName
          }}</el-descriptions-item>
          <el-descriptions-item label="数量">{{
            item.printDescribe.quantity
          }}</el-descriptions-item>
        </el-descriptions>
      </div>

      <el-table :data="item.printTable">
        <el-table-column prop="processCode" label="工序编号" />
        <el-table-column prop="processName" label="工序名称" />
        <el-table-column prop="remark" label="工序备注" />
        <el-table-column label="工序二维码">
          <template #default="scope">
            <img style="height: 70px" :src="scope.row.processUrl" alt="" />
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import CommonTable from '@/components/CommonTable/index.vue'

interface IProps {
  printDataInfo: Array<any>
}

const props = defineProps<IProps>()
const columns = [
  {
    prop: 'processCode',
    label: '工序编号'
  },
  {
    prop: 'processName',
    label: '工序名称'
  },
  {
    prop: 'remark',
    label: '工序备注'
  },
  {
    label: '工序二维码',
    slot: 'processUrl'
  }
]
const DateInfo = computed(() => {
  let DataList = new Date()
  // 格式化日期为年月日
  const year = DataList.getFullYear()
  const month = (DataList.getMonth() + 1).toString().padStart(2, '0')
  const day = DataList.getDate().toString().padStart(2, '0')
  return `${year}-${month}-${day}`
})
</script>

<style scoped lang="scss">
.pillar {
  height: 1080px;
  // page-break-after: always;

  .descry-info {
    margin-bottom: 30px;
  }
  .print-header {
    text-align: center;
    position: relative;
    margin-bottom: 100px;

    .header-text {
      font-size: 30px;
      font-weight: bold;
      margin-top: 30px;
      display: inline-block;
    }

    img {
      width: 100px;
      display: inline-block;
      position: absolute;
      right: 100px;
    }

    .time {
      position: absolute;
      top: 110px;
      right: 110px;
    }
  }

  .print-describe {
    justify-content: space-around;
    display: flex;
    width: 40%;
    flex-wrap: wrap;
    .describe-init {
      display: flex;
      height: 20px;
      .text-describe {
        margin-right: 20px;
        font-weight: bold;
      }
    }
  }
}
</style>
