import { PURCHASE_SOURCE } from '@/utils/const'

// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '入库编号',
    prop: 'inNumber',
    placeholder: '请输入入库编号'
  },
  // {
  //   type: 'input',
  //   label: '采购单号',
  //   prop: 'receiptNumber',
  //   placeholder: '请输入采购单号'
  // },
  {
    type: 'input',
    label: '仓库名称',
    prop: 'wareHouse',
    placeholder: '请输入仓库'
  },
  // {
  //   type: 'select',
  //   label: '采购来源',
  //   prop: 'source',
  //   placeholder: '请选择采购来源',
  //   options: PURCHASE_SOURCE
  // },
  {
    type: 'scopeTime',
    label: '交货日期',
    prop: 'deliveryDate',
    placeholder: '请选择交货日期'
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
    label: '产品编码',
    prop: 'number',
    placeholder: '请输入产品编码'
  },
  {
    type: 'input',
    label: '产品名称',
    prop: 'name',
    placeholder: '请输入产品名称'
  },
  {
    type: 'input',
    label: '规格',
    prop: 'spec',
    placeholder: '请输入规格'
  },
  {
    type: 'input',
    label: '型号',
    prop: 'model',
    placeholder: '请输入型号'
  },
  {
    type: 'select',
    label: '供应商',
    prop: 'vendor',
    placeholder: '请输入供应商',
    options: []
  },
  {
    type: 'input',
    label: '采购单号',
    prop: 'receiptNumber',
    placeholder: '请输入采购单号'
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
    prop: 'itemCode',
    label: '产品编号',
    width: '180'
  },
  {
    prop: 'receiptNumber',
    label: '采购单号',
    width: '180'
  },
  {
    prop: 'itemName',
    label: '产品名称',
    width: '180'
  },
  {
    prop: 'kind',
    label: '产品类别'
  },
  {
    prop: 'model',
    label: '产品型号'
  },
  {
    prop: 'vendor',
    label: '供应商',
    width: '210'
  },
  {
    prop: 'spec',
    label: '规格'
  },
  {
    prop: 'quantity',
    label: '数量'
  },
  {
    prop: 'purchasePrice',
    label: '单价'
  },
  {
    prop: 'unitOfMeasure',
    label: '单位'
  }
]
export const tableColumns: Table.Column[] = [
  {
    prop: 'inNumber',
    label: '入库编号',
    width: '150'
  },
  {
    prop: 'vendor',
    label: '供应商',
    width: '200'
  },
  {
    prop: 'wareHouse',
    label: '仓库',
    width: '150'
  },
  {
    prop: 'deliveryDate',
    label: '交货日期',
    width: '170'
  },
  {
    prop: 'quantity',
    label: '数量'
  },
  {
    prop: 'source',
    label: '来源'
  },
  {
    prop: 'updateTime',
    label: '更新时间',
    width: '170'
  },
  {
    prop: 'status',
    label: '状态'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '260'
  }
]
export const returnGoodsColumns: Table.Column[] = [
  {
    prop: 'itemCode',
    label: '产品编号',
    width: '160'
  },
  {
    prop: 'receiptNumber',
    label: '采购单号',
    width: '160'
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
    prop: 'spec',
    label: '规格'
  },
  {
    prop: 'vendor',
    label: '供应商',
    width: '200'
  },
  {
    prop: 'quantity',
    label: '入库数量'
  },
  {
    prop: 'purchasePrice',
    label: '单价'
  },
  {
    prop: 'totalPrice',
    label: '总价',
    width: '140'
  },
  {
    prop: 'unitOfMeasure',
    label: '单位',
    width: '140'
  },
  {
    prop: 'kind',
    label: '类别',
    width: '140'
  },
  {
    slot: 'barcode',
    label: '条码',
    width: '140'
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
