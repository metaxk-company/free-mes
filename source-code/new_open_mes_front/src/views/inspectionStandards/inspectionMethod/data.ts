// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '检验编号',
    prop: 'inspectCode',
    placeholder: '请输入检验编号'
  },
  {
    type: 'input',
    label: '检验名称',
    prop: 'inspectName',
    placeholder: '请输检验名称'
  }
])

export const tableColumns: Table.Column[] = [
  {
    prop: 'inspectCode',
    label: '检验编号'
  },
  {
    prop: 'inspectName',
    label: '检验名称'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right'
  }
]
