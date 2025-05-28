<template>
  <ContentWrap>
    <el-row :gutter="50">
      <el-col :span="7" :xs="25">
        <VDatePicker
          :attributes="VCalendarAttrs"
          expanded
          :rows="2"
          :step="1"
          @dayclick="handleDayClick"
          v-model.range="range"
        />
      </el-col>
      <el-col :span="16" :xs="25">
        <el-tabs v-model="activeName" class="demo-tabs">
          <el-tab-pane label="排产" name="first">
            <el-calendar v-model="calendarDate">
              <template #date-cell="{ data }">
                <div style="height: 100%" @contextmenu.prevent="handleSetCalendar(data)">
                  <el-row>
                    <el-col :span="15">
                      <div class="lunar" style="font-size: 17px">
                        {{ data.day.split('-').slice(1).join('-') }}
                      </div>
                    </el-col>
                    <el-col :span="8">
                      <el-tag effect="dark" type="success">班</el-tag>
                    </el-col>
                  </el-row>

                  <div :key="index" v-for="(item, index) in calendarChartData">
                    <el-row v-if="item.date == data.day">
                      <el-col style="font-size: 14px; color: #409eff" :span="24"
                        >班组数量：{{ item.teamCount }}</el-col
                      >
                    </el-row>
                  </div>
                </div>
              </template>
            </el-calendar>
          </el-tab-pane>
          <el-tab-pane label="报工" name="2">
            <div id="barsSchedulingECharts" :style="{ height: '520px', width: '100vh' }"></div>
            <el-pagination
              background
              @current-change="handleSizeChange"
              :default-page-size="7"
              layout="prev, pager, next"
              :total="barCurrentPage"
            />
            <div id="barsReportWorkECharts" :style="{ height: '520px', width: '100vh' }"></div>
            <el-pagination
              background
              @current-change="handleReportWorkSizeChange"
              :default-page-size="7"
              layout="prev, pager, next"
              :total="barReportWorkCurrentPage"
            />
          </el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>
  </ContentWrap>

  <XModal v-model="calendarVisible" title="设置日期" width="70%" @close="handleCloseCalendar">
    <el-form :model="formInline" :inline="true">
      <el-row>
        <el-col :span="8">
          <el-form-item label="班组名称">
            <el-select
              v-model="formInline.teamName"
              placeholder="请选择班组名称"
              clearable
              @change="handleQueryTeamName"
            >
              <el-option
                v-for="item in teamNameOptions"
                :key="item.teamCode"
                :label="item.teamName"
                :value="item.teamCode"
              />
            </el-select> </el-form-item
        ></el-col>
        <el-col :span="11">
          <el-form-item>
            <el-radio-group v-model="formInline.timeOfDayActive" @change="handleRadio">
              <el-radio label="白班" size="large">白班</el-radio>
              <el-radio label="中班" size="large">中班</el-radio>
              <el-radio label="晚班" size="large">晚班</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="3">
          <el-form-item label="工作类型">
            <el-switch
              style="--el-switch-on-color: #409eff; --el-switch-off-color: #67c23a"
              v-model="formInline.isWorkingDay"
              active-text="班"
              inactive-text="休"
              @change="handleWorkingStateSwitch"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <common-table
      :pagination="pagination"
      :tableData="tableDataList"
      :columns="columnData"
      :loading="tableLoading"
    />
    <template #footer></template>
  </XModal>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import commonTable from '@/components/CommonTable/index.vue'
import { columnData } from './data'
import * as echarts from 'echarts'
import {
  getBarEchartsData,
  getCalendarDataAPI,
  getTodayTeamInfo,
  getTeamNamePeopleListAPI,
  getBarReportWorkData
} from '@/api/scheduling/schedulingCalendar'

defineProps({})
let echart = echarts

const activeName = ref('first')
const pagination = reactive<Table.Pagination>({
  total: 0, // 总条目数
  pageSize: 10, // 每页显示条目个数
  pageSizes: [5, 10, 20, 50, 100], //每页显示个数选择器的选项设置
  currentPage: 1 // 当前页数
})
// 日历组件参数
const VCalendarAttrs = ref([
  {
    key: 'today',
    highlight: true,
    dates: new Date(),
    popover: {
      label: '美好的一天！要开心呦！'
    }
  }
])
const range = ref({
  start: new Date(),
  end: new Date()
})
const calendarDate = ref(new Date())
const calendarVisible = ref<boolean>(false)
const formInline = ref<any>({
  teamName: '',
  timeOfDayActive: '白班',
  isWorkingDay: true
})
// 班组列表
const teamNameOptions = ref<any>([])
const tableLoading = ref<boolean>()
const tableDataList = ref([])
const chartDom = ref<any>()
// 排产柱状图数据格式
const schedulingBarOption = ref<any>({
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    }
  },
  title: {
    left: 'center',
    text: '' // 报工柱状图
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  xAxis: {
    type: 'category',
    data: [],
    axisTick: {
      alignWithLabel: true
    }
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      label: {
        show: true,
        fontSize: 37,
        position: 'inside'
      },
      data: [],
      type: 'bar',
      showBackground: true,
      backgroundStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: '#83bff6' },
          { offset: 0.5, color: '#188df0' },
          { offset: 1, color: '#188df0' }
        ])
      },
      emphasis: {
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: '#2378f7' },
            { offset: 0.7, color: '#2378f7' },
            { offset: 1, color: '#83bff6' }
          ])
        }
      }
    }
  ]
})
// 报工柱状图数据格式
const reportWorkBarOption = ref<any>({
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    }
  },
  title: {
    left: 'center',
    text: '' // 报工柱状图
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  xAxis: {
    type: 'category',
    data: [],
    axisTick: {
      alignWithLabel: true
    }
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      label: {
        show: true,
        fontSize: 37,
        position: 'inside'
      },
      data: [],
      type: 'bar',
      showBackground: true,
      backgroundStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: '#83bff6' },
          { offset: 0.5, color: '#188df0' },
          { offset: 1, color: '#188df0' }
        ])
      },
      emphasis: {
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: '#2378f7' },
            { offset: 0.7, color: '#2378f7' },
            { offset: 1, color: '#83bff6' }
          ])
        }
      }
    }
  ]
})
const barCurrentPage = ref<number>(0)
// 排产柱状图分页
const schedulingCurrentPage = ref<number>(1)

