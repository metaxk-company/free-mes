// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '退货编号',
    prop: 'number',
    placeholder: '请输入采购退货单编号'
  },
  {
    type: 'input',
    label: '采购编号',
    prop: 'poNumber',
    placeholder: '请输入采购单编号'
  },
  {
    type: 'input',
    label: '供应商名称',
    prop: 'vendorName',
    placeholder: '请输入供应商名称'
  },
  {
    type: 'scopeTime',
    label: '退货日期',
    prop: 'returnDate',
    placeholder: '请选择退货日期'
  }
])

// 客户端搜索条件
export const searchOrderConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '采购编号',
    prop: 'number',
    placeholder: '请输入采购编号'
  },
  {
    type: 'input',
    label: '供应商',
    prop: 'vendorName',
    placeholder: '请输入供应商'
  },
  {
    type: 'scopeTime',
    label: '交货日期',
    prop: 'deliveryDate',
    placeholder: '请选择交货日期'
  }
])

// 客户端搜索条件
export const searchMatterConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '产品名称',
    prop: 'name',
    placeholder: '请输入产品名称'
  },
  {
    type: 'input',
    label: '产品编号',
    prop: 'number',
    placeholder: '请输入产品编号'
  }
])

export const tableColumns: Table.Column[] = [
  {
    prop: 'number',
    label: '退货编号',
    width: '160'
  },
  {
    prop: 'vendorName',
    label: '供应商名称',
    width: '220'
  },
  {
    prop: 'poNumber',
    label: '采购编号',
    width: '160'
  },
  {
    prop: 'returnDate',
    label: '退货日期',
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

// 采购退货选择列表
export const purchaseReturnColumns: Table.Column[] = [
  {
    prop: 'itemCode',
    label: '产品编号',
    width: '170'
  },
  {
    prop: 'itemName',
    label: '产品名称',
    width: '190'
  },
  {
    prop: 'model',
    label: '型号'
  },

  {
    slot: 'amount',
    label: '数量',
    width: '190'
  },
  {
    prop: 'purchasePrice',
    label: '购买价格'
  },
  {
    prop: 'spec',
    label: '规格'
  },
  {
    prop: 'unitOfMeasure',
    label: '单位'
  },
  {
    slot: 'totalPrice',
    label: '总价'
  }
]
export const purchaseReturnColumnsInfo: Table.Column[] = [
  {
    prop: 'itemCode',
    label: '产品编号'
  },
  {
    prop: 'itemName',
    label: '产品名称'
  },
  {
    prop: 'model',
    label: '型号'
  },

  {
    prop: 'quantity',
    label: '数量'
  },
  {
    prop: 'purchasePrice',
    label: '购买价格'
  },
  {
    prop: 'spec',
    label: '规格'
  },
  {
    prop: 'unitOfMeasure',
    label: '单位'
  }
]

// 订单列表
export const purchaseOrderColumns: Table.Column[] = [
  {
    prop: 'number',
    label: '采购编号'
  },
  // {
  //   prop: 'productType',
  //   label: '产品名称'
  // },
  {
    prop: 'status',
    label: '状态'
  },
  {
    prop: 'vendorName',
    label: '供应商',
    width: '310'
  },
  {
    prop: 'taxPrice',
    label: '价格'
  },
  {
    prop: 'deliveryDate',
    label: '交货日期'
  }
]

// 采购订单列查询数据
export const purchaseReturnQueryColumns: Table.Column[] = [
  {
    prop: 'areaNumber',
    label: '订单编号'
  },
  {}
]

//
