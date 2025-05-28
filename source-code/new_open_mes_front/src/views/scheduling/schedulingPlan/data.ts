// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '班组类型',
    prop: 'teamType',
    placeholder: '请输入班组类型'
  },
  {
    type: 'input',
    label: '排班编号',
    prop: 'planCode',
    placeholder: '请输入班组编号'
  },
  {
    type: 'input',
    label: '排班名称',
    prop: 'planName',
    placeholder: '请输入班组名称'
  },
  {
    type: 'date',
    label: '开始日期',
    prop: 'startDate',
    placeholder: '请选择开始日期'
  },
  {
    type: 'date',
    label: '结束日期',
    prop: 'endDate',
    placeholder: '请选择结束日期'
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
    label: '批量删除',
    disabled: true,
    permissions: []
  },
  {
    type: 'warning',
    icon: 'download',
    state: 'download',
    label: '导出',
    disabled: false,
    permissions: []
  }
])

export const tableColumns: Table.Column[] = [
  {
    label: '排班编号',
    prop: 'planCode',
    width: '150'
  },
  {
    label: '排班名称',
    prop: 'planName',
    width: '130'
  },
  {
    label: '排班类型',
    prop: 'teamType'
  },
  {
    label: '班组编号',
    prop: 'teamCode'
  },
  {
    label: '班组名称',
    prop: 'teamName',
    width: '130'
  },
  {
    label: '班组人员',
    slot: 'teamPeople'
  },
  {
    label: '轮班时长',
    prop: 'shiftDuration'
  },
  {
    label: '轮班方式',
    prop: 'shiftWay'
  },
  {
    label: '倒班方式',
    prop: 'changeShiftWay'
  },
  {
    label: '开始日期',
    prop: 'startDate',
    width: '170'
  },
  {
    label: '结束日期',
    prop: 'endDate',
    width: '170'
  },
  {
    label: '操作',
    slot: 'operate',
    width: '250',
    fixed: 'right'
  }
]

export const personnelColumns: Table.Column[] = [
  {
    label: '人员编号',
    prop: 'id'
  },
  {
    label: '人员名称',
    prop: 'planPeopleName'
  },
  {
    label: '分配任务数量',
    slot: 'peopleQuantity'
  },
  {
    label: '任务名称',
    slot: 'taskCode'
  },
  {
    label: '操作',
    slot: 'operate'
  }
]
