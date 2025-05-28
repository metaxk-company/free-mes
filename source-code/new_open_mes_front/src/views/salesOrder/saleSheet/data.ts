import { FINISH_STATUS, LINES_NAME } from '@/utils/const'

// 客户端搜索条件
export const searchConditions: Search.Column[] = [
  {
    type: 'input',
    label: '订单编号',
    prop: 'saleNumber',
    placeholder: '请输入订单编号'
  },
  {
    type: 'input',
    label: '客户名称',
    prop: 'customerName',
    placeholder: '请输入客户名称'
    // options: []
  },

  {
    type: 'select',
    label: '订单状态',
    prop: 'status',
    placeholder: '请选订单状态',
    options: FINISH_STATUS
  }
]

// progressSearch
export const progressSearch: Search.Column[] = [
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
  }
  // {
  //   type: 'select',
  //   label: '订单状态',
  //   prop: 'status',
  //   placeholder: '请选订单状态',
  //   options: FINISH_STATUS
  // }
]

export const searchPurchase: Search.Column[] = [
  {
    type: 'input',
    label: '客户编号',
    prop: 'inspectCode',
    placeholder: '请输入客户编号'
  },
  {
    type: 'input',
    label: '客户名称',
    prop: 'inspectCode',
    placeholder: '请输入客户名称'
  }
]

// 采购单搜索条件
export const searchPurchaseConditions: Search.Column[] = reactive([
  {
    type: 'select',
    label: '线别名称',
    prop: 'lineType',
    placeholder: '请选择线别',
    options: LINES_NAME
  },
  {
    type: 'input',
    label: '产品规格',
    prop: 'spec',
    placeholder: '请选择产品规格'
  },
  {
    type: 'input',
    label: '型号名称',
    prop: 'model',
    placeholder: '请选择型号名称'
  },
  {
    type: 'input',
    label: '产品编号',
    prop: 'productNumber',
    placeholder: '请输入产品编号'
  },
  {
    type: 'input',
    label: '产品名称',
    prop: 'productName',
    placeholder: '请输入产品名称'
  }
])

// 产品详情搜索条件
export const searchProductConditions: Search.Column[] = [
  {
    type: 'input',
    label: '盘号',
    prop: 'inspectCode',
    placeholder: '请输入盘号'
  },
  {
    type: 'input',
    label: '颜色',
    prop: 'inspectCode',
    placeholder: '请输入颜色'
  },
  {
    type: 'input',
    label: '库存总量',
    prop: 'inspectCode',
    placeholder: '请输入库存总量'
  }
]

// 产品详情表单
export const tableDetailsColumns: Table.Column[] = [
  {
    prop: 'spec',
    label: '规格'
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
    prop: 'totalHeight',
    label: '库存总量'
  }
]

export const tablePurchaseColumns: Table.Column[] = [
  {
    prop: 'inspectName',
    label: '客户编号'
  },
  {
    prop: 'inspectName',
    label: '客户名称'
  },
  {
    prop: 'inspectName',
    label: '价格'
  },
  {
    prop: 'inspectName',
    label: '数量'
  },
  {
    prop: 'inspectName',
    label: '已发'
  },
  {
    prop: 'inspectName',
    label: '未发'
  },
  {
    prop: 'inspectName',
    label: '备注'
  },
  {
    prop: 'inspectName',
    label: '状态'
  }
]

export const tableProgressColumns: Table.Column[] = [
  {
    prop: 'number',
    label: '销售单号'
  },
  {
    prop: 'model',
    label: '型号名称'
  },
  {
    prop: 'spec',
    label: '规格名称'
  },
  {
    prop: 'quantity',
    label: '销售数量'
  },
  {
    prop: 'sendOut',
    label: '已出库'
  },
  {
    prop: 'noSend',
    label: '剩余出库'
  },
  {
    prop: 'status',
    label: '出库状态'
    // formatter: (row) => (row.itemStatus == 1 ? '已出库' : '未出库')
  }
  // {
  //   prop: 'inventoryStatus',
  //   label: '库存状态(KG)'
  // }
]

export const tableOrderColumns: Table.Column[] = [
  {
    prop: 'productNumber',
    label: '产品编号',
    width: '180'
  },
  {
    prop: 'productName',
    label: '产品名称',
    width: '210'
  },
  {
    prop: 'lineType',
    label: '线别'
  },
  {
    prop: 'model',
    label: '型号',
    width: '130'
  },
  {
    prop: 'customerName',
    label: '客户名称',
    width: '210'
  },
  {
    prop: 'spec',
    label: '规格'
  },
  {
    prop: 'unit',
    label: '单位'
  },
  {
    prop: 'rawPrice',
    label: '成本价'
  },
  {
    prop: 'price',
    label: '销售价'
  },
  {
    prop: 'processingFee',
    label: '加工费'
  },
  {
    slot: 'stocks',
    label: '库存数量',
    fixed: 'right'
  },
  {
    prop: 'productType',
    label: '物料类型'
  }
]

export const tableColumns: Table.Column[] = [
  {
    slot: 'number',
    label: '订单编号',
    width: '130'
  },
  {
    prop: 'customerName',
    label: '客户名称',
    width: '210'
  },
  {
    prop: 'customerOrderNumber',
    label: '客户订单号',
    width: '180'
  },
  {
    prop: 'quantity',
    label: '总数量'
  },
  {
    prop: 'priceModel',
    label: '件数'
  },
  {
    prop: 'includeTax',
    label: '金额'
  },
  {
    prop: 'status',
    label: '状态'
  },
  {
    prop: 'createTime',
    label: '创建时间',
    width: '160'
  },
  {
    prop: 'updateTime',
    label: '更新时间 ',
    width: '160'
  },
  {
    prop: 'remark',
    label: '备注'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '290'
  }
]
export const returnGoodsColumns: Table.Column[] = [
  {
    prop: 'productNumber',
    label: '产品编号',
    width: '140'
  },
  {
    prop: 'lineType',
    label: '线别'
  },
  {
    prop: 'model',
    label: '型号',
    width: '120'
  },
  {
    slot: 'quantity',
    label: '数量',
    width: '180'
  },
  {
    prop: 'spec',
    label: '规格'
  },
  {
    slot: 'rawPrice',
    label: '原材料价',
    width: '180'
  },
  {
    slot: 'processingFee',
    label: '加工费'
  },
  {
    slot: 'price',
    label: '销售价',
    width: '120'
  },
  {
    slot: 'totalPrice',
    label: '总价',
    width: '140',
    fixed: 'right'
  },

  {
    prop: 'unit',
    label: '单位'
  },

  {
    prop: 'stocks',
    label: '库存'
  },
  {
    slot: 'color',
    label: '颜色',
    width: '110'
  },
  {
    slot: 'panhao',
    label: '盘号',
    width: '110'
  },
  {
    slot: 'customerCode',
    label: '客户代码',
    width: '180'
  },
  {
    slot: 'inventoryNumber',
    label: '存货编号',
    width: '180'
  },
  {
    slot: 'warrantNumber',
    label: '制令单号',
    width: '180'
  },
  {
    slot: 'remark',
    label: '备注',
    width: '180'
  }
]
