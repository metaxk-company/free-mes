// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '仓库编码',
    prop: 'warehouseNumber',
    placeholder: '请输入仓库编码'
  },
  {
    type: 'input',
    label: '仓库名称',
    prop: 'wareHouseName',
    placeholder: '请输入仓库名称'
  }
])

export const tableColumns: Table.Column[] = [
  {
    prop: 'warehouseNumber',
    label: '仓库编码'
  },
  {
    prop: 'warehouseName',
    label: '仓库名称'
  },
  {
    prop: 'location',
    label: '位置'
  },
  {
    prop: 'area',
    label: '面积'
  },
  {
    prop: 'charge',
    label: '负责人'
  },
  {
    prop: 'remark',
    label: '备注'
  },
  {
    label: '操作',
    slot: 'operation',
    fixed: 'right',
    width: '160'
  }
]
