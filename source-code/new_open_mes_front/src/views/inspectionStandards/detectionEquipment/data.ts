// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '器具编号',
    prop: 'deviceCode',
    placeholder: '请输入器具编号'
  },
  {
    type: 'input',
    label: '器具名称',
    prop: 'deviceName',
    placeholder: '请输器具名称'
  }
])

export const tableColumns: Table.Column[] = [
  {
    prop: 'deviceCode',
    label: '器具编号'
  },
  {
    prop: 'deviceName',
    label: '器具名称'
  },
  {
    prop: 'agreement',
    label: '数据协议'
  },
  {
    prop: 'workshop',
    label: '所属车间'
  },
  {
    prop: 'department',
    label: '所属部门'
  },
  {
    prop: 'process',
    label: '所属工序'
  },
  {
    prop: 'remark',
    label: '描述'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '230'
  }
]
