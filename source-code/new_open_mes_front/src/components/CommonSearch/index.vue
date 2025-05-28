<template>
  <!-- <el-row :class="['search-content', showBack ? 'pack-up' : 'pack-away']" ref="stateDom"> -->
  <el-row :class="['search-content', showBack ? 'pack-up' : 'pack-away']">
    <div
      v-for="(item, index) in conditionsList"
      :key="index"
      :class="[item.type !== 'dateTime' ? 'search-item' : 'search-date']"
    >
      <!-- 输入框 -->
      <div class="border-style" v-if="item.type === 'input'">
        <div :class="item.textWidth" class="font-style">{{ item.label }}</div>
        <el-input
          @keyup.enter="handleQueryData"
          v-model="searchModel[item.prop]"
          :placeholder="item.placeholder"
          style="width: 220px"
        />
      </div>

      <!-- 下拉框 -->
      <div class="border-style" v-if="item.type === 'select'">
        <div class="font-style">{{ item.label }}</div>
        <el-select
          clearable
          @keyup.enter="handleQueryData"
          v-model="searchModel[item.prop]"
          :placeholder="item.placeholder"
          filterable
          style="width: 220px"
        >
          <el-option
            v-for="(params, key) in item.options"
            :key="key"
            :label="params.label"
            :value="params.value"
          />
        </el-select>
      </div>

      <!-- 日期选择器 -->
      <div class="border-style" v-if="item.type === 'date'">
        <div class="font-style">{{ item.label }}</div>
        <el-date-picker
          :placeholder="item.placeholder"
          type="date"
          @keyup.enter="handleQueryData"
          :value-format="item.valueFormat || 'YYYY-MM-DD'"
          v-model="searchModel[item.prop]"
        />
      </div>

      <!-- 日期时间选择器 -->
      <div class="border-style" v-if="item.type === 'dateTime'">
        <div class="font-style">{{ item.label }}</div>
        <el-date-picker
          style="width: 220px"
          v-model="searchModel[item.prop]"
          type="datetime"
          @keyup.enter="handleQueryData"
          :placeholder="item.placeholder"
          format="YYYY-MM-DD HH:mm:ss"
          value-format="YYYY-MM-DD HH:mm"
        />
      </div>

      <!-- 日期区间查询 -->
      <div class="border-style" v-if="item.type === 'scopeTime'">
        <div class="font-style">{{ item.label }}</div>
        <el-date-picker
          style="width: 220px"
          v-model="searchModel[item.prop]"
          type="daterange"
          @keyup.enter="handleQueryData"
          value-format="YYYY-MM-DD"
          :range-separator="rangeSeparator"
          :start-placeholder="stateText"
          :end-placeholder="endText"
        />
      </div>
    </div>

    <div class="search-btn">
      <el-button @click="handleQueryData" type="primary" plain>查询</el-button>
      <el-button @click="handleResetData" v-if="showResetBtn" plain>重置</el-button>
      <el-button link :icon="showIcon" v-if="showExpand" @click="handleSpread">{{
        showLabel
      }}</el-button>
    </div>
  </el-row>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
const props = defineProps({
  // 搜索条件列表
  conditionsList: {
    type: Array<Object | any>,
    default: () => []
  },
  // 搜索字段
  searchModel: {
    type: Object,
    default: () => {}
  },
  // 是否显示重置按钮
  showResetBtn: {
    type: Boolean,
    default: true
  },
  // 开始时间文字
  stateText: {
    type: String,
    default: '开始时间'
  },
  // 结束时间文字
  endText: {
    type: String,
    default: '结束时间'
  },
  // 时间中间区分
  rangeSeparator: {
    type: String,
    default: '-'
  }
})

const emit = defineEmits(['query-data'])
// 展开状态
let showBack = ref<boolean>(true)
// 计算是否需要自动显示展开按钮
const showExpand = computed(() => props.conditionsList.length >= 4)
// 根据状态文字
const showLabel = computed(() => (showBack.value ? '展开' : '收起'))
// 根据状态显示icon
const showIcon = computed(() => (showBack.value ? 'ArrowUp' : 'ArrowDown'))

// 重置功能
const handleResetData = () => {
  for (let i in props.searchModel) {
    props.searchModel[i] = ''
  }
  handleQueryData()
}

// 查询功能
const handleQueryData = () => {
  emit('query-data')
}

// 展开/收起功能
const handleSpread = () => {
  // if (stateDom.value.$el.style.maxHeight) {
  //   stateDom.value.$el.style.maxHeight = null
  // } else {
  //   stateDom.value.$el.style.maxHeight = stateDom.value.$el.style.scrollHeight + 'px'
  // }
  showBack.value = !showBack.value
}

defineExpose({
  handleResetData
})
</script>

<style scoped lang="scss">
:deep .el-input__wrapper {
  width: 182px;
}
.tab-content {
  min-width: 60px;
  // position: relative;
  // display: flex;
  // flex-wrap: wrap;
  background: #ffff;
  overflow: hidden;
  transition: max-height 1s ease-out;
}
:deep .el-date-editor {
  width: 100%;
}
.search-content {
  min-width: 60px;
  // position: relative;
  display: flex;
  flex-wrap: wrap;
  // background: $font-black-9;
  overflow: hidden;
  transition: max-height 1s ease-out;

  &.pack-up {
    // height: 32px !important;
    // transition: all 1s ease;

    // // transition: height 0.5s ease-out;
    // overflow: hidden !important;
    height: 32px;
    overflow: hidden;
    transition: max-height 0.3s linear;
  }

  &.pack-away {
    transition: max-height 0.3s linear;
    overflow: hidden;
  }

  .search-item {
    margin-right: 48px;
    margin-bottom: 24px;
    display: flex;
    flex: 0 0 24%;
    align-items: center;
    transition: height 0.5s ease-out;
  }

  .search-btn {
    position: absolute;
    right: 0;
  }

  .search-date {
    margin-right: 48px;
    flex: 0 0 24%;
    width: 300px;
  }
  .border-style {
    display: flex;
    align-items: center;
    min-width: 264px;

    .font-style {
      flex: 0 0 auto;
      margin-right: 10px;
      font-family: 'PingFang SC';
      font-style: normal;
      font-weight: 400;
      font-size: 14px;
      line-height: 22px;
    }
  }
}
</style>
