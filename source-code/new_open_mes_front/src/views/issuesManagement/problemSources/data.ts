// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '问题名称',
    prop: 'name',
    placeholder: '请输入问题名称'
  }
])

export const tablePurchaseColumns: Table.Column[] = [
  {
    prop: 'name',
    label: '名称',
    width: '200'
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
