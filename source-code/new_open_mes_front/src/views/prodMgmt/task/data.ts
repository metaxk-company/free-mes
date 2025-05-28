// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '工作站名称',
    prop: 'workstationName',
    placeholder: '请输入工作站名称'
  },
  {
    type: 'input',
    label: '任务编号',
    prop: 'taskCode',
    placeholder: '请输入任务编号'
  },
  {
    type: 'input',
    label: '任务名称',
    prop: 'taskName',
    placeholder: '请输入任务名称'
  },
  {
    type: 'input',
    label: '订单编号',
    prop: 'workorderCode',
    placeholder: '请输入工作单编号'
  },
  {
    type: 'input',
    label: '工序编号',
    prop: 'processCode',
    placeholder: '请输入工序编号'
  },
  {
    type: 'input',
    label: '工序名称',
    prop: 'processName',
    placeholder: '请输入工序名称'
  },
  {
    type: 'date',
    label: '开始时间',
    prop: 'startTime',
    placeholder: '请选择开始时间'
  },
  {
    type: 'date',
    label: '结束时间',
    prop: 'endTime',
    placeholder: '请选择结束时间'
  }
])

// 按钮功能
export const btnConditions: Btn.IButton[] = reactive([
  {
    type: 'danger',
    icon: 'delete',
    state: 'remove',
    label: '批量删除',
    disabled: true,
    permissions: []
  },
  {
    type: 'warning',
    icon: 'download',
    state: 'download',
    label: '导出',
    permissions: []
  }
])

export const tableColumns: Table.Column[] = [
  {
    label: '任务编号',
    prop: 'taskCode',
    width: '150'
  },
  {
    prop: 'taskName',
    label: '任务名称',
    width: '140'
  },
  {
    prop: 'quantity',
    label: '数量',
    width: '70'
  },
  {
    prop: 'teamCode',
    label: '班组编号',
    width: '100'
  },
  {
    prop: 'teamName',
    label: '班组名称',
    width: '100'
  },
  {
    prop: 'workorderCode',
    label: '订单编号',
    width: '150'
  },
  {
    label: '订单名称',
    prop: 'workorderName',
    width: '120'
  },
  {
    prop: 'workstationName',
    label: '工作站名称',
    width: '120'
  },
  {
    prop: 'processCode',
    label: '工序编号',
    width: '120'
  },
  {
    prop: 'processName',
    label: '工序名称',
    width: '120'
  },
  {
    prop: 'startTime',
    label: '开始生产时间',
    width: '170'
  },
  {
    prop: 'requestDate',
    label: '预计完成时间',
    width: '170'
  }
]
