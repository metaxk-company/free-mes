import { STORE_HOUSE_STATE } from '@/utils/const'

// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '入库编号',
    prop: 'inNumber',
    placeholder: '请输入入库编号'
  },

  {
    type: 'input',
    label: '仓库名称',
    prop: 'wareHouse',
    placeholder: '请输入仓库'
  },

  {
    type: 'select',
    label: '状态',
    prop: 'source',
    placeholder: '请选择状态',
    options: STORE_HOUSE_STATE
  }
])
export const searchPurchase: Search.Column[] = [
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
]
// 采购单搜索条件
export const searchPurchaseConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '到货通知单编号',
    prop: 'recNumber',
    placeholder: '请输入到货通知单编号'
  },
  {
    type: 'input',
    label: '产品编号',
    prop: 'itemCode',
    placeholder: '请输入产品编号'
  },
  {
    type: 'input',
    label: '产品名称',
    prop: 'itemName',
    placeholder: '请输入产品名称'
  }
])
export const tablePurchaseColumns: Table.Column[] = [
  {
    prop: 'inspectName',
    label: '采购单号'
  },
  {
    prop: 'inspectName',
    label: '供应商名称'
  },
  {
    prop: 'inspectName',
    label: '状态'
  },
  {
    prop: 'inspectName',
    label: '含税总价(元）'
  },
  {
    prop: 'inspectName',
    label: '创建日期'
  },
  {
    prop: 'inspectName',
    label: '交货日期'
  }
]

export const tableOrderColumns: Table.Column[] = [
  {
    prop: 'receiptNumber',
    label: '采购订单编号',
    width: '180'
  },
  {
    prop: 'recNumber',
    label: '到货通知单编号',
    width: '180'
  },
  {
    prop: 'itemCode',
    label: '产品编号',
    width: '180'
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
    prop: 'purchasePrice',
    label: '采购价'
  },
  {
    prop: 'quantity',
    label: '到货数量'
  }
  // {
  //   prop: 'amount',
  //   label: '入库数量'
  // },
  // {
  //   prop: 'unqualifiedNumber',
  //   label: '不合格数'
  // }
]
export const tableColumns: Table.Column[] = [
  {
    prop: 'inNumber',
    label: '入库编号'
  },

  {
    prop: 'wareHouse',
    label: '仓库'
  },

  {
    prop: 'quantity',
    label: '入库数量'
  },
  {
    prop: 'source',
    label: '来源'
  },

  {
    prop: 'status',
    label: '状态'
  },

  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '280'
  }
]
export const returnGoodsColumns: Table.Column[] = [
  {
    prop: 'receiptNumber',
    label: '采购订单编号',
    width: '150'
  },
  {
    prop: 'recNumber',
    label: '到货通知单编号',
    width: '150'
  },
  {
    prop: 'itemCode',
    label: '产品编号',
    width: '150'
  },
  {
    prop: 'itemName',
    label: '产品名称',
    width: '150'
  },
  {
    prop: 'model',
    label: '产品型号'
  },
  {
    prop: 'spec',
    label: '产品规格'
  },
  {
    prop: 'kind',
    label: '产品类别',
    width: '210'
  },
  {
    prop: 'spec',
    label: '产品规格'
  },
  {
    prop: 'unitOfMeasure',
    label: '单位'
  },
  {
    prop: 'purchasePrice',
    label: '采购价'
  },
  {
    prop: 'quantity',
    label: '到货数量'
  },
  {
    prop: 'amount',
    label: '入库数量'
  },
  {
    prop: 'unqualifiedNumber',
    label: '不合格数'
  },
  {
    slot: 'boxNumber',
    label: '箱号',
    width: '140'
  },
  {
    slot: 'batchNumber',
    label: '批次号',
    width: '140'
  },
  {
    slot: 'productionDate',
    label: '生产日期',
    width: '220'
  },
  {
    slot: 'effectiveDate',
    label: '有效日期',
    width: '220'
  },
  {
    slot: 'remark',
    label: '备注',
    width: '140'
  }
]
