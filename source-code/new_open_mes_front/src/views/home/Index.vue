<template>
  <div>
    <el-card shadow="never">
      <el-skeleton :loading="loading" animated>
        <el-row :gutter="20" justify="space-between">
          <el-col :xl="12" :lg="12" :md="12" :sm="24" :xs="24">
            <div class="flex items-center justify-center">
              <img :src="avatar" alt="" class="w-70px h-70px rounded-[50%] mr-20px" />
              <div>
                <div class="text-20px text-700">
                  {{ t('workplace.welcome') }} {{ username }} {{ t('workplace.happyDay') }}
                </div>
                <div class="mt-10px text-14px text-gray-500">
                  {{ t('workplace.toady') }}，20℃ - 32℃！
                </div>
                <div class="mt-10px text-14px text-gray-500">
                  {{ dateYear }} {{ dateDay }} {{ dateWeek }}
                </div>
              </div>
            </div>
            <div>
              <div class="loader">
                <div class="red bar"></div>
                <div class="orange bar"></div>
                <div class="yellow bar"></div>
                <div class="green bar"></div>
                <div class="blue bar"></div>
                <div class="violet bar"></div>
              </div>
            </div>
          </el-col>
          <el-col :xl="12" :lg="12" :md="12" :sm="24" :xs="24">
            <div class="card-day">
              <div class="container">
                <div class="cloud front">
                  <span class="left-front"></span>
                  <span class="right-front"></span>
                </div>
                <span class="sun sunshine"></span>
                <span class="sun"></span>
                <div class="cloud back">
                  <span class="left-back"></span>
                  <span class="right-back"></span>
                </div>
              </div>

              <div class="card-header">
                <span>{{ titleName }}</span>
                <span>{{ dateYear }} {{ dateDay }} {{ dateWeek }}</span>
              </div>

              <span class="temp">23°</span>
            </div>
            <!-- <div class="flex h-70px items-center justify-end <sm:mt-10px">
              <div class="px-8px text-right">
                <div class="text-14px text-gray-400 mb-20px">{{ t('workplace.project') }}</div>
                <CountTo
                  class="text-20px"
                  :start-val="0"
                  :end-val="totalSate.project"
                  :duration="2600"
                />
              </div>
              <el-divider direction="vertical" />
              <div class="px-8px text-right">
                <div class="text-14px text-gray-400 mb-20px">{{ t('workplace.toDo') }}</div>
                <CountTo
                  class="text-20px"
                  :start-val="0"
                  :end-val="totalSate.todo"
                  :duration="2600"
                />
              </div>
              <el-divider direction="vertical" border-style="dashed" />
              <div class="px-8px text-right">
                <div class="text-14px text-gray-400 mb-20px">{{ t('workplace.access') }}</div>
                <CountTo
                  class="text-20px"
                  :start-val="0"
                  :end-val="totalSate.access"
                  :duration="2600"
                />
              </div>
            </div> -->
          </el-col>
        </el-row>
      </el-skeleton>
    </el-card>
  </div>

  <el-row class="data-style mt-5px" :gutter="20" justify="space-between">
    <el-col :xl="16" :lg="16" :md="24" :sm="24" :xs="24" class="mb-10px">
      <el-card shadow="never">
        <template #header>
          <div class="flex justify-between h-3">
            <span>{{ t('workplace.project') }}</span>
          </div>
        </template>

        <el-row>
          <!-- <el-col
            :span="8"
            style="text-align: center"
            v-for="(item, index) in orderQuantity"
            :key="index"
          >
            <div style="margin-bottom: 12px; display: block; font-size: 24px">{{ item.title }}</div>
            <div class="grid-content ep-bg-purple">
              <el-progress type="dashboard" :percentage="item.percentage" :color="colors">
                <template #default="{ percentage }">
                  <CountTo
                    class="text-20px"
                    :start-val="0"
                    :end-val="percentage"
                    :duration="3600"
                  />
                </template>
              </el-progress>
            </div>
          </el-col> -->
          <el-col> <div id="myEcharts" :style="{ height: '340px' }"></div></el-col>
        </el-row>
      </el-card>
    </el-col>
    <el-col :xl="8" :lg="8" :md="24" :sm="24" :xs="24" class="mb-10px">
      <el-card shadow="never">
        <template #header>
          <div class="flex justify-between h-3">
            <span>{{ t('workplace.quickOperation') }}</span>
          </div>
        </template>
        <el-skeleton :loading="loading" animated>
          <div class="cards">
            <div
              :class="['card', item.tips]"
              @click="handleRouter(item.url)"
              v-for="(item, index) in routerList"
              :key="index"
            >
              <p class="tip">{{ item.label }}</p>
              <p class="second-text">{{ item.enLabel }}</p>
            </div>
          </div>
        </el-skeleton>
      </el-card>
    </el-col>
    <el-col :xl="26" :lg="26" :md="24" :sm="24" :xs="24" class="mb-10px">
      <el-card shadow="never">
        <template #header>
          <div class="flex justify-between h-3">
            <span>{{ t('workplace.shortcutOperation') }}</span>
          </div>
        </template>
        <el-col>
          <div id="barEcharts" :style="{ height: '340px' }"></div>
        </el-col>
      </el-card>
    </el-col>
  </el-row>
