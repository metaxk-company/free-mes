<template>
  <div class="home">
    <el-table
      :summary-method="getSummaries"
      :show-summary="isShowSummary"
      :sum-text="sumText"
      :highlight-current-row="currentRow"
      ref="multipleTableRef"
      v-loading="loading"
      :border="isBorder"
      style="width: 100%"
      :height="height"
      :data="tableData"
      :header-cell-style="{ backgroundColor: '#f5f5f5' }"
      @selection-change="handleSelectionChange"
      @current-change="handleCurrentChange"
    >
      <el-table-column type="selection" width="40" v-if="isSelection" />
      <template #empty>
        <el-empty description="暂无数据" v-if="isEmpty" />
        <slot v-else name="empty"></slot>
      </template>

      <template v-for="column in columns" :key="column.prop || column.key">
        <el-table-column
          label="序号"
          :width="column.width"
          :align="column.align"
          v-if="column.label == '序号'"
        >
          <template #default="scope">
            <slot name="xiuhao" :scope="scope">
              <span
                >{{ scope.$index + (pagination.currentPage - 1) * pagination.pageSize + 1 }}
              </span>
            </slot>
          </template>
        </el-table-column>
        <el-table-column
          v-else-if="column.slot"
          :label="column.label"
          :width="column.width"
          header-:align="column.align"
          :align="column.align"
          :fixed="column.fixed"
        >
          <template #default="scope">
            <slot :name="column.slot" :scope="scope"></slot>
          </template>
        </el-table-column>

        <el-table-column
          v-else
          :formatter="column?.formatter"
          :label="column.label"
          :width="column.width"
          :prop="column.prop"
          show-overflow-tooltip
          :align="column.align"
        >
          <template #default="{ row }">
            <!-- 对时间处理 -->
            <template v-if="column.type === 'date'">
              <span>{{ timeFormat(row[column.prop as string], column.dateFormat as string) }}</span>
            </template>
          </template></el-table-column
        >
      </template>
    </el-table>

    <div class="pagination">
      <!-- 分页 -->
      <el-pagination
        :hide-on-single-page="isSinglePage"
        style="margin-top: 12px"
        v-model:current-page="pagination.currentPage"
        v-model:page-sizes="pagination.pageSizes"
        v-model:page-size="pagination.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        v-model:total="pagination.total"
        v-if="isShowPagination"
      />
    </div>
  </div>
</template>

<script lang="ts" setup>
import { toRefs, watch, ref } from 'vue'
import { timeFormat } from '@/utils'
import { ElTable } from 'element-plus'

const multipleTableRef = ref<InstanceType<typeof ElTable>>()

const props = withDefaults(
  defineProps<{
    tableData: Array<object> // table的数据
    columns: Table.Column[] // 每列的配置项
    pagination?: Table.Pagination | any //页码
    isSelection?: boolean
    isBorder?: boolean | any
    loading?: boolean | any
    currentRow?: boolean
    getSummaries?: Function | any
    isShowSummary?: boolean
    sumText?: string
    isSinglePage?: boolean
    isShowPagination?: boolean
    height?: number | string
    isEmpty?: boolean
  }>(),
  {
    isShowPagination: true,
    height: '100%',
    isEmpty: true,
    isBorder: true
  }
)

// defineProps({
//   property: {
//     type: Array<Object>,
//     required: true
//   },
//   columns: {
//     type: Array<Object>,
//     required: true
//   },
//   pagination: {
//     type: Object,
//     required: false
//   },
//   isSelection: {
//     type: Boolean,
//     required: false
//   },
//   isBorder: {
//     type: Boolean,
//     required: false
//   },
//   loading: {
//     type: Boolean,
//     required: false
//   },
//   currentRow: {
//     type: Boolean,
//     required: false
//   },
//   isShowSummary: {
//     type: Boolean,
//     required: false
//   },
//   sumText: {
//     type: String,
//     required: false
//   },
//   isSinglePage: {
//     type: Boolean,
//     required: false
//   },
//   isShowPagination: {
//     type: Boolean,
//     required: false
//   }
// })
interface EmitEvent {
  (e: 'selection-change', params: any): void // 当选择项发生变化时会触发该事件
  (e: 'current-change', params: any): void // 当前行选择项发生变化时会触发该事件
  (e: 'size-change', pageSize: number): void // pageSize事件
  (e: 'current-change', currentPage: number): void // currentPage按钮组事件
  (e: 'pagination-change', pagination: object | any): void //pageSize事件和currentPage按钮组事件
  (e: 'get-summaries', pagination: object): void //pageSize事件和currentPage按钮组事件
}
// 使用 v-model 放弃elemntui-Pagination方法
// 监听 current-page ,  page-size 的改变,
const emit = defineEmits<EmitEvent>()
const { pagination } = toRefs(props)
watch(
  // 当前页
  () => pagination?.value?.currentPage,
  (newValue) => {
    pagination.value.currentPage = newValue

    emit('pagination-change', pagination)
  }
)
watch(
  () => pagination?.value?.pageSize,
  (newValue) => {
    pagination.value.pageSize = newValue

    pagination.value.currentPage = 1
    emit('pagination-change', pagination)
  }
)

const handleSelectionChange = (selection) => {
  emit('selection-change', selection)
}

//单选事件
const handleCurrentChange = (selection) => {
  clearCurrentRow(selection)
  emit('current-change', selection)
}
// 取消全部选中事件
const clearSelectionData = () => {
  multipleTableRef.value!.clearSelection()
}

// 取消单选事件
const clearCurrentRow = (value) => {
  multipleTableRef.value!.setCurrentRow(value)
}

defineExpose({
  clearSelectionData,
  clearCurrentRow
})

/*
 * 注册分页 change 方法
 */
// const emit = defineEmits<EmitEvent>();
// const { pagination } = toRefs(props);
// // 切换pageSize
// const pageSizeChange = (pageSize: number) => {
//   pagination.value.pageSize = pageSize;
//   pagination.value.currentPage = 1;
//   emit("pagination-change", pagination);
// };
// // 切换currentPage
// const currentPageChange = (currentPage: number) => {
//   pagination.value.currentPage = currentPage;
//   emit("pagination-change", pagination);
// };
</script>

<style lang="scss" scoped>
.home {
  width: 100%;
}

.pagination {
  width: 100%;
  display: flex;
  margin-top: 10px;
  justify-content: flex-end;
}

.el-tooltip__popper {
  max-width: 20% !important;
}

.el-tooltip__popper,
.el-tooltip__popper.is-dark {
  background: rgb(48, 65, 86) !important;
  color: #fff !important;
  line-height: 20px;
}
</style>
