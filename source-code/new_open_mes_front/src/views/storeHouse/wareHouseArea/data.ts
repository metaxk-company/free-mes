// 客户端搜索条件
export const searchConditions: Search.Column[] = reactive([
  {
    type: 'input',
    label: '库区编号',
    prop: 'areaNumber',
    placeholder: '请输入库区编号'
  },
  {
    type: 'input',
    label: '库区名称',
    prop: 'areaName',
    placeholder: '请输入库区名称'
  }
])

export const tableColumns: Table.Column[] = [
  {
    prop: 'areaNumber',
    label: '库区编码'
  },
  {
    prop: 'areaName',
    label: '库区名称'
  },
  {
    prop: 'warehouseName',
    label: '所属仓库'
  },
  {
    prop: 'location',
    label: '所属库位'
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
