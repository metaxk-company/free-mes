// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '半成品编号',
    prop: 'number',
    placeholder: '请输入半成品编号'
    // options: []
  }
  // {
  //   type: 'scopeTime',
  //   label: '退货日期',
  //   prop: 'returnDate',
  //   placeholder: '请输入退货日期'
  // }
])

export const tableColumns: Table.Column[] = [
  {
    prop: 'number',
    label: '半成品编号'
  },
  {
    prop: 'createTime',
    label: '创建日期'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '320'
  }
]

export const returnGoodsColumns: Table.Column[] = [
  {
    slot: 'model',
    label: '型号'
  },
  {
    slot: 'spec',
    label: '规格'
  },
  {
    slot: 'color',
    label: '颜色'
  },
  {
    slot: 'lineType',
    label: '线别'
  },
  {
    slot: 'reelNumber',
    label: '盘号'
  },
  {
    slot: 'quantity',
    label: '数量'
  },
  {
    slot: 'unitOfMeasure',
    label: '单位'
  },
  {
    slot: 'remark',
    label: '备注',
    width: '260'
  }
]
