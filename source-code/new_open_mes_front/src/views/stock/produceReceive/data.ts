import { PURCHASE_STATE } from '@/utils/const'

// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '领料编号',
    prop: 'number',
    placeholder: '请输入领料编号'
  },

  {
    type: 'scopeTime',
    label: '领料日期',
    prop: 'pickDate',
    placeholder: '请选择领料日期'
  }
  // {
  //   type: 'select',
  //   label: '类型筛选',
  //   prop: 'status',
  //   placeholder: '请选择类型筛选',
  //   options: PURCHASE_STATE
  // }
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
    width: '380'
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

export const tableColumns: Table.Column[] = [
  {
    prop: 'number',
    label: '领料编号',
    width: '160'
  },
  {
    prop: 'pickDate',
    label: '领料日期'
  },
  {
    prop: 'productType',
    label: '类别'
  },
  // {
  //   prop: 'taxPrice',
  //   label: '含税总价'
  // },
  // {
  //   prop: 'noTaxPrice',
  //   label: '总价'
  // },
  // {
  //   prop: 'status',
  //   label: '类型'
  // },
  {
    prop: 'createTime',
    label: '创建日期',
    width: '160'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '350'
  }
]
export const returnGoodsColumns: Table.Column[] = [
  {
    prop: 'itemCode',
    label: '编号'
  },
  {
    prop: 'itemName',
    label: '名称'
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
  // {
  //   prop: 'purchasePrice',
  //   label: '采购价'
  // },
  {
    slot: 'quantity',
    label: '数量',
    width: '190'
  }
  // {
  //   slot: 'includTax',
  //   label: '含税总价',
  //   width: '120'
  // },
  // {
  //   slot: 'noIncludTax',
  //   label: '不含税总价',
  //   width: '120'
  // }
  // {
  //   slot: 'includTax',
  //   label: '总价',
  //   width: '120'
  // }
]
