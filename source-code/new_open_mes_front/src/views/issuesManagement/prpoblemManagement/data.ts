// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '用户名',
    prop: 'username',
    placeholder: '请输入用户名'
  },
  {
    type: 'input',
    label: '所处车间',
    prop: 'workshopAffiliation',
    placeholder: '请输入所处车间'
  },
  {
    type: 'input',
    label: '来源',
    prop: 'source',
    placeholder: '请输入来源'
  },
  {
    type: 'input',
    label: '零部件',
    prop: 'component',
    placeholder: '请输入零部件'
  },
  {
    type: 'input',
    label: '类型',
    prop: 'type',
    placeholder: '请输入类型'
  },
  {
    type: 'input',
    label: '模式',
    prop: 'mode',
    placeholder: '请输入模式'
  },
  {
    type: 'input',
    label: '状态',
    prop: 'status',
    placeholder: '请输入状态'
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
    prop: 'username',
    label: '用户名',
    width: '200'
  },
  {
    prop: 'workshopAffiliation',
    label: '所属车间',
    width: '150'
  },
  // {
  //   slot: 'orderNumber',
  //   label: '订单编号',
  //   width: '150'
  // },
  // {
  //   prop: 'productNumber',
  //   label: '产品编号',
  //   width: '300'
  // },

  {
    prop: 'source',
    label: '来源',
    width: '150'
  },
  {
    prop: 'component',
    label: '零部件',
    width: '150'
  },
  {
    prop: 'type',
    label: '类型',
    width: '150'
  },
  {
    prop: 'mode',
    label: '模式',
    width: '150'
  },
  {
    prop: 'description',
    label: '描述',
    width: '150'
  },
  {
    prop: 'status',
    label: '状态',
    width: '150'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '300'
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
