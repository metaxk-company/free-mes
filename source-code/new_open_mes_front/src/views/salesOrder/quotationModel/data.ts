// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '型号名称',
    prop: 'model',
    placeholder: '请输入型号名称'
  },
  {
    type: 'scopeTime',
    label: '创建日期',
    prop: 'createTimes',
    placeholder: '请输入创建日期'
  }
])

export const tableColumns: Table.Column[] = [
  {
    prop: 'model',
    label: '型号名称',
    width: '400'
  },
  {
    prop: 'createTime',
    label: '创建日期'
  },
  {
    prop: 'updateTime',
    label: '更新日期'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right'
  }
]
