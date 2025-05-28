// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '检验编号',
    prop: 'projectCode',
    placeholder: '请输入检验编号'
  },
  {
    type: 'input',
    label: '检验名称',
    prop: 'projectName',
    placeholder: '请输检验名称'
  }
])

export const tableColumns: Table.Column[] = [
  {
    prop: 'projectCode',
    label: '检验编号'
  },
  {
    prop: 'projectName',
    label: '检验名称'
  },
  {
    prop: 'classify',
    label: '分类'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right'
  }
]