const barReportWorkCurrentPage = ref<number>(0)
// 报工柱状图分页
const reportWorkCurrentPage = ref<number>(1)

// 日历图数据
const calendarChartData = ref()

// 班组名称下拉数据
const handleQueryTeamName = () => {
  getTeamNamePeopleList()
}

// 排产柱状图分页改变
const handleSizeChange = (value) => {
  schedulingCurrentPage.value = value
  infoBarScheduling()
}

// 报工柱状图分页的改变
const handleReportWorkSizeChange = (value) => {
  reportWorkCurrentPage.value = value
  infoBarReportWorkData()
}

// 日历框的点击事件
const handleDayClick = (value) => {
  // currentpage.value = value
}

// 工作类型开关
const handleWorkingStateSwitch = (value) => {}

// 单选框选择事件
const handleRadio = () => {
  getTeamNamePeopleList()
}

// 日历点击打开弹框
const handleSetCalendar = async (data) => {
  await getTodayTeamInfo({ time: data.day }).then((res) => {
    const { list } = res || {}
    teamNameOptions.value = list
    formInline.value.teamName = list[0]?.teamCode
  })
  getTeamNamePeopleList() // 在getTodayTeamInfo 之后执行getTeamNamePeopleList
  calendarVisible.value = true // 在getTodayTeamInfo之后执行calendarVisible赋值为true
}

// 通过班组和排班时间查询人数
const getTeamNamePeopleList = () => {
  const params = {
    teamCode: formInline.value.teamName,
    shiftWay: formInline.value.timeOfDayActive
  }
  tableLoading.value = true
  getTeamNamePeopleListAPI(params)
    .then((res) => {
      tableDataList.value = res
    })
    .finally(() => {
      tableLoading.value = false
    })
}

// 日历弹框关闭事件
const handleCloseCalendar = () => {
  formInline.value = { timeOfDayActive: '白班' }
  calendarVisible.value = false
}

const getBarEcharts = () => {
  if (chartDom.value != null && chartDom.value != '' && chartDom.value != undefined)
    return chartDom.value.dispose()
  infoBarScheduling()
  infoBarReportWorkData()

  window.onresize = function () {
    chartDom.value.resize()
  }
}

// 初始化获取排产柱状图
const infoBarScheduling = () => {
  let chart = markRaw(echart.init(document.getElementById('barsSchedulingECharts') as HTMLElement))
  getBarEchartsData({ page: schedulingCurrentPage.value }).then((res) => {
    schedulingBarOption.value.title.text = '排产柱状图'
    barCurrentPage.value = res.total
    schedulingBarOption.value.xAxis.data = res.time
    schedulingBarOption.value.series[0].data = res.quantity
    chart.setOption(schedulingBarOption.value, true)
  })
}

// 初始化获取报工柱状图
const infoBarReportWorkData = () => {
  let barsReportWorkECharts = markRaw(
    echart.init(document.getElementById('barsReportWorkECharts') as HTMLElement)
  )
  getBarReportWorkData({ page: reportWorkCurrentPage.value }).then((res) => {
    reportWorkBarOption.value.title.text = '报工柱状图'
    barReportWorkCurrentPage.value = res.total
    reportWorkBarOption.value.xAxis.data = res.time
    reportWorkBarOption.value.series[0].data = res.quantity
    barsReportWorkECharts.setOption(reportWorkBarOption.value, true)
  })
}

const getCalendarDataInfo = () => {
  getCalendarDataAPI().then((res) => {
    calendarChartData.value = res.result
  })
}

onMounted(() => {
  getBarEcharts()
  getCalendarDataInfo()
})
</script>

<style scoped lang="scss">
:deep .el-calendar .el-calendar__header .el-calendar__title {
  font-size: 17px;
}

:deep .vc-container .vc-weekday-1,
:deep .vc-container .vc-weekday-7 {
  color: #409eff;
}
</style>
