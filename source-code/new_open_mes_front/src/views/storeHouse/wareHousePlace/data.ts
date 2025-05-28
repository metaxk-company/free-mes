// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '库位编码',
    prop: 'locationNumber',
    placeholder: '请输入仓库编码'
  },
  {
    type: 'input',
    label: '库位名称',
    prop: 'locationName',
    placeholder: '请输入仓库名称'
  }
])

export const tableColumns: Table.Column[] = [
  {
    prop: 'locationNumber',
    label: '库位编号'
  },
  {
    prop: 'locationName',
    label: '库位名称'
  },
  {
    prop: 'warehouseName',
    label: '所属仓库'
  },
  {
    prop: 'areaName',
    label: '所属库区'
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
