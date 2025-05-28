// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '盘号名称',
    prop: 'number',
    placeholder: '请输入盘号名称'
  }
])

export const tableColumns: Table.Column[] = [
  {
    prop: 'number',
    label: '盘号'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right'
  }
]