</template>
<script setup lang="ts" name="Home">
import { set } from 'lodash-es'
import { EChartsOption } from 'echarts'
// import { formatTime } from '@/utils'
import dayjs from 'dayjs'
import { onMounted, onBeforeUnmount, markRaw } from 'vue'
import { useUserStore } from '@/store/modules/user'
import { useWatermark } from '@/hooks/web/useWatermark'
import avatarImg from '@/assets/imgs/avatar.jpg'
import type { WorkplaceTotal, Project, Notice, Shortcut } from './types'
import { pieOptions, barOptions } from './echarts-data'
import { useRouter } from 'vue-router'
import * as echarts from 'echarts'
import { getWorkerOrderAll, getStartOrders, getCompleted, getFindStatus } from '@/api/home/index'

const router = useRouter()

const { t } = useI18n()
const titleName = import.meta.env.VITE_APP_TITLE
let echart = echarts
const userStore = useUserStore()
const { setWatermark } = useWatermark()
const loading = ref(true)
// const avatar = userStore.getUser.avatar ? userStore.getUser.avatar : avatarImg
const avatar = avatarImg
const username = userStore.getUser.nickname
const pieOptionsData = reactive<EChartsOption>(pieOptions) as EChartsOption
// 获取统计数
let totalSate = reactive<WorkplaceTotal>({
  project: 0,
  access: 0,
  todo: 0
})
const colors = [
  { color: '#f56c6c', percentage: 20 },
  { color: '#e6a23c', percentage: 40 },
  { color: '#5cb87a', percentage: 60 },
  { color: '#1989fa', percentage: 80 },
  { color: '#6f7ad3', percentage: 100 }
]

// 饼图格式数据
const pieOption = ref<any>({
  tooltip: {
    trigger: 'item'
  },
  legend: {
    top: '2%',
    left: 'left',
    orient: 'vertical'
  },
  series: [
    {
      name: '生产订单',
      type: 'pie',
      roseType: 'radius',
      radius: ['40%', '70%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: {
        show: true,
        formatter(param) {
          return param.name + ' (' + param.value + ')'
        }
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 40,
          fontWeight: 'bold'
        }
      },
      labelLine: {
        show: false
      },
      data: []
    }
  ]
})

