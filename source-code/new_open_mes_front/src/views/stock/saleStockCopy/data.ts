// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '出库单号',
    prop: 'number',
    placeholder: '请输入出库单号'
  },
  {
    type: 'input',
    label: '客户名称',
    prop: 'customerName',
    placeholder: '请输入客户名称'
  },

  {
    type: 'select',
    label: '状态',
    prop: 'status',
    placeholder: '请选择采购状态',
    options: [
      {
        label: '待出库',
        value: '待出库'
      },
      {
        label: '已出库',
        value: '已出库'
      }
    ]
  }
])

export const searchPurchase: Search.Column[] = [
  {
    type: 'input',
    label: '客户编号',
    prop: 'customerNumber',
    placeholder: '请输入客户编号'
  },
  {
    type: 'input',
    label: '客户名称',
    prop: 'customerName',
    placeholder: '请输入客户名称'
  }
]

// 采购单搜索条件
export const searchPurchaseConditions: Search.Column[] = reactive([
  {
    type: 'select',
    label: '客户名称',
    prop: 'customerNumber',
    placeholder: '请输入客户名称',
    options: []
  },
  {
    type: 'input',
    label: '线别名称',
    prop: 'lineType',
    placeholder: '请选择线别名称'
  },
  {
    type: 'input',
    label: '型号名称',
    prop: 'model',
    placeholder: '请输入型号名称'
  },
  {
    type: 'input',
    label: '规格名称',
    prop: 'spec',
    placeholder: '请选择物料分类'
  }
])

export const tablePurchaseColumns: Table.Column[] = [
  {
    prop: 'customerNumber',
    label: '客户编号'
  },
  {
    prop: 'customerName',
    label: '客户名称',
    width: '210'
  },
  {
    prop: 'totalPrice',
    label: '价格'
  },
  {
    prop: 'quantity',
    label: '数量'
  },
  {
    prop: 'sendOut',
    label: '已发'
  },
  {
    prop: 'noSend',
    label: '未发'
  },
  {
    prop: 'remark',
    label: '备注'
  },
  {
    prop: 'status',
    label: '状态'
  }
]

export const tableOrderColumns: Table.Column[] = [
  {
    prop: 'customerName',
    label: '客户名称',
    width: '210'
  },
  {
    prop: 'saleNumber',
    label: '销售单号',
    width: '190'
  },
  {
    prop: 'itemCode',
    label: '编号',
    width: '190'
  },
  {
    prop: 'model',
    label: '型号',
    width: '190'
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
    prop: 'customerCode',
    label: '客户代码'
  },

  {
    prop: 'color',
    label: '颜色'
  },
  {
    prop: 'panhao',
    label: '盘号'
  },
  {
    prop: 'price',
    label: '单价'
  },
  {
    prop: 'totalPrice',
    label: '总价'
  },
  {
    prop: 'quantity',
    label: '销售数量'
  },
  {
    prop: 'sendOut',
    label: '已发'
  },
  {
    prop: 'noSend',
    label: '未发'
  },
  {
    prop: 'stocks',
    label: '库存'
  },
  // {
  //   prop: 'weight',
  //   label: '总净重'
  // },
  // {
  //   prop: 'totalTareWeight',
  //   label: '总皮重'
  // },
  {
    prop: 'pieces',
    label: '件数'
  },
  {
    prop: 'unit',
    label: '单位'
  },
  {
    prop: 'remark',
    label: '备注'
  }
]

