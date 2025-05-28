// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'select',
    label: '规格型号',
    prop: 'model',
    placeholder: '请输入规格型号',
    options: []
  },
  {
    type: 'input',
    label: '规格编号',
    prop: 'serial',
    placeholder: '请输入规格编号'
  },
  {
    type: 'input',
    label: '规格名称',
    prop: 'name',
    placeholder: '请输入规格名称'
  }
])

export const tableColumns: Table.Column[] = [
  {
    prop: 'model',
    label: '规格型号'
  },
  {
    prop: 'serial',
    label: '规格编号'
  },
  {
    prop: 'name',
    label: '规格名称'
  },
  {
    prop: 'createTime',
    label: '创建时间'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right'
  }
]
