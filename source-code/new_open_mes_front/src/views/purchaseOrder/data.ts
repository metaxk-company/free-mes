// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '采购单号',
    prop: 'number',
    placeholder: '请输入采购单号'
  },
  {
    type: 'input',
    label: '供应商名称',
    prop: 'vendorName',
    placeholder: '请输入供应商名称'
  },
  {
    type: 'scopeTime',
    label: '交货日期',
    prop: 'deliveryDate',
    placeholder: '请选择交货日期'
  }
])

// 采购单搜索条件
export const searchPurchaseConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '产品名称',
    prop: 'itemName',
    placeholder: '请输入产品名称'
  },
  {
    type: 'input',
    label: '规格',
    prop: 'specification',
    placeholder: '请输入规格'
  }
  // {
  //   type: 'input',
  //   label: '物料分类',
  //   prop: 'itemTypeName',
  //   placeholder: '请选择物料分类'
  // },
  // {
  //   type: 'input',
  //   label: '产品类别',
  //   prop: 'kind',
  //   placeholder: '请选择产品类别'
  // }
])

//
export const tableOrderColumns: Table.Column[] = [
  {
    prop: 'vendor',
    label: '供应商',
    width: '280'
  },
  {
    prop: 'itemCode',
    label: '产品编号',
    width: '180'
  },
  {
    prop: 'itemName',
    label: '产品名称',
    width: '180'
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
    prop: 'unitOfMeasure',
    label: '单位'
  },
  {
    prop: 'itemTypeName',
    label: '物料分类'
  },
  {
    prop: 'kind',
    label: '产品类别'
  },
  {
    prop: 'purchasePrice',
    label: '采购价格'
  },
  {
    prop: 'salePrice',
    label: '销售价格'
  }
]

export const tablePurchaseColumns: Table.Column[] = [
  {
    prop: 'number',
    label: '采购单号',
    width: '220'
  },
  {
    prop: 'deliveryDate',
    label: '交货日期',
    width: '230'
  },
  {
    prop: 'taxPrice',
    label: '总价（元）',
    width: '150'
  },
  {
    prop: 'vendorName',
    label: '供应商名称',
    width: '300'
  },
  // {
  //   prop: 'status',
  //   label: '状态'
  // },
  {
    prop: 'createTime',
    label: '创建日期',
    width: '230'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '630'
  }
]

export const tablePurchaseListColumns: Table.Column[] = [
  {
    prop: 'number',
    label: '采购单号',
    width: '140'
  },
  {
    prop: 'deliveryDate',
    label: '交货日期'
  },
  {
    prop: 'includTax',
    label: '含税总价（元）'
  },
  {
    prop: 'vendorName',
    label: '供应商名称'
  },

  {
    prop: 'createTime',
    label: '创建日期',
    width: '160'
  }
]
export const returnGoodsColumns: Table.Column[] = [
  {
    prop: 'vendor',
    label: '供应商',
    width: '280'
  },
  {
    prop: 'itemCode',
    label: '产品编号',
    width: '160'
  },
  {
    prop: 'itemName',
    label: '产品名称',
    width: '160'
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
    slot: 'quantity',
    label: '数量',
    width: '190'
  },
  {
    prop: 'kind',
    label: '类别'
  },
  {
    prop: 'unitOfMeasure',
    label: '单位'
  },
  {
    slot: 'purchasePrice',
    label: '采购价',
    width: '190'
  },
  {
    slot: 'includTax',
    label: '价格',
    width: '120'
  }
  // {
  //   slot: 'noIncludTax',
  //   label: '总价',
  //   width: '120'
  // }
]
export const deliveryData: Table.Column[] = [
  {
    prop: 'vendor',
    label: '供应商'
  },
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
    prop: 'spec',
    label: '规格'
  },

  {
    prop: 'kind',
    label: '类别'
  },
  {
    prop: 'unitOfMeasure',
    label: '单位'
  },
  {
    slot: 'quantity',
    label: '总量'
  },
  {
    slot: 'arrivedQuantity',
    label: '已到货数量'
  },
  {
    slot: 'amount',
    label: '到货数量',
    width: '260'
  }
]