// 柱状图数据格式
const barOption = ref<any>({
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    }
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
      data: [120, 200, 150, 80, 70, 110, 130],
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

const getCount = async () => {
  const data = {
    project: 40,
    access: 2340,
    todo: 10
  }
  totalSate = Object.assign(totalSate, data)
}

const routerList = [
  {
    url: '/prodMgmt/reportWorkTwo',
    label: '生产报工2',
    enLabel: 'Production report',
    tips: 'red'
  },
  {
    url: '/prodMgmt/reportWork',
    label: '报工设备',
    enLabel: 'Reporting equipment',
    tips: 'blue'
  },
  {
    url: '/prodMgmt/workHours',
    label: '工时管理',
    enLabel: 'Working hours management',
    tips: 'green'
  }
]

const handleRouter = (value) => {
  router.push(value)
}

// 获取项目数
let projects = reactive<Project[]>([])
const getProject = async () => {
  const data = [
    {
      name: 'Github',
      icon: 'akar-icons:github-fill',
      message: 'workplace.introduction',
      personal: 'Archer',
      time: new Date()
    },
    {
      name: 'Vue',
      icon: 'logos:vue',
      message: 'workplace.introduction',
      personal: 'Archer',
      time: new Date()
    },
    {
      name: 'Angular',
      icon: 'logos:angular-icon',
      message: 'workplace.introduction',
      personal: 'Archer',
      time: new Date()
    },
    {
      name: 'React',
      icon: 'logos:react',
      message: 'workplace.introduction',
      personal: 'Archer',
      time: new Date()
    },
    {
      name: 'Webpack',
      icon: 'logos:webpack',
      message: 'workplace.introduction',
      personal: 'Archer',
      time: new Date()
    },
    {
      name: 'Vite',
      icon: 'vscode-icons:file-type-vite',
      message: 'workplace.introduction',
      personal: 'Archer',
      time: new Date()
    }
  ]
  projects = Object.assign(projects, data)
}

// 获取通知公告
let notice = reactive<Notice[]>([])
const getNotice = async () => {
  const data = [
    {
      title: '系统升级版本',
      type: '通知',
      keys: ['通知', '升级'],
      date: new Date()
    },
    {
      title: '系统凌晨维护',
      type: '公告',
      keys: ['公告', '维护'],
      date: new Date()
    },
    {
      title: '系统升级版本',
      type: '通知',
      keys: ['通知', '升级'],
      date: new Date()
    },
    {
      title: '系统凌晨维护',
      type: '公告',
      keys: ['公告', '维护'],
      date: new Date()
    }
  ]
  notice = Object.assign(notice, data)
}

// 获取快捷入口
let shortcut = reactive<Shortcut[]>([])

const getShortcut = async () => {
  const data = [
    {
      name: 'Github',
      icon: 'akar-icons:github-fill',
      url: 'github.io'
    },
    {
      name: 'Vue',
      icon: 'logos:vue',
      url: 'vuejs.org'
    },
    {
      name: 'Vite',
      icon: 'vscode-icons:file-type-vite',
      url: 'https://vitejs.dev/'
    },
    {
      name: 'Angular',
      icon: 'logos:angular-icon',
      url: 'github.io'
    },
    {
      name: 'React',
      icon: 'logos:react',
      url: 'github.io'
    },
    {
      name: 'Webpack',
      icon: 'logos:webpack',
      url: 'github.io'
    }
  ]
  shortcut = Object.assign(shortcut, data)
}

// 用户来源
const getUserAccessSource = async () => {
  const data = [
    { value: 335, name: 'analysis.directAccess' },
    { value: 310, name: 'analysis.mailMarketing' },
    { value: 234, name: 'analysis.allianceAdvertising' },
    { value: 135, name: 'analysis.videoAdvertising' },
    { value: 1548, name: 'analysis.searchEngines' }
  ]
  set(
    pieOptionsData,
    'legend.data',
    data.map((v) => t(v.name))
  )
  pieOptionsData!.series![0].data = data.map((v) => {
    return {
      name: t(v.name),
      value: v.value
    }
  })
}
const barOptionsData = reactive<EChartsOption>(barOptions) as EChartsOption

// 周活跃量
const getWeeklyUserActivity = async () => {
  const data = [
    { value: 13253, name: 'analysis.monday' },
    { value: 34235, name: 'analysis.tuesday' },
    { value: 26321, name: 'analysis.wednesday' },
    { value: 12340, name: 'analysis.thursday' },
    { value: 24643, name: 'analysis.friday' },
    { value: 1322, name: 'analysis.saturday' },
    { value: 1324, name: 'analysis.sunday' }
  ]
  set(
    barOptionsData,
    'xAxis.data',
    data.map((v) => t(v.name))
  )
  set(barOptionsData, 'series', [
    {
      name: t('analysis.activeQuantity'),
      data: data.map((v) => v.value),
      type: 'bar'
    }
  ])
}

const getAllApi = async () => {
  await Promise.all([
    getCount(),
    getProject(),
    getNotice(),
    getShortcut(),
    getUserAccessSource(),
    getWeeklyUserActivity()
  ])
  loading.value = false
}

let dateDay = ref()
let dateYear = ref()
let dateWeek = ref()
let weekday = ref(['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'])
let timer = ref<any>(null)

const getBarEcharts = () => {
  let chart = markRaw(echart.init(document.getElementById('barEcharts') as HTMLElement))
  getFindStatus().then((res) => {
    barOption.value.xAxis.data = res.name
    barOption.value.series[0].data = res.numData
    // 使用刚指定的配置项和数据显示图表。
    chart.setOption(barOption.value)
  })
}

console.log(0.1 + 0.2)
console.log(0.3 - 0.1)
console.log(0.2 * 0.1)
console.log(0.3 / 0.1)

const getEcharts = async () => {
  //  // 基于准备好的dom，初始化echarts实例
  let chart = markRaw(echart.init(document.getElementById('myEcharts') as HTMLElement))

  const results = await Promise.all([getWorkerOrderAll(), getStartOrders(), getCompleted()])
  pieOption.value.series[0].data = results.map((item) => {
    return {
      name: item.name,
      value: item.totalQuentity
    }
  })

  // 使用刚指定的配置项和数据显示图表。
  chart.setOption(pieOption.value)
  // window.onresize = function () {
  //   //自适应大小
  //   chart.resize()
  // }
}

onMounted(() => {
  getAllApi()
  getEcharts()
  getBarEcharts()
  timer.value = setInterval(() => {
    const date = dayjs(new Date())
    dateDay.value = date.format('HH:mm:ss')
    dateYear.value = date.format('YYYY-MM-DD')
    dateWeek.value = date.format(weekday.value[date.day()])
  }, 1000)
})

onBeforeUnmount(() => {
  echart.dispose
  if (timer.value) {
    clearInterval(timer.value)
  }
})
</script>

<style scoped lang="scss">
.loader {
  height: 100px;
  display: flex;
  align-items: flex-end;
  gap: 70px;
  justify-content: center;
}

.bar {
  height: 5px;
  width: 12px;
  animation-duration: 0.45s;
  animation-name: changeHeight;
  animation-iteration-count: infinite;
  animation-direction: alternate;
}

.red {
  background-color: #e50000;
  box-shadow: 1px 1px 10px #e50000;
  animation-delay: 0.1s;
}

.orange {
  background-color: #ff8d00;
  box-shadow: 1px 1px 10px #ff8d00;
  animation-delay: 0.2s;
}

.yellow {
  background-color: #ffee00;
  box-shadow: 1px 1px 10px #ffee00;
  animation-delay: 0.25s;
}

.green {
  background-color: #008121;
  box-shadow: 1px 1px 10px #008121;
  animation-delay: 0.3s;
}

.blue {
  background-color: #004cff;
  box-shadow: 1px 1px 10px #004cff;
  animation-delay: 0.35s;
}

.violet {
  background-color: #760188;
  box-shadow: 1px 1px 10px #760188;
  animation-delay: 0.4s;
}

@keyframes changeHeight {
  from {
    height: 5px;
  }

  to {
    height: 40px;
  }
}
.cards {
  display: flex;
  flex-direction: column;
  gap: 15px;
  align-items: center;
  flex-wrap: wrap;
}

.cards .red {
  background-color: #f43f5e;
}

.cards .blue {
  background-color: #3b82f6;
}

.cards .green {
  background-color: #22c55e;
}

.cards .card {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  text-align: center;
  height: 100px;
  width: 250px;
  border-radius: 10px;
  color: white;
  cursor: pointer;
  transition: 400ms;
}

.cards .card p.tip {
  font-size: 2em;
  font-weight: 700;
}

.cards .card p.second-text {
  font-size: 1em;
}

.cards .card:hover {
  transform: scale(1.1, 1.1);
}

.cards:hover > .card:not(:hover) {
  filter: blur(10px);
  transform: scale(0.9, 0.9);
}

.card-day {
  /* width: 350px; */
  height: 235px;
  position: relative;
  padding: 25px;
  background: radial-gradient(
        178.94% 106.41% at 26.42% 106.41%,
        #fff7b1 0%,
        rgba(255, 255, 255, 0) 71.88%
      )
      /* warning: gradient uses a rotation that is not supported by CSS and may not behave as expected */,
    #ffffff;
  box-shadow: 0px 155px 62px rgba(0, 0, 0, 0.01), 0px 87px 52px rgba(0, 0, 0, 0.05),
    0px 39px 39px rgba(0, 0, 0, 0.09), 0px 10px 21px rgba(0, 0, 0, 0.1),
    0px 0px 0px rgba(0, 0, 0, 0.1);
  border-radius: 23px;
  transition: all 0.8s cubic-bezier(0.15, 0.83, 0.66, 1);
  cursor: pointer;

  .card:hover {
    transform: scale(1.05);
  }

  .container {
    width: 250px;
    height: 250px;
    position: absolute;
    right: -35px;
    top: -50px;
    display: flex;
    align-items: center;
    justify-content: center;
    transform: scale(0.7);
  }

  .cloud {
    width: 250px;
  }

  .front {
    padding-top: 45px;
    margin-left: 25px;
    display: inline;
    position: absolute;
    z-index: 11;
    animation: clouds 8s infinite;
    animation-timing-function: ease-in-out;
  }

  .back {
    margin-top: -30px;
    margin-left: 150px;
    z-index: 12;
    animation: clouds 12s infinite;
    animation-timing-function: ease-in-out;
  }

  .right-front {
    width: 45px;
    height: 45px;
    border-radius: 50% 50% 50% 0%;
    background-color: #4c9beb;
    display: inline-block;
    margin-left: -25px;
    z-index: 5;
  }

  .left-front {
    width: 65px;
    height: 65px;
    border-radius: 50% 50% 0% 50%;
    background-color: #4c9beb;
    display: inline-block;
    z-index: 5;
  }

  .right-back {
    width: 50px;
    height: 50px;
    border-radius: 50% 50% 50% 0%;
    background-color: #4c9beb;
    display: inline-block;
    margin-left: -20px;
    z-index: 5;
  }

  .left-back {
    width: 30px;
    height: 30px;
    border-radius: 50% 50% 0% 50%;
    background-color: #4c9beb;
    display: inline-block;
    z-index: 5;
  }

  .sun {
    width: 120px;
    height: 120px;
    background: -webkit-linear-gradient(to right, #fcbb04, #fffc00);
    background: linear-gradient(to right, #fcbb04, #fffc00);
    border-radius: 60px;
    display: inline;
    position: absolute;
  }

  .sunshine {
    animation: sunshines 2s infinite;
  }

  @keyframes sunshines {
    0% {
      transform: scale(1);
      opacity: 0.6;
    }

    100% {
      transform: scale(1.4);
      opacity: 0;
    }
  }

  @keyframes clouds {
    0% {
      transform: translateX(15px);
    }

    50% {
      transform: translateX(0px);
    }

    100% {
      transform: translateX(15px);
    }
  }

  .card-header {
    display: flex;
    flex-direction: column;
    gap: 10px;
  }

  .card-header span:first-child {
    word-break: break-all;
    font-weight: 800;
    font-size: 15px;
    line-height: 135%;
    color: rgba(87, 77, 51, 0.66);
  }

  .card-header span:last-child {
    font-weight: 700;
    font-size: 15px;
    line-height: 135%;
    color: rgba(87, 77, 51, 0.33);
  }

  .temp {
    position: absolute;
    left: 25px;
    bottom: 12px;
    font-weight: 700;
    font-size: 64px;
    line-height: 77px;
    color: rgba(87, 77, 51, 1);
  }
}
</style>
