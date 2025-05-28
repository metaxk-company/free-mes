// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '工作站编号',
    prop: 'workstationCode',
    placeholder: '请输入工作站编号'
  },
  {
    type: 'input',
    label: '车间编号',
    prop: 'workshopCode',
    placeholder: '请输入车间编号'
  },
  {
    type: 'input',
    label: '工序编号',
    prop: 'processCode',
    placeholder: '请输入工序编号'
  },
  {
    type: 'input',
    label: '工人名称',
    prop: 'userName',
    placeholder: '请输入工人名称'
  },
  {
    type: 'input',
    label: '设备编号',
    prop: 'machineryCode',
    placeholder: '请输入设备编号'
  },
  {
    type: 'date',
    label: '开工时间',
    prop: 'feedbackTime',
    placeholder: '请选择开始时间'
  },
  {
    type: 'date',
    label: '报工时间',
    prop: 'finishedTime',
    placeholder: '请选择结束时间'
  }
])

export const tableColumns: Table.Column[] = [
  { label: '订单编号', prop: 'workorderCode', width: '150' },
  { label: '工作站编号', prop: 'workstationCode', width: '120' },
  { label: '任务单号', prop: 'taskCode', width: '150' },
  { label: '产品名称', prop: 'itemName' },
  { label: '车间编号', prop: 'workshopCode' },
  { label: '工序编号', prop: 'processCode', width: '150' },
  { label: '设备编号', prop: 'equipmentCode' },
  { label: '人工工时', prop: 'workHour', width: '150' },
  { label: '设备工时', prop: 'equipmentHour', width: '170' },
  { label: '开工时间', prop: 'feedbackTime', width: '170' },
  { label: '人工报工时间', prop: 'workerFinishedTime', width: '170' },
  { label: '设备报工时间', prop: 'equipmentFinishedTime', width: '170' },
  { label: '报工人', prop: 'userName' }
]

// 按钮功能
export const btnConditions: Btn.IButton[] = reactive([
  {
    type: 'warning',
    icon: 'download',
    state: 'download',
    label: '导出',
    permissions: []
  }
])
