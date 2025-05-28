// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '订单编码',
    prop: 'workorderCode',
    placeholder: '请输入订单编码'
  },
  {
    type: 'input',
    label: '订单名称',
    prop: 'workorderName',
    placeholder: '请输入订单名称'
  },
  // {
  //   type: 'input',
  //   label: '来源单据',
  //   prop: 'sourceCode',
  //   placeholder: '请输入来源单据'
  // },
  {
    type: 'input',
    label: '产品编号',
    prop: 'productCode',
    placeholder: '请输入产品编号'
  },
  {
    type: 'input',
    label: '产品名称',
    prop: 'productName',
    placeholder: '请输入产品名称'
  },
  // {
  //   type: 'input',
  //   label: '客户编码',
  //   prop: 'clientCode',
  //   placeholder: '请输入客户编码'
  // },
  {
    type: 'input',
    label: '客户名称',
    prop: 'clientName',
    placeholder: '请输入客户名称'
  },
  {
    type: 'date',
    label: '需求日期',
    prop: 'requestDate',
    placeholder: '请选择需求日期'
  },
  {
    type: 'date',
    label: '订单日期',
    prop: 'orderDate',
    placeholder: '生产订单日期'
  },
  {
    type: 'date',
    label: '生产日期',
    prop: 'produceDate',
    placeholder: '生产日期'
  }
])

export const tableColumns: Table.Column[] = [
  {
    slot: 'taskCode',
    label: '任务编号',
    width: '150'
  },
  {
    prop: 'workorderName',
    label: '任务名称',
    width: '150'
  },
  {
    prop: 'workorderCode',
    label: '订单编号',
    width: '180'
  },

  {
    prop: 'workstationName',
    label: '工作站名称',
    width: '150'
  },
  {
    prop: 'quantity',
    label: '排产数量',
    width: '120'
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
    prop: 'startTime',
    label: '开始时间',
    width: '170'
  },
  {
    prop: 'duration',
    label: '生产时长',
    width: '120'
  },
  {
    prop: 'endTime',
    label: '预计完成时间',
    width: '170'
  },
  {
    label: '操作',
    slot: 'operation',
    width: '120',
    fixed: 'right'
  }
]
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
    type: 'primary',
    icon: 'printer',
    state: 'printer',
    disabled: true,
    label: '打印',
    loading: false,
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

// 工作站选择 - 搜索
export const searchConditionsClient: Search.Column[] = reactive([
  {
    type: 'input',
    label: '工作站编号',
    prop: 'workstationCode',
    placeholder: '请输入工作站编号'
  },
  {
    type: 'select',
    label: '所属工序',
    prop: 'processName',
    placeholder: '请选择工序',
    options: []
  },
  {
    type: 'select',
    label: '所在车间',
    prop: 'workshopName',
    placeholder: '请选择车间',
    options: []
  }
])

// 工作站选择  - 表格
export const columnClient: Table.Column[] = [
  {
    label: '工作站编号',
    prop: 'workstationCode'
  },
  {
    label: '工作站名称',
    prop: 'workstationName'
  },
  {
    prop: 'workstationAddress',
    label: '工作站地点'
  },
  {
    prop: 'workshopName',
    label: '所在车间名称'
  },
  {
    prop: 'processName',
    label: '所属工序'
  },
  {
    prop: 'remark',
    label: '备注'
  }
]

export const columnProduction: Table.Column[] = [
  {
    slot: 'workorderCode',
    label: '订单编码',
    width: '150'
  },
  {
    prop: 'workorderName',
    label: '订单名称',
    width: '150'
  },
  {
    prop: 'quantity',
    label: '订单数量',
    width: '150'
  },
  {
    slot: 'orderSource',
    label: '订单来源',
    width: '150'
  },
  // {
  //   prop: 'sourceCode',
  //   label: '订单编号',
  //   width: '150'
  // },
  {
    prop: 'productCode',
    label: '产品编号',
    width: '150'
  },
  {
    prop: 'productName',
    label: '产品名称'
  },
  {
    prop: 'productSpc',
    label: '规格型号',
    width: '120'
  },
  {
    slot: 'status',
    label: '排产状态'
  },
  {
    slot: 'productionSchedule',
    label: '生产进度条',
    width: '150'
  },
  {
    slot: 'produceProgress',
    label: '排产进度',
    width: '150'
  },
  {
    prop: 'unitOfMeasure',
    label: '单位'
  },

  // {
  //   prop: 'quantityChanged',
  //   label: '调整数量'
  // },
  // {
  //   prop: 'quantityProduced',
  //   label: '已生产数量'
  // },
  // {
  //   prop: 'clientCode',
  //   label: '客户编码'
  // },
  {
    prop: 'clientName',
    label: '客户名称',
    width: '120'
  },
  {
    prop: 'orderDate',
    label: '生产订单日期',
    width: '170'
  },
  {
    prop: 'produceDate',
    label: '生产日期',
    width: '170'
  },
  {
    prop: 'requestDate',
    label: '需求日期',
    width: '170'
  }

  // {
  //   label: '操作',
  //   slot: 'operation',
  //   width: '170',
  //   fixed: 'right'
  // }
]
