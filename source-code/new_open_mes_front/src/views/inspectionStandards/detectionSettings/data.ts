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
    prop: 'standValue',
    label: '量化数值'
  },
  {
    prop: 'standUnit',
    label: '量化单位'
  },
  {
    prop: 'inspectDevice',
    label: '检测器具'
  },
  {
    prop: 'remark',
    label: '描述'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '240'
  }
]

export const tableColumnsUtensil: Table.Column[] = [
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
    slot: 'operate',
    label: '操作'
  }
]