export const tableColumns: Table.Column[] = [
  {
    slot: 'number',
    label: '出库单号',
    width: '150'
  },
  {
    prop: 'outboundTotalPrice',
    label: '出库总价',
    width: '130'
  },
  {
    prop: 'sendOut',
    label: '已发'
  },
  {
    prop: 'noSend',
    label: '未发'
  },
  {
    prop: 'customerName',
    label: '客户名',
    width: '310'
  },
  {
    prop: 'isTax',
    label: '是否含税',
    formatter: (value) => (value.isTax == 'Y' ? '是' : '否')
  },
  {
    prop: 'status',
    label: '状态',
    width: '130'
  },

  {
    prop: 'remark',
    label: '备注'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '500'
  }
]
export const returnGoodsColumns: Table.Column[] = [
  {
    prop: 'itemCode',
    label: '编号',
    width: '160'
  },
  {
    prop: 'saleNumber',
    label: '销售单号',
    width: '160'
  },
  {
    prop: 'model',
    label: '型号',
    width: '110'
  },
  {
    prop: 'customerName',
    label: '客户名',
    width: '200'
  },
  {
    prop: 'spec',
    label: '规格'
  },
  {
    prop: 'price',
    label: '单价'
  },
  {
    prop: 'lineType',
    label: '线别'
  },
  {
    prop: 'customerCode',
    label: '客户代码'
  },
  {
    prop: 'color',
    label: '颜色'
  },
  {
    prop: 'panhao',
    label: '盘号'
  },

  {
    slot: 'totalPrice',
    label: '总价',
    width: '130'
  },
  {
    prop: 'unit',
    label: '单位'
  },
  {
    prop: 'quantity',
    label: '销售数量'
  },
  {
    prop: 'sendOut',
    label: '已发'
  },
  {
    prop: 'noSend',
    label: '未发'
  },
  // {
  //   slot: 'outHighestLimit',
  //   label: '不能高于剩余出库数量百分比',
  //   width: '190'
  // },
  // {
  //   slot: 'outLowestLimit',
  //   label: '不能低于剩余出库数量百分比',
  //   width: '190'
  // },
  {
    prop: 'totalWeight',
    label: '总净重'
  },
  {
    slot: 'tare',
    label: '皮重',
    width: '190'
  },
  {
    prop: 'totalTare',
    label: '总皮重'
  },
  {
    prop: 'pieces',
    label: '件数'
  },

  {
    prop: 'remark',
    label: '备注'
  }
]

// 详情表格头信息
export const detailsColumns: Table.Column[] = [
  {
    prop: 'itemCode',
    label: '型号'
  },
  {
    prop: 'remark',
    label: '规格'
  },
  {
    prop: 'remark',
    label: '箱号'
  },
  {
    prop: 'remark',
    label: '轴数'
  },
  {
    prop: 'remark',
    label: '总皮重'
  },
  {
    prop: 'remark',
    label: '总净重'
  },
  {
    prop: 'remark',
    label: '单价'
  },
  {
    prop: 'remark',
    label: '总价'
  },
  {
    prop: 'remark',
    label: '批次号'
  },
  {
    prop: 'remark',
    label: '成本'
  },
  {
    prop: 'remark',
    label: '条码'
  },
  {
    prop: 'remark',
    label: '线别'
  },
  {
    prop: 'customerCode',
    label: '客户代码'
  },
  {
    prop: 'remark',
    label: '颜色'
  },
  {
    prop: 'panhao',
    label: '盘号'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '90'
  }
]

// 销售出库搜索信息
export const searchStock: Search.Column[] = reactive([
  {
    type: 'input',
    label: '线别名称',
    prop: 'lineType',
    placeholder: '请输入线别名称'
  },
  {
    type: 'input',
    label: '型号名称',
    prop: 'model',
    placeholder: '请输入型号名称'
  },
  {
    type: 'input',
    label: '规格名称',
    prop: 'spec',
    placeholder: '请输入规格名称'
  },
  {
    type: 'input',
    label: '客户代码',
    prop: 'customerCode',
    placeholder: '请输入客户代码'
  },
  {
    type: 'input',
    label: '盘号名称',
    prop: 'panhao',
    placeholder: '请输入盘号名称'
  },
  {
    type: 'input',
    label: '颜色名称',
    prop: 'color',
    placeholder: '请输入颜色名称'
  }
])

// 销售出库详情列表选择信息
export const detailsTableColumns: Table.Column[] = [
  {
    prop: 'id',
    label: '编号'
  },
  {
    prop: 'model',
    label: '型号'
  },
  {
    prop: 'spec',
    label: '规格'
  },
  {
    prop: 'boxNumber',
    label: '箱号',
    width: '120'
  },
  {
    prop: 'axlesNum',
    label: '轴数',
    width: '120'
  },
  {
    prop: 'totalHeight',
    label: '总净重'
  },

  {
    prop: 'batchNumber',
    label: '批次号',
    width: '120'
  },
  {
    prop: 'unitOfMeasure',
    label: '单位'
  },
  {
    prop: 'date',
    label: '日期',
    width: '160'
  },
  {
    prop: 'status',
    label: '状态'
  },
  {
    prop: 'lineType',
    label: '线别'
  },
  {
    prop: 'reelNumber',
    label: '盘号'
  },
  {
    prop: 'color',
    label: '颜色'
  },
  {
    prop: 'tare',
    label: '皮重'
  },
  {
    prop: 'totalTare',
    label: '总皮重'
  },
  {
    prop: 'clientCode',
    label: '客户代码'
  }
]
