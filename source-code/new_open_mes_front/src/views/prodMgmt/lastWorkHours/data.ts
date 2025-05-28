// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'select',
    label: '工时类型',
    prop: 'workhoursType',
    placeholder: '选择工时类型',
    options: []
  },
  {
    type: 'input',
    label: '工时(小时)',
    prop: 'workhours',
    placeholder: '请输入工时'
  },
  {
    type: 'input',
    label: '工人姓名',
    prop: 'workerName',
    placeholder: '请输入工人姓名'
  },

  {
    type: 'input',
    label: '所属车间',
    prop: 'workshopName',
    placeholder: '请输入所属车间'
  },
  {
    type: 'dateTime',
    label: '开始时间',
    prop: 'createTime',
    placeholder: '请选择开始时间'
  }
  // {
  //   type: 'dateTime',
  //   label: '结束时间',
  //   prop: 'endTime',
  //   placeholder: '请选择结束时间'
  // }
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
    label: '工时类型',
    prop: 'workhoursType'
  },
  {
    label: '工时(小时)',
    prop: 'workhours'
  },
  {
    prop: 'workerName',
    label: '工人姓名'
  },
  {
    prop: 'workshopName',
    label: '所属车间'
  },
  {
    prop: 'createTime',
    label: '创建时间'
  },
  {
    label: '操作',
    slot: 'operation',
    width: '120',
    fixed: 'right'
  }
]
