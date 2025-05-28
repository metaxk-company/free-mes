<template>
  <el-card class="box-card">
    <template #header>
      <div class="card-header">
        <span>{{ cardInfo.title }}</span>
        <el-button
          v-if="!cardInfo?.isEdit"
          class="button"
          link
          type="primary"
          @click="handleResourceSave(cardInfo.cardDescribe)"
          >新增</el-button
        >
      </div>
      <Common-table
        style="height: 170px !important"
        :columns="cardInfo.tableColumns"
        :tableData="cardInfo.tableFormList"
        :pagination="cardInfo.pagination"
      >
        <template #operate="scope" v-if="!cardInfo?.isEdit">
          <el-button link type="primary" size="small" v-if="cardInfo.cardDescribe !== 'facility'"
            >修改</el-button
          >
          <el-button link type="danger" size="small" @click="handleDelete(scope.scope.row)"
            >删除</el-button
          >
        </template>
      </Common-table>
    </template>
  </el-card>
</template>

<script setup lang="ts">
import CommonTable from '@/components/CommonTable/index.vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import { deleteFacilityList } from '@/api/masterData/workstation'

const props = defineProps({
  cardInfo: {
    type: Object,
    default: () => {}
  }
})

const emit = defineEmits<{
  (e: 'handleResourceSave', value: string): void
  (e: 'upDateList', value: string): void
}>()

// 工作站资源新增
const handleResourceSave = (value: string) => {
  emit('handleResourceSave', value)
}
// 删除
const handleDelete = (value) => {
  ElMessageBox.confirm('是否删除当前数据?').then(() => {
    deleteFacilityList([value?.id]).then((res) => {
      ElMessage.success('删除成功')
      emit('upDateList', props.cardInfo.cardDescribe)
    })
  })
}
</script>

<style scoped lang="scss">
.box-card {
  width: 100% !important;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 20px;
}
</style>
