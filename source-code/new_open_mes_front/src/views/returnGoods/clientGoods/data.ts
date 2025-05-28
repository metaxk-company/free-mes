// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  // {
  //   type: 'select',
  //   label: '客户名称',
  //   prop: 'customerName',
  //   placeholder: '请选择客户名称',
  //   options: []
  // },
  {
    type: 'input',
    label: '退货订单编号',
    prop: 'number',
    placeholder: '请输入退货订单编号'
  },
  {
    type: 'scopeTime',
    label: '退货日期',
    prop: 'returnDate',
    placeholder: '请输入退货日期'
  }
])

export const tableColumns: Table.Column[] = [
  {
    prop: 'number',
    label: '编号',
    width: '150'
  },
  // {
  //   prop: 'customerNumber',
  //   label: '客户编号'
  // },
  {
    prop: 'customerName',
    label: '客户名称',
    width: '310'
  },
  {
    prop: 'weight',
    label: '总净重'
  },
  {
    prop: 'totalPrice',
    label: '总金额'
  },
  {
    prop: 'returnDate',
    label: '退货日期',
    width: '160'
  },
  {
    prop: 'createTime',
    label: '创建日期',
    width: '160'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '310'
  }
]
export const returnGoodsColumns: Table.Column[] = [
  {
    slot: 'boxNumber',
    label: '箱号',
    width: '140'
  },
  {
    slot: 'lineType',
    label: '线别',
    width: '140'
  },
  {
    slot: 'model',
    label: '型号',
    width: '140'
  },
  {
    slot: 'spec',
    label: '规格',
    width: '140'
  },
  {
    slot: 'weight',
    label: '总净重',
    width: '180'
  },
  {
    slot: 'price',
    label: '单价',
    width: '180'
  },
  {
    slot: 'totalPrice',
    label: '总价',
    width: '140'
  },
  {
    slot: 'color',
    label: '颜色',
    width: '140'
  },
  // {
  //   slot: 'boxNumber',
  //   label: '箱号',
  //   width: '140'
  // },
  {
    slot: 'axlesNumber',
    label: '轴数',
    width: '140'
  },
  {
    slot: 'batch',
    label: '批次',
    width: '140'
  },
  // {
  //   slot: 'axlesNum',
  //   label: '轴数',
  //   width: '140'
  // },
  // {
  //   slot: 'batchNumber',
  //   label: '批次',
  //   width: '140'
  // },
  {
    slot: 'barCode',
    label: '条码',
    width: '140'
  },
  {
    slot: 'remark',
    label: '备注',
    width: '140'
  }
]
