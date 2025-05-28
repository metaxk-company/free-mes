// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '方法名称',
    prop: 'name',
    placeholder: '请输入方法名称'
  },
  { type: 'input', label: '类型', prop: 'type', placeholder: '请输入类型' }
])

export const tablePurchaseColumns: Table.Column[] = [
  {
    prop: 'name',
    label: '名称',
    width: '200'
  },
  {
    prop: 'type',
    label: '类型'
  },
  {
    prop: 'description',
    label: '描述'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '300'
  }
]
