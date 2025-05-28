// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '检验单号',
    prop: 'number',
    placeholder: '请输入检验单号'
  },
  {
    type: 'input',
    label: '生产订单编号',
    prop: 'workOrderCode',
    placeholder: '请输入生产订单编号'
  },
  {
    type: 'input',
    label: '型号',
    prop: 'model',
    placeholder: '请输入型号'
  },
  {
    type: 'input',
    label: '规格',
    prop: 'spec',
    placeholder: '请输入规格'
  },
  {
    type: 'input',
    label: '线别',
    prop: 'lineType',
    placeholder: '请输入线别'
  }
])

export const tableColumns: Table.Column[] = [
  {
    label: '检验单编号',
    prop: 'number'
  },
  {
    label: '生产订单编号',
    prop: 'workOrderCode'
  },
  {
    label: '型号',
    prop: 'model'
  },
  {
    prop: 'spec',
    label: '规格'
  },

  {
    prop: 'lineType',
    label: '线别'
  },
  {
    prop: 'createTime',
    label: '创建日期'
  },
  {
    slot: 'status',
    label: '状态'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '180'
  }
]
export const columnState: Table.Column[] = [
  {
    prop: 'itemName',
    label: '检验名称'
  },
  {
    prop: 'itemStandard',
    label: '检验标准'
  },
  {
    prop: 'itemDevice',
    label: '检验仪器'
  },
  {
    prop: 'itemValue',
    label: '实际信息'
  },
  {
    slot: 'status',
    label: '是否合格'
  }
]

export const columnTakeNotes: Table.Column[] = [
  {
    prop: 'processCode',
    label: '来料编号'
  },
  {
    prop: 'processName',
    label: '来料名称'
  },
  {
    prop: 'unqualifiedNum',
    label: '不合格数'
  },
  {
    prop: 'qualifiedNum',
    label: '合格数'
  },
  {
    prop: 'inspectNum',
    label: '检验数量'
  },
  {
    prop: 'quantity',
    label: '总数'
  }
]
export const tableTestingItemsColumns: Table.Column[] = [
  {
    slot: 'itemName',
    label: '检测名称'
  },
  {
    slot: 'itemStandard',
    label: '检测标准'
  },
  {
    slot: 'itemDevice',
    label: '检测器具'
  },
  {
    slot: 'actual',
    label: '实际信息'
  },
  {
    slot: 'remark',
    label: '检验描述'
  },
  {
    slot: 'status',
    label: '是否合格',
    width: '300'
  }
]

// 工序弹框搜索
export const searchProcessConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '生产订单编码',
    prop: 'workorderCode',
    placeholder: '请输入生产订单编码'
  },
  {
    type: 'input',
    label: '生产订单名称',
    prop: 'workorderName',
    placeholder: '请输入生产订单名称'
  },
  {
    type: 'input',
    label: '产品编号',
    prop: 'productCode',
    placeholder: '请输入产品编号'
  }
])

export const tableProcessColumns: Table.Column[] = [
  {
    prop: 'workorderCode',
    label: '生产订单编码'
  },
  {
    prop: 'workorderName',
    label: '生产订单名称'
  },
  {
    prop: 'productCode',
    label: '产品编号'
  },
  {
    prop: 'productName',
    label: '产品名称'
  },
  {
    prop: 'sourceCode',
    label: '单据来源'
  },
  {
    prop: 'productSpc',
    label: '型号规格'
  },
  {
    prop: 'unitOfMeasure',
    label: '单位'
  }
]
