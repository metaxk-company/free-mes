import { WAREHOUSE_LIST } from '@/utils/const'
// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '到货通知单编号',
    prop: 'number',
    placeholder: '请输入到货通知单编号'
  },
  {
    type: 'input',
    label: '采购单编号',
    prop: 'receiptNumber',
    placeholder: '请输入采购单编号'
  },
  {
    type: 'select',
    label: '仓库',
    prop: 'wareHouse',
    placeholder: '请选择仓库',
    options: WAREHOUSE_LIST
  }
])
// 详情搜索
export const searchPurchaseConditions: Search.Column[] = reactive([
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
  },
  {
    type: 'input',
    label: '型号',
    prop: 'model',
    placeholder: '请选择型号'
  },
  {
    type: 'input',
    label: '规格',
    prop: 'spec',
    placeholder: '请输入规格'
  }
])
// 客户端列表
export const tablePurchaseColumns: Table.Column[] = [
  {
    prop: 'number',
    label: '到货通知单编号'
    // width: '280'
  },
  {
    prop: 'receiptNumber',
    label: '采购单编号'
  },
  {
    prop: 'wareHouse',
    label: '仓库'
  },
  {
    prop: 'receiveDate',
    label: '到货日期'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '200'
  }
]
// 详情列表
export const returnGoodsColumns: Table.Column[] = [
  {
    prop: 'recNumber',
    label: '到货通知单编号',
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
    prop: 'kind',
    label: '类别'
  },
  {
    prop: 'unitOfMeasure',
    label: '单位'
  },
  {
    prop: 'quantity',
    label: '到货数量',
    width: '190'
  }
]
