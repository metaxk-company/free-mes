// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '颜色名称',
    prop: 'name',
    placeholder: '请输入颜色名称'
  }
])

export const tableColumns: Table.Column[] = [
  {
    prop: 'name',
    label: '颜色'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right'
  }
]
