// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '型号编号',
    prop: 'number',
    placeholder: '请输入型号编号'
  },
  {
    type: 'input',
    label: '型号名称',
    prop: 'name',
    placeholder: '请输入型号名称'
  }
])

export const tableColumns: Table.Column[] = [
  {
    prop: 'number',
    label: '型号编号'
  },
  {
    prop: 'name',
    label: '型号名称'
  },

  {
    label: '操作',
    slot: 'operation',
    fixed: 'right'
  }
]
