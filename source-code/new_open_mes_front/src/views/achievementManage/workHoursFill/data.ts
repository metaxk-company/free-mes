// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '任务单号',
    prop: 'input',
    placeholder: '请输入任务单号'
  },
  {
    type: 'select',
    label: '工时类别',
    prop: 'workhoursType',
    placeholder: '请选择工时类别',
    options: []
  },
  {
    type: 'date',
    label: '填报日期',
    prop: 'input',
    placeholder: '请选择填报日期'
  },
  {
    type: 'input',
    label: '人员姓名',
    prop: 'input',
    placeholder: '请输入人员姓名'
  },
  {
    type: 'input',
    label: '人员编号',
    prop: 'input',
    placeholder: '请输入人员编号'
  }
])

// 按钮功能
export const btnConditions: Btn.IButton[] = reactive([
  {
    type: 'primary',
    icon: 'plus',
    state: 'save',
    label: '新增',
    disabled: false,
    permissions: []
  },
  {
    type: 'success',
    icon: 'edit',
    state: 'edit',
    label: '修改',
    disabled: true,
    permissions: []
  },
  {
    type: 'danger',
    icon: 'delete',
    state: 'remove',
    label: '删除',
    disabled: true,
    permissions: []
  }
  // {
  //   type: 'warning',
  //   icon: 'download',
  //   state: 'download',
  //   label: '导出',
  //   permissions: []
  // }
])

export const tableColumns: Table.Column[] = [
  {
    label: '任务单号',
    slot: 'processCode'
  },
  {
    prop: 'remark',
    label: '填报日期'
  },
  {
    prop: 'remark',
    label: '人员姓名'
  },
  {
    prop: 'remark',
    label: '人员编号'
  },
  {
    prop: 'remark',
    label: '工时类别'
  },
  {
    prop: 'remark',
    label: '工时时长'
  },
  {
    prop: 'remark',
    label: '生产数量'
  },
  {
    prop: 'remark',
    label: '备注'
  },
  {
    label: '操作',
    slot: 'operation',
    width: '120',
    fixed: 'right'
  }
]
