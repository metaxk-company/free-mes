// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'select',
    label: '类别名称',
    prop: 'category',
    placeholder: '请选择类别名称',
    options: []
  },
  {
    type: 'date',
    label: '创建日期',
    prop: 'createTime',
    placeholder: '请输入创建日期'
  }
])

export const tableColumns: Table.Column[] = [
  {
    prop: 'category',
    label: '类别'
  },
  {
    prop: 'time',
    label: '日期',
    width: '160'
  },
  {
    prop: 'priceTon',
    label: '价格（吨）'
  },
  {
    prop: 'priceKg',
    label: '价格（千克）'
  },
  {
    prop: 'createTime',
    label: '创建时间',
    width: '160'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '160'
  }
]
