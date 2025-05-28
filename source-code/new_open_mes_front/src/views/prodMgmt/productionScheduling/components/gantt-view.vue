<template>
  <div class="flex" style="margin-bottom: 20px">
    <div style="width: 200px">
      <div class="flex">
        <div style="width: 80px; line-height: 30px; font-size: 15px">任务名</div>
        <el-input
          v-model="ganttInput"
          placeholder="任务名"
          ref="ganttInputRef"
          @input="handleSearch"
        />
      </div>
    </div>
    <el-button @click="handleShowGrid">隐藏左侧表格</el-button>
    <el-button @click="handleFullscreen">全屏</el-button>
  </div>

  <div id="myCover" ref="ganttRef" style="min-height: 300px; overflow: hidden"></div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { gantt } from 'dhtmlx-gantt'

const emit = defineEmits(['handleGanttView', 'handleGanttAdd'])
const props = defineProps({
  ganttDataList: Object
})

// 挂载ref
const ganttRef = ref()
const ganttInputRef = ref()
// const ganttDataList = ref()

// 甘特图搜索值
const ganttInput = ref()

// 搜索功能
const handleSearch = () => {
  gantt.refreshData()
}

const filterLogic = (task, match?): any => {
  match = match || false
  gantt.eachTask(function (child) {
    if (filterLogic(child)) {
      match = true
    }
  }, task.id)

  // 筛选任务的参数
  if (task.text.toLowerCase().indexOf(ganttInput.value.toLowerCase()) > -1) {
    match = true
  }
  return match
}

gantt.attachEvent('onBeforeTaskDisplay', (id, task) => {
  if (!ganttInput.value) {
    return true
  } else {
    return filterLogic(task)
  }
})

// 隐藏左侧表格
const handleShowGrid = () => {
  if (gantt.config.show_grid) {
    gantt.config.show_grid = false
  } else {
    gantt.config.show_grid = true
  }

  // 初始化甘特图
  gantt.init(ganttRef.value)
}

// 全屏Gantt数据
const handleFullscreen = () => {
  gantt.ext.fullscreen.toggle()
}

// 表格操作功能
gantt.attachEvent('onTaskClick', function (id, e) {
  var button = e.target.closest('[data-action]')
  if (button) {
    var action = button.getAttribute('data-action')
    switch (action) {
      case 'view':
        emit('handleGanttView', id)
        break
      case 'add':
        emit('handleGanttAdd', id)
        break
    }
    return false
  } else {
    console.log(id, 12121)
  }
  return true
})

