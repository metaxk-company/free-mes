<template>
  <div>
    <el-row :gutter="50">
      <el-col :span="6"
        ><div class="grid-left-card">
          <div class="statistic-card">
            <div v-for="(item, index) in statsData" :key="index">
              <el-statistic :value="item.quantity">
                <template #title>
                  <div style="display: inline-flex; align-items: center; font-size: 20px">
                    本月工时总数
                  </div>
                </template>
              </el-statistic>
              <div class="statistic-footer">
                <div class="footer-item">
                  <span :class="item.date == 'week' ? 'green' : 'red'">
                    <CountTo
                      class="text-20px"
                      :start-val="0"
                      :end-val="item.sameRatio"
                      :duration="3600"
                    />
                    %
                    <el-icon>
                      <component :is="item.date == 'week' ? 'CaretTop' : 'CaretBottom'" />
                    </el-icon>
                  </span>
                  <span>同比上周</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="18">
        <el-tabs v-model="activeName" class="demo-tabs">
          <el-tab-pane label="今日" name="today" />
          <el-tab-pane label="本周" name="tswk" />
          <el-tab-pane label="本月" name="month" />
        </el-tabs>
        <div id="barEcharts" :style="{ height: '340px' }"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import * as echarts from 'echarts'
import { onMounted, onBeforeUnmount, watch, markRaw } from 'vue'

const echart = echarts
const chartDom = ref<any>()

const statsData = ref([
  // 本月工时总数
  {
    quantity: 1234,
    sameRatio: 123,
    date: 'week'
  },
  // 本周工时数量
  {
    quantity: 32123,
    sameRatio: 3,
    date: 'month'
  }
])

// 柱状图数据
const barOption = ref<any>({
  title: {
    text: ''
  },
  xAxis: {
    type: 'category',
    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      data: [120, 200, 150, 80, 70, 110, 130],
      type: 'bar',
      showBackground: true,
      backgroundStyle: {
        color: 'rgba(180, 180, 180, 0.2)'
      }
    }
  ],
  grid: {
    top: '20%', // 一下数值可为百分比也可为具体像素值
    bottom: '10%',
    left: '4%',
    right: '10%'
  }
})
const activeName = ref('today')

watch(
  () => activeName.value,
  () => {
    barOption.value.xAxis.data = [120, 200, 150, 80, 70, 110, 130]
    // 调用柱状图参数接口
    getBarEcharts()
  },
  { deep: true }
)

// 获取柱状图数据
const getBarEcharts = () => {
  if (chartDom.value != null && chartDom.value != '' && chartDom.value != undefined) {
    chartDom.value.dispose() //解决echarts dom已经加载的报错
  }

  chartDom.value = markRaw(echart.init(document.getElementById('barEcharts') as HTMLElement))

  chartDom.value.setOption(barOption.value, true)
  window.onresize = function () {
    chartDom.value.resize()
  }
}

onMounted(() => {
  getBarEcharts()
})

onBeforeUnmount(() => {
  echart.dispose
})
</script>

<style scoped lang="scss">
.grid-left-card {
  padding: 20px;
  box-sizing: border-box;
  height: 390px;
  border-radius: 50px;
  background: #ffffff;
  box-shadow: 20px 20px 60px #d9d9d9, -20px -20px 60px #ffffff;
}

:global(h2#card-usage ~ .example .example-showcase) {
  background-color: var(--el-fill-color) !important;
}

.el-statistic {
  --el-statistic-content-font-size: 28px;
}

.statistic-card {
  height: 100%;
  padding: 20px;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: center;
  border-radius: 4px;
  background-color: var(--el-bg-color-overlay);
}

.statistic-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  font-size: 12px;
  color: var(--el-text-color-regular);
  margin-top: 16px;
}

.statistic-footer .footer-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.statistic-footer .footer-item span:last-child {
  display: inline-flex;
  align-items: center;
  margin-left: 4px;
}

.green {
  color: var(--el-color-success);
}
.red {
  color: var(--el-color-error);
}
</style>
