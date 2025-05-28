// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '方法名称',
    prop: 'name',
    placeholder: '请输入方法名称'
  },
  { type: 'input', label: '子主题', prop: 'subTopic', placeholder: '请输入子主题' }
])

export const tablePurchaseColumns: Table.Column[] = [
  {
    prop: 'name',
    label: '名称',
    width: '200'
  },
  {
    prop: 'subTopic',
    label: '子主题'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '300'
  }
]