// 初始化Gantt数据
const initConfig = () => {
  // 默认配置
  gantt.config.xml_date = '%Y-%m-%d %H:%i:%s' // 日期格式
  gantt.i18n.setLocale('cn') // 设置中文
  gantt.config.readonly = false // 设置为只读
  gantt.config.show_links = false // 禁用连线
  // 显示列配置
  gantt.config.duration_unit = 'hour'
  gantt.config.duration_step = 8
  gantt.config.grid_width = 600
  gantt.config.columns = [
    { name: 'text', label: '任务名', tree: true, width: '300' },
    { name: 'taskCode', label: '任务编号', tree: true, width: '350' },
    { name: 'workstation', label: '工作站', align: 'center', width: '*' },
    { name: 'start_date', label: '开始时间', align: 'center', width: '*' },
    { name: 'end_date', label: '结束时间', align: 'center', width: '*' },
    {
      name: 'operate',
      label: '操作',
      template: function (task) {
        return (
          '<i class="fa fa-file-text-o" data-action="view"></i>' +
          '<i class="fa fa-pencil" data-action="add"></i>'
        )
      },
      align: 'center',
      min_width: 150
    }
  ]
  // 初始化的时候就展开树结构
  gantt.config.open_tree_initially = true
  // 显示到Gantt图上的任务上的文本
  gantt.templates.task_text = (start, end, task: any): any => {
    if (task.type == 'project') {
      return (
        "<b style='text-align:left;'>生产工单:</b> " +
        task.text +
        "    <span style='text-align:left;'>" +
        ' 完成比例：' +
        Math.round(task.progress * 100) +
        '% </span>'
      )
    } else {
      return (
        "<b style='text-align:left;'>生产任务:</b> " +
        task.process +
        ' ' +
        task.text +
        "    <span style='text-align:left;'>" +
        ' 完成比例：' +
        Math.round(task.progress * 100) +
        '% </span>'
      )
    }
  }
  // 显示到Gantt图上的任务单元格
  gantt.config.show_task_cells = true

  const weekScaleTemplate = (date) => {
    var dateToStr = gantt.date.date_to_str('%Y年%M%d日')
    var endDate = gantt.date.add(gantt.date.add(date, 1, 'week'), -1, 'day')
    return dateToStr(date) + ' - ' + dateToStr(endDate)
  }

  const dayTemplate = (date) => {
    var dateToStr = gantt.date.date_to_str('%M %d')
    var weekDay = gantt.date.date_to_str('%D')
    return dateToStr(date)
    ;+'(' + weekDay(date) + ')'
  }

  const daysStyle = (date) => {
    // you can use gantt.isWorkTime(date)
    // when gantt.config.work_time config is enabled
    // In this sample it's not so we just check week days

    if (date.getDay() === 0 || date.getDay() === 6) {
      return 'weekend'
    }
    return ''
  }

  gantt.config.scales = [
    { unit: 'week', step: 1, format: weekScaleTemplate },
    { unit: 'day', step: 1, format: dayTemplate, css: daysStyle },
    { unit: 'hour', step: 8, format: '%H:%i' } //这里的step要根据当前的班次设置来。如果是三班倒则是8，如果是两班倒则是12。
  ]

  // 拖动设置
  // 3.1 自动调整类型,当存在子节点时自动升级为project
  gantt.config.auto_types = false
  // 3.2 设置不可以拖动进度
  gantt.config.drag_progress = false
  // 3.3 设置Task不可以拖动
  gantt.config.drag_move = true
  // 3.4 设置不可以拖动关系
  gantt.config.drag_links = false
  // 3.5 设置不可拖动Task 大小
  gantt.config.drag_resize = true
  // 3.6 单击显示添加详情
  gantt.config.details_on_create = true
  // 3.7 双击显示明细
  gantt.config.details_on_dblclick = true
  //时间范围自动适应
  gantt.config.fit_tasks = true

  // 点击表头可排序
  gantt.config.sort = true
  // 设置行高
  gantt.config.row_height = 40
  // 配置gantt图时间高度
  gantt.config.scale_height = 70
  // 取消双击点击显示出编辑弹框
  gantt.config.lightbox = false
  // 显示单元格

  // 鼠标悬浮框
  gantt.plugins({
    // 启用全屏插件
    fullscreen: true,
    // 启用撤销插件
    undo: true,
    // 启用tooltip
    tooltip: true,
    // multiselect: true,
    // 标记
    marker: true,
    // 自动调度
    auto_scheduling: true,
    critical_path: true,
    // 显示是否可以编辑任务名称
    quick_info: false
  })

  gantt.ext.fullscreen.getFullscreenElement = function (): any {
    return document.getElementById('myCover')
  }

  // 移入显示悬浮内容
  gantt.templates.tooltip_text = (start, end, task: any): any => {
    if (task.type == 'project') {
      return (
        "<b style='text-align:left;'>生产工单:</b> " +
        task.text +
        "    <span style='text-align:left;'>" +
        ' 完成比例：' +
        Math.round(task.progress * 100) +
        '% </span>'
      )
    } else {
      return (
        "<b style='text-align:left;'>生产任务:</b> " +
        task.process +
        ' ' +
        task.text +
        "    <span style='text-align:left;'>" +
        ' 完成比例：' +
        Math.round(task.progress * 100) +
        '% </span>'
      )
    }
  }

  // let t: any = props.ganttDataList
  gantt.attachEvent('onAfterTaskUpdate', function (id, obj) {
    console.log(id, '_ididid')

    // let tt = t.data.filter((item: any) => item.id == id)
    // tt = obj
  })
}

const addTodayLine = () => {
  // 时间线
  var date_to_str = gantt.date.date_to_str(gantt.config.task_date)
  var today = new Date()

  gantt.addMarker({
    start_date: today,
    css: 'today',
    text: '今天',
    title: '今天: ' + date_to_str(today)
  })
}

// 调用初始化甘特图数据
const reload = () => {
  // 从甘特图中删除所有任务和其他元素（包括标记）
  gantt.clearAll()
  addTodayLine()
  // 初始化甘特图
  gantt.init(ganttRef.value)
  gantt.parse(props.ganttDataList)
  // 渲染整个甘特图
  gantt.render()
}

defineExpose({
  reload
})

onMounted(() => {
  // 初始化甘特图配置
  initConfig()
  addTodayLine()
  // 初始化甘特图
  gantt.init(ganttRef.value)
  // 渲染数据
  gantt.parse(props.ganttDataList)
})
</script>

<style scoped lang="scss">
// :deep .gantt_task_line {
//   height: 18px !important;
//   line-height: 18px !important;
//   margin-top: 8px;
//   border: none !important;
//   border-radius: 10px;
//   background: linear-gradient(
//     43deg,
//     rgb(65, 88, 208) 0%,
//     rgb(200, 80, 192) 46%,
//     rgb(255, 204, 112) 100%
//   );
// }

// :deep .gantt_task_line::after {
//   content: '';
//   position: absolute;
//   top: -2px;
//   left: -2px;
//   right: -2px;
//   bottom: -2px;
//   background: #ffffff;
//   z-index: -2;
//   /*添加模糊滤镜*/
//   filter: blur(40px);
// }

:deep .gantt_task_progress {
  background-image: linear-gradient(160deg, #0093e9 0%, #80d0c7 100%);
}

:deep .fa {
  cursor: pointer;
  font-size: 14px;
  text-align: center;
  opacity: 0.2;
  padding: 5px;
}

:deep .fa:hover {
  opacity: 1;
}

:deep .fa-pencil {
  color: #ffa011;
}

// :deep .fa-plus {
//   color: #328ea0;
// }

:deep .fa-times {
  color: red;
}
</style>
